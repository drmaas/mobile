package edu.umn.kill9.contactviewer.model.json;

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
	private String groupId;
	
	@SerializedName("name")
    private String name;
	
	@SerializedName("title")
    private String title;
	
	@SerializedName("email")
    private String email;
	
	@SerializedName("phone")
    private String phone;
	
	@SerializedName("twitterId")
    private String twitterId;
	
	@SerializedName("_id")
    private String _id;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String toString() {
		return "name="+ name ;
	}
}
