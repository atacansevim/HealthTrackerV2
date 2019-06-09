package com.example.health_tracker_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeDaily extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    String username;
    DateFormat df;
    Date now;
    EditText one;
    EditText two;
    EditText three;
    String date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_daily);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
       mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        one = (EditText) findViewById(R.id.editText10);
        two = (EditText)findViewById(R.id.editText11);
        three = (EditText)findViewById(R.id.editText12);
        now = new Date();
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        df = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(df.format(now));
    }
    public void Save(View view)
    {
            try{
            if(Integer.parseInt(one.getText().toString()) > 0 && Integer.parseInt(two.getText().toString()) > 0  && Integer.parseInt(three.getText().toString()) > 0 )
            {
                df = new SimpleDateFormat("dd/MM/yyyy");
                date1 = df.format(now);
                date1 = date1.replace("/","|");

                myRef.child("Daily Info").child(username).child(date1).child("BloodPressure").setValue(one.getText().toString());
                myRef.child("Daily Info").child(username).child(date1).child("BloodSugar").setValue(two.getText().toString());
                myRef.child("Daily Info").child(username).child(date1).child("HeartRate").setValue(three.getText().toString());
                //DatabaseReference newReference = firebaseDatabase.getReference("Users").child(username);
                Toast.makeText(TakeDaily.this,"Your Daily Info Succesfuly Save",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),User_Profile.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
            else
            {

                Toast.makeText(TakeDaily.this,"Ensure Fill All Blanks",Toast.LENGTH_SHORT).show();
            }

            }
            catch (Exception e)
            {
                Toast.makeText(TakeDaily.this,"Ensure Fill All Blanks",Toast.LENGTH_SHORT).show();

            }

    }
}
