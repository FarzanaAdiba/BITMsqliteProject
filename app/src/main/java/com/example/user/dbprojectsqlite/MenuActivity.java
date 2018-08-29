package com.example.user.dbprojectsqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jackandphantom.blurimage.BlurImage;

public class MenuActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        imageView=findViewById(R.id.img_back);

        BlurImage.with(getApplicationContext()).load(R.drawable.menu_gym).intensity(150).Async(true).into(imageView);
    }

    @Override
    public void onBackPressed() {

    }

    public void memberInfo(View view) {
        Intent intent=new Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
