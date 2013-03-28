//
//  JSONDataStore.h
//  ContactViewerIos
//
//  Created by Dan Maas on 3/14/13.
//  Copyright (c) 2013 Tiny Mission. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface JSONDataStore : NSObject

//TODO need method to keep track of largest existing id, so we can increment when adding contacts

@property(nonatomic,retain) NSString* filename;
@property(strong) NSMutableDictionary* datastore;

+ (id)singleton;
    
- (id)initWithFileName:(NSString*)filename;

- (NSMutableArray*)getAllContacts;

- (void)saveAllContacts:(NSMutableArray*)contacts;

- (NSMutableDictionary*)loadFromFile;

- (void)saveToFile;

@end