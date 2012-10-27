package com.jrm.tablettournament;

import java.util.ArrayList;
import java.util.Vector;

import com.jrm.tablettournament.enumuerations.ScreenLayout;
import com.jrm.tablettournament.enumuerations.ScreenRegion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class MiniGameMatch extends SurfaceView {
	
	public MiniGameMatch(Context context) {
		super(context);
	}
	
	public abstract void start();
	public abstract void update(int ds);
	public abstract void draw(Canvas cv);
	
	protected void onAfterSetScreenDimensions(){};
	
	private Matrix [] projections = new Matrix[6];
	
	protected ScreenLayout translatorLayout = ScreenLayout.FULL;
	
	protected int view_left, view_top;
	protected int view_right, view_bottom;
	protected int view_width, view_height;
	protected int view_center_x, view_center_y;
	
	DrawThread drawThread;
	
	ArrayList<InputJoystick> joystick = new ArrayList<InputJoystick>();
	
	public void setScreenDimensions(int left, int top, int right, int bottom)
	{
		int width = right - left;
		int height = bottom - top;
		
		int cx = width/2,
			cy = height/2;
		
		// set view parameters
		view_left = left;
		view_top = top;
		view_right = right;
		view_bottom = bottom;
		view_width = width;
		view_height = height;
		view_center_x = cx;
		view_center_y = cy;
		
		// setup all projections
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
		
		this.onAfterSetScreenDimensions();
	}
	
	public void startMatch(){
		drawThread = new DrawThread(this, this.getHolder());
		drawThread.start();
	}
	
	private float [] r2s_pt_holder = new float[2];
	protected void mapPoint_RegionToScreen(ScreenRegion region, PointF point){
		r2s_pt_holder[0] = point.x;
		r2s_pt_holder[1] = point.y;
		projections[region.ordinal()].mapPoints(r2s_pt_holder);
		point.x = r2s_pt_holder[0];
		point.y = r2s_pt_holder[1];
	}
	
	protected void setToProjection(ScreenRegion region, Canvas cv){
		cv.setMatrix(projections[region.ordinal()]);
	}
	
	protected int registerJoystick(ScreenRegion region, int x, int y, int r)
	{
		
		return -1;
	}
	
	protected int registerButton(ScreenRegion region, int x, int y, int w, int h)
	{
		return -1;
	}
	
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
		default:
			break;
		}
		
		return ScreenRegion.TOP;
	}
	
	
	
	private class InputJoystick
	{
		public int cx, cy;
		public int r;
		
		public boolean containsPoint(int x, int y){
			return Math.pow(x-cx,2) + Math.pow(y-cy,2) < Math.pow(r, 2);
		}
		
		public void updateStateVectorFromPoint(float x, float y, PointF vector){
			vector.x = x - cx;
			vector.y = y - cy;
		}
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
					match.update(0);
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