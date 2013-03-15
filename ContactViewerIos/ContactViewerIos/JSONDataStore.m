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
- (int)addWithName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter {
    
    NSString *_id = [self createId:16];
    
    return _id.intValue;
}

//delete contact
- (void)deleteWithId:(int)_id {
    
}

//update contact details
- (void)updateWithId:(int)_id andName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter {
    
}

//get particular contact details
- (NSDictionary*)getWithId:(int)_id {
    
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

//create random id
- (NSString *) createId:(int)len {
    
    NSString *letters = @"abcdefghijklmnopqrstuvwxyzZ0123456789";
    NSMutableString *randomString = [NSMutableString stringWithCapacity: len];
    
    for (int i=0; i<len; i++) {
        [randomString appendFormat: @"%C", [letters characterAtIndex: arc4random() % [letters length]]];
    }
    
    return randomString;
}

@end
