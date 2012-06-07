//
//  MyClass.h
//  PluginTest
//
//  Created by Project BJ on 12. 6. 4..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>
#import "SharedObject.h"
#define EUCKEEncoding -2147481280

@interface MyClass : CDVPlugin <TransceiveDeligate>
{
    BOOL receivedFlg;
    NSMutableString *tempMessage;
    NSString *callbackID;
    SharedObject *sharedConn;
}

@property (nonatomic, copy) NSString* callbackID;

-(void) receiveData:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;
-(void) sendData:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;

-(SharedObject *) isSharedConn:(SharedObject *)conn;
@end
