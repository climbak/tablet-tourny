package com.jrm.tablettournament;

import com.jrm.tablettournament.minigames.mazerace.MazeRaceMatch;

import android.os.Bundle;
import android.view.Window;
import android.app.Activity;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        MiniGameMatch match = new MazeRaceMatch(this);

        setContentView(match);
        match.setScreenDimensions(0, 20, 800, 1160);
        match.startMatch();
    }
}