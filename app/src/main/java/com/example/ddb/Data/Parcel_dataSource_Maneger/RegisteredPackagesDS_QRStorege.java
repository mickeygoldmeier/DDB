package com.example.ddb.Data.Parcel_dataSource_Maneger;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.ddb.Data.Action;
import com.example.ddb.Data.NotifyDataChange;
import com.example.ddb.Entities.Parcel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class RegisteredPackagesDS_QRStorege {
    static StorageReference qrRef;
    static List<Parcel> parcelList;

    static {
        FirebaseStorage database = FirebaseStorage.getInstance();
        qrRef = database.getReference("images");
        parcelList = new ArrayList<>();
    }


    public static void addQR(final Uri QR, String id, final Action<String> action) {

        qrRef = qrRef.child(id + ".jpg");
        qrRef.putFile(QR)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        action.onProgress("upload parcel data", 90);
                        // Get a URL to the uploaded content
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        action.onFailure(exception);
                        action.onProgress("error upload parcel image", 100);
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double uploadBytes = taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
                double progress = (90.0 * uploadBytes);
                action.onProgress("upload image", progress);
            }
        });
    }

}