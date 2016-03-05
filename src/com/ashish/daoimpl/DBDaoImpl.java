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
		
		try{
			
			conn = RestDBUtil.getCon();
			prepStat = conn.prepareStatement("SELECT PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC " +	
											 " FROM PC_PARTS WHERE UPPER(PC_PARTS_MAKER) = ? ");
			prepStat.setString(1, brandName.toUpperCase());
			res = prepStat.executeQuery();

			jsonObject = jsonUtilConverter.toJSONArray(res);
			brandParts = jsonObject.toString();

			System.out.println("in getBrandParts json : " + brandParts);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			prepStat.close();
			RestDBUtil.close(conn);
		}
		return brandParts;
	}

}
