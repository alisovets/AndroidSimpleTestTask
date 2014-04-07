package alisovets.example.simpletesttask.data;

import java.util.List;

import alisovets.example.simpletesttask.dto.CascadeRecord;

public class CascadeRecordDataProcessor {

	/**
	 * Inserts the list of cascade records in db via the data adapter    
	 * @param recordList
	 * @param dataAdapter
	 * @return
	 */
	public static int insertRecordsInDB(List<CascadeRecord> recordList, DataAdapter dataAdapter) {
		int count = 0;
		for (CascadeRecord record : recordList) {
			count += insertRecordInDB(record, dataAdapter);
		}
		return count;
	}

	/**
	 * Inserts in the db  the passed in parameter cascade record via the passed data adapter  
	 * @param record cascade record
	 * @param dataAdapter 
	 * @return number complete inserted record
	 */
	public static int insertRecordInDB(CascadeRecord record, DataAdapter dataAdapter) {
		long id = dataAdapter.insertRecord(record.record);
		if (id < 0) {
			return 0;
		}
		int count = dataAdapter.insertCommentList(record.comments);
		if (count < record.comments.size()) {
			return 0;
		}
		return 1;
	}

}
