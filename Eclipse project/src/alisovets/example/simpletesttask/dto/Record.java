package alisovets.example.simpletesttask.dto;

/**
 * To store record
 * 
 * @author Alexander Lisovets 
 *
 */
public class Record {

	private long id;
	private String name;
	private String code;
	private String description;
	private long price;
	private Detail details;

	public Record() {
	}

	public Record(long id, String name, String code, String description, long price, Detail details) {
		
		this.id = id;
		this.name = name; 
		this.code = code;
		this.description = description;
		this.price = price;
		this.details = details;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Detail getDetails() {
		return details;
	}

	public void setDetails(Detail details) {
		this.details = details;
	}

	
}
