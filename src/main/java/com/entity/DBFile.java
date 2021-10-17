package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="files")
public class DBFile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/*
	universally unique identifier 
	
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
	 
	 */
	@Column(name="name")
	private String name;
	
	@Column(name="type")
	private String type;
	
	@Lob
	@Column(name="data")
	private byte[] data;
	
	
	public DBFile() {
		
	}
	public DBFile(String name, String type, byte[] data) {
		this.name = name;
		this.type = type;
		this.data = data;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public byte[] getData() {
		return data;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	
	
}
