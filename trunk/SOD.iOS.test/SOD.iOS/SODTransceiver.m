//
//  SODTransceiver.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 4..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODTransceiver.h"

@implementation SODTransceiver

-(void)send:(SODServerInfo *)serverInfo withMessag:(char *)msg
{
    struct sockaddr_in server;
    int server_len = sizeof(server);
    
    server = serverInfo.server;
    
    if(sendto(serverInfo.sockfd, msg, strlen(msg), 0, (struct sockaddr*)&server, server_len) < 0){
        NSLog(@"fail to receive message\n");
    }
}

-(NSString *)receive:(SODServerInfo *)serverInfo
{
    char msg[1024];
    struct sockaddr_in server;
    socklen_t server_len = sizeof(server);
    NSString *message;
    
    server = serverInfo.server;
    
    memset(msg, 0, sizeof(msg));
    
    if(recvfrom(serverInfo.sockfd, (void *)msg, 1024, 0, (struct sockaddr*)&server, &server_len) < 0){
        NSLog(@"fail to receive message\n");
    }
    
    message = [[NSString alloc] initWithCString:msg encoding:NSUTF8StringEncoding];
    
    return message;
}
@end
