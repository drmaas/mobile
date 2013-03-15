//
//  DetailViewController.m
//  ContactViewerIos
//
//  Created by ANDY SELVIG on 3/7/12.
//  Copyright (c) 2012 Tiny Mission. All rights reserved.
//

#import "DetailViewController.h"
#import "Contact.h"

@interface DetailViewController ()
@property (strong, nonatomic) UIPopoverController *masterPopoverController;
- (void)configureView;
@end

@implementation DetailViewController

@synthesize masterPopoverController = _masterPopoverController;
@synthesize datacontroller = _datacontroller;
@synthesize contact = _contact;
@synthesize mode = _mode;
@synthesize cname = _cname;
@synthesize ctitle = _ctitle;
@synthesize cemail = _cemail;
@synthesize cphone = _cphone;
@synthesize ctwitter = _ctwitter;

#pragma mark - Managing the detail item

//make keyboard go away
-(BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}

- (void)setContact:(Contact *)newContact
{
    if (_contact != newContact) {
        _contact = newContact;
        
        // Update the view.
        [self configureView];
    }

    if (self.masterPopoverController != nil) {
        [self.masterPopoverController dismissPopoverAnimated:YES];
    }        
}

- (void)configureView
{
    // Update the user interface for the detail item.
    Contact *c = self.contact;
    if (c) {
        //NSString *test = [NSString stringWithFormat:@"Name: %@", c.name];
        //NSLog(test);
        self.cname.text = c.name;
        self.ctitle.text = c.title;
        self.cemail.text = c.email;
        self.cphone.text = c.phone;
        self.ctwitter.text = c.twitterId;
        
    }
    
    //TODO get datacontroller from delegate? 
    
    [self updateMode];
}

- (void)updateMode {
    //disable edit fields when viewing
    if ([self.mode isEqualToString:@"view"]) {
        self.cname.enabled = NO;
        self.ctitle.enabled = NO;
        self.cemail.enabled = NO;
        self.cphone.enabled = NO;
        self.ctwitter.enabled = NO;
        self.editButton.title = @"Edit";
        self.backButton.title = @"Back";
    }
    else {
        self.cname.enabled = YES;
        self.ctitle.enabled = YES;
        self.cemail.enabled = YES;
        self.cphone.enabled = YES;
        self.ctwitter.enabled = YES;
        self.editButton.title = @"Done";
        self.backButton.title = @"Cancel";
    }
}

//enable editing or saving
- (void)onEditPress:(id)sender {
    
    //save
    if ([self.mode isEqual: @"edit"]) {
        self.mode = @"view";
        [self.contact update:self.cname.text andPhone:self.cphone.text andTitle:self.ctitle.text andEmail:self.cemail.text andTwitterId:self.ctwitter.text];
        
        //TODO persist to JSON via ContactDataController
        //[self.datacontroller updateContact:self.contact];
        
        //show message indicating save
        UIAlertView* alert = [[UIAlertView alloc] initWithTitle:@"Contact Saved" message:nil delegate:nil cancelButtonTitle:@"OK" otherButtonTitles: nil];
        [alert show];
    }
    //edit
    else {
        self.mode = @"edit";
    }
    
    [self updateMode];
}

//go back
-(void)onBackPress:(id)sender {
    //figure out how to navigate to main view
    [[self navigationController] popViewControllerAnimated:YES];
}

//delete press, show popup alert
- (IBAction)onDeletePress:(id)sender {
    //show message indicating save
    NSString* title = [NSString stringWithFormat:@"Delete %@", self.cname.text];
    UIAlertView* alert = [[UIAlertView alloc] initWithTitle:title message:nil delegate:self cancelButtonTitle:@"No" otherButtonTitles:@"Yes", nil];
    [alert show];
}


//delete contact or cancel
- (void) alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 1) {
        //delete contact by calling contactdatacontroller.delete
        //[self.datacontroller deleteContact:self.contact];
        
        //go back to main page
        [[self navigationController] popViewControllerAnimated:YES];
    }
    else {
        //cancel
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    [self configureView];
    
    //for hiding keyboard
    self.cname.delegate = self;
    self.ctitle.delegate = self;
    self.cemail.delegate = self;
    self.cphone.delegate = self;
    self.ctwitter.delegate = self;
    
}

- (void)viewDidUnload
{
    [self setEditButton:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}

#pragma mark - Split view

- (void)splitViewController:(UISplitViewController *)splitController willHideViewController:(UIViewController *)viewController withBarButtonItem:(UIBarButtonItem *)barButtonItem forPopoverController:(UIPopoverController *)popoverController
{
    barButtonItem.title = NSLocalizedString(@"Master", @"Master");
    [self.navigationItem setLeftBarButtonItem:barButtonItem animated:YES];
    self.masterPopoverController = popoverController;
}

- (void)splitViewController:(UISplitViewController *)splitController willShowViewController:(UIViewController *)viewController invalidatingBarButtonItem:(UIBarButtonItem *)barButtonItem
{
    // Called when the view is shown again in the split view, invalidating the button and popover controller.
    [self.navigationItem setLeftBarButtonItem:nil animated:YES];
    self.masterPopoverController = nil;
}

@end
