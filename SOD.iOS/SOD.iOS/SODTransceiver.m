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
    char *result;
    int msg_len;
    struct sockaddr_in server;
    socklen_t server_len = sizeof(server);
    NSString *message;
    
    server = serverInfo.server;
    
    memset(msg, 0, sizeof(msg));
    
    if(recvfrom(serverInfo.sockfd, (void *)msg, 1024, 0, (struct sockaddr*)&server, &server_len) < 0){
        NSLog(@"fail to receive message\n");
    }
    
    msg_len = strlen(msg);
    
    result = (char *)malloc(msg_len);
    
    for(int i = 0, j = 0; i < msg_len; i++){
        if(msg[i] == '\r' || msg[i] == '\n');
        else {
            result[j++] = msg[i];
        }
    }
    
    result[msg_len] = '\0';
    
    message = [[NSString alloc] initWithCString:result encoding:NSUTF8StringEncoding];
    free(result);
    
    return message;
}
@end
