package com.example.ddb.Utils;

import android.graphics.Bitmap;
import android.net.Uri;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.image.ImageType;

import java.io.File;


public class QR_code {

    public static Uri getMyBitmap(String id) {
        File file = QRCode.from(id).to(ImageType.JPG).file();
        return Uri.fromFile(file);
    }


}
