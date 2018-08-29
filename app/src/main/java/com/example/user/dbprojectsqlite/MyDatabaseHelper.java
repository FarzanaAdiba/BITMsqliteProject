package com.example.user.dbprojectsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Gymnesium";
    private static final String TRAINEE_TABLE_NAME="Trainee";
    private static final int VERSION_NUMBER=8;

     static final String TRAINEE_ID="traineeId";
     static final String TRAINEE_NAME="traineeName";
     static final String TRAINEE_AGE="traineeAge";
     static final String TRAINEE_PHONE="traineeGender";
    // static final String TRAINEE_IMAGE="traineeImage";

    private static final String CREAE_TABLE_TRAINEE="CREATE TABLE "
            +TRAINEE_TABLE_NAME+"("+TRAINEE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            ""+TRAINEE_NAME+" VARCHAR(255),"+TRAINEE_AGE+" INTEGER,"+TRAINEE_PHONE+" TEXT)";

    private static final String DROP_TABLE_TRAINEE="DROP TABLE IF EXISTS "+TRAINEE_TABLE_NAME;

    //for Login
    private static final String USER_TABLE_NAME="user_details";

    static final String USER_ID="id";
    static final String USER_NAME="name";
    static final String USER_EMAIL="email";
    static final String USER_USERNAME="username";
    static final String USER_PASSWORD="password";

    private static final String CREATE_TABLE_USER="CREATE TABLE "+USER_TABLE_NAME+"("+USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_NAME+" VARCHAR(255) NOT NULL, "+USER_EMAIL+" TEXT NOT NULL, "+USER_USERNAME+" TEXT NOT NULL, "+USER_PASSWORD+" TEXT NOT NULL)";

    private static final String DROP_TABLE_USER="DROP TABLE IF EXISTS "+USER_TABLE_NAME;

    private Context context;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{

            Toast.makeText(context,"onCreate is called",Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(CREAE_TABLE_TRAINEE);
            sqLiteDatabase.execSQL(CREATE_TABLE_USER);

        }catch (Exception e){
            Toast.makeText(context,"Exception :"+e,Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {
            Toast.makeText(context,"onUpgrade is called",Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(DROP_TABLE_TRAINEE);
            sqLiteDatabase.execSQL(DROP_TABLE_USER);
            onCreate(sqLiteDatabase);
        }catch (Exception e){
            Toast.makeText(context,"Exception :"+e,Toast.LENGTH_SHORT).show();

        }

    }


    public long insertData(String name,String age,String phone){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        //contentValues.put(TRAINEE_IMAGE,traineeImg);
        contentValues.put(TRAINEE_NAME,name);
        contentValues.put(TRAINEE_AGE,age);
        contentValues.put(TRAINEE_PHONE,phone);
        long rowId=sqLiteDatabase.insert(TRAINEE_TABLE_NAME,null,contentValues);
        return rowId;
    }

    public Cursor displayAllData(){
       SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TRAINEE_TABLE_NAME,null);
       return cursor;
    }

    public Boolean updateData(String id,String name,String age,String phone){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TRAINEE_ID,id);
        contentValues.put(TRAINEE_NAME,name);
        contentValues.put(TRAINEE_AGE,age);
        contentValues.put(TRAINEE_PHONE,phone);
        sqLiteDatabase.update(TRAINEE_TABLE_NAME,contentValues,TRAINEE_ID+" = ?",new String[]{id});
        return true;
    }

    public int deleteData(String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(TRAINEE_TABLE_NAME,TRAINEE_ID+" = ?",new String[]{id});
    }
    public long insertForRegister(UserDetails userDetails){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        //contentValues.put(TRAINEE_IMAGE,traineeImg);
        contentValues.put(USER_NAME,userDetails.getName());
        contentValues.put(USER_EMAIL,userDetails.getPassword());
        contentValues.put(USER_USERNAME,userDetails.getUsername());
        contentValues.put(USER_PASSWORD,userDetails.getPassword());
        long rowId=sqLiteDatabase.insert(USER_TABLE_NAME,null,contentValues);
        return rowId;
    }

    public Boolean findPasswordUser(String uname,String pass){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+USER_TABLE_NAME,null);
        Boolean result=false;

        if(cursor.getCount()==0){
            Toast.makeText(context,"No data is found!!",Toast.LENGTH_SHORT).show();
        }while (cursor.moveToNext()){
            String username=cursor.getString(3);
            String password=cursor.getString(4);

            if(username.equals(uname)&&password.equals(pass)){
                result=true;
                break;
            }
        }
        return result;

    }
}
