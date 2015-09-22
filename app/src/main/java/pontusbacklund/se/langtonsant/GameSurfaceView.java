package pontusbacklund.se.langtonsant;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Langtons Ant (pontusbacklund.se.langtonsant)
 * Copyright © 2015. All rights reserved
 * Created by pontus on 14/09/15
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread mThread;
    private Context context;
    public GameSurfaceView(Context context) {
        super(context);
        this.context = context;
        setKeepScreenOn(true);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new GameThread(holder, context);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mThread.setSize(width, height);
        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
        {
            mThread.quit();
        }
}
