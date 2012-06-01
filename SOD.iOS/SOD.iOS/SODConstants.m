//
//  SODConstants.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODConstants.h"

@implementation SODConstants
@synthesize DATATYPE_INT, DATATYPE_FLOAT, DATATYPE_DOUBLE, DATATYPE_STRING,DATATYPE_BYTEARRAY;

-(id)init
{
    DATATYPE_INT = @"int";
    DATATYPE_FLOAT = @"float";
    DATATYPE_DOUBLE = @"double";
    DATATYPE_STRING = @"string";
    DATATYPE_BYTEARRAY = @"bytearray";
    
    return self;
}

-(int) REQUEST_ACCEPT
{
    return 0xff000010;
}

-(int) RESPONSE_ACCEPT
{
    return 0xff000011;
}

-(int) REQUEST_PING
{
    return 0xff000020;
}
-(int) RESPONSE_PING
{
    return 0xff000021;
}
-(int) RESPONSE_SERVICE_NAME
{
    return 0xff000000;
}
-(int) REQUEST_CLIENT_ALIVE
{
    return 0xff000001;
}
-(int) RESPONSE_CLIENT_ALIVE
{
    return 0xff000002;
}
-(int) REQUEST_SERVICE_DATA
{
    return 0xff000003;
}
-(int) RESPONSE_SERVICE_DATA
{
    return 0xff000004;
}

@end
