//
//  SODServerInfo.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODServerInfo.h"

@implementation SODServerInfo
@synthesize server, hp, server_len, sockfd;

-(BOOL)initWithAddress:(NSString *) ipAddr withPort:(int) portNum
{    
    if([ipAddr length] == 0 || portNum < 0)
        return NO;
    
    else {
        server_len = sizeof(struct sockaddr_in);
        
        hp= gethostbyname((char *)[ipAddr cStringUsingEncoding:NSASCIIStringEncoding]);
        
        memcpy(&server.sin_addr, hp->h_addr, hp->h_length);
        
        server.sin_family = AF_INET;
        server.sin_port = htons(portNum);
        
        if((sockfd = socket(PF_INET, SOCK_DGRAM, 0)) == -1){
            NSLog(@"fail to call sock()\n");
        }
        
        return YES; 
    }
}

@end
