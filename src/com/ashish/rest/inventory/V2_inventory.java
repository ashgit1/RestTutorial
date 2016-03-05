package com.ashish.rest.inventory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ashish.dao.DBDao;
import com.ashish.daoimpl.DBDaoImpl;

@Path("/v2/inventory/")
public class V2_inventory {

	DBDao dbDao = null;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(
			@QueryParam("brand") String brand)
			throws Exception{
		
		if(brand==null){
			return Response.status(400).entity("Error: Please specify search for this brand").build();
		}
		String brandParts=null;
		dbDao = new DBDaoImpl();
		brandParts = dbDao.getBrandParts(brand);
		return Response.ok(brandParts).build();
	}
}
