//
//  AppDelegate.m
//  ContactViewerIos
//
//  Created by ANDY SELVIG on 3/7/12.
//  Copyright (c) 2012 Tiny Mission. All rights reserved.
//

#import "AppDelegate.h"

@implementation AppDelegate

@synthesize window = _window;
@synthesize contacts;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    
    // Override point for customization after application launch.
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        UISplitViewController *splitViewController = (UISplitViewController *)self.window.rootViewController;
        UINavigationController *navigationController = [splitViewController.viewControllers lastObject];
        splitViewController.delegate = (id)navigationController.topViewController;
    }
    
    //load contacts
    [self loadContactsFromStorage];
    
    return YES;
}
							
- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
    
    //save all contacts
    [self saveContactsToStorage];

}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
    
    //retrieve all contacts from storage
    [self loadContactsFromStorage];
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    /*
     Called when the application is about to terminate.
     Save data if appropriate.
     See also applicationDidEnterBackground:.
     */
    //save all contacts
    [self saveContactsToStorage];
}

//save contact list to file
- (void)saveContactsToStorage {
    contacts = [ContactList singleton];
    JSONDataStore *datastore = [JSONDataStore singleton];
    ContactDataController *datacontroller = [[ContactDataController alloc] initWithDataStore:datastore];
    [datacontroller saveAllContacts:contacts];
}

//load contact list from file
//TODO toggle last 2 lines when we want to use the datastore
- (void)loadContactsFromStorage {
    JSONDataStore *datastore = [JSONDataStore singleton];
    ContactDataController *datacontroller = [[ContactDataController alloc] initWithDataStore:datastore];
    
    if([[datastore datastore] count] == 0){
        contacts = [datacontroller getAllSampleContacts];
    }
    else{
        contacts = [datacontroller getAllContacts];
    }
}

@end
