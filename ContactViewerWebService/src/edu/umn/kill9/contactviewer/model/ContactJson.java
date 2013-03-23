package edu.umn.kill9.contactviewer.model;

import com.google.gson.annotations.SerializedName;

public class ContactJson {
	
/* This is how the webservice contact looks like
 	"groupId": "4f6c19b7fb4dc0430b000001",
    "name": "Hoban Washburne",
    "title": "Pilot",
    "email": "wash@serenity.com",
    "phone": "612-555-9012",
    "twitterId": "wash",
    "_id": "514322583a59b74e2b00055a"

 */
	@SerializedName("groupId")
	public String groupId;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("title")
	public String title;
	
	@SerializedName("email")
	public String email;
	
	@SerializedName("phone")
	public String phone;
	
	@SerializedName("twitterId")
	public String twitterId;
	
	@SerializedName("_id")
	public String _id;	

	
	public String toString() {
		return "name="+ name ;
	}
}
