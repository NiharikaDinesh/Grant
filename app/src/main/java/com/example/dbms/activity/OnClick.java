package com.example.dbms.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dbms.Message;
import com.example.dbms.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnClick extends AppCompatActivity {

    private TextView name, date, add, bt, contact, em;
    private ImageView imgblood1, imgem1, imgpho1, imgloc1, imageView,imageView2;
    private FirebaseAuth niAuth;
    private FirebaseFirestore db;

    //  private FirebaseFirestore user;
    private CircleImageView imgpro1;
    Button upload;
    private static final int PICK_IMAGE = 1;
    Uri uri;
    StorageReference niStorageRef;
    FirebaseStorage storage;
    private StorageTask uploadTask;
    // private DatabaseReference mDatabase;
    private DocumentReference documentReference;
    String userID;
    Toolbar toolbar;
    public DrawerLayout drawerLayout;
    // private Uri resultUri;

    private static final int PICK_IMAGE_REQUEST = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click);

        name = findViewById(R.id.txtname);
        //  date = findViewById(R.id.txtdob);
        add = findViewById(R.id.txtadd);
        //  bt = findViewById(R.id.txtbt);
        contact = findViewById(R.id.txtph);
         em = findViewById(R.id.txtemail);
        //   upload =findViewById(R.id.upbtn);

        imgpro1 =findViewById(R.id.imgpro);
        //  imgblood1 = findViewById(R.id.imgbt);
          imgem1 = findViewById(R.id.imgemail);
        imgloc1 = findViewById(R.id.imgadd);
        imgpho1 = findViewById(R.id.imgph);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageButton);

        storage = FirebaseStorage.getInstance();

        //   niStorageRef = storage.getInstance().getReference("Images");
        niAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        ActionBar actionBar = getSupportActionBar();


     /*   final DocumentReference docRef = db.collection("users").document(niAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(OnClick.this, "Doc there", Toast.LENGTH_SHORT).show();
                        name.setText(document.get("fname").toString());
                        add.setText(document.get("address").toString());
                        date.setText(document.get("date").toString());
                        bt.setText(document.get("bloodType").toString());
                        contact.setText(document.get("contactNo").toString());
                        em.setText(document.get("email").toString());
                        //  imgpro1.setImageURI((Uri) document.get("Images"));

                    } else {
                        Toast.makeText(OnClick.this, "No such doc", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OnClick.this, "couldn't connect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    } */


        Intent intent = getIntent();

        String nam = intent.getStringExtra("fname");
        String addr = intent.getStringExtra("address");
        final String phone = intent.getStringExtra("contactNo");
        final String email = intent.getStringExtra("email");
        String uri=intent.getStringExtra("url");

        //  actionBar.setTitle(nam);

        name.setText(nam);
        add.setText(addr);
        contact.setText(phone);
        em.setText(email);
        Glide.with(this)
                .load(uri)
                .into(imgpro1);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    documentReference = db.collection("users").document();
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                           // String linkOfImage = documentSnapshot.getString("contactNo");
                            if (!phone.isEmpty()) {

                                String s = "tel:" + phone;
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse(s));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);


                            }


                        }
                    });

                } catch (Exception e) {
                }
            }


        });


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OnClick.this, Message.class);
                startActivity(intent);

            }
        });
    }
}
