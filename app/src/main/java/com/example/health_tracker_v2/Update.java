package com.example.health_tracker_v2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class Update extends AppCompatActivity {
    ImageView avatarView;
    EditText birthday;
    EditText  height;
    EditText weight;
    EditText nickname;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    Bitmap selectedimage;
    Uri image;
    String username;
    Date date;
    Double height_1;
    Double weight_1;
    String nickname_1;
    Spinner dropdown_1;
    String gender;


    String Url;
    String shownickname;
    String height1;
    String weight1;
    String gender1;
    String birthday1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        birthday = (EditText)findViewById(R.id.editText17);
        height = (EditText)findViewById(R.id.editText18);
        weight = (EditText)findViewById(R.id.editText19);
        nickname = (EditText)findViewById(R.id.editText13);
        avatarView = findViewById(R.id.imageView3);
        getdataFromFirebase();



    }

    public void getdataFromFirebase()
    {
        DatabaseReference newReference = firebaseDatabase.getReference("Users").child(username);
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot;


                HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                shownickname = hashMap.get("nickname");
                Url =  hashMap.get("dowlandUrl");
                height1 = hashMap.get("height").toString();
                weight1 = hashMap.get("weight").toString();
                birthday1 = hashMap.get("birthday").toString();


                birthday.setText(birthday1);
                height.setText(height1);
                weight.setText(weight1);
                nickname.setText(shownickname);
                Picasso.get().load(Url).into(avatarView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void Save(View view)
    {

        /*
        height_1 = Double.parseDouble(height.getText().toString());
        weight_1 = Double.parseDouble(weight.getText().toString());*/
        if(image != null)
        {
            nickname_1 = nickname.getText().toString();

            StorageReference storageReference = mStorageRef.child("AvatarImages/"+username+".jpg");
            storageReference.putFile(image).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //dowland url
                    // save infos
                    StorageReference newReference = FirebaseStorage.getInstance().getReference("AvatarImages/"+username+".jpg");
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String dowlandURL = uri.toString();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //String[] username_1_1 = username.split("@");
                            //drop down seçilme kontrolü yapp

                            myRef.child("Users").child(username).child("nickname").setValue(nickname.getText().toString());
                            myRef.child("Users").child(username).child("birthday").setValue(birthday.getText().toString());
                            myRef.child("Users").child(username).child("height").setValue(height.getText().toString());
                            myRef.child("Users").child(username).child("weight").setValue(weight.getText().toString());
                            myRef.child("Users").child(username).child("dowlandUrl").setValue(dowlandURL);

                            Intent intent = new Intent(getApplicationContext(),User_Profile.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                            Toast.makeText(Update.this,"Your information updated",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Update.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            myRef.child("Users").child(username).child("nickname").setValue(nickname.getText().toString());
            myRef.child("Users").child(username).child("birthday").setValue(birthday.getText().toString());
            myRef.child("Users").child(username).child("height").setValue(height.getText().toString());
            myRef.child("Users").child(username).child("weight").setValue(weight.getText().toString());

            Intent intent = new Intent(getApplicationContext(),User_Profile.class);
            intent.putExtra("username",username);
            startActivity(intent);
            Toast.makeText(Update.this,"Your information updated",Toast.LENGTH_SHORT).show();
        }


    }
    public void select(View view)
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) // izin kontorol ypksa
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);//izin iste

        }// izin önceden varda
        else
        {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//izin varsa fotosec(PATHını al gel)
            startActivityForResult(intent,2);


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//izin varsa fotosec(PATHını al gel)
                startActivityForResult(intent,2);


            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)//Resim Seçildikten sonra RequestCode Verdiğimiz Code yukarıdaki
    {// foto null değil isee resultcode = result veriyor mu kontorlu
        if(requestCode == 2 && resultCode == RESULT_OK && data != null)
        {
            image = data.getData();
            try {
                selectedimage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                avatarView.setImageBitmap(selectedimage);
            }
            catch (IOException e)
            {

                e.printStackTrace();
            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }



}
