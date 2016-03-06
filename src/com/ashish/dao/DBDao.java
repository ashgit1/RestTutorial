package com.ashish.dao;

public interface DBDao {
	public String getDBDate() throws Exception;

	public String getAllPcParts() throws Exception;

	public String getBrandParts(String brandName) throws Exception;
	
	public String getBrand(String brandName, String brandCode) throws Exception;
	
	public int insertIntoPC_PARTS(String PC_PARTS_TITLE, 
			String PC_PARTS_CODE, 
			String PC_PARTS_MAKER, 
			String PC_PARTS_AVAIL, 
			String PC_PARTS_DESC) throws Exception;
}
