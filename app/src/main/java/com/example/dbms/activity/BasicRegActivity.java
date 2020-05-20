package com.example.dbms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dbms.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class BasicRegActivity extends AppCompatActivity {

    TextView text4, text5, text6, text7, text8, text9, text10,basic;
    EditText First, Last, Address, Phone, Email,BT, dob;
    Button sub;
    FirebaseAuth niAuth;
    String Email1,add,fname,lname,dob1,ph,bt;
    ProgressDialog progress;
    FirebaseFirestore fstore;
    String userID;
    ProgressDialog progress1;
  DatabaseReference mDatabase;
   StorageReference storageReference;
    ProgressDialog progressDialog;
    private Uri filePath;

    Uri uri;
    StorageReference niStorageRef;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_reg);

        text4 = (TextView)findViewById(R.id.textView4);
        text5 = (TextView)findViewById(R.id.textView5);
        text6 = (TextView)findViewById(R.id.textView6);
        text7 = (TextView)findViewById(R.id.textView7);
        text8 = (TextView)findViewById(R.id.textView8);
        text9 = (TextView)findViewById(R.id.textView9);
        text10 = (TextView)findViewById(R.id.textView10);
        First = (EditText)findViewById(R.id.first);
        Last = (EditText)findViewById(R.id.last);
        Email = (EditText)findViewById(R.id.email1);
        BT = (EditText)findViewById(R.id.pass1);
        Address = (EditText)findViewById(R.id.address);
        Phone = (EditText)findViewById(R.id.phone);
        dob = (EditText)findViewById(R.id.date);
        sub = (Button)findViewById(R.id.submit);
        basic=(TextView)findViewById(R.id.basic);
        niAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        progress1 = new ProgressDialog(this);

        storage=FirebaseStorage.getInstance();

        niStorageRef = storage.getInstance().getReference("Images");

     //   storageReference = FirebaseStorage.getInstance().getReference();
     //   mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);


    /*   if(niAuth.getCurrentUser()!=null){
           startActivity(new Intent(getApplicationContext(),HomeActivity.class));
           finish();
       } */

       sub.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Email1 = Email.getText().toString();
               bt = BT.getText().toString();
               fname = First.getText().toString();
               lname = Last.getText().toString();
               dob1 = dob.getText().toString();
               ph = Phone.getText().toString();
               add = Address.getText().toString();
               if (Email1.isEmpty()) {
                   Email.setError("Please enter email id");
                   Email.requestFocus();
               } else if (bt.isEmpty()) {
                   BT.setError("Please enter the Blood Type");
                   BT.requestFocus();
               } else if (dob1.isEmpty()) {
                   dob.setError("Please enter your Date of Birth");
                   dob.requestFocus();
               } else if (add.isEmpty()) {
                   Address.setError("Please enter your Address");
                   Address.requestFocus();
               } else if (ph.isEmpty()) {
                   Phone.setError("Please enter your phone number");
                   Phone.requestFocus();
               } else if (fname.isEmpty()) {
                   First.setError("Please enter your first name");
                   First.requestFocus();
               } else if (lname.isEmpty()) {
                   Last.setError("Please enter your last name");
                   Last.requestFocus();
               }


               userID=niAuth.getCurrentUser().getUid();
               DocumentReference documentReference=fstore.collection("users").document(userID);
               Map<String,Object>user=new HashMap<>();
               user.put("fname",fname);
               user.put("lname",lname);
               user.put("email",Email1);
               user.put("bloodType",bt);
               user.put("contactNo",ph);
               user.put("address",add);
               user.put("date",dob1);

               documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Log.d("TAG","User Profile Is Created For"+userID);
                   }
               });
               progress1.dismiss();
            //   startActivity(new Intent(BasicRegActivity.this, HomeActivity.class));
           }





             /*  niAuth.createUserWithEmailAndPassword(Email1, fname).addOnCompleteListener(BasicRegActivity.this, new OnCompleteListener<AuthResult>() {

                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           Toast.makeText(BasicRegActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                          userID=niAuth.getCurrentUser().getUid();
                           DocumentReference documentReference=fstore.collection("users").document(userID);
                           Map<String,Object>user=new HashMap<>();
                           user.put("fname",fname);
                           user.put("lname",lname);
                           user.put("email",Email1);
                           user.put("bloodType",bt);
                           user.put("contactNo",ph);
                           user.put("address",add);
                           user.put("date",dob1);
                           documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Log.d("TAG","User Profile Is Created For"+userID);
                               }
                           });
progress1.dismiss();
                           startActivity(new Intent(BasicRegActivity.this, HomeActivity.class));
                       }
                       else {
                           Toast.makeText(BasicRegActivity.this,"Error Occured!",Toast.LENGTH_SHORT).show();
                       }


                   }
               }); */
           });
   /*   StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

        sRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //dismissing the progress dialog
                        progressDialog.dismiss();

                        //displaying success toast
                        Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                        //creating the upload object to store uploaded image details
                  //      ModelUser modelUser = new Upload(First.getText().toString().trim(), taskSnapshot.().toString());

                        //adding an upload to firebase database
                        String uploadId = mDatabase.push().getKey();
                        mDatabase.child(uploadId).setValue(sub);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //displaying the upload progress
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                }); */
    









    } protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }




}
