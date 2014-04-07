package alisovets.example.simpletesttask.data;

import java.util.ArrayList;
import java.util.List;

import alisovets.example.simpletesttask.dto.Comment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * DAO class to exchange Comment objects from the database
 * 
 * @author Alexander Lisovets
 *
 */
public class CommentDao {
	private final static String TAG = CommentDao.class.getSimpleName();
	public final static String TABLE_NAME = "comments";
	public final static String TABLE_ALIAS = "c";
	public final static String A_TABLE_NAME = TABLE_NAME + " " + TABLE_ALIAS;
	final static String ID_COLUMN = "id";
	final static String RECORD_COLUMN = "recordId";
	final static String NAME_COLUMN = "name";
	final static String COMMENT_COLUMN = "comment";

	final static String ALL_COLUMNS = ID_COLUMN + ", " + RECORD_COLUMN + ", " + NAME_COLUMN + ", " + COMMENT_COLUMN;
	public final static String CREATE_SQL = "create table " + TABLE_NAME + "(" + ID_COLUMN + " integer primary key, " + RECORD_COLUMN
			+ " integer not null, " + NAME_COLUMN + " text not null, " + COMMENT_COLUMN + " text not null, " + "FOREIGN KEY (" + RECORD_COLUMN
			+ ") REFERENCES " + RecordDao.TABLE_NAME + "(" + RecordDao.ID_COLUMN + "));";

	final static String SELECT_ALL_SQL = "select " + ALL_COLUMNS + " from " + TABLE_NAME + ";";
	final static String SELECT_BY_ID_SQL = "select " + ALL_COLUMNS + " from " + TABLE_NAME + " where " + ID_COLUMN + "= ?;";
	final static String SELECT_BY_RECORD_SQL = "select " + ALL_COLUMNS + " from " + TABLE_NAME + " where " + RECORD_COLUMN + "= ?;";
	final static String COUNT_SQL = "select count(1) from " + TABLE_NAME + ";";
	final static String COUNT_BY_RECORD_SQL = "select count(1) from " + TABLE_NAME + " where " + RECORD_COLUMN + "=?;";

	private SQLiteDatabase mDb;

	/**
	 * Constructor
	 * @param db
	 */
	public CommentDao(SQLiteDatabase db) {
		mDb = db;
	}

	/**
	 * Inserts Comment object in the db 
	 * @param comment the inserted object
	 * @return the id of the inserted object or -1 if fail
	 */
	public long insert(Comment comment) {
		ContentValues values = new ContentValues();
		values.put(RECORD_COLUMN, comment.getRecordId());
		values.put(NAME_COLUMN, comment.getName());
		values.put(COMMENT_COLUMN, comment.getComment());

		long id = mDb.insert(TABLE_NAME, null, values);
		Log.d(TAG, "New Comment inserted with id: " + id);
		return id;
	}

	/**
	 * Inserts the list of Comment objects in the database
	 * @param commentList 
	 * @return the number of inserted object
	 */
	public int insertList(List<Comment> commentList) {
		int count = 0;
		for (Comment comment : commentList) {
			long id = insert(comment);
			if (id > 0) {
				count++;
			}
		}
		return count;
	}

	/**
	 * updates the existing Comment in db 
	 * @param comment
	 * @return the number of updated records
	 */
	public int update(Comment comment) {
		ContentValues values = new ContentValues();
		values.put(ID_COLUMN, comment.getId());
		values.put(RECORD_COLUMN, comment.getRecordId());
		values.put(NAME_COLUMN, comment.getName());
		values.put(COMMENT_COLUMN, comment.getComment());

		int count = mDb.update(TABLE_NAME, values, ID_COLUMN + "=" + comment.getId(), null);
		Log.d(TAG, count + " of comments updated with id: " + comment.getId());
		return count;
	}

	/**
	 * deletes object from the db by Id
	 * @param id
	 * @return the number of deleted records
	 */
	public int delete(long id) {
		int count = mDb.delete(TABLE_NAME, ID_COLUMN + "=" + id, null);
		Log.d(TAG, count + " comments deleted with id: " + id);
		return count;
	}

	/**
	 * deletes by record Id
	 * @param recordId
	 * @return the number of deleted records
	 */
	public int deleteByRecord(long recordId) {
		int count = mDb.delete(TABLE_NAME, RECORD_COLUMN + "=" + recordId, null);
		Log.d(TAG, count + " comments deleted for recordId: " + recordId);
		return count;
	}

	/**
	 * deletes all comment objects from the db
	 * @return the number of the deleted records
	 */
	public int deleteAll() {
		int count = mDb.delete(TABLE_NAME, "1", null);
		Log.d(TAG, count + " (all) comments deleted");
		return count;
	}

	/**
	 * gets from the db and returns the Comment object with specified id
	 * @param id
	 * @return gotten object
	 */
	public Comment get(long id) {
		String[] args = { "" + id };
		Comment comment = null;
		Cursor cursor = mDb.rawQuery(SELECT_BY_ID_SQL, args);
		try {
			cursor.moveToFirst();
			comment = cursorToComment(cursor);
		} finally {
			cursor.close();
		}
		return comment;
	}

	/**
	 * gets from the db and returns the list of all Comment objects
	 * @return the list of object 
	 */
	public List<Comment> getAll() {
		Cursor cursor = mDb.rawQuery(SELECT_ALL_SQL, null);
		return cursorToList(cursor);
	}

	/**
	 * gets from the db and returns the list of all Comment objects that belong the Record with specified id
	 * @param recordId  the Record object id.
	 * @return the list of object 
	 */
	public List<Comment> getByRecord(long recordId) {
		String[] args = { "" + recordId };
		Cursor cursor = mDb.rawQuery(SELECT_BY_RECORD_SQL, args);
		return cursorToList(cursor);
	}

	/**
	 * counts and returns number of all records in the db table
	 * @return the number of records in db table
	 */
	public long count() {
		long count = 0;
		Cursor cursor = mDb.rawQuery(COUNT_SQL, null);
		try {
			cursor.moveToFirst();
			count = cursor.getLong(0);
		} finally {
			cursor.close();
		}
		return count;
	}

	/**
	 * counts and return number comments that belong the Record with specified id 
	 * @param recordId the Record id
	 * @return the number of comments
	 */
	public long countByRecord(long recordId) {
		String[] args = { "" + recordId };
		long count = 0;
		Cursor cursor = mDb.rawQuery(COUNT_BY_RECORD_SQL, args);
		try {
			cursor.moveToFirst();
			count = cursor.getLong(0);
		} finally {
			cursor.close();
		}
		return count;
	}

	/*
	 * returns list of Comment objects that extracts from the cursor
	 */
	private List<Comment> cursorToList(Cursor cursor) {
		List<Comment> comments = new ArrayList<Comment>();
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Comment comment = cursorToComment(cursor);
				comments.add(comment);
				cursor.moveToNext();
			}
		} finally {
			cursor.close();
		}
		return comments;
	}

	/*
	 * extracts from the cursor and return a Comment object
	 */
	private Comment cursorToComment(Cursor cursor) {
		if (cursor.getCount() == 0) {
			return null;
		}
		Comment comment = new Comment();
		comment.setId(cursor.getLong(0));
		comment.setRecordId(cursor.getLong(1));
		comment.setName(cursor.getString(2));
		comment.setComment(cursor.getString(3));
		return comment;
	}

}
