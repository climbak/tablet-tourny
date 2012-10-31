package com.jrm.tablettournament;

import com.jrm.tablettournament.enumuerations.ScreenRegion;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;

public class RegionTransformer {
	private Matrix [] projections = new Matrix[6];
	private Matrix [] translators = new Matrix[6];
	private int [] widths = new int[6];
	private int [] heights = new int[6];
	
	
	public void setScreenDimensions(int left, int top, int right, int bottom){
		int width = right - left;
		int height = bottom - top;
		
		int cx = width/2;
		int cy = height/2;
		
		widths[ScreenRegion.TOP.ordinal()] = 
				widths[ScreenRegion.BOTTOM.ordinal()] = width;
		
		widths[ScreenRegion.TOP_LEFT.ordinal()] = 
				widths[ScreenRegion.BOTTOM_RIGHT.ordinal()] = 
				widths[ScreenRegion.BOTTOM_RIGHT.ordinal()]= 
				widths[ScreenRegion.BOTTOM_RIGHT.ordinal()] = width/2;
		
		
		heights[ScreenRegion.TOP.ordinal()] = 
				widths[ScreenRegion.BOTTOM.ordinal()] = height/2;
		
		heights[ScreenRegion.TOP_LEFT.ordinal()] = 
				widths[ScreenRegion.BOTTOM_RIGHT.ordinal()] = 
				widths[ScreenRegion.BOTTOM_RIGHT.ordinal()]= 
				widths[ScreenRegion.BOTTOM_RIGHT.ordinal()] = height/2;
		
		
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
		
		// setup all translators
		topMatrix = new Matrix();
		projections[ScreenRegion.TOP_LEFT.ordinal()].invert(topMatrix);
		translators[ScreenRegion.TOP_LEFT.ordinal()] = topMatrix;
		
		bottomMatrix = new Matrix();
		projections[ScreenRegion.BOTTOM_RIGHT.ordinal()].invert(bottomMatrix);
		translators[ScreenRegion.BOTTOM_RIGHT.ordinal()] = bottomMatrix;
		
		bottomLeftMatrix = new Matrix();
		projections[ScreenRegion.BOTTOM.ordinal()].invert(bottomLeftMatrix);
		translators[ScreenRegion.BOTTOM.ordinal()] = bottomLeftMatrix;
		translators[ScreenRegion.BOTTOM_LEFT.ordinal()] = bottomLeftMatrix;
		
		topRightMatrix = new Matrix();
		projections[ScreenRegion.TOP.ordinal()].invert(topRightMatrix);
		translators[ScreenRegion.TOP.ordinal()] = topRightMatrix;
		translators[ScreenRegion.TOP_RIGHT.ordinal()] = topRightMatrix;
	}

	
	private float [] r2s_pt_holder = new float[2];
	public void mapPoint_RegionToScreen(ScreenRegion region, PointF point){
		r2s_pt_holder[0] = point.x;
		r2s_pt_holder[1] = point.y;
		projections[region.ordinal()].mapPoints(r2s_pt_holder);
		point.x = r2s_pt_holder[0];
		point.y = r2s_pt_holder[1];
	}
	
	private float [] s2r_pt_holder = new float[2];
	public void mapPoint_ScreenToRegion(ScreenRegion region, PointF point){
		s2r_pt_holder[0] = point.x;
		s2r_pt_holder[1] = point.y;
		translators[region.ordinal()].mapPoints(s2r_pt_holder);
		point.x = s2r_pt_holder[0];
		point.y = s2r_pt_holder[1];
	}
	
	public void mapPoint_ScreenToRegion(ScreenRegion region, PointF point, boolean reverse_x, boolean reverse_y){
		if (reverse_y){
			point.y = heights[region.ordinal()] - point.y;
		}
		
		if (reverse_x){
			point.x = widths[region.ordinal()] - point.x;
		}
		
		this.mapPoint_ScreenToRegion(region, point);
	}
	
	public void setToProjection(ScreenRegion region, Canvas cv){
		cv.setMatrix(projections[region.ordinal()]);
	}
	
}
