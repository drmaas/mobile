//function saveContact(contact, cb) {

//    $.ajax('http://contacts.tinyapollo.com/contacts/' + contact._id + 
//    }









var _apiKey = 'kill-9';
var _contacts = null;

//gets the contact list from the server
function getContacts(cb) {
    $.get(
        'http://contacts.tinyapollo.com/contacts?key=' + _apiKey,
        function(data) {
            _contacts = data.contacts;
            cb();
        }
    )
}

//home page before show
$(document).on("pagebeforeshow", "#home-page", function() {
    var contactList = $('#contact-list');
    contactList.html('');
    getContacts(function() {
        for (var i in _contacts) {
            var contact = _contacts[i];
            contactList.append('<li><a href="#">' + contact.name + '</a></li>');
        }
        contactList.listview('refresh');
    })
})