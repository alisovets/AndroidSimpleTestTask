package alisovets.example.simpletesttask.dto;

/**
 * to store comments
 * @author Lisovets Alexander
 *
 */
public class Comment {
	
	private long id;
	private long recordId;
	private String name;
	private String comment;

	public Comment() {
	}

	public Comment(long id, long recordId, String name, String comment) {
		this.id = id;
		this.recordId = recordId;
		this.name = name;
		this.comment = comment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
