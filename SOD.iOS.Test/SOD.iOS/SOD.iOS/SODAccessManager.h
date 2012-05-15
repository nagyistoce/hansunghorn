//
//  SODAccessManager.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SODServerInfo.h"
#import <sys/types.h>
#import <sys/socket.h>
#import <netinet/in.h>
#import <netdb.h>
#import <string.h>

@interface SODAccessManager : NSObject
{
    @public
    NSArray *serverInfo;
    int sockfd;
}

-(NSArray *)searchServer;
//-(void)connectToServer:(NSString *)msg;

-(void)connectToServer:(NSString *)message withIPAddress:(NSString *) IPaddr withPort:(int) portNum;


-(void)connectWithServerInfo:(SODServerInfo *)info;
-(void)disconnect;
@end
