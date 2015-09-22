package pontusbacklund.se.langtonsant.renderables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Arrays;

import pontusbacklund.se.langtonsant.Constants;
import pontusbacklund.se.langtonsant.GameInterface;

/**
 * Langtons Ant (pontusbacklund.se.langtonsant)
 * Copyright © 2015. All rights reserved
 * Created by pontus on 14/09/15
 */
public class TileMap extends Renderable {


    Paint mPaintColored = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mPaintBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int tileSize;
    private int maxTilesHeight;
    private Tile[] tiles;
    private GameInterface gameInterface;

    public TileMap(GameInterface gameInterface) {
        mPaintColored.setStyle(Paint.Style.FILL);
        mPaintBlack.setStyle(Paint.Style.FILL);
//        mPaintColored.setStrokeWidth(5f);
        mPaintColored.setColor(Color.parseColor("#ff4411"));
        mPaintBlack.setColor(Color.parseColor("#00ff00"));
        this.gameInterface = gameInterface;
    }
    private class Tile {
        private boolean isColored = true;

        public boolean isColored() {
            return isColored;
        }

        public void setIsColored(boolean isColored) {
            this.isColored = isColored;
        }
    }
    public void switchmode(int pos) {
        Tile t = tiles[pos];
        t.setIsColored(!t.isColored);
        gameInterface.setDirection(444);
        //TODO: Continue here
    }

    @Override
    public void playfield(int width, int height) {
        tileSize = width / Constants.MAX_TILES_X;
        maxTilesHeight = (int) Math.floor(height/tileSize);
        int maxTiles = Constants.MAX_TILES_X * maxTilesHeight;
        tiles = new Tile[maxTiles];
        Arrays.fill(tiles, new Tile());
    }

    @Override
    public void update(RectF dirty, double timeDelta) {

    }

    @Override
    public void draw(Canvas c) {
        int leftMaxCheck = 0;
        int row = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (i % Constants.MAX_TILES_X == 0 && i != 0) {
                row++;
                leftMaxCheck = 0;
            } else if (i != 0){
                leftMaxCheck++;
            }
            int left = leftMaxCheck * tileSize;
            int right = tileSize + (leftMaxCheck * tileSize);
            int top = tileSize * row;
            int bottom = tileSize + (tileSize * row);
            if (tiles[i].isColored) {
                c.drawRect(left,top,right,bottom, mPaintColored);

            } else {
                c.drawRect(left, top, right, bottom, mPaintBlack);
            }
        }
    }
}
