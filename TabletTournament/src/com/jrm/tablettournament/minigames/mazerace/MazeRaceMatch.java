package com.jrm.tablettournament.minigames.mazerace;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jrm.tablettournament.MiniGameMatch;
import com.jrm.tablettournament.enumuerations.ScreenLayout;
import com.jrm.tablettournament.enumuerations.ScreenRegion;
import com.jrm.tablettournament.shared.ActionDelay;
import com.jrm.tablettournament.shared.MathUtil;

public class MazeRaceMatch extends MiniGameMatch
{
	Matrix [] projections;
	
	Paint pBlue = new Paint();
	Paint pRed = new Paint();
	Paint pBackground = new Paint();
	
	int p1_jstick, p2_jstick;
	int p1_js_aim, p2_js_aim;
	
	int h_p1Delay;
	int h_p2Delay;
	
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	ActionDelay delay = new ActionDelay();
	
	public MazeRaceMatch(Context context){
		super(context);
		pBlue.setColor(Color.BLUE);
		pBlue.setTextSize(20);
		pRed.setColor(Color.RED);
		pRed.setTextSize(40);
		pBackground.setColor(Color.BLACK);
		translatorLayout = ScreenLayout.MIRRORED_SPLIT;
		
		p1_jstick = this.registerJoystick(ScreenRegion.TOP, 100, 450, 80);
		p2_jstick = this.registerJoystick(ScreenRegion.BOTTOM, 100, 450, 80);
		
		p1_js_aim = this.registerJoystick(ScreenRegion.TOP, 700, 450, 80);
		p2_js_aim = this.registerJoystick(ScreenRegion.BOTTOM, 700, 450, 80);
		
		this.registerButton(ScreenRegion.TOP, 40, 40, 100, 100);
		
		h_p1Delay = delay.addDelay(1000);
		h_p2Delay = delay.addDelay(1000);
	}
	
	
	@Override
	protected void onAfterSetScreenDimensions(){
		p1 = new PointF(this.view_center_x, this.view_center_y);
		p2 = new PointF(this.view_center_x, this.view_center_y);
		p1_aim = new PointF();
		p2_aim = new PointF();
	}
	
	public void start() {
		
	}
	
	
	PointF p1, p2;
	PointF p1_aim, p2_aim;
	
	public void update(int ds){
		PointF p1_js = this.getJoystickState(p1_jstick);
		PointF p2_js = this.getJoystickState(p2_jstick);
		
		p1_aim = this.getJoystickState(p1_js_aim);
		p2_aim = this.getJoystickState(p2_js_aim);
		
		p1_aim.x = -p1_aim.x;
		p1_aim.y = -p1_aim.y;
		
		p1_js.x *= .3;
		p1_js.y *= .3;
		
		p2_js.x *= .3;
		p2_js.y *= .3;
		
		p1.x -= p1_js.x;
		p1.y -= p1_js.y;
		
		p2.x += p2_js.x;
		p2.y += p2_js.y;
		
		restrain_point(p1);
		restrain_point(p2);
		
		
		if (p1_aim.x != 0 && p1_aim.y != 0){
			if (delay.get(h_p1Delay)){
				Bullet b = new Bullet();
				
				b.x = p1.x;
				b.y = p1.y;
				
				b.vx = p1_aim.x / 2;
				b.vy = p1_aim.y / 2;
				
				b.player1 = true;
				
				bullets.add(b);
			}
		}
		
		if (p2_aim.x != 0 && p2_aim.y != 0){
			if (delay.get(h_p2Delay)){
				Bullet b = new Bullet();
				
				b.x = p2.x;
				b.y = p2.y;
				
				b.vx = p2_aim.x / 2;
				b.vy = p2_aim.y / 2;
				
				b.player1 = false;
				
				bullets.add(b);
			}
		}
		
		ArrayList<Bullet> delete = new ArrayList<Bullet>();
		
		for (Bullet bullet : bullets){
			bullet.x += bullet.vx;
			bullet.y += bullet.vy;
			
			
			// detect collisions
			if (MathUtil.pointsClose(bullet.x, bullet.y, p1.x, p1.y, 5))
			{
				delete.add(bullet);
			} 
			else if (MathUtil.pointsClose(bullet.x, bullet.y, p2.x, p2.y, 5))
			{
				delete.add(bullet);
			} 
			else if (bullet.x < 0 || bullet.y < 0) delete.add(bullet);
			else if (bullet.y > this.view_height || bullet.x > this.view_width) delete.add(bullet); 

			
		}
		
		for (Bullet b : delete){
			bullets.remove(b);
		}
	}

	public void restrain_point(PointF p){
		if (p.x < 0) p.x = 0;
		if (p.y < 0) p.y = 0;
		if (p.x > this.view_width) p.x = this.view_width;
		if (p.y > this.view_height) p.y = this.view_height;	
	}
	
	public void draw(Canvas cv) {
		cv.drawPaint(pBackground);
		
		
		// draw_top_left(cv, ScreenRegion.TOP);
		cv.drawCircle(p1.x, p1.y, 20, pBlue);
		cv.drawLine(p1.x, p1.y, p1.x + p1_aim.x, p1.y + p1_aim.y, pBlue);
		
		//draw_top_left(cv, ScreenRegion.BOTTOM);
		cv.drawCircle(p2.x,  p2.y, 20, pRed);
		cv.drawLine(p2.x, p2.y, p2.x + p2_aim.x, p2.y + p2_aim.y, pRed);

		for (Bullet bullet : bullets){
			cv.drawCircle(bullet.x, bullet.y, 5, bullet.player1 ? pBlue : pRed);
		}
	}

	ScreenRegion testRegion = ScreenRegion.TOP;
	
	public void draw_top_left(Canvas cv, ScreenRegion region){
		setToProjection(region, cv);
		
		int middle_x = this.view_center_x;
		int middle_y = this.view_center_y/2;
		
		// cv.drawRect(0, 0, i, i, (testRegion == region)? pBlue : pRed);
		cv.drawLine(1, 1, 40, 1, pBlue);
		cv.drawLine(1, 1, 1, 40, pBlue);
		cv.drawText("Projection " + region, 2, 20, pBlue);
		
	}

	private class Bullet {
		public float x, y;
		public float vx, vy;
		boolean player1 = false;
	}
}
