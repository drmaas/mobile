package edu.umn.kill9.contactviewer.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/** Model class for storing a single contact.
 *
 * Implements parcelable to pass between intents
 */
public class Contact implements Parcelable {

    //Called by framework to create a contact from a parcelable
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel pc) {
            return new Contact(pc);
        }
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

	/** Creates a contact and assigns its name.
	 * 
	 * @param name the contact's name
	 */
	public Contact(String name) {
		_name = name;
	}

    /**
     *
     */
    public Contact() {
    }

    /**
     * Constructor for parcelable
     *
     * @param p
     */
    public Contact(Parcel p) {
        _id = p.readString();
        _name  = p.readString();
        _title = p.readString();
        _email = p.readString();
        _phone = p.readString();
        _twitterId = p.readString();
    }

    private String _id;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

	private String _name;
	
	/** Set the contact's name.
	 */
	public Contact setName(String name) {
		_name = name;
		return this;
	}

	/** Get the contact's name.
	 */
	public String getName() {
		return _name;
	}
	
	private String _phone;

	/**
	 * @return the contact's phone number
	 */
	public String getPhone() {
		return _phone;
	}

	/** Set's the contact's phone number.
	 */
	public Contact setPhone(String phone) {
		_phone = phone;
		return this;
	}
	
	private String _title;

	/**
	 * @return The contact's title
	 */
	public String getTitle() {
		return _title;
	}

	/** Sets the contact's title.
	 */
	public Contact setTitle(String title) {
		_title = title;
		return this;
	}
	
	private String _email;

	/**
	 * @return the contact's e-mail address
	 */
	public String getEmail() {
		return _email;
	}

	/** Sets the contact's e-mail address.
	 */
	public Contact setEmail(String email) {
		_email = email;
		return this;
	}
	
	private String _twitterId;

	/**
	 * @return the contact's Twitter ID
	 */
	public String getTwitterId() {
		return _twitterId;
	}

	/** Sets the contact's Twitter ID.
	 */
	public Contact setTwitterId(String twitterId) {
		_twitterId = twitterId;
		return this;
	}
	
	public String toString() {
		return _name + " (" + _title + ")";
	}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeString(_id);
        p.writeString(_name);
        p.writeString(_title);
        p.writeString(_email);
        p.writeString(_phone);
        p.writeString(_twitterId);
    }
}

