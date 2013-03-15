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
- (Contact *)addContactWithName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter {
    
    int _id = [self.store addWithName:name andTitle:title andEmail:email andPhone:phone andTwitter:twitter];
    
    //TODO: create contact object and return it. add id field to contact
    
    return nil;
}

//update contact details, delegating to JSONDataStore
- (void)updateContact:(Contact*)contact {
    
}

//delete contact, delegating to JSONDataStore
- (void)deleteContact:(Contact*)contact {
    
}

//get contact
- (Contact*)getContact:(NSInteger*)cid {
    
    return nil;
}

//return all contacts, delegating to JSONDataStore
- (ContactList*)getAllContacts {
    
    return nil;
}

//save current contact list
- (void)saveAllContacts:(ContactList*) list {
    
}

@end
