package com.ashish.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import com.ashish.dao.DBDao;
import com.ashish.daoimpl.DBDaoImpl;
import com.ashish.model.ItemEntry;

@Path("/v2/inventory")
public class V2_inventory {

	DBDao dbDao = null;

	/**
	 * QueryParameter Example.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand)
			throws Exception {

		if (brand == null) {
			return Response.status(400)
					.entity("Error: Please specify search for this brand")
					.build();
		}
		String brandParts = null;
		dbDao = new DBDaoImpl();
		brandParts = dbDao.getBrandParts(brand);
		return Response.ok(brandParts).build();
	}

	/**
	 * PathParameter Example.
	 */
	@Path("/{brand}")
	@GET
	public Response retBrand(@PathParam("brand") String brand) throws Exception {

		if (brand == null) {
			return Response.status(400)
					.entity("Error: Please specify search for this brand")
					.build();
		}
		String brandParts = null;
		dbDao = new DBDaoImpl();
		brandParts = dbDao.getBrandParts(brand);
		return Response.ok(brandParts).build();
	}

	/**
	 * PathParameter Example involving 2 parameters. In Depth Search.
	 */
	@Path("/{brand}/{brandcode}")
	@GET
	public Response retBrandInfo(@PathParam("brand") String brand,
			@PathParam("brandcode") String brandCode) throws Exception {

		if (brand == null) {
			return Response.status(400)
					.entity("Error: Please specify search for this brand")
					.build();
		}

		String brandInfo = null;
		dbDao = new DBDaoImpl();
		brandInfo = dbDao.getBrand(brand, brandCode);
		return Response.ok(brandInfo).build();
	}
	
	/**
	 * Adding an Item Entry
	 */
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception{
		
		String returnString=null;
		int http_code=0;
		dbDao = new DBDaoImpl();
		System.out.println("Incoming pc parts in inventory : " + incomingData);
		// Converts a json string into java object...
		ObjectMapper mapper = new ObjectMapper();
		ItemEntry item = mapper.readValue(incomingData, ItemEntry.class);
		http_code = dbDao.insertIntoPC_PARTS(item.PC_PARTS_TITLE, 
									item.PC_PARTS_CODE, 
									item.PC_PARTS_MAKER, 
									item.PC_PARTS_AVAIL, 
									item.PC_PARTS_DESC);
		if(http_code == 200){
			returnString = "Item inserted successfully"; 
		}
		else{
			return Response.status(500).entity("Unable to process items!").build();
		}
		return Response.ok(returnString).build();
	}

}
