//
//  SODViewController.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODViewController.h"

@interface SODViewController ()

@end

@implementation SODViewController
@synthesize delegate;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    UIImage* img1 = [UIImage imageNamed:@"ana_service_search_server_up.png"];
    UIImage* img2 = [UIImage imageNamed:@"ana_service_map_view.png"];
    UIImage* img3 = [UIImage imageNamed:@"ana_service_view_service_list.png"];
    
    [serverList setImage:img1 forState:UIControlStateNormal];
    [locationView setImage:img2 forState:UIControlStateNormal];
    [installedService setImage:img3 forState:UIControlStateNormal];
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

-(IBAction)showTestMenu:(id)sender
{
    SODTestViewController *newView = [[SODTestViewController alloc] initWithNibName:@"TestView" bundle:nil];
    [self.navigationController pushViewController:newView animated:YES];
}

-(IBAction)showList:(id)sender
{
    SODTVListViewController *newView = [[SODTVListViewController alloc] initWithNibName:@"SODTVList" bundle:nil];
    [newView setDelegate:self.delegate];
    [self.navigationController pushViewController:newView animated:YES];
}


-(IBAction)showTVLcationList:(id)sender
{
    SODTVLocationViewController *newView = [[SODTVLocationViewController alloc] initWithNibName:@"SODMapView" bundle:nil];
    [self.navigationController pushViewController:newView animated:YES];
}

-(IBAction)showServiceList:(id)sender
{
    SODServiceListViewController *newView = [[SODServiceListViewController alloc] initWithNibName:@"SODServiceListView" bundle:nil];
    [self.navigationController pushViewController:newView animated:YES];
}

@end
