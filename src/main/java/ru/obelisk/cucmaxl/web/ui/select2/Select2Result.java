package ru.obelisk.cucmaxl.web.ui.select2;

public class Select2Result {
	private int id;
	private String text;
	
	public Select2Result(int id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Select2Result [id=" + id + ", text=" + text + "]";
	}
}
