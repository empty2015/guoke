package com.jiaye.guoke.module.account.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jiaye.guoke.R;
import com.jiaye.guoke.base.BaseActivity;
import com.jiaye.guoke.base.component.CommonHeaderView;
import com.jiaye.guoke.base.component.RoundCornersImageView;
import com.jiaye.guoke.load.IUpload;
import com.jiaye.guoke.load.UploadManager;
import com.jiaye.guoke.module.account.view.SelectPicPopWindow;
import com.jiaye.guoke.util.PhoneUtil;

import java.io.File;

/**
 * Created by Administrator on 2017/12/12.
 */

public class RoleInfoActivity extends BaseActivity {
    CommonHeaderView headerView;
    RoundCornersImageView imageHeader;
    EditText nickName;
    RadioButton rbMale;
    RadioButton rbFemale;
    TextView tvAction;
    boolean isSelectMale = true;
    private String headerFile = "";
    @Override
    public int getLayoutId() {
        return R.layout.activity_roleinfo;
    }

    @Override
    public void initView() {
        headerView = (CommonHeaderView)findViewById(R.id.headerview);
        headerView.setSplitViewState(View.GONE);
        imageHeader = (RoundCornersImageView)findViewById(R.id.image_header);
        nickName = (EditText)findViewById(R.id.edit_nick_name);
        rbMale = (RadioButton)findViewById(R.id.rb_male);
        rbFemale = (RadioButton)findViewById(R.id.rb_female);
        tvAction = (TextView)findViewById(R.id.tv_action);
        rbMale.setChecked(true);
    }

    private void changeSexState(){
        isSelectMale = !isSelectMale;
        rbMale.setChecked(isSelectMale);
        rbFemale.setChecked(!isSelectMale);
    }

    @Override
    public void initListener() {
        rbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSexState();
            }
        });
        rbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSexState();
            }
        });
        tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction();
            }
        });
        headerView.setOnHeaderClickEvent(new CommonHeaderView.OnHeaderClickEvent() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }

            @Override
            public void onRightClick() {

            }
        });
        imageHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseHeader();
            }
        });
        nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    tvAction.setEnabled(true);
                }else{
                    tvAction.setEnabled(false);
                }
            }
        });
    }

    SelectPicPopWindow picPopWindow;
    private void chooseHeader(){
        if(picPopWindow!=null){
            if (picPopWindow.isShowing())
                picPopWindow.dismiss();
            picPopWindow = null;
        }
        picPopWindow = new SelectPicPopWindow(this, new SelectPicPopWindow.OnPopWinClickEvent() {
            @Override
            public void onCameraClick() {
                takePhoto();
            }

            @Override
            public void onAlbumClick() {
                takeAlbum();
            }
        });
        picPopWindow.setWidth(PhoneUtil.getScreenWidth(this)-PhoneUtil.dip2px(this,20));
        int popH = PhoneUtil.dip2px(this,160);
        picPopWindow.setHeight(popH);
        picPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY,
                    PhoneUtil.dip2px(this,10), PhoneUtil.getScreenHeight(this)-PhoneUtil.dip2px(this,170));

    }

    private void doAction(){
        UploadManager.getInstance().uploadFile(headerFile, new IUpload() {
            @Override
            public void onSuccess(String url) {

            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    Uri photoUri = null;
    private final int SELECT_PIC_BY_TACK_PHOTO = 101;
    private final int SELECT_PIC_BY_PICK_PHOTO = 102;
    private void takePhoto(){
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    private void takeAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //判断系统中是否有处理该Intent的Activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
        } else {
            Toast.makeText(this, "未找到图片查看器", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 点击取消按钮
        if(resultCode == RESULT_CANCELED){
            return;
        }

        // 可以使用同一个方法，这里分开写为了防止以后扩展不同的需求
        switch (requestCode) {
            case SELECT_PIC_BY_PICK_PHOTO:// 如果是直接从相册获取
                doPhoto(requestCode, data);
                break;
            case SELECT_PIC_BY_TACK_PHOTO:// 如果是调用相机拍照时
                doPhoto(requestCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {

        // 从相册取图片，有些手机有异常情况，请注意
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
            if (data == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if (photoUri == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String[] pojo = { MediaStore.MediaColumns.DATA };
        // The method managedQuery() from the type Activity is deprecated
        //Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        Cursor cursor = getContentResolver().query(photoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            headerFile = cursor.getString(columnIndex);

            // 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
            if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                cursor.close();
            }
        }
        //判断图片格式
        if (headerFile != null && (headerFile.toLowerCase().endsWith(".png")||
                headerFile.toLowerCase().endsWith(".jpg")||
                headerFile.toLowerCase().endsWith(".jpeg"))){
            Bitmap bm = getSmallBitmap(headerFile,imageHeader.getWidth(),imageHeader.getHeight());
            // 显示在图片控件上
            imageHeader.setImageBitmap(bm);
        }else{
            Toast.makeText(this, "选择图片文件格式出错", Toast.LENGTH_LONG).show();
        }



    }

    /**
     * 获取小图片，防止OOM
     *
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
    /**
     * 计算图片缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
