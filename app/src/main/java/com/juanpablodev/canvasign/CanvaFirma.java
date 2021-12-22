package com.juanpablodev.canvasign;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class CanvaFirma extends View {

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    Context context;
    public int width;
    public int height;
    private Bitmap bitmap;
    private Canvas caja;
    private Path patron;
    private Paint dibujo, dibujoCaja;
    private int paintColor = 0xFF660000;

    public CanvaFirma(Context context) {
        super(context);
        this.context = context;
        patron = new Path();
        dibujo = new Paint();
        dibujo.setColor(paintColor);
        dibujo.setAntiAlias(true);
        dibujo.setStrokeWidth(20);
        dibujo.setStyle(Paint.Style.STROKE);
        dibujo.setStrokeJoin(Paint.Join.ROUND);
        dibujo.setStrokeCap(Paint.Cap.ROUND);

        dibujoCaja = new Paint(Paint.DITHER_FLAG);
    }

    public void limpiarCaja() {
        caja.drawColor(Color.WHITE);
        invalidate();
    }

    public void getBase64() {
        Bitmap b = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        this.layout(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
        this.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String firmaBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("bits guardados",firmaBase64);
        caja.drawColor(Color.WHITE);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        caja = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRGB(251, 254, 213);
        canvas.drawBitmap(bitmap, 0, 0, dibujoCaja);
        canvas.drawPath(patron, dibujo);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("Touch", "inicia touch");
                patron.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("Touch", "dibujando");
                patron.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("Touch", "termina touch");
                caja.drawPath(patron, dibujo);
                patron.reset();
                getBase64();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
}
