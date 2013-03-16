//
//  ContactDataController.m
//  ContactViewerIos
//
//  Created by Dan Maas on 3/14/13.
//  Copyright (c) 2013 Tiny Mission. All rights reserved.
//

#import "ContactDataController.h"

@implementation ContactDataController

@synthesize store = _store;

//create
- (id)initWithDataStore:(JSONDataStore*)newstore {
    
    self = [super init];
    self.store = newstore;
    return self;
}

//add contact, delegating to JSONDataStore
/*
- (Contact *)addContactWithName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter {
    
    int _id = [self.store addWithName:name andTitle:title andEmail:email andPhone:phone andTwitter:twitter];
    
    //create contact object and return it. add id field to contact
    Contact *c = [[Contact alloc] initWithName:name andPhone:phone andTitle:title andEmail:email andTwitterId:twitter];
    c._id = _id;
    
    return c;
}
 */

//update contact details, delegating to JSONDataStore
/*
- (void)updateContact:(Contact*)contact {
    
    [self.store updateWithId:contact._id andName:contact.name andTitle:contact.title andEmail:contact.email andPhone:contact.phone andTwitter:contact.twitterId];
    
}
 */

//delete contact, delegating to JSONDataStore
/*
- (void)deleteContact:(Contact*)contact {
    
    [self.store deleteWithId:contact._id];
    
}
 */

//get contact
/*
- (Contact*)getContact:(int)cid {
    
    NSMutableDictionary *dict = [self.store getWithId:cid];
    NSString *name = [dict objectForKey:@"name"];
    NSString *title = [dict objectForKey:@"title"];
    NSString *email = [dict objectForKey:@"email"];
    NSString *phone = [dict objectForKey:@"phone"];
    NSString *twitter = [dict objectForKey:@"twitter"];
    
    Contact *c = [[Contact alloc] initWithName:name andPhone:phone andTitle:title andEmail:email andTwitterId:twitter];
    c._id = cid;
    
    return c;
}
 */

//return all contacts, delegating to JSONDataStore
- (ContactList*)getAllContacts {
    
    NSMutableArray *array = [self.store getAllContacts];
    ContactList *contacts = [ContactList singletonFromArray:array];
    
    return contacts;
}

//save current contact list
- (void)saveAllContacts:(ContactList*) list {
    
    NSMutableArray *array = [list allContacts];
    [self.store saveAllContacts:array];
}

@end
