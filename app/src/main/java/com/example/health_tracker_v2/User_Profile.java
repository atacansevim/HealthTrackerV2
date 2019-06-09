package com.example.health_tracker_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class User_Profile extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    boolean flag = false;
    TextView navUsername;
    TextView heightw;
    TextView weightw;
    TextView birthdayw;
    ImageView imageView;
    ImageView imageView1;
    String username ="";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String keyname = "";
   String Url="";
    String shownickname="";
    String height="";
   String weight="";
    String gender="";
     String birthday="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        navUsername = (TextView)headerView.findViewById(R.id.textView3);
        imageView = (ImageView)headerView.findViewById(R.id.imageView);
        imageView1 = (ImageView)findViewById(R.id.imageView4);
        heightw = (TextView)findViewById(R.id.textView8);
        weightw = (TextView)findViewById(R.id.textView9);
        birthdayw = (TextView)findViewById(R.id.textView10);
        Intent intent2 = getIntent();
        username = intent2.getStringExtra("username");
        navUsername.setText(username);
       keyname = username;



           getdataFromFirebase();




    }

    public void Save_Daily(View view)
    {

        Intent intent = new Intent(getApplicationContext(),TakeDaily.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void login(View view){

        Intent intent = new Intent(getApplicationContext(),Update.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void getdataFromFirebase()
    {
        DatabaseReference newReference = firebaseDatabase.getReference("Users").child(keyname);
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               DataSnapshot ds = dataSnapshot;


                HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();


                try{
                    shownickname = hashMap.get("nickname");
                    Url =  hashMap.get("dowlandUrl");
                    height = hashMap.get("height").toString();
                    weight = hashMap.get("weight").toString();
                    gender = hashMap.get("gender").toString();
                    birthday = hashMap.get("birthday").toString();

                if(Url != null && weight != null && height != null)
                {
                    Picasso.get().load(Url).into(imageView);
                    Picasso.get().load(Url).into(imageView1);
                    navUsername.setText(shownickname);
                    heightw.setText(height.toString());
                    weightw.setText(weight.toString());
                    birthdayw.setText(birthday.toString());

                }
                }
                catch (Exception e)
                {



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user__profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.BMI) {
            Intent intent = new Intent(getApplicationContext(),BMI.class);
            intent.putExtra("username",username);
            intent.putExtra("url",Url);
            intent.putExtra("nickname",shownickname);
            intent.putExtra("height",height);
            intent.putExtra("weight",weight);
            /////
            intent.putExtra("birthday",birthday);
            intent.putExtra("gender",gender);
            startActivity(intent);
        } else if (id == R.id.EER) {
            Intent intent = new Intent(getApplicationContext(),EER.class);
            intent.putExtra("username",username);
            intent.putExtra("url",Url);
            intent.putExtra("nickname",shownickname);
            intent.putExtra("height",height);
            intent.putExtra("weight",weight);
            intent.putExtra("birthday",birthday);
            intent.putExtra("gender",gender);

            startActivity(intent);
        } else if (id == R.id.PHT) {
            Intent intent = new Intent(getApplicationContext(),PHT.class);
            intent.putExtra("url",Url);
            intent.putExtra("nickname",shownickname);
            intent.putExtra("username",username);
            /////
            intent.putExtra("birthday",birthday);
            intent.putExtra("gender",gender);
            intent.putExtra("height",height);
            intent.putExtra("weight",weight);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
