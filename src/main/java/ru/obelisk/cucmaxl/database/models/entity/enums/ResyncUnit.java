package ru.obelisk.cucmaxl.database.models.entity.enums;

public enum ResyncUnit {
	MINUTE(0),
	HOUR(1),
	DAY(2),
	MONTH(3);
	
	ResyncUnit(){	
	}
	
	private int num;
	
	
	private ResyncUnit(int num){
		this.num = num;
	}

	public int getNumber(){
		return num;
	}
}