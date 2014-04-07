package alisovets.example.simpletesttask.data;

import java.util.ArrayList;
import java.util.List;

import alisovets.example.simpletesttask.dto.Detail;
import alisovets.example.simpletesttask.dto.Record;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * DAO class to exchange Record objects from the database
 * 
 * @author Alexander Lisovets
 *
 */
public class RecordDao {
	private final static String TAG = RecordDao.class.getSimpleName();
	public final static String TABLE_NAME = "records";
	public final static String TABLE_ALIAS = "r";
	public final static String A_TABLE_NAME = TABLE_NAME + " " + TABLE_ALIAS;

	final static String ID_COLUMN = "id";
	final static String NAME_COLUMN = "name";
	final static String CODE_COLUMN = "code";
	final static String DESCRIPTION_COLUMN = "description";
	final static String PRICE_COLUMN = "price";
	final static String DISPLAY_COLUMN = "display";
	final static String PROCESSOR_COLUMN = "processor";
	final static String MEMORY_COLUMN = "memory";
	final static String HDD_COLUMN = "hdd";
	final static String OTHER_COLUMN = "other";
	final static String ALL_COLUMNS = ID_COLUMN + ", " + NAME_COLUMN + ", " + CODE_COLUMN + ", " + DESCRIPTION_COLUMN + ", " + PRICE_COLUMN
			+ ", " + DISPLAY_COLUMN + ", " + PROCESSOR_COLUMN + ", " + MEMORY_COLUMN + ", " + HDD_COLUMN + ", " + OTHER_COLUMN;

	public final static String CREATE_SQL = "create table " + TABLE_NAME + "(" + ID_COLUMN + " integer primary key, " + NAME_COLUMN
			+ " text not null, " + CODE_COLUMN + " text not null unique, " + DESCRIPTION_COLUMN + " text not null default '', "
			+ PRICE_COLUMN + " integer not null, " + DISPLAY_COLUMN + " text not null default '', " + PROCESSOR_COLUMN
			+ " text not null default '', " + MEMORY_COLUMN + " text not null default '', " + HDD_COLUMN + " text not null default '', "
			+ OTHER_COLUMN + " text not null default '');";

	final static String SELECT_ALL_SQL = "select " + ALL_COLUMNS + " from " + TABLE_NAME + ";";
	final static String SELECT_BY_ID_SQL = "select " + ALL_COLUMNS + " from " + TABLE_NAME + " where " + ID_COLUMN + "= ?;";
	final static String COUNT_SQL = "select count(1) from " + TABLE_NAME + ";";

	private SQLiteDatabase mDb;

	public RecordDao(SQLiteDatabase db) {
		mDb = db;
	}

	/**
	 * inserts a Record in the db
	 * @param record
	 * @return id or -1
	 */
	public long insert(Record record) {
		ContentValues values = new ContentValues();
		values.put(ID_COLUMN, record.getId());
		values.put(NAME_COLUMN, record.getName());
		values.put(CODE_COLUMN, record.getCode());
		values.put(PRICE_COLUMN, record.getPrice());
		if (record.getDetails() != null) {
			if (record.getDescription() != null)
				values.put(DESCRIPTION_COLUMN, record.getDescription());
			if (record.getDetails().getDisplay() != null)
				values.put(DISPLAY_COLUMN, record.getDetails().getDisplay());
			if (record.getDetails().getProcessor() != null)
				values.put(PROCESSOR_COLUMN, record.getDetails().getProcessor());
			if (record.getDetails().getMemory() != null)
				values.put(MEMORY_COLUMN, record.getDetails().getMemory());
			if (record.getDetails().getHdd() != null)
				values.put(HDD_COLUMN, record.getDetails().getHdd());
			if (record.getDetails().getOther() != null)
				values.put(OTHER_COLUMN, record.getDetails().getOther());
		}
		long id = mDb.insert(TABLE_NAME, null, values);
		Log.d(TAG, "New Record inserted with id: " + id);
		return id;
	}

	/**
	 * updates an existing Record
	 * @param record
	 * @return the number of the updated Record
	 */
	public int update(Record record) {
		ContentValues values = new ContentValues();
		values.put(ID_COLUMN, record.getId());
		values.put(NAME_COLUMN, record.getName());
		values.put(CODE_COLUMN, record.getCode());
		values.put(DESCRIPTION_COLUMN, record.getDescription());
		values.put(PRICE_COLUMN, record.getPrice());
		values.put(DISPLAY_COLUMN, record.getDetails().getDisplay());
		values.put(PROCESSOR_COLUMN, record.getDetails().getProcessor());
		values.put(MEMORY_COLUMN, record.getDetails().getMemory());
		values.put(HDD_COLUMN, record.getDetails().getHdd());
		values.put(OTHER_COLUMN, record.getDetails().getOther());
		int count = mDb.update(TABLE_NAME, values, ID_COLUMN + "=" + record.getId(), null);
		Log.d(TAG, count + " of records updated with id: " + record.getId());
		return count;
	}

	/**
	 * deletes the Record with specified id
	 * @param id
	 * @return the number of deleted records
	 */
	public int delete(long id) {
		int count = mDb.delete(TABLE_NAME, ID_COLUMN + "=" + id, null);
		Log.d(TAG, count + " records deleted with id: " + id);
		return count;
	}

	/**
	 * deletes all Records
	 * @return the number of deleted records
	 */
	public int deleteAll() {
		int count = mDb.delete(TABLE_NAME, "1", null);
		return count;
	}

	/**
	 * gets the Record object  with specified id from the db
	 * @param id
	 * @return the gotten Record object 
	 */
	public Record get(long id) {

		String[] args = { "" + id };
		Record record = null;
		Cursor cursor = mDb.rawQuery(SELECT_BY_ID_SQL, args);
		try {
			cursor.moveToFirst();
			record = cursorToRecord(cursor);
		} finally {
			cursor.close();
		}
		return record;
	}

	/**
	 * gets all Record objects from the db
	 * @return gotten Record objects
	 */
	public List<Record> getAll() {
		Cursor cursor = mDb.rawQuery(SELECT_ALL_SQL, null);
		return cursorToList(cursor);
	}

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

	private List<Record> cursorToList(Cursor cursor) {
		List<Record> records = new ArrayList<Record>();
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Record record = cursorToRecord(cursor);
				records.add(record);
				cursor.moveToNext();
			}
		} finally {
			cursor.close();
		}
		return records;
	}

	private Record cursorToRecord(Cursor cursor) {
		if (cursor.getCount() == 0) {
			return null;
		}
		Record record = new Record();
		Detail detail = new Detail();
		record.setId(cursor.getLong(0));
		record.setName(cursor.getString(1));
		record.setCode(cursor.getString(2));
		record.setDescription(cursor.getString(3));
		record.setPrice(cursor.getLong(4));
		detail.setDisplay(cursor.getString(5));
		detail.setProcessor(cursor.getString(6));
		detail.setMemory(cursor.getString(7));
		detail.setHdd(cursor.getString(8));
		detail.setOther(cursor.getString(9));
		record.setDetails(detail);
		return record;
	}
}
