package pontusbacklund.se.langtonsant;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import pontusbacklund.se.langtonsant.renderables.Renderable;

/**
 * Langtons Ant (pontusbacklund.se.langtonsant)
 * Copyright © 2015. All rights reserved
 * Created by pontus on 14/09/15
 */
public class Background extends Renderable {
    private final Paint mBg = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Background() {
        mBg.setColor(Color.parseColor("#ff6600"));
    }

    @Override
    public void playfield(int width, int height) {

    }

    @Override
    public void update(RectF dirty, double timeDelta) {

    }

    @Override
    public void draw(Canvas c) {
        c.drawPaint(mBg);
    }
}
