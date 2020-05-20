package com.example.dbms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dbms.fragment.AboutFragment;
import com.example.dbms.fragment.BloodFragment;
import com.example.dbms.fragment.HomeFragment;
import com.example.dbms.adapter.NewAdapter;
import com.example.dbms.fragment.ProfileFragment;
import com.example.dbms.R;
import com.example.dbms.models.Utils;
import com.example.dbms.api.ApiClient;
import com.example.dbms.api.ApiInterface;
import com.example.dbms.models.Article;
import com.example.dbms.models.News;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dbms.R.id.frame;
import static com.google.android.material.navigation.NavigationView.*;

public class HomeActivity extends AppCompatActivity implements OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public CoordinatorLayout coordinateLayout;
    public Toolbar toolbar;
    public FrameLayout frameLayout;
    public NavigationView navigationView;
    ProgressDialog progressDialog;
    FirebaseAuth niAuth;
    ImageView ima;
    private DocumentReference documentReference;
   // ListView lvRss;
  //  ArrayList<String>titles;
 //   ArrayList<String>links;
  //  private FirebaseAuth.AuthStateListener niAuthStateListener;
  //  public static final String API_KEY="eb0cea94e9ef4a92b0d9fa247f38f42e";
   // private RecyclerView recyclerView;
     RecyclerView.LayoutManager layoutManager;
 //   private List<Article> articles=new ArrayList<>();
  //  private Adapter adapter;
    private String TAG=HomeActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       // recyclerView=findViewById(R.id.recyclerhome);
        frameLayout = findViewById(R.id.layoutDate);
      //  layoutManager=new LinearLayoutManager(HomeActivity.this);
       // recyclerView.setItemAnimator(new DefaultItemAnimator());
      //  recyclerView.setNestedScrollingEnabled(false);
     //   LoadJson();


        drawerLayout = findViewById(R.id.draw);
        coordinateLayout = findViewById(R.id.coordination_view);
        toolbar = findViewById(R.id.toolbar);
        niAuth=FirebaseAuth.getInstance();
        ima=(ImageView)findViewById(R.id.imageView6);
     //   lvRss=(ListView)findViewById(R.id.lvRss);
      //  titles=new ArrayList<String>();
      //  links=new ArrayList<String>();

        navigationView = findViewById(R.id.navigation_view);

     //   progressDialog = new ProgressDialog(this);
        setUpToolBar();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                HomeActivity.this, drawerLayout, R.string.open_drawer, R.string.close_drawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    /*   if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(frame,
                    new HomeFragment()).addToBackStack("Home").commit();
            navigationView.setCheckedItem(R.id.dashboard);
        } */


     /*  lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Uri uri= Uri.parse(links.get(position));
               Intent intent=new Intent(Intent.ACTION_VIEW,uri);
               startActivity(intent);

           }
       });

       new ProcessInBackground().execute(); */
    }

  /*  public InputStream getInputStream(URL url){
        try {
            return url.openConnection().getInputStream();
        }catch (IOException e){
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer,Void,Exception>{

        ProgressDialog progressDialog=new ProgressDialog(HomeActivity.this);
        Exception exception=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           progressDialog.setMessage("Busy loading rss feed...please wait...");
           progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {

            try {
                URL url=new URL("view-source:http://feeds.fin24.com/articles/fin24/tech/rss");

                XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp=factory.newPullParser();
                xpp.setInput(getInputStream(url),"UTF_8");

                boolean insideItem=false;
                int eventType=xpp.getEventType();

                while (eventType!=XmlPullParser.END_DOCUMENT){
                    if(eventType==XmlPullParser.START_TAG){
                        if(xpp.getName().equalsIgnoreCase("items")){
                            insideItem=true;
                        }else if(xpp.getName().equalsIgnoreCase("title")){
                            if(insideItem){
                                titles.add(xpp.nextText());

                            }
                        }
                        else if(xpp.getName().equalsIgnoreCase("link")){
                            if(insideItem){
                                links.add(xpp.nextText());
                            }
                        }
                    }
                    else if (eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                        insideItem=false;
                    }
                    eventType=xpp.next();
                }

            }catch (MalformedURLException e){
                exception= e;
            }
            catch (XmlPullParserException e){
                exception=e;
            }catch (IOException e){
                exception=e;
            }



            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            ArrayAdapter<String>adapter=new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_list_item_1,titles);

            lvRss.setAdapter(adapter);

            progressDialog.dismiss();
        }
    }

 /*   public void LoadJson(){
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        String country= Utils.getCountry();

        Call<News> call;
        call=apiInterface.getNews(country,API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticle()!=null){
                    if(!articles.isEmpty()){
                        articles.clear();
                    }

                    articles=response.body().getArticle();
                    adapter= (Adapter) new NewAdapter(articles,HomeActivity.this);
                    recyclerView.setAdapter((RecyclerView.Adapter) adapter);
                    ((RecyclerView.Adapter) adapter).notifyDataSetChanged();

                }else {
                    Toast.makeText(HomeActivity.this,"No Result",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    } */


  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
          case R.id.dashboard:
                getSupportFragmentManager().beginTransaction().replace(frame,
                        new HomeFragment()).addToBackStack("Home").commit();
                getSupportActionBar().setTitle("News Feed");

                break;

            case R.id.favourites:
                getSupportFragmentManager().beginTransaction().replace(frame,
                        new BloodFragment()).addToBackStack(null).commit();
                getSupportActionBar().setTitle("Blood");
                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(frame,
                        new ProfileFragment()).addToBackStack(null).commit();
                getSupportActionBar().setTitle("Profile");

                break;
            case R.id.about_app:
                getSupportFragmentManager().beginTransaction().replace(frame,
                        new AboutFragment()).addToBackStack(null).commit();
                getSupportActionBar().setTitle("About Us");
                break;

            case R.id.logout:
              //  progressDialog.dismiss();

                FirebaseAuth.getInstance().signOut();
                Intent inToLog=new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(inToLog);
                finish();

                 break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {

        }
        super.onBackPressed();
    }


    public void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Blood Bank");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);

    }


}
