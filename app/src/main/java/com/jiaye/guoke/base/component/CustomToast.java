package com.jiaye.guoke.base.component;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jiaye.guoke.R;
import com.jiaye.guoke.util.PhoneUtil;

/**
 * Created by Administrator on 2017/12/10.
 */

public class CustomToast {
    private static Toast toast = null;
    public static void showToast(Context context,String message){
        cancelToast();
        if(toast==null){
            View toastRoot = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
            toast = new Toast(context);
            toast.setGravity(Gravity.TOP, 0,0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastRoot);
        }
        ((TextView)toast.getView().findViewById(R.id.tv_msg)).setText(message);
        toast.show();
    }

    private static void cancelToast(){
        if(toast!=null){
            toast.cancel();
        }
        toast = null;
    }
}
