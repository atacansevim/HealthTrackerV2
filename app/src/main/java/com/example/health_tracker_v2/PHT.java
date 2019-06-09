package com.example.health_tracker_v2;

        import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class PHT extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    BarChart barChart;
    BarChart barChart_2;
    BarChart barChart_3;
    TextView navUsername;
    ImageView imageView;
    String shownickname;
    String Url;
    String height;
    String weight;
    String gender;
    String birthday_1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String userkeyname;
    ArrayList<Double> bloodpressure;
    ArrayList<Double> bloodsugar;
    ArrayList<Double> heartrate;
    ArrayList<String> yaxes;
    double[] deneme = new double[4];
    ArrayList<BarEntry> bar1;
    ArrayList<BarEntry> bar2;
    ArrayList<BarEntry> bar3;

    View view;
    String array;
    String array1;
    String array2;
    int count = 0;
    int [] colors;
    int [] colors1;
    int [] colors2;
    DateFormat df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pht);
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
        //////////////
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        ///////////////////////7
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView)headerView.findViewById(R.id.textView1);
        //result = (TextView)findViewById(R.id.textView7);
        imageView = (ImageView)headerView.findViewById(R.id.imageView);
        Intent intent = getIntent();
        //////////
        shownickname = intent.getStringExtra("nickname");
        userkeyname = intent.getStringExtra("username");
        Url = intent.getStringExtra("url");
        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");
        gender = intent.getStringExtra("gender");
        birthday_1 = intent.getStringExtra("birthday");
        //////////////
        Picasso.get().load(Url).into(imageView);
        navUsername.setText(shownickname);


        barChart = (BarChart)findViewById(R.id.bargraph);
        barChart_2 =(BarChart)findViewById(R.id.bargraph_1);
        barChart_3 =(BarChart)findViewById(R.id.bargraph_2);


        bar1 = new ArrayList<>();
        bar2 = new ArrayList<>();
        bar3 = new ArrayList<>();
        bloodpressure = new ArrayList<>();
        bloodsugar = new ArrayList<>();
        heartrate = new ArrayList<>();
        yaxes  = new ArrayList<String>();


        getinfos();


    }

    public void getColorbs(int index,BarDataSet bar4) {
        if(bar4.getEntryForXIndex(index).getVal() < 50) // less than 95 green
        { colors[index] = Color.BLACK;}
        else if(bar4.getEntryForXIndex(index).getVal() < 150) // less than 100 orange
        {colors[index] = Color.BLUE; }
        else // greater or equal than 100 red
        {colors[index] = Color.RED; }
    }

    public void getColorbh(int index,BarDataSet bar4) {
        if(bar4.getEntryForXIndex(index).getVal() < 100) // less than 95 green
        { colors1[index] = Color.BLACK;}
        else if(bar4.getEntryForXIndex(index).getVal() < 150) // less than 100 orange
        {colors1[index] = Color.BLUE; }
        else // greater or equal than 100 red
        {colors1[index] = Color.RED; }
    }

    public void getColorhr(int index,BarDataSet bar4) {
        if(bar4.getEntryForXIndex(index).getVal() < 20) // less than 95 green
        { colors2[index] = Color.BLACK;}
        else if(bar4.getEntryForXIndex(index).getVal() < 80) // less than 100 orange
        {colors2[index] = Color.BLUE; }
        else // greater or equal than 100 red
        {colors2[index] = Color.RED; }
    }

    public void setCharts( ArrayList<Double> bloodpressure, ArrayList<Double> bloodsugar, ArrayList<Double> heartrate, ArrayList<String> yaxes)
    {
        BarDataSet barDataSet_1;
        BarDataSet barDataSet_2;
        BarDataSet barDataSet_3;
        BarData thedata;
        BarData thedata1;
        BarData thedata2;


        for(int i = 0; i < yaxes.size() ; i++) {

            //String[] day =  yaxes.get(i).split("\\|");


            boolean add = bar1.add(new BarEntry(bloodpressure.get(i).intValue(), i));
            bar2.add(new BarEntry(bloodsugar.get(i).intValue(),i));
            bar3.add(new BarEntry(heartrate.get(i).intValue(),i));


        }


        barDataSet_1 = new BarDataSet(bar1,"Blood Pressure");
        barDataSet_2 = new BarDataSet(bar2,"Blood Sugar");
        barDataSet_3 = new BarDataSet(bar3,"Heart Rate");
        colors = new int[yaxes.size()];
        colors1 = new int[yaxes.size()];
        colors2 = new int[yaxes.size()];
        for(int i = 0; i < yaxes.size() ; i++) {
            getColorbs(i,barDataSet_1);
            getColorbh(i,barDataSet_2);
            getColorhr(i,barDataSet_3);
        }
        barDataSet_1.setColors(colors);
        barDataSet_2.setColors(colors1);
        barDataSet_3.setColors(colors2);
        thedata = new BarData(yaxes,barDataSet_1);
        thedata1 = new BarData(yaxes,barDataSet_2);
        thedata2 = new BarData(yaxes,barDataSet_3);

        barChart.setData(thedata);
        barChart_2.setData(thedata1);
        barChart_3.setData(thedata2);
        barChart.setTouchEnabled(true);


    }
    public void getinfos()
    {

        DatabaseReference newReference = firebaseDatabase.getReference("Daily Info").child(userkeyname);
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();
                    String[] yax = ds.getKey().split("\\|");
                    String fyax = yax[0] + "/"+ yax[1];


                    yaxes.add(fyax);
                    System.out.println("hataaaaaa"+Double.parseDouble(hashMap.get("BloodPressure")));
                    bloodpressure.add(Double.parseDouble(hashMap.get("BloodPressure")));
                    bloodsugar.add(Double.parseDouble(hashMap.get("BloodSugar")));
                    heartrate.add(Double.parseDouble(hashMap.get("HeartRate")));


                }
                setCharts(bloodpressure,bloodsugar,heartrate,yaxes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        try {
            System.out.println("yordnnn"+bloodpressure.get(0).intValue());

        }
        catch (Exception e)
        {

            e.printStackTrace();
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
        getMenuInflater().inflate(R.menu.pht, menu);
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

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

