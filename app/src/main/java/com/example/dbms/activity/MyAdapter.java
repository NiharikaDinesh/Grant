package com.example.dbms.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dbms.models.ModelUser;
import com.example.dbms.R;
import com.example.dbms.models.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter< MyAdapter.ViewHolder> {


    //public ImageView  image2;
    private Context context;
    private List<ModelUser> uploads;
    private List<Upload> uploadList;
    private FirebaseAuth niAuth;
    private FirebaseFirestore db;
    private DocumentReference documentReference;

    public MyAdapter(Context context, List<ModelUser> uploads, List<Upload> uploadList) {
        this.uploads = uploads;
        this.context = context;
       this.uploadList=uploadList;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_apos, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        niAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

       // downloadImage();
        return viewHolder;


    }

 /*   public void downloadImage(){
        try{

            documentReference=db.collection("links").document(niAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String linkOfImage=documentSnapshot.getString("url");

                    Glide.with(context)
                            .load(linkOfImage)
                            .into(imageView2);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"Fails to get image",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    } */




    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
     final   ModelUser upload = uploads.get(position);
     Upload upload1=uploadList.get(position);
      /*  String name=uploads.get(position).getName();

        String btype=uploads.get(position).getBtype();
        String phone=uploads.get(position).getContact(); */
        holder.textViewName.setText(upload.getFname());
        holder.textBt.setText(upload.getAddress());
        holder.textPh.setText(upload.getContactNo());
        holder.textEmail.setText(upload.getEmail());
        Glide.with(context).asBitmap().load(upload1.getUrl()).into(holder.imageView);


        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("tag","onClick:clicked on"+uploads.get(position));
              // Toast.makeText(context,uploads.get(position));

                String name=uploads.get(position).getFname();
                String add=uploads.get(position).getAddress();
                String pho=uploads.get(position).getContactNo();
                String ema=uploads.get(position).getEmail();
                String uri=uploadList.get(position).getUrl();

               // BitmapDrawable bitmapDrawable=(BitmapDrawable)holder.imageView.setImageURI(Uri.parse());
              //  ByteArrayOutputStream stream=new ByteArrayOutputStream();



                Intent i=new Intent(context, OnClick.class);
                i.putExtra("fname", name);
                i.putExtra("address",  add);
                i.putExtra("contactNo",  pho);
                i.putExtra("email",ema);
                i.putExtra("url",uri);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });




    //   holder.textViewName.setText("fname");
      //  holder.textBt.setText("bloodType");
      // holder.textPh.setText("contactNo");
    /*    documentReference=db.collection("links").document();
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String linkOfImage=documentSnapshot.getString("url");

                Glide.with(context)
                        .load(linkOfImage)
                        .into(image2);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Fails to get image",Toast.LENGTH_SHORT).show();
            }
        }); */


      //Glide.with(context).load(upload.getImageUrl()).into(holder.image2);
       // Picasso.get().load(uploads.get(position).getImageUrl()).into(holder.image2);




    }

    @Override
    public int getItemCount() {
        return uploads.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, textBt, textPh,textEmail;
        public CircleImageView imageView, image2;
        LinearLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.name);
            textBt = (TextView) itemView.findViewById(R.id.btype);
            textPh = (TextView) itemView.findViewById(R.id.phone);
            textEmail=(TextView)itemView.findViewById(R.id.email);
            imageView = (CircleImageView) itemView.findViewById(R.id.imageView);
         //   image2 = (ImageView) itemView.findViewById(R.id.imageView2);
            constraintLayout=itemView.findViewById(R.id.click);

         /*   imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        documentReference = db.collection("users").document(niAuth.getUid());
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String linkOfImage = documentSnapshot.getString("contactNo");
                                if (!linkOfImage.isEmpty()) {

                                    String s = "tel:" + linkOfImage;
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse(s));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);


                                }


                            }
                        });

                    } catch (Exception e) {
                    }
                }

            }); */
        }


    }

    }


