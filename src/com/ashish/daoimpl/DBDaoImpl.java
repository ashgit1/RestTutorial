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

			System.out.println("in DaoImpl json : " + allPcParts);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prepStat.close();
			RestDBUtil.close(conn);
		}

		return allPcParts;
	}

}
