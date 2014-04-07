package alisovets.example.simpletesttask.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OpenDataHelper extends SQLiteOpenHelper {
	public static final String TAG = "OpenDataHelper log";
	public static final String DATABASE_FILE_NAME = "edssonTask.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DROP_TABLE_SQL = "drop table if exists ";

	public OpenDataHelper(Context context) {
		super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(RecordDao.CREATE_SQL);
		db.execSQL(CommentDao.CREATE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

		db.execSQL(DROP_TABLE_SQL + CommentDao.TABLE_NAME);
		db.execSQL(DROP_TABLE_SQL + RecordDao.TABLE_NAME);
		onCreate(db);

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		db.execSQL("PRAGMA foreign_keys=ON;");
		super.onOpen(db);
	}

}
