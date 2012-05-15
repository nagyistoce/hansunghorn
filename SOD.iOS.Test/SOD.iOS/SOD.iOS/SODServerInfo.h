//
//  SODServerInfo.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SODServerInfo : NSObject
{
    @public
    NSString *serverAddress;
    NSString *serviceName;
}

-(BOOL)initWithAddress:(NSString *)address withServiceName:(NSString *)name;
@end
