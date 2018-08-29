package com.example.user.dbprojectsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    UserDetails userDetails;
    MyDatabaseHelper myDatabaseHelper;

    private EditText nameET,emailET,usernameET,passET;
    private Button regPgBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameET=findViewById(R.id.nameRegText);
        emailET=findViewById(R.id.emailRegText);
        usernameET=findViewById(R.id.usernameRegText);
        passET=findViewById(R.id.passRegText);
        regPgBT=findViewById(R.id.registerpgBtn);

        userDetails=new UserDetails();
        myDatabaseHelper=new MyDatabaseHelper(this);

        regPgBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=nameET.getText().toString();
                String email=emailET.getText().toString();
                String username=usernameET.getText().toString();
                String password=passET.getText().toString();

                userDetails.setName(name);
                userDetails.setEmail(email);
                userDetails.setUsername(username);
                userDetails.setPassword(password);

                long rowId=myDatabaseHelper.insertForRegister(userDetails);
                //Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                //startActivity(intent);

                if(rowId>0){
                    Toast.makeText(getApplicationContext(),"successfully registered",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Registration failed!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
