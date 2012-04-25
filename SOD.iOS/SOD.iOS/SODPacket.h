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
    
    @private
    int count;
}

-(int) getElementCount;
-(void) pushObject:(NSObject *)object;
-(NSObject *) popObject;

@end
