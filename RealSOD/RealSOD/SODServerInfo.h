//
//  SODServerInfo.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <sys/types.h>
#import <sys/socket.h>
#import <netinet/in.h>
#import <netdb.h>
#import <string.h>

@interface SODServerInfo : NSObject
{
    @public
    struct sockaddr_in server;
    struct hostent *hp;
    int server_len;
    int sockfd;
}

@property (nonatomic) struct sockaddr_in server;
@property (nonatomic) struct hostent *hp;
@property (nonatomic) int server_len;
@property (nonatomic) int sockfd;

-(BOOL)initWithAddress:(NSString *) ipAddr withPort:(int) portNum;
-(BOOL)initWithSearchPort:(int) portNum;
@end
