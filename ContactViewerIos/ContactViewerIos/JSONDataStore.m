//
//  JSONDataStore.m
//  ContactViewerIos
//
//  Created by Dan Maas on 3/14/13.
//  Copyright (c) 2013 Tiny Mission. All rights reserved.
//

#import "JSONDataStore.h"

static JSONDataStore* _singleton = nil;

@implementation JSONDataStore

@synthesize filepath = _filepath;
@synthesize datastore = _datastore;

//get instance
+ (id)singleton {
    _singleton = [[self alloc] initWithFileName:@"REPLACEME"];
    return _singleton;
}

//init
- (id)initWithFileName:(NSString*)filepath {
    self = [super init];
    _filepath = filepath;
    _datastore = [self loadFromFile];
    
    return self;
}

//get all contacts
- (NSMutableArray*)getAllContacts {
    return [self.datastore objectForKey:@"contacts"];
}

//save contact list
- (void)saveAllContacts:(NSMutableArray *)contacts {
    if (contacts != nil) {
        [self.datastore setObject:contacts forKey:@"contacts"];
    }
}

//load all contacts from file and return them
- (NSMutableDictionary*)loadFromFile {
    
    NSMutableDictionary *dict = nil;
    
    //TODO load dict from file
    
    return dict;
}

//save all contact to file
- (void)saveToFile {
    
    //TODO save dict to file
    
}

@end
