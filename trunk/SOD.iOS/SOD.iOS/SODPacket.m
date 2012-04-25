//
//  SODPacket.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODPacket.h"

@implementation SODPacket

enum SerializableObjectType
{
    integer,
    doubleInteger,
    String,
    ByteArray,
};

const int RESPONSE_SERVICE_NAME = 0xff000000;
const int REQUEST_CLIENT_ALIVE = 0xff000001;
const int RESPONSE_CLIENT_ALIVE = 0xff000002;
const int REQUEST_SERVICE_DATA = 0xff000003;
const int RESPONSE_SERVICE_DATA = 0xff000004;

-(int) getElementCount
{
    return count;
}

-(void) pushObject:(NSObject *)object
{
    
}

-(NSObject *) popObject
{
    
}

@end
