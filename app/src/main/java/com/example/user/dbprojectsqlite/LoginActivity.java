package com.example.user.dbprojectsqlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText usernameLogET,passlogET;
    private Button loginBtn,registerBtn;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameLogET=findViewById(R.id.usernameLoginText);
        passlogET=findViewById(R.id.passLoginText);
        loginBtn=findViewById(R.id.loginBT);
        registerBtn=findViewById(R.id.registerBT);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        myDatabaseHelper=new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
    }


    @Override
    public void onClick(View view) {

        String username=usernameLogET.getText().toString();
        String password=passlogET.getText().toString();

        if(view.getId()==R.id.loginBT){

            Boolean result=myDatabaseHelper.findPasswordUser(username,password);
            if(username.equals("")&&password.equals("")){
                Toast.makeText(getApplicationContext(),"Each item must be filled!!",Toast.LENGTH_SHORT).show();
            }
            else if(result==true){
                Intent intent=new Intent(this,MenuActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"Username and password didn't match!!",Toast.LENGTH_SHORT).show();

            }

        }
        if(view.getId()==R.id.registerBT){
            Intent regIntent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(regIntent);
        }
    }
}
