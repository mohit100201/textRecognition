package com.example.textrecognition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
private ImageView eraseIcon,cameraIcon,copyIcon;
private TextView displayText;
private TextRecognizer textRecognizer;
Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finding id's
        eraseIcon=findViewById(R.id.erase_icon);
        cameraIcon=findViewById(R.id.camera_icon);
        copyIcon=findViewById(R.id.paste_icon);
        displayText=findViewById(R.id.text_view_display);

        textRecognizer= TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        copyIcon.setOnClickListener(v -> {
            String text=displayText.getText().toString();
            String t="Extract text will display here";
            if(text.equals(t)){
                Toast.makeText(this, "There is no text to copy", Toast.LENGTH_SHORT).show();
            }
            else{
                ClipboardManager clipboardManager=(ClipboardManager) getSystemService(MainActivity.this.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("Data",displayText.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(this, "Text copy to ClipBoard", Toast.LENGTH_SHORT).show();
            }
        });

        eraseIcon.setOnClickListener(v->{
            if(displayText.getText().toString().equals("Extract text will display here")){
                Toast.makeText(this, "There is no text to clear", Toast.LENGTH_SHORT).show();
            }
            else{
                displayText.setText("Extract text will display here");
            }
        });


        cameraIcon.setOnClickListener(v -> {

            ImagePicker.with(MainActivity.this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){
            if(data!=null){
                imageUri=data.getData();
                Toast.makeText(this, "image selected", Toast.LENGTH_SHORT).show();
                recogniseText();

            }
        }
        else{
            Toast.makeText(this, "image not selected", Toast.LENGTH_SHORT).show();
            
        }

    }

    private void recogniseText() {
        if(imageUri!=null){
            try{
                InputImage inputImage=InputImage.fromFilePath(MainActivity.this,imageUri);
               Task<Text> result=textRecognizer.process(inputImage)
                       .addOnSuccessListener(new OnSuccessListener<Text>() {
                           @Override
                           public void onSuccess(Text text) {
                               String recognizeText=text.getText();
                               displayText.setText(recognizeText);

                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                           }
                       });

            }
            catch(IOException e){
                e.printStackTrace();

            }


        }
        else{

        }


    }
}

