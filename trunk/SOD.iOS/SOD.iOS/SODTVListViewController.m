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
    serverList = [[NSArray alloc] init];
    serverList = [TestManager searchServer];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

-(void)viewDidAppear:(BOOL)animated
{
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    int numberOfRow = [serverList count]/3;
    return numberOfRow;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *serverListCell;
    
    if(indexPath.section == 0) {
        serverListCell = [tableView dequeueReusableCellWithIdentifier:@"CELL_ID_SERVERLIST"];
        
        if(serverListCell == nil) {
            serverListCell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:@"CELL_ID_SERVERLIST"];
        }
        
        serverListCell.textLabel.text = [self.serverList objectAtIndex:(indexPath.row*3)];
        serverListCell.detailTextLabel.text = [self.serverList objectAtIndex:(indexPath.row*3)+2];
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

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSString *ipAddress = [serverList objectAtIndex:indexPath.row*3];
    int port = [[serverList objectAtIndex:(indexPath.row*3)+1] intValue];
    
    [TestManager connectToServer:ipAddress withPort:port];
    
    SODPacket *testPacket = [[SODPacket alloc] init];
    [testPacket pushObject:@"test message" withType:@"string"];
    [TestManager sendToServer:testPacket];
}

- (void)dealloc 
{
    [super dealloc];
    [serverList release];
    [TestManager release];
}
@end
