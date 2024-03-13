package de.mide.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MeinDiagrammView extends View {

    private Paint paint;

    /**
     * Konstruktor
     */
    public MeinDiagrammView(Context context, AttributeSet attrs) {

        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 100, paint);
    }

}
