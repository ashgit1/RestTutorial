package com.ashish.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import com.ashish.dao.DBDao;
import com.ashish.daoimpl.DBDaoImpl;

@Path("/v3/inventory")
public class V3_inventory {

	DBDao dbDao = null;
	
	/**
	 * Adding an Item Entry via post.html and post.js
	 * Also JsonObject and JsonArray.
	 */
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception{
		
		String returnString=null;
		int http_code=0;
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		dbDao = new DBDaoImpl();
		
		System.out.println("Incoming pc parts in inventory : " + incomingData);
		// Converts a json string into java object...
		JSONObject partsData = new JSONObject(incomingData);
		System.out.println("JSONObject : " + partsData.toString());
		
		http_code = dbDao.insertIntoPC_PARTS(partsData.optString("PC_PARTS_TITLE"), 
											 partsData.optString("PC_PARTS_CODE"), 
											 partsData.optString("PC_PARTS_MAKER"), 
											 partsData.optString("PC_PARTS_AVAIL"), 
											 partsData.optString("PC_PARTS_DESC") );
		if(http_code == 200){
			jsonObject.put("HTTP_CODE", "200");
			jsonObject.put("MSG", "Item " + partsData.optString("PC_PARTS_TITLE") + " added successfully, Version 3");
			returnString = jsonArray.put(jsonObject).toString();
		}
		else{
			return Response.status(500).entity("Unable to enter item: " + partsData.optString("PC_PARTS_TITLE")).build();
		}
		
		System.out.println("Response String :" + returnString);
		return Response.ok(returnString).build();
	}
	
}
