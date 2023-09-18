package com.example.textrecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private ImageView eraseIcon,cameraIcon,copyIcon;
private TextView displayText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finding id's
        eraseIcon=findViewById(R.id.erase_icon);
        cameraIcon=findViewById(R.id.camera_icon);
        copyIcon=findViewById(R.id.paste_icon);
        displayText=findViewById(R.id.text_view_display);


        cameraIcon.setOnClickListener(v -> {




        });






    }
}