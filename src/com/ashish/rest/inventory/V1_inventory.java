package com.ashish.rest.inventory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.ashish.dao.DBDao;
import com.ashish.daoimpl.DBDaoImpl;

@Path("/v1/inventory/")
public class V1_inventory {

	DBDao dbDao = null;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPcParts() throws Exception {
		String returnAllPcParts;
		dbDao = new DBDaoImpl();
		returnAllPcParts = dbDao.getAllPcParts();
		return returnAllPcParts;
	}
}
