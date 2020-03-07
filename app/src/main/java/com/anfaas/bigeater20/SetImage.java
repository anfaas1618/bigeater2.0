package com.anfaas.bigeater20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;

public class SetImage extends AppCompatActivity {
   Button btnChooseImg;
   Button btnUpload;
   private static final int PICK_IMAGE_REQUEST=1;
   ImageView setimage;
    Uri urlupload;
   Uri imageuri;

   DatabaseReference myref;
    StorageReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_image);
        btnChooseImg=findViewById(R.id.choseimg);
        btnUpload=findViewById(R.id.uploadimg);
        setimage=findViewById(R.id.imagePreview);
        reference = FirebaseStorage.getInstance().getReference("uploads");
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        myref=database.getReference("uploads");
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFirebase();
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
                    SharedPreferences UID = getSharedPreferences("UID", Context.MODE_PRIVATE);

try {



 final    String uid = UID.getString("UID", "null");

    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri uri) {
            Log.i("urlis",uri.toString());
            Toast.makeText(SetImage.this, "got it do it now", Toast.LENGTH_SHORT).show();
            urlupload=uri;
            UserImage image = new UserImage(uid, urlupload.toString());
            myref.child(uid).setValue(image);
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
}
catch ( Exception e)
{
    Toast.makeText(SetImage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
}

                }
            });

        }
    }

    public  void checkimage(View view)
    {   Log.i("kkk",urlupload.toString());
        try {
            Picasso.get().load(urlupload).into(setimage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private  void uploadImage()
{
    Intent intent=new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(intent,PICK_IMAGE_REQUEST);
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            imageuri=data.getData();
        }
        Log.i("anfaas", String.valueOf(imageuri));
      //  setimage.setImageURI(imageuri);
    }

}
