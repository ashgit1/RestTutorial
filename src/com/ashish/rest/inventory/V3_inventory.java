package com.ashish.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	/**
	 * This method will allow you to update data in the PC_PARTS table.
	 * In this example we are using both PathParms and the message body (payload).
	 * 
	 * @param brand
	 * @param item_number
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@Path("/{brand}/{item_number}")
	@PUT
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(@PathParam("brand") String brand,
									@PathParam("item_number") int item_number,
									String incomingData) 
								throws Exception {
		
		System.out.println("incomingData: " + incomingData);
		System.out.println("brand: " + brand);
		System.out.println("item_number: " + item_number);
		
		int pk;
		int avail;
		String title;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		dbDao = new DBDaoImpl();
		
		try {
			
			JSONObject partsData = new JSONObject(incomingData); //we are using json objects to parse data
			pk = partsData.optInt("PC_PARTS_PK", 0);
			avail = partsData.optInt("PC_PARTS_AVAIL", 0);
			title = partsData.optString("PC_PARTS_TITLE");
			System.out.println("pk: " + pk + " , avail: " + avail + ", title: " + title);
			
			//call the correct sql method
			http_code = dbDao.updatePC_PARTS(pk, avail);
			
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item: " + brand + "("+ item_number + ") has been updated successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			
			returnString = jsonArray.put(jsonObject).toString();
			System.out.println("Response String from updateItem: " + returnString);
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	/**
	 * This method will allow you to delete data in the PC_PARTS table.
	 * 
	 * We really only need the primary key from the message body but I kept
	 * the same URL path as the update (PUT) to let you see that we can use the same
	 * URL path for each http method (GET, POST, PUT, DELETE, HEAD)
	 * 
	 * @param brand
	 * @param item_number
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@Path("/{brand}/{item_number}")
	@DELETE
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(@PathParam("brand") String brand,
									@PathParam("item_number") int item_number,
									String incomingData) 
								throws Exception {
		
		System.out.println("incomingData: " + incomingData);
		System.out.println("brand: " + brand);
		System.out.println("item_number: " + item_number);
		
		int pk;
		int avail;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		dbDao = new DBDaoImpl();;
		
		try {
			
			JSONObject partsData = new JSONObject(incomingData);
			pk = partsData.optInt("PC_PARTS_PK", 0);
			avail = partsData.optInt("PC_PARTS_AVAIL", 0);
			System.out.println("pk: " + pk + " , avail: " + avail);
			
			// Calling the delete method to remove the pc parts...
			http_code = dbDao.deletePC_PARTS(pk);
			
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item " + brand + "("+ item_number  + ") has been deleted successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			
			returnString = jsonArray.put(jsonObject).toString();
			System.out.println("Response String from updateItem: " + returnString);
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}
