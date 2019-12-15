package com.example.ddb.Utils;

import android.graphics.Bitmap;
import android.net.Uri;

import net.glxn.qrgen.android.QRCode;

import java.io.File;

public class QR_code {

    public static Uri getMyBitmap(String id) {
        Bitmap bitmap = QRCode.from(id).bitmap();
        String FILENAME = "image.png";
        String PATH = "/mnt/sdcard/"+ FILENAME;
        File f = new File(PATH);
        Uri yourUri = Uri.fromFile(f);
        return null; //QRCode.from(id).bitmap();
    }


}
