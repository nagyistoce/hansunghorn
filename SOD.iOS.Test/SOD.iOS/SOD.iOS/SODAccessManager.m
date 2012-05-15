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
    //    NSLog(@"버튼을 누름");
    //    if ( (sockfd = socket( AF_INET, SOCK_DGRAM, 0 )) < 0 ) {
    //        NSLog(@"Error creaing socket");
    //    }
    //    
    //    
    //    char *inetAddress;
    //    
    //    struct sockaddr_in serverAddress;
    //    bzero( &serverAddress, sizeof(serverAddress) );
    //    serverAddress.sin_family = AF_INET;
    //    serverAddress.sin_port = htons( 1234 );
    //    
    //    NSMutableArray *ipAddrList = [[NSMutableArray alloc] init];
    //    
    //    for(int i = 0; i <256; i++) {
    //        inetAddress = (char *)[[[NSString alloc] initWithFormat:@"192.168.0.%d", i] cStringUsingEncoding:NSASCIIStringEncoding];
    //        serverAddress.sin_addr.s_addr = inet_addr(inetAddress);
    //    
    //        if (connect(sockfd, (struct sockaddr *)&serverAddress, sizeof(serverAddress)) < 0) { 
    //            continue;
    //        }
    //        
    //        else {//연결 확인하는곳
    //            recv(sockfd, nil, 0, 0);
    //            NSLog(@"sleep");
    //            NSString *temp = [NSString alloc];
    //            temp = (NSString *)[NSString stringWithCString:inetAddress encoding:NSASCIIStringEncoding];
    //            [ipAddrList addObject:temp];
    //        }
    //    }
    //    
    //    NSArray *serverList = [ipAddrList copy];//[[NSArray alloc] initWithObjects: @"1 번째", @"2 번째", @"3 번째", nil];
    
    return nil;
}

-(void)connectToServer:(NSString *)message withIPAddress:(NSString *) IPaddr withPort:(int) portNum
{
    int sockfd;
    char msg[1024];
    struct sockaddr_in server;
    struct hostent *hp;
    int server_len = sizeof(struct sockaddr_in);
    
    hp= gethostbyname((char *)[IPaddr cStringUsingEncoding:NSASCIIStringEncoding]);
    
    memcpy(&server.sin_addr, hp->h_addr, hp->h_length);
    
    //server.sin_addr.s_addr = "123.123.123.123";
    server.sin_family = AF_INET;
    server.sin_port = htons(portNum);
    
    if((sockfd = socket(AF_INET, SOCK_DGRAM, 0)) == -1){
        NSLog(@"fail to call sock()\n");
    }
    
    if(bind(sockfd, (struct sockaddr *)&server, sizeof(struct sockaddr_in)) == -1){
        NSLog(@"fail to call bind()\n");
    }
    
    strcpy(msg, "this is test messsage");
    
    if(sendto(sockfd, &msg, 1024, 0, (struct sockaddr*)&server, server_len) == -1){
        NSLog(@"fail to receive message\n");
    }
    
    strcpy(msg, (char *)[message cStringUsingEncoding:NSASCIIStringEncoding]);
    
    if(sendto(sockfd, &msg, 1024, 0, (struct sockaddr*)&server, server_len) == -1){
        NSLog(@"fail to receive message\n");
    }
    
//    if(recvfrom(sockfd, &msg, 1, 0, (struct sockaddr*)&server, server_len) == -1){
//        NSLog(@"fail to receive message\n");
//    }
}

-(void)connectWithServerInfo:(SODServerInfo *)info
{
}

-(void)disconnect
{
}
@end
