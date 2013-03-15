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

@property(nonatomic,retain) NSString* filepath;
@property(strong) NSDictionary* datastore;

- (int)addWithName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter;

- (void)deleteWithId:(int)_id;

- (void)updateWithId:(int)_id andName:(NSString*)name andTitle:(NSString*)title andEmail:(NSString*)email andPhone:(NSString*)phone andTwitter:(NSString*)twitter;

- (NSDictionary*)getWithId:(int)_id;

- (NSDictionary*)getAll;

- (NSDictionary*)loadFromFile;

@end