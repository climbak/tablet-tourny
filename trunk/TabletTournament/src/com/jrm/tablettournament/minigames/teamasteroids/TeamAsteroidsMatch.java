package com.jrm.tablettournament.minigames.teamasteroids;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.util.FloatMath;

import com.jrm.tablettournament.MiniGameMatch;
import com.jrm.tablettournament.enumuerations.ScreenLayout;
import com.jrm.tablettournament.enumuerations.ScreenRegion;
import com.jrm.tablettournament.shared.ActionDelay;
import com.jrm.tablettournament.shared.MathUtil;

public class TeamAsteroidsMatch extends MiniGameMatch
{
	Matrix [] projections;
	
	Paint pBlue = new Paint();
	Paint pGreen = new Paint();
	Paint pRed = new Paint();
	
	Paint pBackground = new Paint();
	
	int p1_jstick, p2_jstick;
	int p1_js_aim, p2_js_aim;
	
	int h_p1Delay;
	int h_p2Delay;
	
	int p1HP = 5;
	int p2HP = 5;
	
	LinkedList<Baddy> baddies = new LinkedList<Baddy>();
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	ActionDelay delay = new ActionDelay();
	
	public TeamAsteroidsMatch(Context context){
		super(context);
		
		pBlue.setColor(Color.BLUE);
		pBlue.setStyle(Style.STROKE);
		pBlue.setStrokeWidth(2);
		pBlue.setTextSize(20);
		pBlue.setAntiAlias(true);
		
		pGreen.setColor(Color.GREEN);
		pGreen.setTextSize(20);
		pGreen.setStyle(Style.STROKE);
		pGreen.setStrokeWidth(2);
		pGreen.setAntiAlias(true);
		
		pRed.setColor(Color.RED);
		pRed.setAntiAlias(true);
		pRed.setStyle(Style.STROKE);
		pRed.setStrokeWidth(4);
		
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
		Random rand = new Random();
		
		p1 = new PointF(this.view_center_x, this.view_center_y);
		p2 = new PointF(this.view_center_x, this.view_center_y);
		p1_aim = new PointF();
		p2_aim = new PointF();
		
		for (int i=0;i<10;i++)
			baddies.add(new Baddy(rand.nextInt(this.view_width), rand.nextInt(this.view_height), 40 + rand.nextInt(20)));
		
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
		
		for (Baddy bad : baddies){
			bounce(bad.point, bad.vector);
			
			bad.update();
		}
		
		// detect collisions
		for (int i=bullets.size()-1;i>=0;i--){
			Bullet b = bullets.get(i);
			
			b.x += b.vx;
			b.y += b.vy;
		
			if (outOfBounds(b.x, b.y)){
				bullets.remove(i);
				continue;
			}
			
			for (int j=baddies.size()-1;j>=0;j--){
				if (baddies.get(j).bounds.contains(b.x, b.y)){
					bullets.remove(b);
					
					Baddy next = baddies.get(j).split();
					
					if (next == null){
						baddies.remove(j);
					} else {
						baddies.add(next);
					}
				}
			}
		}
		
		for (Baddy baddy : baddies){
			if (baddy.bounds.contains(p1.x, p1.y)){
				p1HP --;
			}
			
			if (baddy.bounds.contains(p2.x, p2.y)){
				p2HP --;
			}
		}
		
	}

	public void bounce(PointF p, PointF vector){
		if (p.x < 0) vector.x = -vector.x;
		if (p.y < 0) vector.y = -vector.y;
		if (p.x > this.view_width) vector.x = -vector.x;
		if (p.y > this.view_height) vector.y = -vector.y;;	
	}
	
	public boolean outOfBounds(PointF p){
		if (p.x < 0) return true;
		if (p.y < 0) return true;
		if (p.x > this.view_width) return true;
		if (p.y > this.view_height) return true;
		return false;
	}
	
	public boolean outOfBounds(float x, float y){
		if (x < 0) return true;
		if (y < 0) return true;
		if (x > this.view_width) return true;
		if (y > this.view_height) return true;
		return false;
	}
	
	public void restrain_point(PointF p){
		if (p.x < 0) p.x = 0;
		if (p.y < 0) p.y = 0;
		if (p.x > this.view_width) p.x = this.view_width;
		if (p.y > this.view_height) p.y = this.view_height;	
	}
	
