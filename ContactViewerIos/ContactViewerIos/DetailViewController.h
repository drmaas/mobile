//
//  DetailViewController.h
//  ContactViewerIos
//
//  Created by ANDY SELVIG on 3/7/12.
//  Copyright (c) 2012 Tiny Mission. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "Contact.h"
#import "ContactDataController.h"

@interface DetailViewController : UIViewController <UISplitViewControllerDelegate, UITextFieldDelegate>

@property (strong, nonatomic) ContactList* contacts;
@property (strong, nonatomic) Contact *contact;
@property (strong, nonatomic) NSString *mode;
@property (weak, nonatomic) IBOutlet UITextField *cname;
@property (weak, nonatomic) IBOutlet UITextField *ctitle;
@property (weak, nonatomic) IBOutlet UITextField *cemail;
@property (weak, nonatomic) IBOutlet UITextField *cphone;
@property (weak, nonatomic) IBOutlet UITextField *ctwitter;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *editButton;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *backButton;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *deleteButton;

-(BOOL)textFieldShouldReturn:(UITextField *)textField;

-(IBAction)onEditPress:(id)sender;
-(IBAction)onBackPress:(id)sender;
-(IBAction)onDeletePress:(id)sender;

@end
