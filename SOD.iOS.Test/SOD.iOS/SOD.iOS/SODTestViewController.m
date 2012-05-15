//
//  SODTestViewController.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 15..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODTestViewController.h"

@implementation SODTestViewController

-(IBAction)sendMessage:(id)sender
{
    conn = [[SODAccessManager alloc] init];
    NSString *ipAddr = [ipAddress text];
    NSString *msg = [iMessage text];
    int port = [[portNum text] intValue];
    [conn connectToServer:msg withIPAddress:ipAddr withPort:port];
}
@end
