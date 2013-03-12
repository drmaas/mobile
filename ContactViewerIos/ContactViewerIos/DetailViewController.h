//
//  DetailViewController.h
//  ContactViewerIos
//
//  Created by ANDY SELVIG on 3/7/12.
//  Copyright (c) 2012 Tiny Mission. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "Contact.h"

@interface DetailViewController : UIViewController <UISplitViewControllerDelegate>

@property (strong, nonatomic) Contact *contact;
@property (weak, nonatomic) IBOutlet UITextField *cname;
@property (weak, nonatomic) IBOutlet UITextField *ctitle;
@property (weak, nonatomic) IBOutlet UITextField *cemail;
@property (weak, nonatomic) IBOutlet UITextField *cphone;
@property (weak, nonatomic) IBOutlet UITextField *ctwitter;

@end
