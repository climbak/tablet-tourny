package com.jrm.tablettournament;

import com.example.tablettournament.R;
import com.jrm.tablettournament.minigames.mazerace.MazeRaceMatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        MazeRaceMatch match = new MazeRaceMatch(this);
        setContentView(match);
        match.setScreenDimensions(0, 100, 800, 1180);
        match.startMatch();
    }
}
