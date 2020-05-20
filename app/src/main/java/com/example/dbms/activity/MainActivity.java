package com.example.dbms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText Name;
    EditText Password;
    TextView SignIn;
    Button Register;
    FirebaseAuth niAuth;
    String Email, Pass;
    //  private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        niAuth = FirebaseAuth.getInstance();
        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        SignIn = (TextView) findViewById(R.id.signin);
        // Login = (Button) findViewById(R.id.btnLogin);
        Register = (Button) findViewById(R.id.register);

        //  Email = Name.toString();
        // Pass = Password.toString();
        // progress = new ProgressDialog(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = Name.getText().toString();
                Pass = Password.getText().toString();
                if (Email.isEmpty()) {
                    Name.setError("Please enter email id");
                    Name.requestFocus();
                } else if (Pass.isEmpty()) {
                    Password.setError("Please enter the password");
                    Password.requestFocus();
                } else if (Email.isEmpty() && Pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(Email.isEmpty() && Pass.isEmpty())) {
                    niAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "SignUp Unsuccessful,Please Try Again", Toast.LENGTH_SHORT).show();
                            } else
                                startActivity(new Intent (getApplicationContext(), ImageActivity.class));
                            finish();



                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this,"Error Occured!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.this.finish();
    }
}