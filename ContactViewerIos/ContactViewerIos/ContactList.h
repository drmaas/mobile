//
//  ContactList.h
//  ContactViewerIos
//
//  Created by ANDY SELVIG on 3/7/12.
//  Copyright (c) 2012 Tiny Mission. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "Contact.h"

#import "ContactUtils.h"

@interface ContactList : NSObject

// initializes the singleton instance
+(void)initSingleton;

+(ContactList*)singleton;

+(ContactList*)singletonFromSample;

+(ContactList*)singletonFromArray:(NSMutableArray*)contacts;

-(id)initWithCapacity:(NSInteger)capacity;

-(NSInteger)count;

-(void)addContact:(Contact*)contact;

-(void)removeContactAtIndex:(NSInteger)index;

-(void)removeContact:(Contact*)contact;

-(void)updateContact:(Contact*)contact atIndex:(NSInteger)index;

-(void)updateContact:(Contact *)contact;
    
-(Contact*)contactAtIndex:(NSInteger)index;

-(NSMutableArray*)contactsAsDictionaries;

@property(strong) NSMutableArray* allContacts;

@end
