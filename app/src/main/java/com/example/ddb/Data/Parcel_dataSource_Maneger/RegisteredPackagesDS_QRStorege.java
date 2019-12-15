package com.example.ddb.Data.Parcel_dataSource_Maneger;

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
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class RegisteredPackagesDS_QRStorege {
    static StorageReference qrRef;
    static List<Parcel> parcelList;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        qrRef = database.getReference("RegisteredPackages");
        parcelList = new ArrayList<>();
    }


    public static void addStudent(final Student student, final Action<Long> action) {
        if (student.getImageLocalUri() != null) {         // upload image
            StorageReference imagesRef = FirebaseStorage.getInstance().getReference();
            imagesRef = imagesRef.child("images").child(System.currentTimeMillis() + ".jpg");
            imagesRef.putFile(student.getImageLocalUri())
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            action.onProgress("upload student data", 90);
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            student.setImageFirebaseUrl(downloadUrl.toString());
                            add studentaddStudentToFirebase (student, action);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            action.onFailure(exception);
                            action.onProgress("error upload student image", 100);
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double uploadBytes = taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
                    double progress = (90.0 * uploadBytes);
                    action.onProgress("upload image", progress);
                }
            });
        } else
            action.onFailure(new Exception("select image first ..."));
    }



    public static void removeParcel(String parcelid, final Action<String> action) {
        final String key = parcelid;

        qrRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Parcel value = dataSnapshot.getValue(Parcel.class);
                if (value == null)
                    action.onFailure(new Exception("parcel not find ..."));
                else {
                    qrRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            action.onSuccess(value.getParcelID());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            action.onFailure(e);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }

    public static void updateParcel(final Parcel toUpdate, final Action<String> action) {
        final String key = toUpdate.getParcelID();
        final String phone = toUpdate.getRecipientPhone();
        qrRef.child(phone + '/' + key).setValue(toUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(key);
                action.onProgress("updated parcel data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
            }
        });
    }

    private static ChildEventListener parcelRefChildEventListener;
    public static void notifyToParcelList(final NotifyDataChange<List<Parcel>> notifyDataChange) {
        if (notifyDataChange != null) {
            if (parcelRefChildEventListener != null) {
                notifyDataChange.onFailure(new Exception("first unNotify student list"));
                return;
            }
            parcelList.clear();

            parcelRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                        Parcel parcel = uniqueKeySnapshot.getValue(Parcel.class);
                        boolean flag = true;
                        for (int i = 0; i < parcelList.size(); i++) {
                            if (parcelList.get(i).getParcelID().equals(parcel.getParcelID())) {
                                parcelList.set(i, parcel);
                                flag = false;
                                break;
                            }
                        }
                        if (flag)
                            parcelList.add(parcel);
                    }
                    notifyDataChange.OnDataChanged(parcelList);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                        Parcel parcel = uniqueKeySnapshot.getValue(Parcel.class);
                        boolean flag = true;
                        for (int i = 0; i < parcelList.size(); i++) {
                            if (parcelList.get(i).getParcelID().equals(parcel.getParcelID())) {
                                parcelList.set(i, parcel);
                                flag = false;
                                break;
                            }
                        }
                        if (flag)
                            parcelList.add(parcel);
                    }

                    notifyDataChange.OnDataChanged(parcelList);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Parcel parcel = dataSnapshot.getValue(Parcel.class);
                    String parcelid = dataSnapshot.getKey();

                    for (int i = 0; i < parcelList.size(); i++) {
                        if (parcelList.get(i).getParcelID() == parcelid) {
                            parcelList.remove(i);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(parcelList);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            qrRef.addChildEventListener(parcelRefChildEventListener);
        }
    }

    public static void stopNotifyToParcelList() {
        if (parcelRefChildEventListener != null) {
            qrRef.removeEventListener(parcelRefChildEventListener);
            parcelRefChildEventListener = null;
        }
    }
}