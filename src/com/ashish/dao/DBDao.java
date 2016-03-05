package com.ashish.dao;

public interface DBDao {
	public String getDBDate() throws Exception;

	public String getAllPcParts() throws Exception;
	
	public String getBrandParts(String brandName) throws Exception;
}
