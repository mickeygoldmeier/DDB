package com.example.ddb.Entities;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.ddb.Data.Action;
import com.example.ddb.Data.Config_dataSource_Maneger.ConfigDS;
import com.example.ddb.Data.NotifyDataChange;
import com.google.firebase.database.Exclude;

public class Parcel {

    private Parcel_Type Type;
    private boolean Fragile;
    private double Weight;
    private Address DistributionCenterAddress;
    private String RecipientPhone;
    private String ParcelID;
    private String CompanyID;


    private Bitmap QRCode;

    public Parcel() {
    }



    public Parcel(Parcel_Type type, boolean fragile, double weight, Address distributionCenterAddress, String recipientPhone, String parcelID, String companyID,Bitmap bitmap) {
        Type = type;
        Fragile = fragile;
        Weight = weight;
        DistributionCenterAddress = distributionCenterAddress;
        RecipientPhone = recipientPhone;
        ParcelID = parcelID;
        CompanyID = companyID;
        QRCode = bitmap;
    }



    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }
    public Parcel_Type getType() {
        return Type;
    }

    public boolean isFragile() {
        return Fragile;
    }

    public double getWeight() {
        return Weight;
    }

    public Address getDistributionCenterAddress() {
        return DistributionCenterAddress;
    }

    public String getRecipientPhone() {
        return RecipientPhone;
    }

    public String getParcelID() {
        return ParcelID;
    }

    public void setParcelID(String id) {
        ParcelID = id;
    }

    public void setType(Parcel_Type type) {
        Type = type;
    }

    public void setFragile(boolean fragile) {
        Fragile = fragile;
    }

    public void setWeight(double weight) throws Exception {
        if(weight <= 0)
            throw new Exception("weight cant be less or equal to 0");
        Weight = weight;
    }

    public void setDistributionCenterAddress(Address distributionCenterAddress) {
        DistributionCenterAddress = distributionCenterAddress;
    }

    public void setRecipientPhone(String recipientPhone) {
        RecipientPhone = recipientPhone;
    }


    public Bitmap getQRCode() {
        return QRCode;
    }

    public void setQRCode(Bitmap QRCode) {
        this.QRCode = QRCode;
    }
    public void getIdFromDataBase() {
        ConfigDS.getConfigID(new NotifyDataChange<String>() {
            @Override
            public void OnDataChanged(String obj) {
                setParcelID(obj);
            }
            @Override
            public void onFailure(Exception exception) {

            }

        });
    }
}


