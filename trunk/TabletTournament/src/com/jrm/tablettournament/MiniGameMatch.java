package com.jrm.tablettournament;

import com.jrm.tablettournament.enumuerations.ScreenLayout;
import com.jrm.tablettournament.enumuerations.ScreenRegion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class MiniGameMatch extends SurfaceView {
	
	public MiniGameMatch(Context context) {
		super(context);
	}
	
	public abstract void start();
	public abstract void draw(Canvas cv);
	
	private Matrix [] projections = new Matrix[6];
	
	protected ScreenLayout translatorLayout = ScreenLayout.FULL;
	
	protected int view_left, view_top;
	protected int view_right, view_bottom;
	protected int view_width, view_height;
	protected int view_center_x, view_center_y;
	
	DrawThread drawThread;
	
	public void setScreenDimensions(int left, int top, int right, int bottom)
	{
		int width = right - left;
		int height = bottom - top;
		
		int cx = width/2,
			cy = height/2;
		
		view_left = left;
		view_top = top;
		view_right = right;
		view_bottom = bottom;
		view_width = width;
		view_height = height;
		view_center_x = cx;
		view_center_y = cy;
		
		Matrix topMatrix = new Matrix();
		topMatrix.setScale(-1, -1);
		topMatrix.postTranslate(left + cx, top + cy);
		
		projections[ScreenRegion.TOP_LEFT.ordinal()] = topMatrix;
		

		Matrix bottomMatrix = new Matrix();
		bottomMatrix.postTranslate(cx + left, cy + top);
		
		projections[ScreenRegion.BOTTOM_RIGHT.ordinal()] = bottomMatrix;
		
		
		Matrix bottomLeftMatrix = new Matrix();
		bottomLeftMatrix.setTranslate(0 + left, cy + top);
		
		projections[ScreenRegion.BOTTOM.ordinal()] = bottomLeftMatrix;
		projections[ScreenRegion.BOTTOM_LEFT.ordinal()] = bottomLeftMatrix;
		
		
		Matrix topRightMatrix = new Matrix();
		topRightMatrix.setScale(-1, -1);
		topRightMatrix.postTranslate(left + width, top + cy);
		
		projections[ScreenRegion.TOP.ordinal()] = topRightMatrix;
		projections[ScreenRegion.TOP_RIGHT.ordinal()] = topRightMatrix;
	}
	
	public void startMatch(){
		drawThread = new DrawThread(this, this.getHolder());
		drawThread.start();
	}
	
  // set canvas transformation matrix to map points onto the specified region
	protected void setToProjection(ScreenRegion region, Canvas cv){
		cv.setMatrix(projections[region.ordinal()]);
	}
	
  // add joystick to region at point {x,y} radius r -- returns handle to joystick
  // {x,y} will be automatically transformed to be positioned correctly on the screen
  // based on the region
  protected int addJoystickToRegion(ScreenRegion region, int x, int y, int r)
  {
    // TODO : write this method
    return -1;
  }
  
  // add button to region at point (same as joystick) -- returns handle to button
  protected int addButtonToRegion(ScreenRegion region, int x, int y, int width, int height){
    // TODO : write this method
    return -1;
  }
  
  // given the button handle, return true if the button is pressed, false if not
  protected bool getButtonPressed(int btn_handle){
    // TODO : write this method
    return false;
  }
  
  // get region that the point lies on.  NOTE
  // ALTERNATIVE :
  // consider changing this to getRegionFromPoint_SPLIT and getRegionFromPoint_QUARTERED
  // to remove the need for setting the translator layout since each game will know what
  // its layout is
	protected ScreenRegion getRegionFromPoint(int x, int y)
	{
		switch (translatorLayout){
			case MIRRORED_SPLIT:
				if (y - view_top < view_center_y)
				{
					return ScreenRegion.TOP;
				} else {
					return ScreenRegion.BOTTOM;
				}
			case MIRRORED_QUARTERED:
				if (y - view_top < view_center_y)
				{
					if (x - view_left < view_center_x){
						return ScreenRegion.TOP_LEFT;
					} else {
						return ScreenRegion.TOP_RIGHT;
					}
					
				} else {
					if (x - view_left < view_center_x){
						return ScreenRegion.BOTTOM_LEFT;
					} else {
						return ScreenRegion.BOTTOM_RIGHT;
					}
				}
		}
		
		return ScreenRegion.TOP;
	}
	
	private class DrawThread extends Thread {
		private SurfaceHolder surfHolder;
		private MiniGameMatch match;
		
		public DrawThread(MiniGameMatch match, SurfaceHolder surfHolder){
			this.surfHolder = surfHolder;
			this.match = match;
		}
		
		@Override
		public void run(){
			
			// wait for canvas to not be null
			Canvas canvas = surfHolder.lockCanvas();
			while (canvas == null) canvas = surfHolder.lockCanvas();
			surfHolder.unlockCanvasAndPost(canvas);
			
			for (int j=0;j<30;j++)
				for (int i=0;i<300;i++){
					canvas = surfHolder.lockCanvas();
					match.draw(canvas);
					surfHolder.unlockCanvasAndPost(canvas);
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
	}
}