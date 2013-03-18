//
//  ContactUtils.m
//  ContactViewerIos
//
//  Created by Dan Maas on 3/16/13.
//  Copyright (c) 2013 Tiny Mission. All rights reserved.
//

#import "ContactUtils.h"

@implementation ContactUtils

//create random id
+ (NSString *) createId:(NSInteger)len {
    
    NSString *letters = @"abcdefghijklmnopqrstuvwxyzZ0123456789";
    NSMutableString *randomString = [NSMutableString stringWithCapacity: len];
    
    for (int i=0; i<len; i++) {
        [randomString appendFormat: @"%C", [letters characterAtIndex: arc4random() % [letters length]]];
    }
    
    return randomString;
}

@end
