package alisovets.example.simpletesttask.data;

import java.util.List;

import alisovets.example.simpletesttask.dto.Comment;
import alisovets.example.simpletesttask.dto.Record;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataAdapter {
	
	private SQLiteDatabase mDb;
	private OpenDataHelper mDbHelper;
	private RecordDao mRecordDao;
	private CommentDao mCommentDao;
	
	/**
	 * Constructor
	 * @param context
	 */
	public DataAdapter(Context context) {
		mDbHelper = new OpenDataHelper(context);
		open();
	}
	
	public void open(){		
		mDb = mDbHelper.getWritableDatabase();
		mRecordDao = new RecordDao(mDb);
		mCommentDao = new CommentDao(mDb);
	}

	public SQLiteDatabase getDatabase(){
		return mDb;
	}

	public void close() {
		mDbHelper.close();
	}
	
	/**
	 * inits new db transaction 
	 */
	public void beginTransaction() {
		mDb.execSQL("BEGIN;");
	}

	/**
	 * ends a db transaction 
	 */
	public void endTransaction() {
		mDb.execSQL("END;");
	}

	/**
	 * dismiss a db transaction
	 */
	public void rollbackTransaction() {
		mDb.execSQL("ROLLBACK;");
	}
	 
	
	/**
	 * gets the Record object  with specified id from the db
	 * @param id
	 * @return the gotten Record object 
	 */
	public Record getRecord(long id){
		return mRecordDao.get(id);
	}
	
	/**
	 * gets all Record objects from the db
	 * @return gotten Record objects
	 */
	public List<Record> getAllRecords(){
		return mRecordDao.getAll();
	}
	
	/**
	 * gets from the db and returns the list of all Comment objects that belong the Record with specified id
	 * @param recordId  the Record object id.
	 * @return the list of object 
	 */
	public List<Comment> getCommentsByRecord(long recordId){
		return mCommentDao.getByRecord(recordId);
	}
	
	
	/**
	 * deletes all Records
	 * @return the number of deleted records
	 */
	public int deleteAllRecords(){
		return mRecordDao.deleteAll();
	}
	
	/**
	 * deletes the Record with specified id
	 * @param id
	 * @return the number of deleted records
	 */
	public int deleteRecord(long id){
		return mRecordDao.delete(id);
	}
	
	
	/**
	 * deletes all comment objects from the db
	 * @return the number of the deleted records
	 */
	public int deleteAllComments(){
		return mCommentDao.deleteAll();
	}
	
	/**
	 * deletes by record Id
	 * @param recordId
	 * @return the number of deleted records
	 */
	public int deleteCommentByRecord(long recordId){
		return mCommentDao.deleteByRecord(recordId);
	}
	
	/**
	 * inserts a Record in the db
	 * @param record
	 * @return id or -1
	 */
	public long insertRecord(Record record){
		return mRecordDao.insert(record);
	}
	
	/**
	 * Inserts Comment object in the db 
	 * @param comment the inserted object
	 * @return the id of the inserted object or -1 if fail
	 */
	public long insertComment(Comment comment){
		return mCommentDao.insert(comment);
	}
	
	/**
	 * Inserts the list of Comment objects in the database
	 * @param commentList 
	 * @return the number of inserted object
	 */
	public int insertCommentList(List<Comment> commentList){
		return mCommentDao.insertList(commentList);
	}

}
