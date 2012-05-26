//
//  SODPacket.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SODConstants.h"

@interface SODPacket : NSObject
{
    @public
    int signiture;
    
    NSMutableArray *datatype;
    NSMutableArray *dataset;
    
    int count;
}

-(void)setSigniture:(int)sig;
-(int)getSigniture;
-(NSString *)getTopElementType;
-(int)getElementCount;
-(BOOL)pushObject:(NSObject *)object withType:(NSString*)typeName;
-(NSString *)popObject;

@end
