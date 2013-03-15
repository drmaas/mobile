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

//TODO: what do these properties mean?
@property (strong) JSONDataStore* store;

- (id)initWithDataStore:(JSONDataStore*)store;

- (Contact *)addContactWithName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter;

- (void)updateContact:(Contact*)contact;

- (void)deleteContact:(Contact*)contact;

- (ContactList*)getAllContacts;

- (void)saveAllContacts:(ContactList*) list;

@end
