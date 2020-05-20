package com.example.dbms.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dbms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;


public class ProfileFragment extends Fragment {
    private TextView name, date, add, bt, contact, em;
    private ImageView imgblood1, imgem1, imgpho1, imgloc1;
    private FirebaseAuth niAuth;
    private FirebaseFirestore db;
    //  private FirebaseFirestore user;
    private ImageView imgpro1;
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
    ProgressDialog progressDialog;
    // private Uri resultUri;

    private static final int PICK_IMAGE_REQUEST = 234;

    // private Object ModelUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        // setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        name = view.findViewById(R.id.txtname);
        date = view.findViewById(R.id.txtdob);
        add = view.findViewById(R.id.txtadd);
        bt = view.findViewById(R.id.txtbt);
        contact = view.findViewById(R.id.txtph);
        em = view.findViewById(R.id.txtemail);
      //  upload = view.findViewById(R.id.upbtn);

        imgpro1 = view.findViewById(R.id.imgpro);
        imgblood1 = view.findViewById(R.id.imgbt);
        imgem1 = view.findViewById(R.id.imgemail);
        imgloc1 = view.findViewById(R.id.imgadd);
        imgpho1 = view.findViewById(R.id.imgph);
        progressDialog = new ProgressDialog(getActivity());

        storage = FirebaseStorage.getInstance();

     //   niStorageRef = storage.getInstance().getReference("Images");
        niAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
      /*  userID=niAuth.getCurrentUser().getUid();
        DocumentReference documentReference=mDatabase.collection("users").document(userID);

        db.collection("users")
                .whereEqualTo("email", "aaabbb@gmail.com")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

            }
        }); */
        progressDialog.setMessage("Please wait...");
      downloadImage();
progressDialog.dismiss();


            /*    try {
                    final File localFile = File.createTempFile("images", "jpg");
                    niStorageRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    // Successfully downloaded data to local file
                                    // ...
                                    Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    imgpro1.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                            Toast.makeText(getActivity(),"Image failed to load",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

              /*  Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE); */


    /*    upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit=new Intent(getActivity(), ImageActivity.class);
                startActivity(edit);

            }
        }); */


