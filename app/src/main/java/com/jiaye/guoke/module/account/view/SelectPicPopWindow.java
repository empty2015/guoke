package com.jiaye.guoke.module.account.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jiaye.guoke.R;

/**
 * Created by Administrator on 2017/12/14.
 */

public class SelectPicPopWindow extends PopupWindow {
    public SelectPicPopWindow(final Activity context, final OnPopWinClickEvent clickEvent){
        View contentView = LayoutInflater.from(context)
                .inflate(R.layout.layout_select_pic_win,null);
        View camera = contentView.findViewById(R.id.tv_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(clickEvent!=null){
                    clickEvent.onCameraClick();
                }
            }
        });

        View album = contentView.findViewById(R.id.tv_album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(clickEvent!=null){
                    clickEvent.onAlbumClick();
                }
            }
        });
        View cancelView = contentView.findViewById(R.id.tv_cancel);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(contentView);
        backgroundAlpha(context,0.5f);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(context,1f);
            }
        });
        // 设置PopupWindow的背景
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        setOutsideTouchable(true);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);
        // 设置PopupWindow是否能响应点击事件
       setTouchable(true);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context,float bgAlpha)
    {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public interface OnPopWinClickEvent{
        public void onCameraClick();
        public void onAlbumClick();
    }
}
