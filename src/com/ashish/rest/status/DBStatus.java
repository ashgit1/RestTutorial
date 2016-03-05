package com.ashish.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.ashish.dao.DBDao;
import com.ashish.daoimpl.DBDaoImpl;

@Path("/db/")
public class DBStatus {

	DBDao dbDao = null;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDBStatus() throws Exception {
		String Status = null;
		String returnStatus = null;
		dbDao = new DBDaoImpl();
		Status = dbDao.getDBDate();
		returnStatus = "<p><font size='10' color='green'> Database Connection Success !!! </font></p>"
				+ "<p><font size='10' color='blue'> Date/Time on server: "
				+ Status + "</font></p>";
		return returnStatus;
	}

}
