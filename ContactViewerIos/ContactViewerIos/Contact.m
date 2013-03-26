//
//  Contact.m
//  ContactViewerIos
//
//  Created by ANDY SELVIG on 3/7/12.
//  Copyright (c) 2012 Tiny Mission. All rights reserved.
//

#import "Contact.h"

@implementation Contact

@synthesize contact_id, name, title, email, phone, twitterId;

-(id)initWithName:(NSString*)newName 
        andPhone:(NSString*)newPhone
        andTitle:(NSString*)newTitle 
        andEmail:(NSString*)newEmail
        andTwitterId:(NSString*)newTwitterId {
    
    self = [super init];
    
    self.name = newName;
    self.phone = newPhone;
    self.title = newTitle;
    self.email = newEmail;
    self.twitterId = newTwitterId;
    
    return self;
}

-(void)updateWithName:(NSString*)newName
         andPhone:(NSString*)newPhone
         andTitle:(NSString*)newTitle
         andEmail:(NSString*)newEmail
     andTwitterId:(NSString*)newTwitterId {
    
    self.name = newName;
    self.phone = newPhone;
    self.title = newTitle;
    self.email = newEmail;
    self.twitterId = newTwitterId;
    
}

-(NSMutableDictionary*)convertToDictionary{
    
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] initWithCapacity:5];
    
    [dict setObject:self.name forKey:@"Name"];
    [dict setObject:self.phone forKey:@"Phone"];
    [dict setObject:self.title forKey:@"Title"];
    [dict setObject:self.email forKey:@"Email"];
    [dict setObject:self.twitterId forKey:@"TwitterId"];
    
    return dict;
}

@end
