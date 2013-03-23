package edu.umn.kill9.contactviewer.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class JsonResponse {
	
	@SerializedName("status")
	public String status;

	@SerializedName("message")
	public String message;
	
	public GroupJson group;
	
	public List<ContactJson> contacts; 
}



