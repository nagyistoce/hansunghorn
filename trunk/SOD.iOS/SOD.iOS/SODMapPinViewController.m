//
//  SODMapPinViewController.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 6. 1..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODMapPinViewController.h"

@implementation SODMapPinViewController
@synthesize myPin;

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
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 2;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *tvInfomationCell;
    
    if(indexPath.section == 0) {
        tvInfomationCell = [tableView dequeueReusableCellWithIdentifier:@"CELL_ID_SERVERLIST"];
        
        if(tvInfomationCell == nil) {
            tvInfomationCell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"CELL_ID_SERVERLIST"];
        }
        
        if(indexPath.row == 0){
            tvInfomationCell.textLabel.text = [myPin title];
        }
        
        else if(indexPath.row == 1){
            tvInfomationCell.textLabel.text = [myPin subtitle];
        }
    }
    
    return tvInfomationCell;
}

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
    return @"스마트TV 정보";
}

-(NSInteger)numberOfSectionInTableView:(UITableView *)tableView
{
    return 1;
}

@end
