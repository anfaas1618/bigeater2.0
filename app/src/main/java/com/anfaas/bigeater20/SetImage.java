package com.anfaas.bigeater20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetImage extends AppCompatActivity {
    private static final String TAG = "setimage";
    BottomAppBar bar;
    FloatingActionButton btnChooseImg;
    Button uploadImage,skipUpload;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri urlupload;
    Uri imageuri;
    String uid_all;
    String email, password;
    ProgressBar uploadProgress;
    CircleImageView setImageCircle;

    DatabaseReference myref;
    StorageReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_image);



        SharedPreferences UID = getSharedPreferences("UID", Context.MODE_PRIVATE);
        final    String uid = UID.getString("UID", "null");
        uid_all=uid;
        btnChooseImg=findViewById(R.id.fabsetimage);
        uploadImage=findViewById(R.id.uploadbtn);
        setImageCircle=findViewById(R.id.circleimageview);
        skipUpload =findViewById(R.id.skipupload);
        uploadProgress=findViewById(R.id.uploadProgress);
        skipUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(   new Intent(SetImage.this, start.class));
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            uploadImage.setVisibility(View.GONE);
            uploadProgress.setVisibility(View.VISIBLE);
            skipUpload.setVisibility(View.GONE);
                uploadFirebase();
            }
        });
      //  setimage=findViewById(R.id.imagePreview);
        reference = FirebaseStorage.getInstance().getReference("uploads");
        FirebaseDatabase authdatabase = FirebaseDatabase.getInstance();

     FirebaseDatabase   data=FirebaseDatabase.getInstance();
     DatabaseReference authref=data.getReference();
        if(uid!=null) {
            authref.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  User user=dataSnapshot.getValue(User.class);
             email=     user.Email;
             password=user.PassWord;
             Log.i("kks",email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        myref=database.getReference("uploads");
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }
    private  String getFileExtention(Uri uri)
    {
        ContentResolver  cr= getContentResolver();
        MimeTypeMap map=MimeTypeMap.getSingleton();
        return  map.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFirebase() {
        if (imageuri!=null)
        {
            final StorageReference ref=reference.child(System.currentTimeMillis()+"."+getFileExtention(imageuri));
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(SetImage.this, "sucess", Toast.LENGTH_SHORT).show();
try {

    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri uri) {
            Log.i("urlis",uri.toString());
            Toast.makeText(SetImage.this, "got it do it now", Toast.LENGTH_SHORT).show();
            urlupload=uri;
            SharedPreferences imageurll = getSharedPreferences("IMAGEURL", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit_name=imageurll.edit();
            edit_name.putString("IMAGEURL",urlupload.toString());
            edit_name.commit();
            UserImage image = new UserImage(uid_all, urlupload.toString());
            myref.child(uid_all).setValue(image);

        }
    });
//    File localFile = File.createTempFile("images", "png");
//    reference.getFile(localFile)
//            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    // Successfully downloaded data to local file
//                    // ...
//                    Toast.makeText(SetImage.this, "done", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception exception) {
//            // Handle failed download
//            // ...
//        }
//    });

    if (imageuri!=null)
        startActivity(   new Intent(SetImage.this, start.class));
    else {
        Toast.makeText(SetImage.this, "please choose a image", Toast.LENGTH_SHORT).show();
        skipUpload.setVisibility(View.VISIBLE);
        uploadImage.setVisibility(View.VISIBLE);
        uploadProgress.setVisibility(View.GONE);
    }
}
catch ( Exception e)
{
    e.printStackTrace();
}

                }
            });

        }
        else
        {
            Toast.makeText(SetImage.this, "please choose a image", Toast.LENGTH_SHORT).show();
            skipUpload.setVisibility(View.VISIBLE);
            uploadImage.setVisibility(View.VISIBLE);
            uploadProgress.setVisibility(View.GONE);
        }
    }

    private  void uploadImage()
{
//    Intent intent=new Intent();
//    intent.setType("image/*");
//    intent.setAction(Intent.ACTION_GET_CONTENT);
//    startActivityForResult(intent,PICK_IMAGE_REQUEST);
    CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1,1)
            .start(this);

}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {



          //  imageuri=data.getData();
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 imageuri = result.getUri();
                Picasso
                        .get()
                        .load(imageuri)
                        .into(setImageCircle);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        Log.i("anfaas", String.valueOf(imageuri));
      //  setimage.setImageURI(imageuri);
    }

}
