package edu.umn.kill9.contactviewer.model.json;

import com.google.gson.annotations.SerializedName;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class ContactJsonResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("group")
    private GroupJson group;

    @SerializedName("contact")
    private ContactJson contact;

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

    public ContactJson getContact() {
        return contact;
    }

    public void setContact(ContactJson contact) {
        this.contact = contact;
    }
}
