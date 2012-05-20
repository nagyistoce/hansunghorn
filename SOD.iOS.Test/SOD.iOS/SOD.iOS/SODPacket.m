//
//  SODPacket.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODPacket.h"

@implementation SODPacket
    
static const NSString *DataType_Int = @"int";
static const NSString *DataType_Float = @"float";
static const NSString *DataType_Double = @"double";
static const NSString *DataType_String = @"string";
static const NSString *DataType_ByteArray = @"bytearray";

const int RESPONSE_SERVICE_NAME = 0xff000000;
const int REQUEST_CLIENT_ALIVE = 0xff000001;
const int RESPONSE_CLIENT_ALIVE = 0xff000002;
const int REQUEST_SERVICE_DATA = 0xff000003;
const int RESPONSE_SERVICE_DATA = 0xff000004;


-(SODPacket *)init
{
    [super init];
    datatype = [[NSMutableArray alloc] init];
    dataset = [[NSMutableArray alloc] init];
    
    return self;
}


-(NSString *)getTopElementType
{
    if([dataset count] == 0)
        return nil;
    
    NSString *popItem = [datatype objectAtIndex:0];
    return popItem;
}

-(int)getElementCount
{
    return [dataset count];
}

-(BOOL)pushObject:(NSObject *)object withType:(NSString*)typeName
{
    if(object == nil) {
        return NO;
    }
    
    else {
        count++;
        [datatype addObject:typeName];
        [dataset addObject:object];
        return YES;
    }
}

-(NSString *)popObject
{
    if([dataset count] == 0)
        return nil;
    
    count--;
    NSString *popItem = [dataset objectAtIndex:0];
    [dataset removeObjectAtIndex:0];
    [datatype removeObjectAtIndex:0];
    
    return popItem;
}

@end
