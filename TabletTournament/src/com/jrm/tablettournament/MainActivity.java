package com.jrm.tablettournament;

import android.app.Activity;
import android.os.Bundle;

import com.jrm.tablettournament.minigames.tictactoe.TicTacToeMatch;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        TicTacToeMatch match = new TicTacToeMatch(this);
        setContentView(match);
        match.setScreenDimensions(0, 100, 800, 1180);
        match.startMatch();
    }
}