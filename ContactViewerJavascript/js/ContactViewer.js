var _apiKey = 'kill-9';
var _baseUrl = 'http://contacts.tinyapollo.com/contacts';
var _selectedContact;
var  _operation;

//refresh the contact list
function refreshContactList() {
    var contactList = $('#contact-list');
    contactList.listview('refresh');
}

//return a new empty contact object
function newContact() {
    return {
        name:"",
        title:"",
        email:"",
        phone:"",
        twitterId:""
    };
}

//get current contact from parent html item container
function getContactFromPage() {
    return {
        name: $('#contact [name="name"]').val(),
        title: $('#contact [name="title"]').val(),
        email: $('#contact [name="email"]').val(),
        phone: $('#contact [name="phone"]').val(),
        twitterId: $('#contact [name="twitterId"]').val()
    };
}

//populate contact fields
function setContactToPage(contact) {
    $('#contact [name="name"]').val(contact.name);
    $('#contact [name="title"]').val(contact.title); 
    $('#contact [name="email"]').val(contact.email); 
    $('#contact [name="phone"]').val(contact.phone); 
    $('#contact [name="twitterId"]').val(contact.twitterId); 
}

 /**
  * REST methods
  */
  
//gets the contact list from the server
function getContacts(cb) {
    var url = _baseUrl + '?key=' + _apiKey;
    $.getJSON(
        url,
        function(data) {
            if (data.status === 'success') {
                var contacts = data.contacts;
                cb(contacts);
            }
            else {
                return alert("Error: " + data.message);
            }
        }
    );
}

//gets specific contact from the server
function getContact(id, cb) {
    var url = _baseUrl + '/' + id + '?key=' + _apiKey;
    $.getJSON(
        _baseUrl + '/' + id + '?key=' + _apiKey,
        function(data) {
            if (data.status === 'success') {
                var contact = data.contact;
                cb(contact);
            }
            else {
                return alert("Error: " + data.message);
            }
        }
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
                return alert(data.message);
            }
            else {
                return alert("Error: " + data.message);
            }
        },
        'json'
    );
}

//update an existing contact
function updateContact(contact, confirm, cb) {
var item, url;
    var id = contact._id;
    delete(contact['_id']); //need to remove _id from payload
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
                if (confirm) {
                    return alert(data.message);
                }
            }  
            else {
                 return alert("Error: " + data.message);
            }
        },
        error: function(jqXHR, status, error) {
            return alert("Unexpected Error From Server: " + status);
        },
        timeout: 5000
    });
 }
 
 //delete this contact
 function deleteContact(contact, cb) {
    var id = contact._id;
    var url = _baseUrl + '/' + id + '?key=' + _apiKey;
    $.ajax({
        url: url,
        type: 'DELETE',
        dataType: 'json',
        success: function(data) {
            if (data.status === 'success') {
                cb();
                //return alert(data.message);
            }  
            else {
                 return alert("Error:" + data.message);
            }
        },
        error: function(jqXHR, status, error) {
            return alert("Unexpected Error From Server: " + status);
        },
        timeout: 5000
    });
 }
 
 /**
  * Listeners
  */

//contact list before show
$(document).on("pagebeforeshow", "#home-page", function(event) {
    var contactList = $('#contact-list');
    contactList.html('');
    
    //get contact list
    getContacts(function(contacts) {
        //insert elements into DOM tree
        for (var i in contacts) {
            var contact = contacts[i];
            contactList.append('<li><a href="#" id=' + i + '>' + contact.name + '</a></li>');
            
            //set up links for contact items 
            $(document).off('click', '#'+i); //need to remove any previous listener
            $(document).on('click', '#'+i, { contact: contact }, function(event) {
                if(event.handled !== true) // This will prevent event triggering more then once
                {
                    _selectedContact = event.data.contact;
                    _operation = 'view';
                    $.mobile.changePage( $("#details"), { transition: "slide"} );
                    event.handled = true;
                }              
            });
        }
        refreshContactList();
    });
    
});

//contact details before show
$(document).on('pagebeforeshow', '#details', function(event, data) {    
    var contact = _selectedContact;
    setContactToPage(contact);
});

//add new contact listener
$(document).on('click', '#new', function(event, data) {
    if(event.handled !== true) // This will prevent event triggering more then once
    {
        _selectedContact = newContact();
        _operation = 'new';
        $.mobile.changePage( $("#details"), { transition: "slide"} );
        event.handled = true;
    }              
});

//save existing contact
$(document).on('click', '#save', function(event, data) {
    if(event.handled !== true) // This will prevent event triggering more then once
    {
        var current = getContactFromPage();
        
        //if contact exists
        if (_operation === 'view') {
            current._id = _selectedContact._id;
            updateContact(current, true, function(contact) {
                _selectedContact = contact; //set current contact to that returned from request
            });
        }
        //for new contact
        else if (_operation === 'new') {
            addContact(current, function(contact) {
                _selectedContact = contact; //set current contact to that returned from request
                _operation = 'view';
            });
        }
        
        event.handled = true;
    }              
});

//save before going back
$(document).on('click', '#back', function(event, data) {
    if(event.handled !== true) // This will prevent event triggering more then once
    {
        var current = getContactFromPage();
        current._id = _selectedContact._id;
        updateContact(current, false, function(contact) {
            _selectedContact = contact; //set current contact to that returned from request
            $.mobile.changePage( $("#home-page") );
        });
    }
    
    event.handled = true;
});

//delete contact dialog
$(document).on('click', '#delete', function(event, data) {
    if(event.handled !== true) // This will prevent event triggering more then once
    {
        //create delete dialog
        $("#deleteDialog [data-role='header'] h2").html('Delete Contact');
        $("#deleteDialog [data-role='content'] p").html('Delete ' + _selectedContact.name + '?');
        $.mobile.changePage( $("#deleteDialog"), { role: "dialog", transition:"flip" } );
    }
    
    event.handled = true;
});

//delete contact
$(document).on('click', '#deleteOK', function(event, data) {
    if(event.handled !== true) // This will prevent event triggering more then once
    {
        var current = getContactFromPage();
        current._id = _selectedContact._id;
        deleteContact(current, function() {
            $.mobile.changePage( $("#home-page") );
        });
    }
    
    event.handled = true;
});