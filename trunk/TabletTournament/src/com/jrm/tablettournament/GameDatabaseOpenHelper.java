package com.jrm.tablettournament;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDatabaseOpenHelper extends SQLiteOpenHelper 
{
	static final String DATABASE_NAME = "database.db";
    static final int CURRENT_VERSION = 1;
	
	public GameDatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE StateParams (StateID GUID NOT NULL, ItemName TEXT NOT NULL, ItemIndex NUMBER, ItemValue TEXT);");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
