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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

public class Sign_in extends AppCompatActivity{
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        avatarView = (ImageView)findViewById(R.id.imageView2);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        birthday = (EditText)findViewById(R.id.editText5);
        height = (EditText)findViewById(R.id.editText6);
        weight = (EditText)findViewById(R.id.editText7);
        nickname = (EditText)findViewById(R.id.editText3);
       dropdown_1 = findViewById(R.id.spinner1);

        String[] items = new String[]{"Female","Male"};//dropdown list için

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown_1.setAdapter(adapter);
    }

    public void Save(View view)
    {

        /*
        height_1 = Double.parseDouble(height.getText().toString());
        weight_1 = Double.parseDouble(weight.getText().toString());*/
        try {




            nickname_1 = nickname.getText().toString();

            StorageReference storageReference = mStorageRef.child("AvatarImages/" + username + ".jpg");
            storageReference.putFile(image).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //dowland url
                    // save infos
                    StorageReference newReference = FirebaseStorage.getInstance().getReference("AvatarImages/" + username + ".jpg");
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String dowlandURL = uri.toString();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String[] username_1_1 = username.split("@");
                            //drop down seçilme kontrolü yapp
                            gender = String.valueOf(dropdown_1.getSelectedItem());
                            myRef.child("Users").child(username_1_1[0]).child("nickname").setValue(nickname_1);
                            myRef.child("Users").child(username_1_1[0]).child("birthday").setValue(birthday.getText().toString());
                            myRef.child("Users").child(username_1_1[0]).child("height").setValue(height.getText().toString());
                            myRef.child("Users").child(username_1_1[0]).child("weight").setValue(weight.getText().toString());
                            myRef.child("Users").child(username_1_1[0]).child("dowlandUrl").setValue(dowlandURL);
                            myRef.child("Users").child(username_1_1[0]).child("gender").setValue(gender);
                            Intent intent = new Intent(getApplicationContext(), Sign_up2.class);
                            intent.putExtra("username", username_1_1[0]);
                            startActivity(intent);
                            Toast.makeText(Sign_in.this, "Please Go", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Sign_in.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    catch (Exception e)
    {
        Toast.makeText(Sign_in.this, "Ensure fill all blank", Toast.LENGTH_SHORT).show();
    }
    }
    public void select(View view)//İzin kontrolu
    {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) // izin kontrolu ypksa
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)//İzin Varsa Media
     {
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
