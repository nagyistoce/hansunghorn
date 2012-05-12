//
//  SODServerInfo.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODServerInfo.h"

@implementation SODServerInfo

-(BOOL)initWithAddress:(NSString *)address withServiceName:(NSString *)name
{    
    if([address length] == 0 || [name length] == 0)
        return NO;
    
    else {
        
        serverAddress =[NSString alloc];
        serviceName =[NSString alloc];
        serverAddress = address;
        serviceName = name;
        
        return YES;
    }
}

@end
