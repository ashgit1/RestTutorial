package com.ashish.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONArray;

import com.ashish.dao.DBDao;
import com.ashish.util.RestDBUtil;
import com.ashish.util.ToJSON;

public class DBDaoImpl implements DBDao {

	PreparedStatement prepStat;
	Connection conn;
	ResultSet res;
	ToJSON jsonUtilConverter = null;
	JSONArray jsonObject = null;

	@Override
	public String getDBDate() throws Exception {

		String status = null;
		try {
			conn = RestDBUtil.getCon();
			prepStat = conn
					.prepareStatement("SELECT CURRENT_TIMESTAMP AS DATE FROM SYSIBM.SYSDUMMY1");
			res = prepStat.executeQuery();

			while (res.next()) {
				status = res.getString("DATE");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prepStat.close();
			RestDBUtil.close(conn);
		}

		return status;
	}

	@Override
	public String getAllPcParts() throws Exception {

		String allPcParts = null;

		try {
			conn = RestDBUtil.getCon();
			prepStat = conn.prepareStatement("select * from pc_parts");
			res = prepStat.executeQuery();

			jsonUtilConverter = new ToJSON();
			jsonObject = new JSONArray();

			jsonObject = jsonUtilConverter.toJSONArray(res);
			allPcParts = jsonObject.toString();

			System.out.println("in getAllPcParts json : " + allPcParts);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prepStat.close();
			RestDBUtil.close(conn);
		}

		return allPcParts;
	}

	@Override
	public String getBrandParts(String brandName) throws Exception {

		String brandParts = null;
		jsonUtilConverter = new ToJSON();
		jsonObject = new JSONArray();

		try {

			conn = RestDBUtil.getCon();
			prepStat = conn
					.prepareStatement("SELECT PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC "
							+ " FROM PC_PARTS WHERE UPPER(PC_PARTS_MAKER) = ? ");
			prepStat.setString(1, brandName.toUpperCase());
			res = prepStat.executeQuery();

			jsonObject = jsonUtilConverter.toJSONArray(res);
			brandParts = jsonObject.toString();

			System.out.println("in getBrandParts json : " + brandParts);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prepStat.close();
			RestDBUtil.close(conn);
		}
		return brandParts;
	}

	@Override
	public String getBrand(String brandName, String brandCode) throws Exception {

		String brandDetails = null;
		jsonUtilConverter = new ToJSON();
		jsonObject = new JSONArray();

		try {

			conn = RestDBUtil.getCon();
			prepStat = conn
					.prepareStatement("SELECT PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC "
							+ " FROM PC_PARTS WHERE UPPER(PC_PARTS_MAKER) = ? AND PC_PARTS_CODE = ? ");
			prepStat.setString(1, brandName.toUpperCase());
			prepStat.setString(2, brandCode);
			res = prepStat.executeQuery();

			jsonObject = jsonUtilConverter.toJSONArray(res);
			brandDetails = jsonObject.toString();

			System.out.println("in getBrandParts json : " + brandDetails);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prepStat.close();
			RestDBUtil.close(conn);
		}
		return brandDetails;
	}

	@Override
	public int insertIntoPC_PARTS(String PC_PARTS_TITLE, 
									String PC_PARTS_CODE, 
									String PC_PARTS_MAKER, 
									String PC_PARTS_AVAIL, 
									String PC_PARTS_DESC) throws Exception {
		
		int http_code=0;
		
		try {
			conn = RestDBUtil.getCon();
			prepStat = conn.prepareStatement("insert into PC_PARTS " +
					"(PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC) " +
					"VALUES ( ?, ?, ?, ?, ? ) ");
			prepStat.setString(1, PC_PARTS_TITLE);
			prepStat.setString(2, PC_PARTS_CODE);
			prepStat.setString(3, PC_PARTS_MAKER);
			//PC_PARTS_AVAIL is a number column, so we need to convert the String into a integer
			int avilInt = Integer.parseInt(PC_PARTS_AVAIL);
			prepStat.setInt(4, avilInt);
			prepStat.setString(5, PC_PARTS_DESC);
			int rowcount = prepStat.executeUpdate(); //note the new command for insert statement
			if(rowcount==1){
				http_code=200;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			http_code=500;
		} finally {
			prepStat.close();
			RestDBUtil.close(conn);
		}

		return http_code;
	}

}
