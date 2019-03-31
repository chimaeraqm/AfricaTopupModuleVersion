package com.crazydwarf.comm_library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.crazydwarf.chimaeraqm.comm_library.R;

public class WaveViewNew extends View
{
    private Paint backgroundPaint;
    private Paint firstWavePaint;
    private Paint secondWavePaint;
    private int backgroundColor;
    private int firstWaveColor;
    private int secondWaveColor;
    private Path firstPath;
    private Path secondPath;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;

    private int wave_Speed;

    private int wave_Amlitude;

    private float wave_Level;

    /**
     * wave_Width,wave_Height,wave_Angle控件尺寸
     */
    private int wave_Width;
    private int wave_Height;
    private int wave_Angle;

    private static final float palstance = 0.5F;

    private static final float wave_LevelMax = 100;


    private static final int wave_LevelStart = 100;
    private boolean wave_Loop;

    private WaveHandler waveHandler;
    private WaveThread waveThread;

    public WaveViewNew(Context context) {
        super(context);
        initView(null);
    }

    public WaveViewNew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    void initView(AttributeSet attrs)
    {
        wave_Angle = 360;
        wave_Loop = true;
        wave_Level = 0;
        Context context = getContext();

        //TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView);

        backgroundColor = ContextCompat.getColor(context, R.color.colorGray);
        firstWaveColor = ContextCompat.getColor(context, R.color.colorBlue);
        secondWaveColor = ContextCompat.getColor(context, R.color.colorOrange);
        wave_Level = 0;
        wave_Speed = 1;
        wave_Amlitude = 10;

/*
        backgroundColor = typedArray.getColor(R.styleable.WaveView_backgroundColor, Color.parseColor("#44EEEEEE"));
        firstWaveColor = typedArray.getColor(R.styleable.WaveView_firstWaveColor, Color.parseColor("#C3F5FE"));
        secondWaveColor = typedArray.getColor(R.styleable.WaveView_secondWaveColor, Color.parseColor("#43DCFE"));
        wave_Level = typedArray.getFloat(R.styleable.WaveView_waterProgress, 0);

        wave_Amlitude = typedArray.getInt(R.styleable.WaveView_amplitude, dpToPx(20));
        wave_Speed = typedArray.getInt(R.styleable.WaveView_speed, 1);
        wave_Size = typedArray.getDimensionPixelSize(R.styleable.WaveView_waveSize, 160);
*/

        waveThread = new WaveThread();
        waveHandler = new WaveHandler();

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setAntiAlias(true);

        firstWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        firstWavePaint.setAntiAlias(true);
        firstWavePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        firstWavePaint.setColor(firstWaveColor);
        firstWavePaint.setStyle(Paint.Style.STROKE);
        firstWavePaint.setStrokeWidth(10);
        firstWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        secondWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondWavePaint.setAntiAlias(true);
        secondWavePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        secondWavePaint.setColor(secondWaveColor);
        secondWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        firstPath = new Path();
        secondPath = new Path();

        waveThread.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension(wave_Width,wave_Height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        wave_Width = getWidth();
        wave_Height = getHeight();
        bitmap = Bitmap.createBitmap(wave_Width, wave_Height, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
//        firstPath.reset();
//        firstPath.moveTo(0,0);
//        firstPath.lineTo(0, wave_Height);
//        firstPath.lineTo(wave_Width, wave_Height);
//        firstPath.lineTo(0, wave_Height);
//        firstPath.lineTo(0,0);
//
//        canvas.drawPath(firstPath, firstWavePaint);

        //canvas.drawLine(0,wave_Height,wave_Width, 0,firstWavePaint);
        bitmapCanvas.drawLine(0,wave_Height,wave_Width,0,firstWavePaint);
        Paint mPaint = new Paint();
        canvas.drawBitmap(bitmap, 0, 0, mPaint);


//        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
//        Paint mViewPaint = new Paint();
//        mViewPaint.setAntiAlias(true);
//        mViewPaint.setShader(shader);
//        canvas.drawRect(0, 0, getWidth(), getHeight(), mViewPaint);


        /*bitmapCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        firstPath.reset();
        secondPath.reset();
        float waterLine = (wave_LevelMax - wave_Level) * wave_Height * 0.01F;
        firstPath.moveTo(0,0);
        firstPath.lineTo(0, waterLine);
        secondPath.moveTo(0, waterLine);

        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        for (int i = 0; i < wave_Width; i++)
        {
            x1 = i;
            x2 = i;
            y1 = (int) (wave_Amlitude * Math.sin((i * palstance + wave_Angle) * Math.PI / 180) + waterLine);
            y2 = (int) (wave_Amlitude * Math.sin((i * palstance + wave_Angle - 90) * Math.PI / 180) + waterLine);
            //firstPath.quadTo(x1, y1, x1 + 1, y1);
            secondPath.quadTo(x2, y2, x2 + 1, y2);
        }
        firstPath.lineTo(wave_Width, waterLine);
//        firstPath.lineTo(0, wave_Height);
        firstPath.lineTo(wave_Width,0);
        firstPath.close();

        secondPath.lineTo(wave_Width, wave_Height);
        secondPath.lineTo(0, wave_Height);
        secondPath.close();

        bitmapCanvas.drawPath(firstPath, firstWavePaint);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        Paint mViewPaint = new Paint();
        mViewPaint.setAntiAlias(true);
        mViewPaint.setShader(shader);
        //bitmapCanvas.drawPath(secondPath, secondWavePaint);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawBitmap(bitmap, 0, 0, mViewPaint);*/
    }

    private final class WaveThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (wave_Loop) {
                wave_Angle = wave_Angle - 1 * wave_Speed;
                if (wave_Angle == 0) {
                    wave_Angle = 360;
                }
                waveHandler.sendEmptyMessage(wave_LevelStart);
                SystemClock.sleep(10);
            }
        }
    }

    private final class WaveHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (wave_LevelStart == msg.what) {
                invalidate();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        wave_Loop = false;
        if (waveThread != null) {
            waveThread.interrupt();
            waveThread = null;
        }
        if (waveHandler != null) {
            waveHandler.removeMessages(wave_LevelStart);
            waveHandler = null;
        }
    }
}
