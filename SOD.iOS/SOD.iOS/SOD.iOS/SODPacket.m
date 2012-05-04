//
//  SODPacket.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODPacket.h"

@implementation SODPacket

const NSString *DataType_Int = @"int";
const NSString *DataType_Long = @"long";
const NSString *DataType_Float = @"float";
const NSString *DataType_Double = @"double";
const NSString *DataType_String = @"NSString";
const NSString *DataType_ByteArray = @"bytearray";

const int RESPONSE_SERVICE_NAME = 0xff000000;
const int REQUEST_CLIENT_ALIVE = 0xff000001;
const int RESPONSE_CLIENT_ALIVE = 0xff000002;
const int REQUEST_SERVICE_DATA = 0xff000003;
const int RESPONSE_SERVICE_DATA = 0xff000004;

-(id)init
{
     dataset = [NSMutableArray init];
}


-(NSString *)getLastElementType
{
    if([dataset count] == 0)
        return nil;
    
    NSObject *o =[NSObject alloc];
    o = [dataset objectAtIndex:0];
    return nil;
}

-(int)getElementCount
{
    return [dataset count];
}

-(BOOL)pushObject:(NSObject *)object
{
    if(object == nil) {
        return NO;
    }
    
    else {
        [dataset addObject:object];
        return YES;
    }
}

-(NSObject *)popObject
{
    if([dataset count] == 0)
        return nil;
    
    NSObject *popItem = [dataset objectAtIndex:0];
    [dataset removeObjectAtIndex:0];
    return popItem;
}

@end
