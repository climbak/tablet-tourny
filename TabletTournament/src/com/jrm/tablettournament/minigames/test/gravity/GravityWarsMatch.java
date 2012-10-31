package com.jrm.tablettournament.minigames.test.gravity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.jrm.tablettournament.MiniGameMatch;
import com.jrm.tablettournament.enumuerations.ScreenLayout;
import com.jrm.tablettournament.enumuerations.ScreenRegion;
import com.jrm.tablettournament.inputs.InputTouch;
import com.jrm.tablettournament.inputs.InputTouchDefinition;

public class GravityWarsMatch extends MiniGameMatch {

	final int P1 = 0;
	final int P2 = 1;
	
	
	int joystick [] = new int[2];
	int button [] = new int[2];
	
	InputTouch.State [] touch = new InputTouch.State[2];
	
	PointF proj [] = new PointF[2];
	
	ScreenRegion [] region = { ScreenRegion.TOP, ScreenRegion.BOTTOM };
	
	Paint pBackground = new Paint();
	Paint pTurret  = new Paint();
	
	PointF turretLocation;
	
	PlayerData [] datas = {new PlayerData(), new PlayerData()};
	
	public GravityWarsMatch(Context context) {
		super(context);
		
		pTurret.setColor(Color.BLUE);
		translatorLayout = ScreenLayout.MIRRORED_SPLIT;
	}

	@Override
	public void onAfterSetScreenDimensions(){
		turretLocation = new PointF(this.view_center_x, this.view_center_y - 120);
		
		PointF jsPoint = new PointF(100, this.view_center_y - 100);
		
		joystick[P1] = this.registerJoystick(region[P1], (int)jsPoint.x, (int)jsPoint.y, 80);
		joystick[P2] = this.registerJoystick(region[P2], (int)jsPoint.x, (int)jsPoint.y, 80);
		
		button[P1] = this.registerButton(region[P1], this.view_width - 100, this.view_center_y -100, 80, 80);
		button[P2] = this.registerButton(region[P2], this.view_width - 100, this.view_center_y -100, 80, 80);
		
		InputTouchDefinition touchDef = new InputTouchDefinition();
		touchDef.rect = new RectF(20, 20, this.view_width - 20, this.view_center_y - 200);
		
		touch[P1] = this.registerInputTouch(region[P1], touchDef);
		touch[P2] = this.registerInputTouch(region[P2], touchDef);
		
		pBackground.setColor(Color.BLACK);
	}
	
	
	@Override
	public void start() {
		
	}

	PointF point_p1 = new PointF(0,0);
	PointF point_p2 = new PointF(0,0);
	
	@Override
	public void update(int ds) {
		datas[P1].turretVector = this.getJoystickState(joystick[P1]);
		datas[P2].turretVector = this.getJoystickState(joystick[P2]);
		
		point_p1.x = touch[P1].getX();
		point_p1.y = touch[P1].getY();
		
		point_p2.x = touch[P2].getX();
		point_p2.y = touch[P2].getY();
		
		
	}

	@Override
	public void draw(Canvas cv) {
		// TODO Auto-generated method stub
		cv.drawPaint(pBackground);
		
		this.transformer.setToProjection(region[P1], cv);
		cv.drawCircle(turretLocation.x, turretLocation.y, 30, pTurret);
		cv.drawLine(turretLocation.x, turretLocation.y, datas[P1].turretVector.x  + turretLocation.x, datas[P1].turretVector.y + turretLocation.y, pTurret);
		cv.drawCircle(point_p1.x, point_p1.y, 30, pTurret);
		
		this.transformer.setToProjection(region[P2], cv);
		cv.drawCircle(turretLocation.x, turretLocation.y, 30, pTurret);
		cv.drawLine(turretLocation.x, turretLocation.y, datas[P2].turretVector.x  + turretLocation.x, datas[P2].turretVector.y + turretLocation.y, pTurret);
		cv.drawCircle(point_p2.x, point_p2.y, 30, pTurret);
		
	}

	private class PlayerData {
		PointF turretVector = new PointF(0,0);
	}
}
