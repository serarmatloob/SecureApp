package com.matloob.secureapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Serar Matloob on 11/21/2019.
 */
public class Util {

    // get the android id
    public static String getGsfAndroidId(Context context) {
        Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        String ID_KEY = "android_id";
        String params[] = {ID_KEY};
        Cursor c = context.getContentResolver().query(URI, null, null,
                params, null);
        if (!c.moveToFirst() || c.getColumnCount() < 2)
            return null;
        try {
            return Long.toHexString(Long.parseLong(c.getString(1)));
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}
