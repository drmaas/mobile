package edu.umn.kill9.contactviewer.model.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ContactListJsonResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("group")
    private GroupJson group;

    @SerializedName("contacts")
    private List<ContactJson> contacts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GroupJson getGroup() {
        return group;
    }

    public void setGroup(GroupJson group) {
        this.group = group;
    }

    public List<ContactJson> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactJson> contacts) {
        this.contacts = contacts;
    }

}



