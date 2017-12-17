package com.jiaye.guoke.base.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jiaye.guoke.R;

/**
 * Created by Administrator on 2017/12/12.
 */

public class RoundCornersImageView extends ImageView {
    private float radius;

    public RoundCornersImageView(Context context) {
        this(context,null);
    }

    public RoundCornersImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundCornersImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundCornersImageView, defStyleAttr, 0);
        radius = a.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, 58);
    }

    /**
     *
     * @param radius 弧度
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        RectF rectF = new RectF(rect);
        path.addRoundRect(rectF, radius, radius, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.REPLACE);//Op.REPLACE这个范围内的都将显示，超出的部分覆盖
        super.onDraw(canvas);
    }
}