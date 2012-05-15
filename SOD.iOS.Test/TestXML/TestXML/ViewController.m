//
//  ViewController.m
//  TestXML
//
//  Created by Project BJ on 12. 5. 12..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize textView;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    testPacket = [[SODPacket alloc] init];
    testSerializer = [[SODSerializer alloc] init];
    buff = [[NSData alloc] init];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    [testPacket release];
    [testSerializer release];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}

-(IBAction)toXMLFromPacket:(id)sender
{
    [testPacket pushObject:@"test" withType:@"string"];
    [testPacket pushObject:@"4" withType:@"int"];
    [testPacket pushObject:@"0x2775039" withType:@"bytearray"];
    
    buff = [testSerializer serialize:buff withPacket:testPacket];
    
    NSString *newStr = [[NSString alloc] initWithData:buff encoding:NSUTF8StringEncoding];
    
    [textView setText:newStr];
}

-(IBAction)toPacketFromXML:(id)sender
{
    testPacket = [[SODPacket alloc]init];
    
    NSString *src = [[NSString alloc] initWithString:@"<?xml version=\"1.0\" encoding=\"UTF-8\"?><Packet><Item type=\"int\"> 23 </Item><Item type=\"int\"> 226 </Item><Item type=\"string\"> test </Item><Item type=\"string\"> test </Item></Packet>"];
    buff = [src dataUsingEncoding:NSUTF8StringEncoding];
    
    testPacket = [testSerializer deSerialize:buff withPacket:testPacket];
    
    NSString *newStr = [testPacket popObject];
    [textView setText:newStr];
}

@end
