//
//  SODPacket.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SODPacket : NSObject
{
    @public
    int signiture;
    
    @protected
    NSMutableArray *datatype;
    NSMutableArray *dataset;
    
    @private
    int count;
}

-(NSString *)getTopElementType;
-(int)getElementCount;
-(BOOL)pushObject:(NSObject *)object withType:(NSString*)typeName;
-(NSString *)popObject;

@end
