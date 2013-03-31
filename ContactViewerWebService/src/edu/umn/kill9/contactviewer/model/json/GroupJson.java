package edu.umn.kill9.contactviewer.model.json;

import com.google.gson.annotations.SerializedName;

public class GroupJson {

	@SerializedName("key")
    private String key;

	@SerializedName("name")
    private String name;

    @SerializedName("_id")
    private String _id;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

}
