package com.example.ddb.Data.Parcel_dataSource_Maneger;

import androidx.annotation.NonNull;

import com.example.ddb.Data.Action;
import com.example.ddb.Entities.Address;
import com.example.ddb.Entities.Parcel;
import com.example.ddb.Entities.Parcel_Type;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisteredPackagesDS {
    static DatabaseReference parcelsRef;
    static List<Parcel> parcelList;
    static {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        parcelsRef = database.getReference("RegisteredPackages");
        parcelList = new ArrayList<>();
    }



    public static void addParcel(final Parcel parcel, final Action<String> action) {
        String phone = parcel.getRecipientPhone();
        String key = parcel.getParcelID();
        parcelsRef.child(phone + "/" +key).setValue(parcel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(parcel.getParcelID());
                action.onProgress("upload parcel data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload parcel data", 100);

            }
        });
        parcelsRef.getParent();
    }


    public static void removeParcel(String parcelid, final Action<String> action) {

        final String key = parcelid;

        parcelsRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Parcel value = dataSnapshot.getValue(Parcel.class);
                if (value == null)
                    action.onFailure(new Exception("parcel not find ..."));
                else {
                    parcelsRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
        final String key =  toUpdate.getParcelID();

        removeParcel(toUpdate.getParcelID(), new Action<String>() {
            @Override
            public void onSuccess(String obj) {
                addParcel(toUpdate, action);
            }

            @Override
            public void onFailure(Exception exception) {
                action.onFailure(exception);
            }

            @Override
            public void onProgress(String status, double percent) {
                action.onProgress(status, percent);
            }
        });
    }

    public void add(){
        Parcel parcel = new Parcel( Parcel_Type.Envelope,true,21.4,new Address("ass","ass","ass",4),"+97254345466","123456");

        RegisteredPackagesDS.addParcel(parcel,new Action<String>(){
            @Override
            public void onSuccess(String obj) {
                System.out.println( "insert id " + obj);
            }

            @Override
            public void onFailure(Exception exception) {
                System.out.println( "Error \n" + exception.getMessage());
            }
            @Override
            public void onProgress(String status, double percent) {
                System.out.println( "Error \n" + percent);
            }
        });
    }

    /* private static ChildEventListener studentRefChildEventListener; */

   /* public static void notifyToStudentList(final NotifyDataChange<List<Student>> notifyDataChange) {
        if (notifyDataChange != null) {

            if (studentRefChildEventListener != null) {
                notifyDataChange.onFailure(new Exception("first unNotify student list"));
                return;
            }
            parcelList.clear();

            studentRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Student student = dataSnapshot.getValue(Student.class);
                    String id = dataSnapshot.getKey();
                    student.setId(Long.parseLong(id));
                    parcelList.add(student);


                    notifyDataChange.OnDataChanged(parcelList);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Student student = dataSnapshot.getValue(Student.class);
                    Long id = Long.parseLong(dataSnapshot.getKey());
                    student.setId(id);


                    for (int i = 0; i < parcelList.size(); i++) {
                        if (parcelList.get(i).getId().equals(id)) {
                            parcelList.set(i, student);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(parcelList);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Student student = dataSnapshot.getValue(Student.class);
                    Long id = Long.parseLong(dataSnapshot.getKey());
                    student.setId(id);

                    for (int i = 0; i < parcelList.size(); i++) {
                        if (parcelList.get(i).getId() == id) {
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
            parcelsRef.addChildEventListener(studentRefChildEventListener);
        }
    }

    public static void stopNotifyToStudentList() {
        if (studentRefChildEventListener != null) {
            parcelsRef.removeEventListener(studentRefChildEventListener);
            studentRefChildEventListener = null;
        }
    }

    */

}
