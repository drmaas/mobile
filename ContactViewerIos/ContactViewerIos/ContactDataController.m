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
    
    return nil;
}

//update contact details, delegating to JSONDataStore
- (void)updateContact:(Contact*)contact andName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter {
    
}

//delete contact, delegating to JSONDataStore
- (void)deleteContact:(Contact*)contact {
    
}

//return all contacts, delegating to JSONDataStore
- (ContactList*)getAllContacts {
    
    return nil;
}

//save current contact list
- (void)saveAllContacts:(ContactList*) list {
    
}

@end
