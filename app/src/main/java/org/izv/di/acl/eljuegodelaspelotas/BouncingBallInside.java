package org.izv.di.acl.eljuegodelaspelotas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BouncingBallInside extends View {
    public List<Ball> balls = new ArrayList<>();

    public BouncingBallInside(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BouncingBallInside(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw the balls
        for(Ball ball : balls){
            //Move first
            ball.move(canvas);
            //Draw them
            canvas.drawOval(ball.oval,ball.paint);
        }
        invalidate(); // See note
    }
}
