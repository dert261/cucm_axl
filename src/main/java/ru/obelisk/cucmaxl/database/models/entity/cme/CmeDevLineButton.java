package ru.obelisk.cucmaxl.database.models.entity.cme;

public class CmeDevLineButton {
	private String devLineButtonID;
	private String devLineButtonMode;
	
	public String getDevLineButtonID() {
		return devLineButtonID;
	}
	public void setDevLineButtonID(String devLineButtonID) {
		this.devLineButtonID = devLineButtonID;
	}
	public String getDevLineButtonMode() {
		return devLineButtonMode;
	}
	public void setDevLineButtonMode(String devLineButtonMode) {
		this.devLineButtonMode = devLineButtonMode;
	}
	
	@Override
	public String toString() {
		return "CmeDevLineButton [devLineButtonID=" + devLineButtonID + ", devLineButtonMode=" + devLineButtonMode
				+ "]";
	}
}
