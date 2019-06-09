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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class EER extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String userkeyname;
    String Url;
    String shownickname;
    String height;
    String weight;
    String gender;
    String birthday_1;
    Double h_1;
    Double w_1;
    Double year;
    TextView navUsername;
    ImageView imageView;
    TextView result;
    String date1;
    DateFormat df;
    Date now;
    Double eer;
    Spinner dropdown_1;
    Double[] men = new Double[]{1.0, 1.11, 1.25,1.48};
    Double[] women = new Double[]{1.0,1.12,1.27,1.45};
    String[] mode = new String[]{"Sedentary","Low Active","Active","Very Active"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eer);
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
        ///////////////////////////
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView)headerView.findViewById(R.id.textView1);
        result = (TextView)findViewById(R.id.textView7);
        imageView = (ImageView)headerView.findViewById(R.id.imageView);
        Intent intent = getIntent();
        //////
        shownickname = intent.getStringExtra("nickname");
        Url = intent.getStringExtra("url");
        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");
        gender = intent.getStringExtra("gender");
        birthday_1 = intent.getStringExtra("birthday");
        userkeyname = intent.getStringExtra("username");

        /////////////////////////
        Picasso.get().load(Url).into(imageView);
        navUsername.setText(shownickname);
        dropdown_1 = findViewById(R.id.spinner1);

        //year = Double.parseDouble(bd[2]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mode);

        dropdown_1.setAdapter(adapter);
        df = new SimpleDateFormat("dd/MM/yyyy");
    }
    public void Calculate(View view)
    {
        dropdown_1.getSelectedItemPosition();
        String mode = String.valueOf(dropdown_1.getSelectedItem());
        String bd ="";
        bd = birthday_1.substring((birthday_1.length() -4),birthday_1.length());

        year = Double.parseDouble(bd);
        try
        {

            if(gender.equalsIgnoreCase("Male"))
            {
                    eer = 662 - (9.53 * (Double.parseDouble("2019") - year)) + men[dropdown_1.getSelectedItemPosition()]*((15.91 * Double.parseDouble(weight)) + (539.6 * Double.parseDouble(height)));
            }
            else
            {
                eer = 354 - (6.91 * Double.parseDouble("2019") - year) + women[dropdown_1.getSelectedItemPosition()]*((9.36 * Double.parseDouble(weight)) + ((726) * Double.parseDouble(height)));
            }
            String s1 = Double.toString(eer);

            String[] s2 = s1.split(Pattern.quote("."));

            String err_result= s2[0] + "." + s2[1].substring(0, 2);


            result.setText(err_result);
        }
        catch (Exception e)
        {
            Toast.makeText(EER.this,"Select Activity Mode",Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.eer, menu);
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
            Intent intent = new Intent(getApplicationContext(),BMI.class);
            intent.putExtra("username",userkeyname);
            intent.putExtra("url",Url);
            intent.putExtra("nickname",shownickname);
            intent.putExtra("height",height);
            intent.putExtra("weight",weight);
            /////
            intent.putExtra("birthday",birthday_1);
            intent.putExtra("gender",gender);
            startActivity(intent);
        } else if (id == R.id.EER) {

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
