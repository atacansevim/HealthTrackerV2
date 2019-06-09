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

public class Sign_up2 extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    String username;
    EditText bp;
    EditText bs;
    EditText hr;
    Double bp_1;
    Double bs_1;
    Double hr_1;
    DateFormat df;
    Date now;
    String date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        bp = (EditText)findViewById(R.id.editText9);
        bs = (EditText)findViewById(R.id.editText4);
        hr = (EditText)findViewById(R.id.editText8);
        df = new SimpleDateFormat("dd/MM/yyyy");
        now = new Date();
    }
    public void FinishSignUp(View view)
    {
        /*
        bp_1 = Double.parseDouble(bp.getText().toString());
        bs_1 = Double.parseDouble(bs.getText().toString());
        hr_1 = Double.parseDouble(hr.getText().toString());*/

    try{
        if(Integer.parseInt(bs.getText().toString()) > 0 && Integer.parseInt(bp.getText().toString()) > 0  && Integer.parseInt(hr.getText().toString()) > 0 )
        {
            df = new SimpleDateFormat("dd/MM/yyyy");
            date1 = df.format(now);
            date1 = date1.replace("/","|");
            System.out.println(date1);
            myRef.child("Daily Info").child(username).child(date1).child("BloodPressure").setValue(bp.getText().toString());
            myRef.child("Daily Info").child(username).child(date1).child("BloodSugar").setValue(bs.getText().toString());
            myRef.child("Daily Info").child(username).child(date1).child("HeartRate").setValue(hr.getText().toString());
            //DatabaseReference newReference = firebaseDatabase.getReference("Users").child(username);
            Toast.makeText(Sign_up2.this,"Your Daily Info Succesfuly Save",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),User_Profile.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else
        {

            Toast.makeText(Sign_up2.this,"Ensure Fill All Blanks",Toast.LENGTH_SHORT).show();
        }


    }
    catch (Exception e)
    {

    }

    }
}
