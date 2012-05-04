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
    NSMutableArray *dataset;
    
    @private
    int count;
}

-(id)init;
-(NSString *)getLastElementType;
-(int)getElementCount;
-(BOOL)pushObject:(NSObject *)object;
-(NSObject *)popObject;

@end
