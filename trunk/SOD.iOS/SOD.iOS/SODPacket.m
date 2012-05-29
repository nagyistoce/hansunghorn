//
//  SODPacket.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODPacket.h"

@implementation SODPacket

-(SODPacket *)init
{
    [super init];
    datatype = [[NSMutableArray alloc] init];
    dataset = [[NSMutableArray alloc] init];
    
    return self;
}

-(void)dealloc
{
    [datatype release];
    [dataset release];
    [super dealloc];
}

-(void)setSigniture:(int)sig
{
    signiture = sig;
}

-(int)getSigniture
{
    return signiture;
}

-(NSString *)getTopElementType
{
    if([dataset count] == 0)
        return nil;
    
    NSString *popItem = [[datatype objectAtIndex:0] copy];
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
        [datatype addObject:[typeName retain]];
        [dataset addObject:[object retain]];
        
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
