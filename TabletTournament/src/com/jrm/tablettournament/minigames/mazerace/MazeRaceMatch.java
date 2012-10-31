package com.jrm.tablettournament.minigames.mazerace;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.Region.Op;

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
	
	int p1HP = 5;
	int p2HP = 5;
	
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	ActionDelay delay = new ActionDelay();
	
	public MazeRaceMatch(Context context){
		super(context);
		
		pBlue.setColor(Color.BLUE);
		pBlue.setStyle(Style.STROKE);
		pBlue.setStrokeWidth(2);
		pBlue.setTextSize(20);
		pBlue.setAntiAlias(true);
		
		pRed.setColor(Color.RED);
		pRed.setTextSize(20);
		pRed.setStyle(Style.STROKE);
		pRed.setStrokeWidth(2);
		pRed.setAntiAlias(true);
		
		pBackground.setColor(Color.BLACK);
		translatorLayout = ScreenLayout.MIRRORED_SPLIT;
		
		p1_jstick = this.registerJoystick(ScreenRegion.TOP, 100, 450, 80);
		p2_jstick = this.registerJoystick(ScreenRegion.BOTTOM, 100, 450, 80);
		
		p1_js_aim = this.registerJoystick(ScreenRegion.TOP, 700, 450, 80);
		p2_js_aim = this.registerJoystick(ScreenRegion.BOTTOM, 700, 450, 80);
		
		// this.registerButton(ScreenRegion.TOP, 40, 40, 100, 100);
		
		h_p1Delay = delay.addDelay(200);
		h_p2Delay = delay.addDelay(200);
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
			if (!bullet.player1 && MathUtil.pointsClose(bullet.x, bullet.y, p1.x, p1.y, 20))
			{
				delete.add(bullet);
				p1HP--;
			} 
			else if (bullet.player1 && MathUtil.pointsClose(bullet.x, bullet.y, p2.x, p2.y, 20))
			{
				delete.add(bullet);
				p2HP--;
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
		
		//draw_top_left(cv, ScreenRegion.BOTTOM);
		drawCraft(cv, p1, p1_aim, pBlue);
		drawCraft(cv, p2, p2_aim, pRed);
		
		

		for (Bullet bullet : bullets){
			cv.drawCircle(bullet.x, bullet.y, 5, bullet.player1 ? pBlue : pRed);
		}
		
		this.transformer.setToProjection(ScreenRegion.BOTTOM, cv);
		cv.drawText(p2HP + "hp", 10, 10, pRed);
		 
		this.transformer.setToProjection(ScreenRegion.TOP, cv);
		cv.drawText(p1HP + "hp", 10, 10, pBlue);
	}
	
	private void drawCraft(Canvas cv, PointF p, PointF p_aim, Paint paint){
		cv.save();
		
		paint.setStrokeWidth(4);
		cv.drawCircle(p.x, p.y, 5, paint);
		paint.setStrokeWidth(3);
		cv.drawCircle(p.x, p.y, 10, paint);
		paint.setStrokeWidth(2);
		cv.drawCircle(p.x, p.y, 14, paint);
		
		Path path = new Path();
		path.addCircle(p.x, p.y, 15, Direction.CCW);
		
		
		cv.clipPath(path, Op.DIFFERENCE);
		
		paint.setStrokeWidth(5);
		cv.drawLine(p.x, p.y, p.x + p_aim.x, p.y + p_aim.y, paint);
		
		paint.setStrokeWidth(2);
		cv.restore();
	}
	

	private class Bullet {
		public float x, y;
		public float vx, vy;
		boolean player1 = false;
	}
}
