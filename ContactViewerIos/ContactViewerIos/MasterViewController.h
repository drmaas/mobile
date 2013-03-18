//
//  MasterViewController.h
//  ContactViewerIos
//
//  Created by ANDY SELVIG on 3/7/12.
//  Copyright (c) 2012 Tiny Mission. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "ContactList.h"
#import "ContactDataController.h"

@class DetailViewController;

@interface MasterViewController : UITableViewController <UITableViewDataSource>

@property (strong, nonatomic) DetailViewController *detailViewController;

@property (strong, nonatomic) ContactList* contacts;

@property (strong, nonatomic) ContactDataController* datacontroller;

// this gets called when the user taps the plus button above the list
-(IBAction)onAddContact:(id)sender;

@end
