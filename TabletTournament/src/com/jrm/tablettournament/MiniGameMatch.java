package com.jrm.tablettournament;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jrm.tablettournament.enumuerations.ScreenLayout;
import com.jrm.tablettournament.enumuerations.ScreenRegion;
import com.jrm.tablettournament.inputs.Input;
import com.jrm.tablettournament.inputs.InputButton;
import com.jrm.tablettournament.inputs.InputJoystick;

public abstract class MiniGameMatch extends SurfaceView {
	
	public MiniGameMatch(Context context) {
		super(context);
	}
	
	public abstract void start();
	public abstract void update(int ds);
	public abstract void draw(Canvas cv);
	
	protected void onAfterSetScreenDimensions(){};
	
	private Matrix [] projections = new Matrix[6];
	private Matrix [] translators = new Matrix[6];
	
	protected ScreenLayout translatorLayout = ScreenLayout.FULL;
	
	protected int view_left, view_top;
	protected int view_right, view_bottom;
	protected int view_width, view_height;
	protected int view_center_x, view_center_y;
	
	DrawThread drawThread;
	
	ArrayList<Input> inputs = new ArrayList<Input>();
	ArrayList<Input> inputTracking = new ArrayList<Input>(10);
	
	public void setScreenDimensions(int left, int top, int right, int bottom)
	{
		int width = right - left;
		int height = bottom - top;
		
		// set view parameters
		view_left = left;
		view_top = top;
		view_right = right;
		view_bottom = bottom;
		view_width = width;
		view_height = height;
		view_center_x = width/2;
		view_center_y = height/2;
		
		setupProjections();
		setupTranslators();
		
		this.onAfterSetScreenDimensions();
	}
	
