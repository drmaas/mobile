//
//  MasterViewController.m
//  ContactViewerIos
//
//  Created by ANDY SELVIG on 3/7/12.
//  Copyright (c) 2012 Tiny Mission. All rights reserved.
//

#import "MasterViewController.h"

#import "DetailViewController.h"

#import "Contact.h"

#import "AppDelegate.h"

@implementation MasterViewController

@synthesize detailViewController = _detailViewController;
@synthesize contacts;
@synthesize datacontroller;

- (void)awakeFromNib
{
    
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        self.clearsSelectionOnViewWillAppear = NO;
        self.contentSizeForViewInPopover = CGSizeMake(320.0, 600.0);
    }
    [super awakeFromNib];
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
    self.detailViewController = (DetailViewController *)[[self.splitViewController.viewControllers lastObject] topViewController];
    
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        [self.tableView selectRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0] animated:NO scrollPosition:UITableViewScrollPositionMiddle];
    }
    
    // get the contact list
    //contacts = [ContactList singleton];
    AppDelegate *controller = (AppDelegate*)[[UIApplication sharedApplication] delegate];
    contacts = controller.contacts;
    
    //setup edit button
    self.navigationItem.leftBarButtonItem = self.editButtonItem;
    
}

//set the state of the edit button
- (void)setEditing:(BOOL)editing animated:(BOOL)animated {
    [super setEditing:editing animated:animated];
    [super.tableView setEditing:editing animated:YES];
    if (editing) {
        //self.navigationItem.leftBarButtonItem.enabled = NO;
    } else {
        self.navigationItem.leftBarButtonItem.enabled = YES;
    }
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    // get the contact list
    // this is important, since when app goes to background
    // viewdidload does not get called again
    contacts = [ContactList singleton];
    
    //refresh contact list
    [self.tableView reloadData];
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


#pragma mark - Table View Data Source
//This is the place to create the detail controller
//data only needs to be persisted when app is closed / loaded when opened
-(IBAction)onAddContact:(id)sender {
    //UIAlertView* alert = [[UIAlertView alloc] initWithTitle:@"New Contact"
    //                                                message:@"You need to do something here"
    //                                               delegate:nil cancelButtonTitle:@"Okay" otherButtonTitles: nil];
    //[alert show];
    
    //navigate to new contact screen
    //self.detailViewController.mode = @"new";
    //[self.navigationController pushViewController:self.detailViewController animated:YES];
}


#pragma mark - Table View Data Source

-(int)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [contacts count];
}

-(int)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    // get or create the cell
    static NSString *CellIdentifier = @"ContactCell";
	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
	if (cell == nil) {
		cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
	}
    
    // set the content in the cell based on the contact
    Contact* contact = [contacts contactAtIndex:indexPath.row];
    cell.textLabel.text = contact.name;
    // this is a bit of a hack, but now we don't need to make a custom cell item
    cell.detailTextLabel.text = [NSString stringWithFormat:@"%@            %@", 
                                 contact.phone, contact.title];
    
    return cell;
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    //self.detailViewController.contact = [contacts contactAtIndex:indexPath.row];
    //self.detailViewController.mode = @"view";
    //[self.navigationController pushViewController:self.detailViewController animated:YES];
    //[self performSegueWithIdentifier:@"ContactDetails" sender:self];
}


// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}

//Return the editing style
- (UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath {
    return UITableViewCellEditingStyleDelete;
}

// Override to support adding and removing from the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        //AppDelegate *controller = (AppDelegate *)[[UIApplication sharedApplication] delegate];
        //[controller removeObjectFromListAtIndex:indexPath.row];
        
        //remove contact from list
        [contacts removeContactAtIndex:indexPath.row];
                
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }   
}

//sent selected object to the detail view
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    DetailViewController *detailViewController = [segue destinationViewController];
    if ([[segue identifier] isEqualToString:@"ViewContact"]) {
        detailViewController.contact = [self.contacts contactAtIndex:[self.tableView indexPathForSelectedRow].row];
        NSLog([NSString stringWithFormat:@"view id:%@",detailViewController.contact.contact_id]);
        detailViewController.mode = @"view";
    }
    else if ([[segue identifier] isEqualToString:@"NewContact"]) {
        detailViewController.contact = [[Contact alloc] initWithName:@"" andPhone:@"" andTitle:@"" andEmail:@"" andTwitterId:@""];
        detailViewController.mode = @"new";
    }
}

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

@end
