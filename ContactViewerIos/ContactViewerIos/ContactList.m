//
//  ContactList.m
//  ContactViewerIos
//
//  Created by ANDY SELVIG on 3/7/12.
//  Copyright (c) 2012 Tiny Mission. All rights reserved.
//

#import "ContactList.h"

static ContactList* _singleton = nil;

@implementation ContactList

@synthesize allContacts=_contacts;


-(id)initWithCapacity:(NSInteger)capacity {
    self = [super init];
    _contacts = [[NSMutableArray alloc] initWithCapacity:capacity];
    return self;
}

//TODO when we are able to read/write json files, don't call this
+(void)initSingleton {

    _singleton = [[ContactList alloc] initWithCapacity:8];
    
   [_singleton addContact:[[Contact alloc] initWithName:@"Malcom Reynolds"
                                            andPhone:@"612-555-1234"
                                            andTitle:@"Captain"
                                            andEmail:@"mal@serenity.com"
                                           andTwitterId:@"malcomreynolds"]];
    [_singleton addContact:[[Contact alloc] initWithName:@"Zoe Washburne"
                                                andPhone:@"612-555-5678"
                                                andTitle:@"First Mate"
                                                andEmail:@"zoe@serenity.com"
                                            andTwitterId:@"zoewashburne"]];
    [_singleton addContact:[[Contact alloc] initWithName:@"Hoban Washburne"
                                                andPhone:@"612-555-9012"
                                                andTitle:@"Pilot"
                                                andEmail:@"wash@serenity.com"
                                            andTwitterId:@"wash"]];
    [_singleton addContact:[[Contact alloc] initWithName:@"Jayne Cobb"
                                                andPhone:@"612-555-1234"
                                                andTitle:@"Muscle"
                                                andEmail:@"jayne@serenity.com"
                                            andTwitterId:@"heroofcanton"]];
    [_singleton addContact:[[Contact alloc] initWithName:@"Kaylee Frye"
                                                andPhone:@"612-555-7890"
                                                andTitle:@"Engineer"
                                                andEmail:@"kaylee@serenity.com"
                                            andTwitterId:@"kaylee"]];
    [_singleton addContact:[[Contact alloc] initWithName:@"Simon Tam"
                                                andPhone:@"612-555-4321"
                                                andTitle:@"Doctor"
                                                andEmail:@"simon@serenity.com"
                                            andTwitterId:@"simontam"]];
    [_singleton addContact:[[Contact alloc] initWithName:@"River Tam"
                                                andPhone:@"612-555-8765"
                                                andTitle:@"Doctor's Sister"
                                                andEmail:@"river@serenity.com"
                                            andTwitterId:@"miranda"]];
    [_singleton addContact:[[Contact alloc] initWithName:@"Shepherd Book"
                                                andPhone:@"612-555-2109"
                                                andTitle:@"Shepherd"
                                                andEmail:@"shepherd@serenity.com"
                                            andTwitterId:@"shepherdbook"]];
}

//add contact
-(void)addContact:(Contact*)contact {
    NSInteger len = 24;
    contact.contact_id = [ContactUtils createId:len];
    [_contacts addObject:contact];
}

//remove contact at array index
-(void)removeContactAtIndex:(NSInteger)index {
    [_contacts removeObjectAtIndex:index];
}

//remove contact based on id
-(void)removeContact:(Contact *)contact {
    NSString *cid = contact.contact_id;
    [_contacts enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        Contact* c = (Contact*)obj;
        NSString *tmp_cid = c.contact_id;
        NSLog([NSString stringWithFormat:@"delete id:%@ this id:%@",cid, tmp_cid]);
        if ([tmp_cid isEqualToString:cid]) {
            [_contacts removeObjectAtIndex:idx];
            *stop = YES;
            return;
        }
    }];
    
}

-(Contact*)contactAtIndex:(NSInteger)index {
    return [_contacts objectAtIndex:index];
}

+(ContactList*)singleton {
    return _singleton;
}

//sample data singleton
+(ContactList*)singletonFromSample {
    if (_singleton == nil) {
        [self initSingleton];
    }
    return _singleton;
}

//return a contactlist from an array of contacts
+(ContactList*)singletonFromArray:(NSMutableArray*)contacts {
    
    _singleton = [[ContactList alloc] initWithCapacity:[contacts count]];
    
    NSEnumerator *e = [contacts objectEnumerator];
    id object;
    Contact *c;
    while (object = [e nextObject]) {
        c = (Contact*)object;
        [_singleton addContact:c];
    }
    
    return _singleton;
}

-(NSInteger)count {
    return [_contacts count];
}


-(NSMutableArray*)contactsAsDictionaries{
    NSMutableArray *myArray = [NSMutableArray array];
    
    for (Contact* contact in self.allContacts) {
        NSMutableDictionary *contactDict = [contact convertToDictionary];
        [myArray addObject:contactDict];
    }
    
    return myArray;
}

@end
