package com.crazydwarf.comm_library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.crazydwarf.chimaeraqm.comm_library.R;
import com.crazydwarf.comm_library.Utilities.UserUtil;

public class BitmapView extends View
{
    private Paint bgPaint;

    private BitmapShader bgShader;
    private Canvas bgCanvas;
    private Bitmap bgBitmap;
    private Bitmap oriBitmap;

    private float mOffset;
    private static final int LevelStart = 100;
    private BitmapHandler bitmapHandler;
    private BitmapThread bitmapThread;
    private boolean mLoop = true;

    private Matrix mShaderMatrix;


    public BitmapView(Context context) {
        super(context);
        init();
    }

    public BitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BitmapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        mShaderMatrix = new Matrix();

        oriBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.launch_bg);
        //bgBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        //bgCanvas = new Canvas(bgBitmap);
        bgShader = new BitmapShader(oriBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);

        mOffset = 100;
        bitmapThread = new BitmapThread();
        bitmapHandler = new BitmapHandler();
        bitmapThread.start();
    }

    private void init(AttributeSet attrs) {
        init();

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
//        int offsetX_px = 0;
//        int offsetY_px = (int)mOffset;

        mShaderMatrix.setTranslate(0,-mOffset);

        bgShader.setLocalMatrix(mShaderMatrix);

        bgPaint.setShader(bgShader);

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);

        canvas.drawRect(0,0,getWidth(),getHeight(),bgPaint);
    }

    private final class BitmapThread extends Thread {
        @Override
        public void run() {
            super.run();
            int multi = 1;
            while (mLoop) {
                mOffset = mOffset + 0.1f * multi;
                if (mOffset >= 600) {
                    multi = -1;
                }
                else if(mOffset <= 100){
                    multi = 1;
                }
                bitmapHandler.sendEmptyMessage(LevelStart);
                SystemClock.sleep(1);
            }
        }
    }

    private final class BitmapHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (LevelStart == msg.what) {
                invalidate();
            }
        }
    }
}
