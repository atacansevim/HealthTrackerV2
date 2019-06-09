package com.example.health_tracker_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

public class BMI extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String keyname;
    String Url;
    String shownickname;
    String height;
    String weight;
    String gender;
    String birthday_1;
    String userkeyname;
    Double h_1;
    Double w_1;
    TextView navUsername;
    ImageView imageView;
    TextView result;
    TextView score;
    Double result_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //////////////////////////////
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView)headerView.findViewById(R.id.textView1);
        result = (TextView)findViewById(R.id.textView4);
        score = (TextView)findViewById(R.id.textView5);
        imageView = (ImageView)headerView.findViewById(R.id.imageView);
        Intent intent = getIntent();
        //////////
        shownickname = intent.getStringExtra("nickname");
        Url = intent.getStringExtra("url");
        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");
        gender = intent.getStringExtra("gender");
        birthday_1 = intent.getStringExtra("birthday");
        userkeyname = intent.getStringExtra("username");


        //////////
        Picasso.get().load(Url).into(imageView);
        navUsername.setText(shownickname);
        h_1 = Double.parseDouble(height);
        w_1 = Double.parseDouble(weight);
        result_1 = w_1 / (h_1 * h_1);//hesaplamaa

        String s1 = Double.toString(result_1);

        String[] s2 = s1.split(Pattern.quote("."));

        String bmi_result= s2[0] + "." + s2[1].substring(0, 2);

        if(result_1 < 18.5)
        {
            result.setText("Underweight");
            score.setText(bmi_result);
        }
        else if(result_1 < 24.9)
        {
            result.setText("Normal Weight");
            score.setText(bmi_result);
        }
        else if(result_1 < 29.9)
        {
            result.setText("Over Weight");
            score.setText(bmi_result);
        }
        else
        {
            result.setText("Obesity");
            score.setText(bmi_result);
        }

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
        getMenuInflater().inflate(R.menu.bmi, menu);
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
            Intent intent = new Intent(getApplicationContext(),User_Profile.class);
            intent.putExtra("username",userkeyname);
            startActivity(intent);
        } else if (id == R.id.BMI) {

        } else if (id == R.id.EER) {
            Intent intent = new Intent(getApplicationContext(),EER.class);
            intent.putExtra("username",userkeyname);
            intent.putExtra("url",Url);
            intent.putExtra("nickname",shownickname);
            intent.putExtra("height",height);
            intent.putExtra("weight",weight);
            intent.putExtra("birthday",birthday_1);
            intent.putExtra("gender",gender);

            startActivity(intent);
        } else if (id == R.id.PHT) {
            Intent intent = new Intent(getApplicationContext(),PHT.class);
            intent.putExtra("url",Url);
            intent.putExtra("nickname",shownickname);
            intent.putExtra("username",userkeyname);
            /////
            intent.putExtra("birthday",birthday_1);
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
