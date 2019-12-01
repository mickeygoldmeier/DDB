package com.example.ddb.Data.User_dataSource_Maneger;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.example.ddb.Data.Action;
import com.example.ddb.Data.NotifyDataChange;

import com.example.ddb.Entities.Company;
import com.example.ddb.Entities.Person;
import com.example.ddb.Entities.User;
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

public class UsersDS {
    static DatabaseReference usersRef;
    static List<User> userList;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Users");
        userList = new ArrayList<>();
    }


    public static void addUser(final User user, final Action<String> action) {
        String key = user.getUserID();
        usersRef.child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(user.getUserID());
                action.onProgress("upload user data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload user data", 100);

            }
        });
    }


    public static void removeParcel(String userid, final Action<String> action) {
        final String key = userid;


        usersRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User value = dataSnapshot.getValue(User.class);
                if (value == null)
                    action.onFailure(new Exception("user not find ..."));
                else {
                    usersRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            action.onSuccess(value.getUserID());
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
/**
    public static void updateParcel(final Parcel toUpdate, final Action<String> action) {
        final String key = toUpdate.getParcelID();

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

 **/
    private static ChildEventListener userRefChildEventListener;
    public static void notifyToParcelList(final NotifyDataChange<List<User>> notifyDataChange) {
        if (notifyDataChange != null) {
            if (userRefChildEventListener != null) {
                notifyDataChange.onFailure(new Exception("first unNotify student list"));
                return;
            }
            userList.clear();

            userRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    User user;
                    try {
                        user = dataSnapshot.getValue(Person.class);
                    } catch (Exception e) {
                        user = dataSnapshot.getValue(Company.class);
                    }

                    userList.add(user);
                    notifyDataChange.OnDataChanged(userList);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    User user = dataSnapshot.getValue(User.class);
                    String parcelid = dataSnapshot.getKey();


                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUserID().equals(parcelid)) {
                            userList.set(i, user);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(userList);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    String parcelid = dataSnapshot.getKey();

                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUserID() == parcelid) {
                            userList.remove(i);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(userList);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            usersRef.addChildEventListener(userRefChildEventListener);
        }
    }


    public static void stopNotifyToUserList() {
        if (userRefChildEventListener != null) {
            usersRef.removeEventListener(userRefChildEventListener);
            userRefChildEventListener = null;
        }
    }


}