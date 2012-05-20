//
//  SODTransceiver.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 4..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SODServerInfo.h"

@interface SODTransceiver : NSObject

-(void)send:(SODServerInfo *)serverInfo withMessag:(char *)message;
-(NSString *)receive:(SODServerInfo *)serverInfo;
@end
