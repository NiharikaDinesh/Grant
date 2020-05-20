package com.example.dbms.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dbms.R;
import com.example.dbms.activity.HomeActivity;


public class AboutFragment extends Fragment {
    TextView text1,text2,text3,text4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        text1=view.findViewById(R.id.textView19);
        text2=view.findViewById(R.id.textView11);
        text3=view.findViewById(R.id.textView20);
        text4=view.findViewById(R.id.textView21);






        return view;
    }


}