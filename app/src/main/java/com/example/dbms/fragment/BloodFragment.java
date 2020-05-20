package com.example.dbms.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.dbms.activity.ABNegative;
import com.example.dbms.activity.ABPositive;
import com.example.dbms.activity.ANegative;
import com.example.dbms.activity.APositive;
import com.example.dbms.activity.BNegative;
import com.example.dbms.activity.BPositive;
import com.example.dbms.activity.OPositive;
import com.example.dbms.activity.Onegative;
import com.example.dbms.R;


public class BloodFragment extends Fragment {

  //  private Object Context;
    // int value=10;
    Button ap,an,bp,bn,abp,abn,op,on;
    Toolbar toolbar;
    public DrawerLayout drawerLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood, container, false);




        ap=view.findViewById(R.id.btnap);
        an=view.findViewById(R.id.btnan);
        bp=view.findViewById(R.id.btnbp);
        bn=view.findViewById(R.id.btnbn);
        abp=view.findViewById(R.id.btnabp);
        abn=view.findViewById(R.id.btnabn);
        op=view.findViewById(R.id.btnop);
        on=view.findViewById(R.id.btnon);

        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),APositive.class);
                startActivity(intent);
            }
        });

        an.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ANegative.class);
                startActivity(intent);
            }
        });

        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BPositive.class);
                startActivity(intent);
            }
        });

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BNegative.class);
                startActivity(intent);
            }
        });

        abp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ABPositive.class);
                startActivity(intent);
            }
        });

        abn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ABNegative.class);
                startActivity(intent);
            }
        });

        op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),OPositive.class);
                startActivity(intent);
            }
        });

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Onegative.class);
                startActivity(intent);
            }
        });





   /*     final String[] bloodList={"A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve","O+ve","O-ve"};

        ListView listView=(ListView)view.findViewById(R.id.listBlood);

        ArrayAdapter<String>listViewAdapter=new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,bloodList
        );

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   Toast.makeText(BloodFragment.this,bloodList[position],Toast.LENGTH_SHORT).show();
                if(position==0){
                    Intent intent=new Intent(getActivity(), APositive.class);
                    startActivity(intent);
                }

                if(position==1){
                    Intent intent=new Intent(getActivity(), ANegative.class);
                    startActivity(intent);
                }

                if(position==2){
                    Intent intent=new Intent(getActivity(), BPositive.class);
                    startActivity(intent);
                }

                if(position==3){
                    Intent intent=new Intent(getActivity(), BNegative.class);
                    startActivity(intent);
                }

                if(position==4){
                    Intent intent=new Intent(getActivity(), ABPositive.class);
                    startActivity(intent);
                }

                if(position==5){
                    Intent intent=new Intent(getActivity(), ABNegative.class);
                    startActivity(intent);
                }

                if(position==6){
                    Intent intent=new Intent(getActivity(), OPositive.class);
                    startActivity(intent);
                }

                if(position==7){
                    Intent intent=new Intent(getActivity(), Onegative.class);
                    startActivity(intent);
                }
            }
        }); */

        return view;



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
       /* if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        } */
        return super.onOptionsItemSelected(item);

    }




}

