//
//  SODTestViewController.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 15..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SODAccessManager.h"

@interface SODTestViewController :  UIViewController <TestViewDeligate>
{
    IBOutlet UITextField *ipAddress;
    IBOutlet UITextField *portNum;
    IBOutlet UITextField *iMessage;
    IBOutlet UITextView *chatList;
    SODAccessManager *conn;   
    BOOL isRunning;
}

-(IBAction) backgroundTap: (id)sender;
-(IBAction)sendMessage:(id)sender;
@end
