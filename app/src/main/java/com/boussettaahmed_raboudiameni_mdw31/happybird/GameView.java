package com.boussettaahmed_raboudiameni_mdw31.happybird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import android.os.Handler;

import java.util.Random;

public class GameView extends View {

    //This our costum View class
    Handler handler; //handler is requered a shudel a runnabel after some delay
    Runnable runnable;
    final int UPDATE_MILLIS=30;
    Bitmap background;
    Bitmap toptube, bottomtube;
    Display display;
    Point point;
    int dWidth, dHeight; //Device width and height respectivly
     Rect rect;
     //a bitmap tab for the bird
     Bitmap[] birds;
    //We need a integer variable to keep track of the bird
     int birdFrame = 0;
     int velocity=0,gravity=3;
     //bird position tracing
    int birdX, birdY;
    boolean gameState = false;
    int gap = 400;//Gap between top tube and bottom tube
    int minTubeOffset, maxTubeOffset;
    int numberOfTubes = 4;
    int distanceBetweenTubes;
    int[] tubeX = new  int[numberOfTubes];
    int[] topTubeY = new  int[numberOfTubes] ;
    Random random;
    int tubeVelocity = 8;
    public GameView(Context context) {
        super(context);
    handler=new Handler();
    runnable = new Runnable() {
        @Override
        public void run() {
         invalidate(); // This will call in onDraw()

        }
    };
    background = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
    toptube = BitmapFactory.decodeResource(getResources(),R.drawable.toptube);
    bottomtube = BitmapFactory.decodeResource(getResources(),R.drawable.bottomtube);
    display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
    point = new Point();
    display.getSize(point);
    dWidth = point.x;
    dHeight = point.y;
    rect = new Rect(0,0,dWidth, dHeight);
    birds = new Bitmap[2];
    birds[0] = BitmapFactory.decodeResource(getResources(),R.drawable.bird);
    birds[1] = BitmapFactory.decodeResource(getResources(),R.drawable.bird2);
    birdX = dWidth/2 - birds[0].getWidth()/2; // center pos
        birdY = dHeight /2 - birds[0].getHeight()/2;
        distanceBetweenTubes = dWidth*3/4;
        minTubeOffset = gap/2;
        maxTubeOffset = dHeight - minTubeOffset -gap;
        random = new Random();
        for (int i=0;i<numberOfTubes;i++){
            tubeX[i] = dWidth + i*distanceBetweenTubes ;
            topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset +1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // we will draw our view inside onDraw()
        // Draw the background on canvas
        ///canvas.drawBitmap(background,0,0,null);
        canvas.drawBitmap(background,null,rect,null);//fixed
        if (birdFrame == 0){
           birdFrame = 1;
        }else {
           birdFrame = 0;
        }
        if (gameState) {
            // fix the bird
            if (birdY < dHeight - birds[0].getHeight() || velocity < 0) {//bird stay in the screen
                velocity += gravity; //bird fall = +velocity*gravity
                birdY += velocity;
            }
            for (int i=0;i<numberOfTubes;i++) {
                tubeX[i] -= tubeVelocity;
                if(tubeX[i] < -toptube.getWidth()) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset +1);
                }
                canvas.drawBitmap(toptube, tubeX[i], topTubeY[i] - toptube.getHeight(), null);
                canvas.drawBitmap(bottomtube, tubeX[i], topTubeY[i] + gap, null);
            }
            }


        //display in center of device
        //bird[1] bird[2] same dimention
        canvas.drawBitmap(birds[birdFrame],birdX, birdY,null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    //touch even


    @Override
    public boolean onTouchEvent(MotionEvent event) {

     int action = event.getAction();
     if(action == MotionEvent.ACTION_DOWN){ // tap detection
         //bird move by some unit
       velocity = -30;//30 unit
         gameState = true;
     }

        return true; //true = done with touch even + no action by android
    }
}