        final DocumentReference docRef = db.collection("users").document(niAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getActivity(), "Doc there", Toast.LENGTH_SHORT).show();
                        name.setText(document.get("fname").toString());
                        add.setText(document.get("address").toString());
                        date.setText(document.get("date").toString());
                        bt.setText(document.get("bloodType").toString());
                        contact.setText(document.get("contactNo").toString());
                        em.setText(document.get("email").toString());
                        //  imgpro1.setImageURI((Uri) document.get("Images"));

                    } else {
                        Toast.makeText(getActivity(), "No such doc", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "couldn't connect", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;


    }

    public void downloadImage(){
        try{

            documentReference=db.collection("users").document(niAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String linkOfImage=documentSnapshot.getString("url");

                    Glide.with(getActivity())
                            .load(linkOfImage)
                            .into(imgpro1);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"Fails to get image",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}


 /*   private String getExtension(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void Fileuploader() {
        userID = niAuth.getCurrentUser().getUid();
        //  StorageReference Ref=niStorageRef.child(System.currentTimeMillis()+"."+getExtension(imageUri));


        if (uri != null) {
        final StorageReference Ref = niStorageRef.child("images/" + userID + "/" + uri.getLastPathSegment());

        uploadTask = Ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(getActivity(), "Image Uploaded Successfully", Toast.LENGTH_LONG).show();

                        //  Upload upload = new Upload();

                        //adding an upload to firebase database
                        //  String uploadId = mDatabase.push().getKey();
                        // mDatabase.child(uploadId).setValue(upload);

                      //  StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                        Task<Uri> downloadUrl = Ref.getDownloadUrl();


                      downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {



                            @Override
                            public void onSuccess(Uri uri) {

                                final String imageReference = uri.toString();
                                mDatabase.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    }
                                });
                            }
                        });
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });



    }

 //   DatabaseReference reference = mDatabase.child("specimens").push();
      //  ModelUser.setImageUrl(" ");
      //  reference.setValue(ModelUser);
    // update the DTO with the Firebase generated key.
  //  String key = reference.getKey();
     //   ModelUser.setKey(key);
}

   private void Fileretriever() throws IOException {

      StorageReference reference=niStorageRef.child(System.currentTimeMillis()+"."+getExtension(uri));
      final File localFile = File.createTempFile("images", "jpg");
        reference.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                        Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        imgpro1.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
                Toast.makeText(getActivity(),"Image failed to load",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void Filechooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(intent,1);
       //startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
   public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null) {
            uri = data.getData();
          //  imgpro1.setImageURI(uri);

           // Bitmap bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
          //  imgpro1.setImageBitmap(bitmap);

           try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                imgpro1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    }















/*
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    tools:context=".fragment.ProfileFragment">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/constraintLayout"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_marginBottom="8dp"
        android:background="@drawable/bgimg"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.0">

        <TextView
            android:id="@+id/txtname"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="350dp"
        android:layout_height="400dp"
        android:layout_marginStart="8dp"
        android:layout_marginLeft="8dp"
        android:layout_marginTop="30dp"
        android:layout_marginEnd="8dp"
        android:layout_marginRight="8dp"
        android:layout_marginBottom="38dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.484"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/imgpro"
        app:layout_constraintVertical_bias="1.0">


        <TextView
            android:id="@+id/txtdob"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />


<ImageView
    android:id="@+id/imgbt"
            android:layout_width="54dp"
            android:layout_height="67dp"
            android:layout_marginTop="100dp"
            app:layout_constraintBottom_toBottomOf="@+id/imgadd"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.06"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="0.0"

            tools:ignore="MissingConstraints"
            tools:src="@drawable/ic_invert_colors_black_24dp"/>

        <TextView
            android:id="@+id/txtbt"
            android:layout_width="209dp"
            android:layout_height="25dp"
            android:layout_marginStart="40dp"
            android:layout_marginLeft="40dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toEndOf="@+id/imgbt"
            app:layout_constraintTop_toBottomOf="@+id/txtdob"
            app:layout_constraintVertical_bias="0.261"

            tools:ignore="MissingConstraints" />

        <ImageView
            android:id="@+id/imgph"
            android:layout_width="52dp"
            android:layout_height="54dp"
            app:layout_constraintBottom_toBottomOf="@+id/imgadd"
            app:layout_constraintTop_toBottomOf="@+id/imgbt"
            app:layout_constraintVertical_bias="0.16"

            tools:ignore="MissingConstraints"
            tools:layout_editor_absoluteX="15dp"
            tools:src="@drawable/ic_imgph" />


        <TextView
            android:id="@+id/txtph"
            android:layout_width="205dp"
            android:layout_height="26dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.779"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/txtbt"
            app:layout_constraintVertical_bias="0.242"

            tools:ignore="MissingConstraints" />


        <ImageView
            android:id="@+id/imgemail"
            android:layout_width="57dp"
            android:layout_height="51dp"
            android:layout_marginBottom="84dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.061"
            app:layout_constraintStart_toStartOf="parent"


            tools:ignore="MissingConstraints"
            tools:src="@drawable/ic_email_black_24dp" />

        <TextView
            android:id="@+id/txtemail"
            android:layout_width="211dp"
            android:layout_height="26dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.758"
            app:layout_constraintStart_toStartOf="@+id/imgemail"
            app:layout_constraintTop_toBottomOf="@+id/txtph"
            app:layout_constraintVertical_bias="0.272"

            tools:ignore="MissingConstraints" />

        <ImageView
            android:id="@+id/imgadd"
            android:layout_width="71dp"
            android:layout_height="54dp"
            android:layout_marginTop="23dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.052"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/imgemail"
            app:layout_constraintVertical_bias="0.269"

            tools:ignore="MissingConstraints"
            tools:src="@drawable/ic_imgadd" />

        <TextView
            android:id="@+id/txtadd"
            android:layout_width="223dp"
            android:layout_height="50dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.848"
            app:layout_constraintStart_toStartOf="@+id/imgadd"
            app:layout_constraintTop_toBottomOf="@+id/txtemail"
            app:layout_constraintVertical_bias="0.381"


            tools:ignore="MissingConstraints" />


    </androidx.constraintlayout.widget.ConstraintLayout>

    <ImageView
        android:id="@+id/imgpro"
        android:layout_width="126dp"
        android:layout_height="105dp"
        tools:ignore="MissingConstraints"
        tools:layout_editor_absoluteX="134dp"
        tools:layout_editor_absoluteY="162dp"
        tools:src="@tools:sample/avatars" />






</androidx.constraintlayout.widget.ConstraintLayout>

*/




