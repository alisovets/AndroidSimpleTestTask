package alisovets.example.simpletesttask.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import alisovets.example.simpletesttask.dto.CascadeRecord;
import alisovets.example.simpletesttask.dto.Comment;
import alisovets.example.simpletesttask.dto.Detail;
import alisovets.example.simpletesttask.dto.Record;
import android.util.Log;

/**
 * to parse json strings  to CascadeRecord objects
 * 
 * @author ALexander Lisovets
 *
 */
public class RecordsJsonParser {
	private static final String RECORDS_JSON_KEY = "records";
	private static final String ID_JSON_KEY = "id";
	private static final String NAME_JSON_KEY = "name";
	private static final String CODE_JSON_KEY = "code";
	private static final String DESCRIPTION_JSON_KEY = "description";
	private static final String PRICE_JSON_KEY = "price";
	private static final String DETAILS_JSON_KEY = "details";
	private static final String DISPLAY_JSON_KEY = "display";
	private static final String PROCESSOR_JSON_KEY = "processor";
	private static final String MEMORY_JSON_KEY = "memory";
	private static final String HDD_JSON_KEY = "hdd";
	private static final String OTHER_JSON_KEY = "other";
	private static final String COMMENTS_JSON_KEY = "comments";
	private static final String USER_NAME_JSON_KEY = "name";
	private static final String TEXT_COMMENT_JSON_KEY = "comment";
	
	
	/**
	 * parses json string and creates list of CascadeRecord objects  
	 * @param jsonString string in the json format
	 * @return result list
	 * @throws JSONException if string has a wrong format
	 */
	public static List<CascadeRecord> unmarshalRecords(String jsonString) throws JSONException{
	
		JSONObject rootJsonObject = (JSONObject) new JSONTokener(jsonString).nextValue();
		JSONArray jsonArray = rootJsonObject.getJSONArray(RECORDS_JSON_KEY);
		List<CascadeRecord> recordList = new ArrayList<CascadeRecord>();
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject recordJsonObject = jsonArray.getJSONObject(i);
			
			Record record = new Record();
			Detail details = new Detail();
			record.setDetails(details);
			long id = recordJsonObject.getLong(ID_JSON_KEY);
			record.setId(id);
			record.setName(recordJsonObject.getString(NAME_JSON_KEY));
			record.setCode(recordJsonObject.getString(CODE_JSON_KEY));
			record.setDescription(recordJsonObject.getString(DESCRIPTION_JSON_KEY));
			record.setPrice(recordJsonObject.getLong(PRICE_JSON_KEY));
			
			JSONObject detailsJsonObject = recordJsonObject.getJSONObject(DETAILS_JSON_KEY);
			details.setDisplay(detailsJsonObject.getString(DISPLAY_JSON_KEY));
			details.setProcessor(detailsJsonObject.getString(PROCESSOR_JSON_KEY));
			details.setMemory(detailsJsonObject.getString(MEMORY_JSON_KEY));
			details.setHdd(detailsJsonObject.getString(HDD_JSON_KEY));
			details.setOther(detailsJsonObject.getString(OTHER_JSON_KEY));
			
			Log.d("log", "id= " + id + "  name= " + record.getName() + "  code= " + record.getCode() );
			Log.d("log", "   description= " + record.getDescription() );
			Log.d("log", "   price= " + record.getPrice());
			
			JSONArray commentsJsonArray = recordJsonObject.getJSONArray(COMMENTS_JSON_KEY);
			List<Comment> commentList = new ArrayList<Comment>();
			
			for(int j = 0; j < commentsJsonArray.length(); j++){
				JSONObject commentJsonObject = commentsJsonArray.getJSONObject(j);
				Comment comment = new Comment();
				comment.setRecordId(id);
				comment.setName(commentJsonObject.getString(USER_NAME_JSON_KEY));
				comment.setComment(commentJsonObject.getString(TEXT_COMMENT_JSON_KEY));
				commentList.add(comment);
				Log.d("log", "        user= " + comment.getName());
				Log.d("log", "        comment= " + comment.getComment());
				Log.d("log", "         .");
			}
			CascadeRecord cascadeRecord = new CascadeRecord(record, commentList);
			recordList.add(cascadeRecord);
			
		}
		
		return recordList;
	}

}
