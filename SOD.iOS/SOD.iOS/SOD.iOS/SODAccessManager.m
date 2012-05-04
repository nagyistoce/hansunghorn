//
//  SODAccessManager.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODAccessManager.h"

@implementation SODAccessManager
-(NSArray *)searchServer
{
    
    
    NSLog(@"버튼을 누름");
    if ( (sockfd = socket( AF_INET, SOCK_DGRAM, 0 )) < 0 ) {
        NSLog(@"Error creaing socket");
    }
    
    
    char *inetAddress;
    
    struct sockaddr_in serverAddress;
    bzero( &serverAddress, sizeof(serverAddress) );
    serverAddress.sin_family = AF_INET;
    serverAddress.sin_port = htons( 1234 );
    
    NSMutableArray *ipAddrList = [[NSMutableArray alloc] init];
    
    for(int i = 0; i <256; i++) {
        inetAddress = (char *)[[[NSString alloc] initWithFormat:@"192.168.0.%d", i] cStringUsingEncoding:NSASCIIStringEncoding];
        serverAddress.sin_addr.s_addr = inet_addr(inetAddress);
    
        if (connect(sockfd, (struct sockaddr *)&serverAddress, sizeof(serverAddress)) < 0) { 
            continue;
        }
        
        else {//연결 확인하는곳
            recv(sockfd, nil, 0, 0);
            NSLog(@"sleep");
            NSString *temp = [NSString alloc];
            temp = (NSString *)[NSString stringWithCString:inetAddress encoding:NSASCIIStringEncoding];
            [ipAddrList addObject:temp];
        }
    }
    
    NSArray *serverList = [ipAddrList copy];//[[NSArray alloc] initWithObjects: @"1 번째", @"2 번째", @"3 번째", nil];
    
    return serverList;
}

-(id)connectToServer
{
    
    NSLog(@"버튼을 누름");
    if ( (sockfd = socket( AF_INET, SOCK_DGRAM, 0 )) < 0 ) {
        NSLog(@"Error creaing socket");
    }
    
    struct sockaddr_in serverAddress;
    bzero( &serverAddress, sizeof(serverAddress) );
    serverAddress.sin_family = AF_INET;
    serverAddress.sin_port = htons( 1234 );
    serverAddress.sin_addr.s_addr = inet_addr("xxx.xxx.xxx.xxx");
    
    if ( connect( sockfd, (struct sockaddr *)&serverAddress, sizeof(serverAddress)) < 0 ) { 
        NSLog(@"connect error");
    }
    
    return nil;
}

-(id)connectWithServerInfo:(SODServerInfo *)info
{
    return nil;
}

-(id)disconnect
{
    return nil;
}
@end
