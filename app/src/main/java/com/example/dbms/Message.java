package com.example.dbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Message extends AppCompatActivity {

    EditText editText,editText1;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        editText=(EditText)findViewById(R.id.editText2);
        editText1=(EditText)findViewById(R.id.editText3);
        button=(Button)findViewById(R.id.button);


    }

    public void send_btn(View view){
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck== PackageManager.PERMISSION_GRANTED){

            myMessage();
        }

        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }

    private void myMessage() {

        String num = editText.getText().toString().trim();
        String text = editText1.getText().toString().trim();


        if (!editText.getText().toString().equals("") || !editText1.getText().toString().equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(num, null, text, null, null);

            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();

        }
        else {

            Toast.makeText(this, "Error sending a message", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){

            case 0:

                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    myMessage();
                }

                else {

                    Toast.makeText(this, "You dont have the required permission", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
