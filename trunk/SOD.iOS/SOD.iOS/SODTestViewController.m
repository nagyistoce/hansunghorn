
//
//  SODTestViewController.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 15..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODTestViewController.h"

@implementation SODTestViewController

-(void)viewDidLoad
{
    conn = [[SODAccessManager alloc] init];
    isRunning = NO;
    [conn setDelegate:self];       
}

-(IBAction)sendMessage:(id)sender
{
    NSString *ipAddr = [ipAddress text];
    NSString *msg = [iMessage text];
    SODPacket *temp = [[SODPacket alloc] init];
    [temp pushObject:msg withType:@"string"];
    int port = [[portNum text] intValue];
    if(isRunning == NO){
        //[conn searchServer];
        [conn connectToServer:ipAddr withPort:port];
        isRunning = YES;
    }
    
    else{
        [conn sendToServer:temp];
    }
    
    [temp release];
}

-(IBAction) backgroundTap: (id)sender 
{
    [ipAddress resignFirstResponder];
    [portNum resignFirstResponder];
    [iMessage resignFirstResponder];
}

-(void)callBack:(SODPacket *)msg
{
    //[chatList performSelectorOnMainThread:@selector(setText:) withObject:msg waitUntilDone:YES];
    NSLog(@"receive");
}
@end
