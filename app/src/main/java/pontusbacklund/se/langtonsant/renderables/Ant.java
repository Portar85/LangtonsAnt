package pontusbacklund.se.langtonsant.renderables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import pontusbacklund.se.langtonsant.Constants;
import pontusbacklund.se.langtonsant.GameInterface;
import pontusbacklund.se.langtonsant.R;

/**
 * Langtons Ant (pontusbacklund.se.langtonsant)
 * Copyright © 2015. All rights reserved
 * Created by pontus on 15/09/15
 */
public class Ant extends Renderable {

    private static final String TAG = "Ant.class";

    private float velocity; //pixels per second
    private float mSize;

    private Context cxt;
    private final Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);

    private Direction direction;
    private GameInterface gameInterface;

    public void setDirection(int direction) {
        this.direction.setDirection(direction);
    }

    public int getDirection() {
        return direction.getDirection();
    }

    private class Direction  {
        private static final int LEFT = 111;
        private static final int UP = 222;
        private static final int RIGHT = 333;
        private static final int DOWN = 444;

        private int direction = RIGHT;

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }
    }

    Bitmap antBmp;
    public Ant(Context cxt, int speed, GameInterface gameInterface) {
        this.cxt = cxt;
        this.gameInterface = gameInterface;
        direction = new Direction();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        velocity = speed;
    }
    private void didLeave(int pos) {
        gameInterface.didLeave(pos);
    }
    @Override
    public void playfield(int width, int height) {
        mSize = width / Constants.MAX_TILES_X;
        Bitmap bitmap = BitmapFactory.decodeResource(cxt.getResources(), R.drawable.ant);
        antBmp = Bitmap.createScaledBitmap(bitmap, (int) mSize, (int) mSize, true);
        bitmap.recycle();
//        mRect.top = height/2 - mSize;
//        mRect.left = width/2 - mSize;
        mRect.left = 0;
        mRect.top = 0;
        Log.d(TAG, "ANT size: " + (int)mSize);
    }
    int pos = 0;
    @Override
    public void update(RectF dirty, double timeDelta) {
        if (direction.getDirection() == Direction.RIGHT) {
            mRect.left += (velocity * timeDelta);
        } else if (direction.getDirection() == Direction.DOWN) {
            mRect.top += (velocity * timeDelta);
        } else if (direction.getDirection() == Direction.LEFT) {
            mRect.left -= (velocity * timeDelta);
        } else if (direction.getDirection() == Direction.UP) {
            mRect.top -= (velocity * timeDelta);
        }
        mRect.right = mRect.left + mSize;
        mRect.bottom = mRect.top + mSize;

        if (Math.round(mRect.left) % mSize == 0) {
            int newPos = (int)(Math.round(mRect.left) / mSize);
            if (newPos != pos) {
                didLeave(pos);
                pos = newPos;
            }
        }
    }
    @Override
    public void draw(Canvas c) {
        c.drawBitmap(antBmp, mRect.left, mRect.top, paint);
    }
}
