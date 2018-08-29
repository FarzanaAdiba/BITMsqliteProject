package com.example.user.dbprojectsqlite;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TraineeListActivity extends AppCompatActivity {
    private ListView TraineeListView;
    private MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_list);

        TraineeListView=findViewById(R.id.traineeList);
        myDatabaseHelper=new MyDatabaseHelper(this);
        loadData();

    }

    public void loadData(){

        ArrayList<String>traineeListData=new ArrayList<>();
        Cursor cursor=myDatabaseHelper.displayAllData();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"No data is available",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                traineeListData.add("ID :"+cursor.getString(0)+" \n "+"Name :"+cursor.getString(1)+" \n "+"Age :"+cursor.getString(2)+" \n "+"Phone :"+cursor.getString(3));
            }
        }
        final ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.trainee_item,R.id.traineeTextView,traineeListData);
        TraineeListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        TraineeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                //String SelectedItem=adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(TraineeListActivity.this,"Selected item: "+SelectedItem,Toast.LENGTH_SHORT).show();
                CharSequence[] items={"update","delete"};
                AlertDialog.Builder dialog=new AlertDialog.Builder(TraineeListActivity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            Cursor c=myDatabaseHelper.displayAllData();
                            ArrayList<Integer>arrId=new ArrayList<>();
                            while ((c.moveToNext())){
                                arrId.add(c.getInt(0));
                            }
                            showDialogUpdate(TraineeListActivity.this,arrId.get(position));
                            adapter.notifyDataSetChanged();
                        }
                        if(i==1){
                            Cursor c=myDatabaseHelper.displayAllData();
                            ArrayList<Integer>arrId=new ArrayList<Integer>();
                            while ((c.moveToNext())){
                                arrId.add(c.getInt(0));
                            }
                            showDialogDelete(arrId.get(position));
                            adapter.notifyDataSetChanged();

                        }
                    }
                });
                dialog.show();
            }
        });

    }

    private void showDialogDelete(final int idRecord) {
        final AlertDialog.Builder dialogDelete=new AlertDialog.Builder(TraineeListActivity.this);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure to delete?");
       // final EditText idText=findViewById(R.id.idET);
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    myDatabaseHelper.deleteData(String.valueOf(idRecord));
                    Toast.makeText(TraineeListActivity.this,"Deleted data",Toast.LENGTH_SHORT).show();



                }catch (Exception e){
                    Log.e("error",e.getMessage());

                }

            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
       // SQLiteDatabase sqLiteDatabase;
       // myDatabaseHelper.onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1);
    }

    private void showDialogUpdate(Activity activity, final int position){
        final Dialog dialog=new Dialog(activity);
        dialog.setContentView(R.layout.trainee_update);
        dialog.setTitle("Update");
        final EditText idText=dialog.findViewById(R.id.idET);

        final EditText nameText=dialog.findViewById(R.id.nameET);
        final EditText ageText=dialog.findViewById(R.id.ageET);
        final EditText genText=dialog.findViewById(R.id.genderET);
        Button updateBT=dialog.findViewById(R.id.updateBtn);

        Cursor c=myDatabaseHelper.displayAllData();
        ArrayList<Integer>arrId=new ArrayList<>();
        while ((c.moveToNext())){
            String id=c.getString(0);
            String name=c.getString(1);
            nameText.setText(name);
            String age=c.getString(2);
            ageText.setText(age);
            String gender=c.getString(3);
            genText.setText(gender);
        }


        int width=(int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height=(int)(activity.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    myDatabaseHelper.updateData(
                            idText.getText().toString().trim(),
                            nameText.getText().toString().trim(),
                            ageText.getText().toString().trim(),
                            genText.getText().toString().trim()
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Update Successfully",Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Log.e("Update error",e.getMessage());
                }
                myDatabaseHelper.updateData(MyDatabaseHelper.TRAINEE_ID,MyDatabaseHelper.TRAINEE_NAME,MyDatabaseHelper.TRAINEE_AGE,MyDatabaseHelper.TRAINEE_PHONE);


            }
        });
    }

   /* private void updateTraineeList() {
        Cursor cursor=myDatabaseHelper.displayAllData();
        TraineeListView.clear();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String age=cursor.getString(2);
            String phone=cursor.getString(3);

        }
    }*/

}
