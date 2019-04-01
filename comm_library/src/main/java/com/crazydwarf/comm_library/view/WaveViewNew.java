package com.crazydwarf.comm_library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private float wave_Speed;

    private int wave_Amlitude;

    private float wave_Level;

    /**
     * wave_Width,wave_Height,wave_Angle控件尺寸
     */
    private int wave_Width;
    private int wave_Height;
    private float wave_Angle;

    private static final float palstance = 0.5F;

    private static final float wave_LevelMax = 100;


    private static final int wave_LevelStart = 100;
    private boolean wave_Loop;

    private WaveHandler waveHandler;
    private WaveThread waveThread;

    private Canvas bitmapCanvas;
    private BitmapShader bitmapShader;

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
        Context context = getContext();

        //TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView);

        backgroundColor = ContextCompat.getColor(context, R.color.colorGray);
        firstWaveColor = ContextCompat.getColor(context, R.color.colorBlue);
        secondWaveColor = ContextCompat.getColor(context, R.color.colorTransBlue);
        wave_Level = 0;
        wave_Speed = 0.02f;
        wave_Amlitude = 20;

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

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);

        firstWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        firstWavePaint.setAntiAlias(true);
        firstWavePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        firstWavePaint.setColor(firstWaveColor);
        firstWavePaint.setStyle(Paint.Style.FILL);
        firstWavePaint.setStrokeWidth(10);

        secondWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondWavePaint.setAntiAlias(true);
        secondWavePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        secondWavePaint.setStyle(Paint.Style.FILL);
        secondWavePaint.setColor(secondWaveColor);

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        backgroundPaint.setShader(bitmapShader);

        wave_Level = getHeight()/2;
        wave_Height = getHeight();
        wave_Width = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //bitmapCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        firstPath.reset();
        secondPath.reset();

        firstPath.moveTo(0,0);
        secondPath.moveTo(0,0);


        int index = 0;
        while (index <= wave_Width)
        {
            float endY = (float) (Math.sin((float) index / (float) wave_Width * 2f * Math.PI +wave_Angle) * (float) wave_Amlitude + wave_Height - wave_Amlitude);
            firstPath.lineTo(index, endY);
            float endY1 = (float) (Math.sin((float) index / (float) wave_Width * 2f * Math.PI +wave_Angle+90) * (float) wave_Amlitude + wave_Height - wave_Amlitude);
            secondPath.lineTo(index, endY1);
            index++;
        }

        firstPath.lineTo(index-1,0);
        firstPath.close();

        secondPath.lineTo(index-1,0);
        secondPath.close();

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        bitmapCanvas.drawPaint(paint);

        bitmapCanvas.drawPath(firstPath, firstWavePaint);
        bitmapCanvas.drawPath(secondPath, secondWavePaint);
        //canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawRect(0,0,getWidth(),getHeight(),backgroundPaint);
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

    public float getWave_Angle() {
        return wave_Angle;
    }

    public void setWave_Angle(float wave_Angle) {
        this.wave_Angle = wave_Angle;
        invalidate();
    }
}
