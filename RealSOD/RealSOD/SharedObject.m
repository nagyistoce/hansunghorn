//
//  SharedObject.m
//  RealSOD
//
//  Created by Project BJ on 12. 6. 5..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SharedObject.h"

@implementation SharedObject
@synthesize myAccess;
static SharedObject *sharedObject = nil;

+(id)init
{
    [[super alloc] init];
    
    return self;
}

+(SharedObject *)sharedObject
{
    if(!sharedObject){
        sharedObject = [[self alloc] init];
    }
    
    return sharedObject;
}

+(void)releaseSharedObject
{
    [sharedObject release];
    sharedObject = nil;
}

-(void)setConnectInstance:(SODAccessManager *)inputObject
{
    myAccess = [inputObject retain];
}
@end
