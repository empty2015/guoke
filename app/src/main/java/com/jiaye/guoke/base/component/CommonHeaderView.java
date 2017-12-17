package com.jiaye.guoke.base.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaye.guoke.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2017/12/10.
 */

public class CommonHeaderView extends RelativeLayout implements View.OnClickListener{

    ImageView imageLeft;
    TextView tvHeader;
    View splitview;
    TextView tvRight;

    public CommonHeaderView(Context context) {
        this(context,null);
    }

    public CommonHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        inflate(context, R.layout.layout_common_header,this);
        initView();
        initListener();
    }

    private void initView(){
        imageLeft = (ImageView)findViewById(R.id.image_left);
        tvHeader = (TextView)findViewById(R.id.tv_header);
        tvRight = (TextView)findViewById(R.id.tv_right) ;
        splitview = findViewById(R.id.splitview);
    }

    public void setRightText(String text){
        tvRight.setText(text);
    }

    private void initListener(){
        imageLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }

    public void setHeader(String text){
        tvHeader.setText(text);
    }

    public void setSplitViewState(int visibility){
        splitview.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_left:
                onLeftClick();
                break;
            case R.id.tv_right:
                onRightClick();
                break;
        }
    }

    private void onRightClick(){
        if(headerClickEvent!=null){
            headerClickEvent.onRightClick();
        }
    }

    private void onLeftClick(){
        if(headerClickEvent!=null){
            headerClickEvent.onLeftClick();
        }
    }

    OnHeaderClickEvent headerClickEvent;
    public interface OnHeaderClickEvent{
        public void onLeftClick();
        public void onRightClick();
    }

    public void setOnHeaderClickEvent(OnHeaderClickEvent event){
        this.headerClickEvent = event;
    }
}
