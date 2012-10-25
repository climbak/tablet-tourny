package com.jrm.tablettournament;

import com.jrm.tablettournament.enumuerations.ScreenRegion;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public abstract class MiniGameMatch {
	public abstract void start();
	public abstract void draw(Canvas cv);
	
	private Matrix [] projections = new Matrix[6];
	
	protected int view_left, view_top;
	protected int view_right, view_bottom;
	protected int view_width, view_height;
	protected int view_center_x, view_center_y;
	
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
	
	protected void setToProjection(ScreenRegion region, Canvas cv){
		Log.d("set projection", region.ordinal() + "");
		cv.setMatrix(projections[region.ordinal()]);
		
	}
}