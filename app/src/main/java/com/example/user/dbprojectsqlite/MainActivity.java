package com.example.user.dbprojectsqlite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jackandphantom.blurimage.BlurImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final int REQUEST_CODE_GALLERY=999;

    MyDatabaseHelper myDatabaseHelper;

    private EditText nameET,ageET,genderET,idET;
    private Button saveBT,displayBT,updateBT,deleteBT;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper=new MyDatabaseHelper(this);
       SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();

        //blur image
       imageView=findViewById(R.id.img_member);
        BlurImage.with(getApplicationContext()).load(R.drawable.trainee).intensity(150).Async(true).into(imageView);

       nameET=findViewById(R.id.nameET);
       ageET=findViewById(R.id.ageET);
       genderET=findViewById(R.id.genderET);
       idET=findViewById(R.id.idET);

       saveBT=findViewById(R.id.saveBtn);
       displayBT=findViewById(R.id.displayBtn);
       //updateBT=findViewById(R.id.updateBtn);
       //deleteBT=findViewById(R.id.deleteBtn);

       imageView=findViewById(R.id.add_img);

       saveBT.setOnClickListener(this);
       displayBT.setOnClickListener(this);


       //imageView.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View view) {

        //String id=idET.getText().toString();

        String name=nameET.getText().toString();
        String age=ageET.getText().toString();
        String gender=genderET.getText().toString();
        //byte[] newTrineeImg=ImageViewToByte(imageView);
      /*  if(view.getId()==R.id.add_img){

            ActivityCompat.requestPermissions
                    (MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);

        }*/

        if(view.getId()==R.id.saveBtn){

            if(name.equals(" ") && age.equals(" ") && gender.equals(" ")){
                Toast.makeText(getApplicationContext(),"Each item must be filled!!",Toast.LENGTH_SHORT).show();
            }else{
                long rowId=myDatabaseHelper.insertData(name,age,gender);
                if(rowId > -1){
                    Toast.makeText(getApplicationContext(),"successfully inserted",Toast.LENGTH_SHORT).show();
                    nameET.setText(" ");
                    ageET.setText(" ");
                    genderET.setText(" ");
                    //imageView.setImageResource(R.drawable.ic_launcher_background);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Not inserted!!",Toast.LENGTH_SHORT).show();
                }

            }

        }

        if(view.getId()==R.id.displayBtn){

            Intent intent=new Intent(MainActivity.this,TraineeListActivity.class);
            startActivity(intent);

        }



    }

   /* private byte[] ImageViewToByte(ImageView imageView) {
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return byteArray;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Uri uri=data.getData();
        try{
            InputStream inputStream=getContentResolver().openInputStream(uri);
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);

        }catch (FileNotFoundException e){
            e.printStackTrace();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Intent imageIntent=new Intent(Intent.ACTION_PICK);
                imageIntent.setType("image/*");
                startActivityForResult(imageIntent,REQUEST_CODE_GALLERY);

            }else {
                Toast.makeText(getApplicationContext(),
                        "You don't have permission to access files!!!",Toast.LENGTH_SHORT).show();

            }
            return;
        }*/

    }

