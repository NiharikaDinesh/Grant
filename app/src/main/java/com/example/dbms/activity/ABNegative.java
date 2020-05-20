package com.example.dbms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.dbms.models.ModelUser;
import com.example.dbms.R;
import com.example.dbms.models.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ABNegative extends AppCompatActivity {

    FirebaseAuth niAuth;
    //  private TextView name1, bt, contact;
    private FirebaseFirestore db;

    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference docRef;

    //progress dialog
    private ProgressDialog progressDialog;
    private DocumentReference documentReference;

    //list to hold all the uploaded images
    private List<ModelUser> uploads;
    private List<Upload>uploadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apositive2);


        recyclerView = (RecyclerView) findViewById(R.id.apos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        //  name1 = findViewById(R.id.name);
        //  bt = findViewById(R.id.btype);
        // contact = findViewById(R.id.phone);
        niAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();
        uploadList=new ArrayList<>();


        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        // getAllUsers();

        load();
       // downloadImage();

    }
    public void load() {

        db.collection("users")
                .whereEqualTo("bloodType", "AB-ve")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelUser modelUser=document.toObject(ModelUser.class);
                                uploads.add(modelUser);
                                Upload upload=document.toObject(Upload.class);
                                uploadList.add(upload);

                                //  name1.setText(document.get("fname").toString().toUpperCase());
                                //  bt.setText(document.get("bloodType").toString());
                                //  contact.setText(document.get("contactNo").toString());
                                adapter = new MyAdapter(getApplicationContext(), uploads,uploadList);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }
                });



    }

 /* public void downloadImage(){
        try{

            documentReference=db.collection("links").document(niAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                   // String linkOfImage=documentSnapshot.getString("url");
                    ModelUser modelUser=documentSnapshot.toObject(ModelUser.class);
                    uploads.add(modelUser);

                    //  name1.setText(document.get("fname").toString().toUpperCase());
                    //  bt.setText(document.get("bloodType").toString());
                    //  contact.setText(document.get("contactNo").toString());
                    adapter = new MyAdapter(getApplicationContext(), uploads);
                    recyclerView.setAdapter(adapter);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ABNegative.this,"Fails to get image",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(ABNegative.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    } */



}