	public void draw(Canvas cv) {
		cv.drawPaint(pBackground);
	
		cv.save();
		
		drawCraft(cv, p1, p1_aim, pBlue);
		drawCraft(cv, p2, p2_aim, pGreen);

		for (Bullet bullet : bullets){
			cv.drawCircle(bullet.x, bullet.y, 5, bullet.player1 ? pBlue : pGreen);
		}
		
		this.transformer.setToProjection(ScreenRegion.BOTTOM, cv);
		cv.drawText(p2HP + "hp", 10, 10, pGreen);
		 
		this.transformer.setToProjection(ScreenRegion.TOP, cv);
		cv.drawText(p1HP + "hp", 10, 10, pBlue);
		
		cv.restore();
		
		for (Baddy baddy : baddies){
			// drawBaddy(cv, baddy);
			baddy.draw(cv);
		}
	}

    void drawBaddy(Canvas cv, Baddy b){
    	/*
    	int start = 8;
    	RectF rect = new RectF(-start, -start, start, start);
    	
    	rect.offset(b.point.x, b.point.y);
    	b.draw_angle = (b.draw_angle + 5) % 360;
        
    	float sweep = 300;
    	
    	cv.drawArc(rect, b.draw_angle, sweep, false, pRed);
    	
    	rect.inset(-5, -5);
    	cv.drawArc(rect, 360 - b.draw_angle, sweep, false, pRed);
    	
    	rect.inset(-5, -5);
    	cv.drawArc(rect, b.draw_angle, sweep, false, pRed);
    	
    	rect.inset(-5, -5);
    	cv.drawArc(rect, 360 - b.draw_angle, sweep, false, pRed);
    	*/
    	b.draw(cv);
    }
	
	
	void drawCraft(Canvas cv, PointF p, PointF p_aim, Paint paint){
		cv.save();
		
		paint.setStrokeWidth(4);
		cv.drawCircle(p.x, p.y, 5, paint);

		if (p_aim.x == 0 && p_aim.y == 0){
			
			paint.setStrokeWidth(3);
			cv.drawCircle(p.x, p.y, 10, paint);
			
			paint.setStrokeWidth(2);
			cv.drawCircle(p.x, p.y, 14, paint);
		
			paint.setStrokeWidth(7);
			cv.drawCircle(p.x, p.y, 21, paint);
			
		} else {

			double aim_rad = Math.atan2(p_aim.y, p_aim.x);
			double aim_deg = Math.toDegrees(aim_rad);
			
			float r = 10;
			RectF rect = new RectF(p.x - r, p.y - r, p.x + r, p.y + r);
			
			paint.setStrokeWidth(3);
			cv.drawArc(rect, (float)aim_deg+30, 300, false, paint);
			
			rect.inset(-4, -4);
			paint.setStrokeWidth(2);
			cv.drawArc(rect, (float)aim_deg+30, 300, false, paint);
			
			rect.inset(-7, -7);
			paint.setStrokeWidth(7);
			cv.drawArc(rect, (float)aim_deg+30, 300, false, paint);
		}
		
		paint.setStrokeWidth(2);
		cv.restore();
	}
	
	private class Baddy {
		Random rand = new Random();
		Paint paint = new Paint();
		Path poly_base = new Path();
		Path poly_current = new Path();
		Matrix trans = new Matrix();
		public RectF bounds = new RectF();
		
		public Baddy(int x, int y, int r){
			paint.setColor(Color.RED);
			paint.setStyle(Style.STROKE);
			
			point = new PointF(x,y);
			size = r;
			
			vector = new PointF(rand.nextInt(7) + 3, rand.nextInt(7) + 3);
			
			resetPath();
			
		}
		
		public void update(){
			trans.reset();
			
			point.x += vector.x;
			point.y += vector.y;
			
			draw_angle = (draw_angle + 5) % 360;
			poly_current.set(poly_base);
			
			trans.setRotate(draw_angle);
			trans.postTranslate(point.x, point.y);
			poly_current.transform(trans);
			
			poly_current.computeBounds(bounds, false);
		}
		
		public Baddy split(){
			this.size = size/2;
			
			if (size < 15) return null;
			
			Baddy other = new Baddy((int)point.x, (int)point.y, size);
			resetPath();
			return other;
		}
		
		public void resetPath(){
			poly_base.rewind();
			
			// build path
			for (float deg=0;deg<(Math.PI * 2);deg+=.5 + (.5 * rand.nextFloat())){
				PointF p = new PointF();
				float dist = (size-10) + (rand.nextFloat() * 20);
				
				p.x = FloatMath.cos(deg) * dist;
				p.y = FloatMath.sin(deg) * dist;
				
				if (deg == 0){
					poly_base.moveTo(p.x, p.y);
				} else {
					poly_base.lineTo(p.x, p.y);
				}
			}
		
			poly_base.close();
		}
		
		public void draw(Canvas cv){
			cv.drawPath(poly_current, paint);
		}
		
		public PointF point;
		public PointF vector;
		public int size;
		
		public int draw_angle = 0;
	}

	private class Bullet {
		public float x, y;
		public float vx, vy;
		boolean player1 = false;
	}
}
