package com.example.dbms.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbms.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView;
    TextView basic,next,prev,text;
    EditText num;
    Button choose,upload;
    String Email1,add,fname,lname,dob1,ph,bt;
    final int IMAGE_REQUEST=71;
    Uri uri;
    ProgressDialog progress1;
    String userID;
    FirebaseAuth niAuth;
    StorageReference storageReference;
    FirebaseFirestore fstore;
    TextView text4, text5, text6, text7, text8, text9, text10;
    EditText First, Last, Address, Phone, Email,BT, dob;
    TextView sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image2);
        try {
            imageView = findViewById(R.id.imageView3);
            basic = findViewById(R.id.textView12);
            //  next=findViewById(R.id.textView2);
            // num=findViewById(R.id.editText);
            //  prev=findViewById(R.id.textView3);
            // text=findViewById(R.id.textView);
            choose = findViewById(R.id.button2);
            upload = findViewById(R.id.button3);
            niAuth = FirebaseAuth.getInstance();

            storageReference = FirebaseStorage.getInstance().getReference("ImageFolder");
            fstore = FirebaseFirestore.getInstance();


            text4 = (TextView) findViewById(R.id.textView13);
            text5 = (TextView) findViewById(R.id.textView14);
            text6 = (TextView) findViewById(R.id.textView15);
            text7 = (TextView) findViewById(R.id.textView16);
            text8 = (TextView) findViewById(R.id.textView17);
            text9 = (TextView) findViewById(R.id.textView18);

            First = (EditText) findViewById(R.id.editText4);

            Email = (EditText) findViewById(R.id.editText5);
            BT = (EditText) findViewById(R.id.editText7);
            Address = (EditText) findViewById(R.id.editText9);
            Phone = (EditText) findViewById(R.id.editText6);
            dob = (EditText) findViewById(R.id.editText8);
            sub = (TextView) findViewById(R.id.save);
            //  basic=(TextView)findViewById(R.id.basic);

          /*  choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();

                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage();

                }
            }); */


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               // progress1.dismiss();
                startActivity(new Intent(ImageActivity.this, HomeActivity.class));
            }


        });
    }
    public void selectImage(View view){
        try{
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,IMAGE_REQUEST);

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        //startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode ==IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData() != null){
                uri=data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    } // !num.getText().toString().isEmpty() &&   num.getText().toString()

    public void uploadImage(View view){
        try{

            if( uri!=null){
                String nameOfImage=Email.getText().toString()+"."+getExtension(uri);
                Email1 = Email.getText().toString();
                bt = BT.getText().toString();
                fname = First.getText().toString();

                dob1 = dob.getText().toString();
                ph = Phone.getText().toString();
                add = Address.getText().toString();

                final StorageReference imageRef=storageReference.child(nameOfImage);

                UploadTask uploadTask=imageRef.putFile(uri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                       if(!task.isSuccessful()){
                           throw task.getException();
                       }
                       return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){




                                  //  DocumentReference documentReference=fstore.collection("users").document(userID);
                            userID=niAuth.getCurrentUser().getUid();
                            Map<String,String>map=new HashMap<>();

                            map.put("fname", fname);

                            map.put("email", Email1);
                            map.put("bloodType", bt);
                            map.put("contactNo", ph);
                            map.put("address", add);
                            map.put("date", dob1);
                            map.put("url",task.getResult().toString());



                            fstore.collection("users").document(niAuth.getUid())
                                    .set(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ImageActivity.this,"Image successfully uploaded",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ImageActivity.this,"Error uploading image",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else if(!task.isSuccessful()){
                            Toast.makeText(ImageActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Toast.makeText(this,"Please enter your phone number",Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private String getExtension(Uri uri) {
        try {
            ContentResolver cr = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }





}
