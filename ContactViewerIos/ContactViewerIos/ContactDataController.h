//
//  ContactDataController.h
//  ContactViewerIos
//
//  Created by Dan Maas on 3/14/13.
//  Copyright (c) 2013 Tiny Mission. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "ContactList.h"
#import "JSONDataStore.h"

@class Contact;

@interface ContactDataController : NSObject

@property (strong) JSONDataStore* store;

- (id)initWithDataStore:(JSONDataStore*)store;
 
- (ContactList*)getAllContacts;

- (ContactList*)getAllSampleContacts;

- (void)saveAllContacts:(ContactList*) list;

@end
