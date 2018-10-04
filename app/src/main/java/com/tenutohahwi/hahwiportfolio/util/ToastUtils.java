package com.tenutohahwi.hahwiportfolio.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils
{

    public static void Toast(Context context, int resourceId) {
        Toast.makeText(context, context.getResources().getString(resourceId), Toast.LENGTH_SHORT).show();
    }
    
    public static void Toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
