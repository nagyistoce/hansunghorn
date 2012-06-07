//
//  MyClass.m
//  PluginTest
//
//  Created by Project BJ on 12. 6. 4..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "MyClass.h" 

@implementation MyClass 

@synthesize callbackID;

- (void) receiveData:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options
{
    NSLog(@"call receive");
    sharedConn = [self isSharedConn:sharedConn];
    tempMessage = [NSMutableString stringWithString: @""];;
    receivedFlg = NO;
    
    self.callbackID = [arguments pop];
    
    SODPacket *tempPacket = [[SODPacket alloc] init];
    [tempPacket pushObject:@"download" withType:@"string"];
    [[sharedConn myAccess] sendToServer:tempPacket];
    
    while(receivedFlg == NO);
    
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:tempMessage];
    
    if (sharedConn != nil)//[stringObtainedFromJavascript isEqualToString:@"HelloWorld"] == YES)
    {
        // Call the javascript success function
        [self writeJavascript: [pluginResult toSuccessCallbackString:self.callbackID]];
    } 
    
    else
    {    
        // Call the javascript error function
        [self writeJavascript: [pluginResult toErrorCallbackString:self.callbackID]];
    }
}

-(void) sendData:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options
{
    NSLog(@"call send");
    sharedConn = [self isSharedConn:sharedConn];
    
    
    self.callbackID = [arguments pop];
    NSLog(@"%@", self.callbackID);
    
    NSString *stringObtainedFromJavascript = [arguments objectAtIndex:0];
    
    SODPacket *tempPacket = [[SODPacket alloc] init];
    [tempPacket pushObject:@"Questionnaire" withType:@"string"];
    [tempPacket pushObject:stringObtainedFromJavascript withType:@"string"];
    BOOL success;
    
    success = [sharedConn.myAccess sendToServer:tempPacket];
    
    NSMutableString *stringToReturn = [NSMutableString stringWithString: @"String Send"];
    
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:stringToReturn];
    
    if (success == YES)//[stringObtainedFromJavascript isEqualToString:@"HelloWorld"] == YES)
    {
        // Call the javascript success function
        [self writeJavascript: [pluginResult toSuccessCallbackString:self.callbackID]];
    } 
    
    else
    {    
        // Call the javascript error function
        [self writeJavascript: [pluginResult toErrorCallbackString:self.callbackID]];
    }
}

-(void)callBack:(SODPacket *)msg
{
    while (msg.getElementCount > 0) {
        [tempMessage appendString:[msg popObject]];
    }
    
    receivedFlg = YES;
}

-(SharedObject *) isSharedConn:(SharedObject *)conn
{
    if(conn != nil)
        return conn;
    else{
        conn = [SharedObject sharedObject];
        [conn.myAccess setDelegate:self];
        return conn;
    }
}
@end