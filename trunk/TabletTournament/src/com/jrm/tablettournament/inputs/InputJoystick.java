package com.jrm.tablettournament.inputs;


public class InputJoystick extends Input
{
	public int cx, cy;
	public int r;
	
	public boolean active;
	public float current_x, current_y;
	
	public boolean containsPoint(float x, float y){
		return Math.pow(x-cx,2) + Math.pow(y-cy,2) < Math.pow(r, 2);
	}
	
	@Override
	public void handleDownEvent(float region_x, float region_y){
		this.active = true;
		current_x = region_x - cx;
		current_y = region_y - cy;
	}
	
	@Override
	public void handleMoveEvent(float region_x, float region_y){
		current_x = region_x - cx;
		current_y = region_y - cy;
	}
	
	@Override
	public void handleUpEvent(){
		this.active = false;
	}
}

