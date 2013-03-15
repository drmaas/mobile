//
//  JSONDataStore.m
//  ContactViewerIos
//
//  Created by Dan Maas on 3/14/13.
//  Copyright (c) 2013 Tiny Mission. All rights reserved.
//

#import "JSONDataStore.h"

@implementation JSONDataStore

@synthesize filepath;
@synthesize datastore;

//add contact, and return the data added
- (NSDictionary*)addWithId:(NSInteger*)jid andName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter {
    
    return nil;
}

//delete contact
- (void)deleteWithId:(NSInteger*)jid {
    
}

//update contact details
- (void)updateWithId:(NSInteger*)jid andName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter {
    
}

//get particular contact details
- (NSDictionary*)getWithId:(NSInteger*)jid {
    
    return nil;
}

//get all contacts
- (NSDictionary*)getAll {
    return self.datastore;
}

//load all contacts from file and return them
- (NSDictionary*)loadFromFile {
    
    return self.datastore;
}

@end