	private void setupProjections(){
		// setup all projections
		Matrix topMatrix = new Matrix();
		topMatrix.setScale(-1, -1);
		topMatrix.postTranslate(view_left + view_center_x, view_top + view_center_y);
		
		projections[ScreenRegion.TOP_LEFT.ordinal()] = topMatrix;
		

		Matrix bottomMatrix = new Matrix();
		bottomMatrix.postTranslate(view_center_x + view_left, view_center_y + view_top);
		
		projections[ScreenRegion.BOTTOM_RIGHT.ordinal()] = bottomMatrix;
		
		
		Matrix bottomLeftMatrix = new Matrix();
		bottomLeftMatrix.setTranslate(0 + view_left, view_center_y + view_top);
		
		projections[ScreenRegion.BOTTOM.ordinal()] = bottomLeftMatrix;
		projections[ScreenRegion.BOTTOM_LEFT.ordinal()] = bottomLeftMatrix;
		
		
		Matrix topRightMatrix = new Matrix();
		topRightMatrix.setScale(-1, -1);
		topRightMatrix.postTranslate(view_left + view_width, view_top + view_center_y);
		
		projections[ScreenRegion.TOP.ordinal()] = topRightMatrix;
		projections[ScreenRegion.TOP_RIGHT.ordinal()] = topRightMatrix;
	}

	
	private void setupTranslators(){
		// setup all translators
		Matrix topMatrix = new Matrix();
		projections[ScreenRegion.TOP_LEFT.ordinal()].invert(topMatrix);
		translators[ScreenRegion.TOP_LEFT.ordinal()] = topMatrix;
		
		Matrix bottomMatrix = new Matrix();
		projections[ScreenRegion.BOTTOM_RIGHT.ordinal()].invert(bottomMatrix);
		translators[ScreenRegion.BOTTOM_RIGHT.ordinal()] = bottomMatrix;
		
		Matrix bottomLeftMatrix = new Matrix();
		projections[ScreenRegion.BOTTOM.ordinal()].invert(bottomLeftMatrix);
		translators[ScreenRegion.BOTTOM.ordinal()] = bottomLeftMatrix;
		translators[ScreenRegion.BOTTOM_LEFT.ordinal()] = bottomLeftMatrix;
		
		Matrix topRightMatrix = new Matrix();
		projections[ScreenRegion.TOP.ordinal()].invert(topRightMatrix);
		translators[ScreenRegion.TOP.ordinal()] = topRightMatrix;
		translators[ScreenRegion.TOP_RIGHT.ordinal()] = topRightMatrix;
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
	
	private float [] s2r_pt_holder = new float[2];
	protected void mapPoint_ScreenToRegion(ScreenRegion region, PointF point){
		s2r_pt_holder[0] = point.x;
		s2r_pt_holder[1] = point.y;
		translators[region.ordinal()].mapPoints(s2r_pt_holder);
		point.x = s2r_pt_holder[0];
		point.y = s2r_pt_holder[1];
	}
	
	protected void setToProjection(ScreenRegion region, Canvas cv){
		cv.setMatrix(projections[region.ordinal()]);
	}
	
	protected int registerButton(ScreenRegion region, int x, int y, int width, int height){
		InputButton button = new InputButton(x,y,width,height);
		button.handle = inputs.size();
		button.region = region;
		inputs.add(button);
		return button.handle;
	}
	
	protected boolean getButtonDown(int handle){
		return ((InputButton)inputs.get(handle)).active;
	}
	
	// adds a joystick and returns a "handle" to that joystick
	protected int registerJoystick(ScreenRegion region, int x, int y, int r)
	{
		InputJoystick joystick = new InputJoystick();
		
		joystick.handle = inputs.size();
		joystick.cx = x;
		joystick.cy = y;
		joystick.r = r;
		joystick.region = region;
		
		inputs.add(joystick);
		
		return joystick.handle;
	}
	
	protected PointF getJoystickState(int handle)
	{
		InputJoystick js = (InputJoystick)inputs.get(handle);
		
		if (js.active){
			return new PointF(js.current_x, js.current_y);
		} else {
			return new PointF();
		}
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
	

	private void handleActionDown(MotionEvent event){
		int pid = event.getPointerId(event.getActionIndex());
		Log.d("ACTION", "DOWN " + pid);
		
		for (int i=inputTracking.size();i<=pid;i++) inputTracking.add(null);
		
		if (inputTracking.get(pid) != null){
			return;
		}
		
		int pindex = event.findPointerIndex(pid);
		
		float screen_x = event.getX(pindex);
		float screen_y = event.getY(pindex);
		
		ScreenRegion region = this.getRegionFromPoint((int)screen_x, (int)screen_y);
		
		PointF p = new PointF(screen_x, screen_y);
		this.mapPoint_ScreenToRegion(region, p);

		Input input = findInputAtPoint(region, p.x, p.y);
		
		if (input == null) return; // no inputs, lets get out of here
		
		Log.d("ACTION", "DOWN (INPUT)" + input.handle);
		
		input.handleDownEvent(p.x, p.y);
		
		inputTracking.set(pid, input);
	}
	
	private void handleActionUp(MotionEvent event){
		int pid = event.getPointerId(event.getActionIndex());
		Log.d("ACTION", "UP "  + pid);
		
		Input input = inputTracking.get(pid);
		
		if (input != null)
			input.handleUpEvent();
		
		inputTracking.set(pid, null);
	}
	
	private void handleActionMove(MotionEvent event){
		for (int pindex=0;pindex<event.getPointerCount();pindex++){
			
			int pid = event.getPointerId(pindex);
			
			Input input = inputTracking.get(pid);
			
			// no input for this pointer, continue
			if (input == null) continue;
			
			float screen_x = event.getX(pindex);
			float screen_y = event.getY(pindex);
			
			ScreenRegion region = this.getRegionFromPoint((int)screen_x, (int)screen_y);
			
			PointF ptRegion = new PointF(screen_x, screen_y);
			this.mapPoint_ScreenToRegion(region, ptRegion);

			input.handleMoveEvent(ptRegion.x, ptRegion.y);
		}
	}
	
	private Input findInputAtPoint(ScreenRegion region, float region_x, float region_y)
	{
		for (Input input : inputs){
			if (input.region == region){
				if (input.containsPoint(region_x, region_y))
				{
					return input;
				}
			}
		}
		
		return null;
	}
	

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		switch (event.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				handleActionDown(event);
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				handleActionUp(event);
				break;
			case MotionEvent.ACTION_MOVE:
				handleActionMove(event);
				break;
		}
		
	    return true;
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
			
			while (true){
				canvas = surfHolder.lockCanvas();
				
				if (canvas == null) break;
				
				canvas.save();
				
				match.update(0);
				match.draw(canvas);
				
				canvas.restore();
				
				for (Input input : inputs){
					match.setToProjection(input.region, canvas);
					input.draw(canvas);
				}
				
				surfHolder.unlockCanvasAndPost(canvas);
				
				try {
					Thread.sleep(33);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}