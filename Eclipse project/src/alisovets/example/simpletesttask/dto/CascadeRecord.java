package alisovets.example.simpletesttask.dto;

import java.util.List;
/**
 * To store the record and associated comments
 * 
 * @author Alexander Lisovets
 *
 */
public class CascadeRecord {
	public Record record;
	public List<Comment> comments;

	public CascadeRecord() {
	}
	
	public CascadeRecord(Record record, List<Comment> comments) {
		this.record = record; 
		this.comments = comments;	
	}
	
}
