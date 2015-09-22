package pontusbacklund.se.langtonsant.renderables;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Langtons Ant (pontusbacklund.se.langtonsant)
 * Copyright © 2015. All rights reserved
 * Created by pontus on 14/09/15
 */
public abstract class Renderable {
    protected final RectF mRect = new RectF();

    public abstract void playfield(int width, int height);
    public abstract void update(RectF dirty, double timeDelta);
    public abstract void draw(Canvas c);

    public final void unionRect(RectF rectF) {
        rectF.union(mRect);
    }
}
