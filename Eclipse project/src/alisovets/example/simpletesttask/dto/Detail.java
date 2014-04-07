package alisovets.example.simpletesttask.dto;

/**
 * to store details of the records
 * 
 * @author Alexander Lisovets 
 *
 */
public class Detail {
	private String display;
	private String processor;
	private String memory;
	private String hdd;
	private String other;
	
	public Detail() {
	}
	
	public Detail(String display, String processor, String memory, String hdd, String other) {
		
		this.display = display;
		this.processor = processor;
		this.memory = memory;
		this.hdd = hdd;
		this.other = other;
	}
	
	public String getDisplay() {
		return display;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}
	
	public String getProcessor() {
		return processor;
	}
	
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	
	public String getMemory() {
		return memory;
	}
	
	public void setMemory(String memory) {
		this.memory = memory;
	}
	
	public String getHdd() {
		return hdd;
	}
	
	public void setHdd(String hdd) {
		this.hdd = hdd;
	}
	
	public String getOther() {
		return other;
	}
	
	public void setOther(String other) {
		this.other = other;
	}

}
