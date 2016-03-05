package com.ashish.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ashish.dao.DBDao;
import com.ashish.util.RestDBUtil;

public class DBDaoImpl implements DBDao {

	PreparedStatement prepStat;
	Connection conn;
	ResultSet res;

	@Override
	public String getDBDate() throws Exception {
		
		String status=null;
		try {
			conn = RestDBUtil.getCon();
			prepStat = conn
					.prepareStatement("SELECT CURRENT_TIMESTAMP AS DATE FROM SYSIBM.SYSDUMMY1");
			res = prepStat.executeQuery();
			
			while(res.next()){
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

}
