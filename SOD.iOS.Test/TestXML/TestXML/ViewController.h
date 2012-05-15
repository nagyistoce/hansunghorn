//
//  ViewController.h
//  TestXML
//
//  Created by Project BJ on 12. 5. 12..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
//#import "SODPacket.h"
#import "SODSerializer.h"

@interface ViewController : UIViewController
{
    @public 
    SODPacket *testPacket;
    SODSerializer *testSerializer;
    
    NSData *buff;
    
    IBOutlet UITextView *textView;
    IBOutlet UIButton *toXML;
    IBOutlet UIButton *toPacket;
}

@property (nonatomic, retain) IBOutlet UITextView *textView;

-(IBAction)toXMLFromPacket:(id)sender;
-(IBAction)toPacketFromXML:(id)sender;

@end
