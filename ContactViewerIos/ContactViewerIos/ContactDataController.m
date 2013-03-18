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
    _store = newstore;
    return self;
}

//return all contacts, delegating to JSONDataStore
- (ContactList*)getAllContacts {
    
    NSMutableArray *array = [self.store getAllContacts];
    ContactList *contacts = [ContactList singletonFromArray:array];
    
    return contacts;
}

//return all contacts, delegating to JSONDataStore
- (ContactList*)getAllSampleContacts {
    
    ContactList* contacts = [ContactList singletonFromSample];
    
    return contacts;
}

//save current contact list
- (void)saveAllContacts:(ContactList*) list {
    
    NSMutableArray *array = [list allContacts];
    [self.store saveAllContacts:array];
}

@end
