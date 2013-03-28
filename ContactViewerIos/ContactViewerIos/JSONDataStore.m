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

@synthesize filename = _filename;
@synthesize datastore = _datastore;

//get instance
+ (id)singleton {
    _singleton = [[self alloc] initWithFileName:@"data.json"];
    return _singleton;
}

//init
- (id)initWithFileName:(NSString*)filename {
    self = [super init];
    _filename = filename;
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
        [self saveToFile];
    }
}

//load all contacts from file and return them
- (NSMutableDictionary*)loadFromFile {
//    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    
    NSError *jsonError = nil;
    
    NSFileManager *fileManager =[NSFileManager defaultManager];
    
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:self.filename];
    if(![fileManager fileExistsAtPath:filePath]){
        return [[NSMutableDictionary alloc] init];
    }
    
    NSData *jsonData = [NSData dataWithContentsOfFile:filePath options:kNilOptions error:&jsonError ];
    
    NSMutableDictionary *dictionary = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONWritingPrettyPrinted error:&jsonError];
        
    return dictionary;
}

//save all contact to file
- (void)saveToFile {
    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:self.datastore
                                            options:NSJSONWritingPrettyPrinted
                                            error:&error];
    
    //applications Documents dirctory path
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    
    NSString *filePath = [NSString stringWithFormat:@"%@/%@", documentsDirectory,self.filename];
    
    [jsonData writeToFile:filePath atomically:YES];
    
//    NSString* aStr;
//    aStr = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
//    
//    NSLog(aStr);   
}

@end
