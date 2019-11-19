package com.example.ddb.Entities;

import com.google.firebase.database.Exclude;

public class Parcel {

    private Parcel_Type Type;
    private boolean Fragile;
    private double Weight;
    private Address DistributionCenterAddress;
    private String RecipientPhone;
    private String ParcelID;
    @Exclude
    private static int id;

    public Parcel() {
    }

    public Parcel(Parcel_Type type, boolean fragile, double weight, Address distributionCenterAddress, String recipientPhone, String parcelID) {
        Type = type;
        Fragile = fragile;
        Weight = weight;
        DistributionCenterAddress = distributionCenterAddress;
        RecipientPhone = recipientPhone;
        ParcelID = parcelID;
    }

    public Parcel_Type getType() {return Type;}
    public boolean isFragile() {return Fragile;}
    public double getWeight() {return Weight;}
    public Address getDistributionCenterAddress() {return DistributionCenterAddress; }
    public String getRecipientPhone() {return RecipientPhone;}
    public String getParcelID() {return ParcelID;}

    public void setParcelID() {
        ParcelID = String.valueOf(id);
        id += 1;
    }
    public void setType(Parcel_Type type) {Type = type;}
    public void setFragile(boolean fragile) {Fragile = fragile;}
    public void setWeight(double weight) {Weight = weight;}
    public void setDistributionCenterAddress(Address distributionCenterAddress) {DistributionCenterAddress = distributionCenterAddress; }
    public void setRecipientPhone(String recipientPhone) {RecipientPhone = recipientPhone; }

}
