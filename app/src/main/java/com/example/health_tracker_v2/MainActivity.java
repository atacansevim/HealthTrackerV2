package com.example.health_tracker_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText et;
    EditText et2;
    String un;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText)findViewById(R.id.editText2);//username
        et2 = (EditText)findViewById(R.id.editText);//password
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
       /* if(user != null)
        {
            Intent intent = new Intent(getApplicationContext(),Sign_in.class);
          //  Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }*/
    }

    public void login(View view)
    {
        //Intent intent = new Intent(getApplicationContext(),User_Profile.class);
        try {
            un = et.getText().toString();

            String ps = et2.getText().toString();



            mAuth.signInWithEmailAndPassword(un,ps)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                String[] un_1 = un.split("@");
                                Intent intent1 = new Intent(getApplicationContext(),User_Profile.class);

                                intent1.putExtra("username",un_1[0]);
                                Toast.makeText(MainActivity.this,"Welcome Again",Toast.LENGTH_SHORT).show();
                                startActivity(intent1);
                            }


                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,"Ensure Fill All Blanks",Toast.LENGTH_SHORT).show();
        }


    }
    public  void signup(View view)
    {
        try{
            un = et.getText().toString();
            mAuth.createUserWithEmailAndPassword(et.getText().toString(),et2.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Intent intent = new Intent(getApplicationContext(),Sign_in.class);
                                Toast.makeText(MainActivity.this,"Your account has been successfully centered",Toast.LENGTH_SHORT).show();
                                intent.putExtra("username",un);

                                startActivity(intent);
                            }

                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,"Ensure Fill All Blanks",Toast.LENGTH_SHORT).show();
        }


    }
}
