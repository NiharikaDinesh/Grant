package com.example.dbms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText Name;
    EditText Password;
    TextView SignUp;
    Button LogIn;
    FirebaseAuth niAuth;
    String Email, Pass;
    ProgressDialog progress;
    private FirebaseAuth.AuthStateListener niAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        niAuth = FirebaseAuth.getInstance();
        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        SignUp = (TextView) findViewById(R.id.SignUp);
        LogIn = (Button) findViewById(R.id.logIn);
        progress = new ProgressDialog(this);

        niAuthStateListener=new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser niFireBaseUser=niAuth.getCurrentUser();
                if(niFireBaseUser!=null){
                    Toast.makeText(LoginActivity.this,"You are Logged In",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }

            }
        };

        LogIn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(LoginActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(Email.isEmpty() && Pass.isEmpty())) {
                    niAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progress.dismiss();
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "LogIn Error,Please Try Again", Toast.LENGTH_SHORT).show();
                            } else{
                                Intent inToHome;
                                inToHome = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(inToHome);
                                finish();
                            }

                            // startActivity(new Intent(LoginActivity.this, Register.class));

                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this,"Error Occured!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoSignUp=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intoSignUp);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        niAuth.addAuthStateListener(niAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LoginActivity.this.finish();
    }
}
