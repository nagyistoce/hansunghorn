//
//  SharedObject.h
//  RealSOD
//
//  Created by Project BJ on 12. 6. 5..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SODAccessManager.h"

@interface SharedObject : NSObject
{
    SODAccessManager *myAccess;
}

@property (nonatomic, retain)SODAccessManager *myAccess;
+(SharedObject *)sharedObject;
+(void)releaseSharedObject;
-(void)setConnectInstance:(SODAccessManager *)inputObject;
@end
