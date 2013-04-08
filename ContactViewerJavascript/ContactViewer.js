var _apiKey = 'kill-9';
var _baseUrl = 'http://contacts.tinyapollo.com/contacts';
var _selectedContact;

//populate contact fields
function setContactValues(contact) {
	var name = $('#contact [name="name"]');
	var title = $('#contact [name="title"]'); 
	var email = $('#contact [name="email"]'); 
	var phone = $('#contact [name="phone"]'); 
	var twitter = $('#contact [name="twitterId"]'); 
	$(name).val(contact.name);
	$(title).val(contact.title);
	$(email).val(contact.email);
	$(phone).val(contact.phone);
	$(twitter).val(contact.twitterId);
}
//gets the contact list from the server
function getContacts(cb) {
    var url = _baseUrl + '?key=' + _apiKey;
    $.get(
        url,
        function(data) {
	    if (data.status === 'success') {
                var contacts = data.contacts;
                cb(contacts);
	    }
	    else {
                return alert("Error: " + data.message);
            }   
        },
	'json'
    );
}

//gets specific contact from the server
function getContact(id, cb) {
    var url = _baseUrl + '/' + id + '?key=' + _apiKey;
    $.get(
        _baseUrl + '/' + id + '?key=' + _apiKey,
	function(data) {
	    if (data.status === 'success') {
	        var contact = data.contact;
	        cb(contact);
	    }
	    else {
                return alert("Error: " + data.message);
            }  
	},
	'json'
    );
}

//add a new contact with data in 'contact' object
function addContact(contact, cb) {
    var url = _baseUrl + '?key=' + _apiKey;
    $.post(
        url,
	contact,
	function(data) {
	    if (data.status === 'success') {
	        var contact = data.contact;
	        cb(contact);
	    }
	    else {
                return alert("Error: " + data.message);
            } 	
	},
	'json'
    );
}

//update an existing contact
function updateContact(contact, cb) {
var item, url;
    var id = contact.id;
    var url = _baseUrl + '/' + id + '?key=' + _apiKey;
    $.ajax({
        url: url,
        type: 'PUT',
        dataType: 'json',
        data: contact,
        success: function(data) {
            if (data.status === 'success') {
		var contact = data.contact;
	        cb(contact);
            }  
            else {
                 return alert("Error: " + data.message);
            }
        }
    });
 }
 
 //delete this contact
 function deleteContact(contact, cb) {
    var id = contact.id;
    var url = _baseUrl + '/' + id + '?key=' + _apiKey;
    $.ajax({
        url: url,
        type: 'DELETE',
        dataType: 'json',
        success: function(data) {
            if (data.status === 'success') {
	        cb();
            }  
            else {
                 return alert(data.message);
            }
        }
    });
 }

//contact list before show
$(document).on("pagebeforeshow", "#home-page", function(event) {
    var contactList = $('#contact-list');
    contactList.html('');
	
	//get contact list
    getContacts(function(contacts) {
		//insert elements into DOM tree
        for (var i in contacts) {
            var contact = contacts[i];
			var contactId = contact._id;
            contactList.append('<li><a href="#" id=' + i + '>' + contact.name + '</a></li>');
			
			//set up links for contact items 
			$(document).on('click', '#'+i, { contact: contact }, function(event) {
				if(event.handled !== true) // This will prevent event triggering more then once
				{
					_selectedContact = event.data.contact;
					$.mobile.changePage( $("#details"), { transition: "slide"} );
					event.handled = true;
				}              
			});
        }
        contactList.listview('refresh');

    });
	
});

//contact details before show
$(document).on('pagebeforeshow', '#details', function(event,data) {    
    var contact = _selectedContact;
    setContactValues(contact);
});
