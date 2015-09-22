package pontusbacklund.se.langtonsant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.view.SurfaceHolder;

import java.util.Random;

import pontusbacklund.se.langtonsant.renderables.Ant;
import pontusbacklund.se.langtonsant.renderables.Renderable;
import pontusbacklund.se.langtonsant.renderables.TileMap;

/**
 * Langtons Ant (pontusbacklund.se.langtonsant)
 * Copyright © 2015. All rights reserved
 * Created by pontus on 14/09/15
 */
public class GameThread extends Thread implements GameInterface {
    private SurfaceHolder mHolder;
    private boolean       mQuit;
    private TileMap mTileMap;
    private Ant ant;
    private Renderable ball;

    public GameThread(SurfaceHolder holder, Context context) {
        super(GameThread.class.getSimpleName());
        mHolder = holder;
        mTileMap = new TileMap(this);
        ant = new Ant(context, 50, this);
    }

    public void setSize(int width, int height) {
        mTileMap.playfield(width, height);
        ant.playfield(width,height);
    }

    @Override
    public void run() {
        mQuit = false;
        Rect dirty  = new Rect();
        RectF dirtyF = new RectF();
        double dt = 1 / 60.0; // upper-bound 60fps
        double currentTime = SystemClock.elapsedRealtime();
        while(!mQuit){
            double newTime = SystemClock.elapsedRealtime();
            double frameTime = (newTime - currentTime) / 1000.0f;
            currentTime = newTime;
            dirtyF.setEmpty();
            while(frameTime > 0.0){
                double deltaTime = Math.min(frameTime, dt);
                integrate(dirtyF, 1.0f * deltaTime);
                frameTime -= deltaTime;
            }
            dirty.set((int) dirtyF.left, (int) dirtyF.top,
                    Math.round(dirtyF.right),
                    Math.round(dirtyF.bottom));
            render(dirty);
        }
    }

    private void integrate(RectF dirty, double timeDelta) {
            mTileMap.unionRect(dirty);
            ant.unionRect(dirty);
            ant.update(dirty, timeDelta);
            ant.unionRect(dirty);
    }

    private void render(Rect dirty) {
        Canvas c = mHolder.lockCanvas(dirty);
        if(c != null){
            c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mTileMap.draw(c);
            ant.draw(c);
            mHolder.unlockCanvasAndPost(c);
        }
    }

    public void quit() {
        mQuit = true;
        try {
            this.join();
        }
        catch(InterruptedException e){
            //TODO: let's not end up here
        }
    }


    @Override
    public void didLeave(int pos) {
        mTileMap.switchmode(pos);
    }

    @Override
    public void setDirection(int direction) {
        ant.setDirection(direction);
    }
}
