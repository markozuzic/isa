package com.example.model.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PostData {
	
	private String items;
	
	private String longitude;
	
	private String latitude;
	
	private String flag;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date dateStart;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date dateEnd;
	
	private String tables;
	
	public PostData(){};

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "PostData [items=" + items + ", flag=" + flag + "]";
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getTables() {
		return tables;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}
}
