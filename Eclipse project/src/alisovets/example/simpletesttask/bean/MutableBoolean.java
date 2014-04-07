package alisovets.example.simpletesttask.bean;

/**
 * Just the mutable wrapper for a boolean value 
 * 
 * @author Alexander Lisovets 
 *
 */
public class MutableBoolean {
	private boolean value;
	
	public MutableBoolean() {
	}
	
	public MutableBoolean(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
	
}
