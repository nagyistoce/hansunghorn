//
//  SODTVListViewController.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 3..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODTVListViewController.h"

@interface SODTVListViewController ()

@end

@implementation SODTVListViewController
@synthesize serverList;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    TestManager = [[SODAccessManager alloc] init];
    serverList = [NSArray alloc];
    serverList = [TestManager searchServer];
}

- (void)viewDidUnload
{
    [self setServerList:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [serverList count];
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *serverListCell;
    
    if(indexPath.section == 0) {
        serverListCell = [tableView dequeueReusableCellWithIdentifier:@"CELL_ID_SERVERLIST"];
        
        if(nil == serverListCell) {
            serverListCell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:@"CELL_ID_SERVERLIST"];
        }
        
        serverListCell.textLabel.text = [self.serverList objectAtIndex:indexPath.row];
        serverListCell.detailTextLabel.text = [self.serverList objectAtIndex:indexPath.row];
    }
    
    return serverListCell;
}

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
    return @"주변 스마트TV 목록";
}

-(NSInteger)numberOfSectionInTableView:(UITableView *)tableView
{
    return 1;
}

- (void)dealloc 
{
    [serverList release];
    [TestManager release];
    [super dealloc];
}
@end
