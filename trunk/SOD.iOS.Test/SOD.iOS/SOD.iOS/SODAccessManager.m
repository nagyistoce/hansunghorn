//
//  SODAccessManager.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODAccessManager.h"

@implementation SODAccessManager
@synthesize delegate; 

-(id)init
{
    conn = [[SODTransceiver alloc] init];
    serverInfo = [[SODServerInfo alloc] init];  
    isRunning = NO;
    
    return self;
}

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

-(BOOL)connectToServer:(NSString *) ipAddr withPort:(int) portNum
{  
    if(isRunning != YES){
        if([serverInfo initWithAddress:ipAddr withPort:portNum] == YES){
            isRunning = YES;
            receiveThread = [[NSThread alloc] initWithTarget:self selector:@selector(receiveFromServer:) object:nil];
            [receiveThread start];
            return YES;
        }
        
        else{
            isRunning = NO;
            return NO;
        }
    }
    
    return NO;
}

-(void)sendToServer:(NSString *)message
{
    char msg[1024];
    memset(msg, 0, sizeof(msg));
    strcpy(msg, [message cStringUsingEncoding:NSUTF8StringEncoding]);
    
    [conn send:serverInfo withMessag:msg];
}

-(void)receiveFromServer:(NSString *)arg
{
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    NSString *receivedStr;
    while(isRunning){
        receivedStr = [conn receive:serverInfo];
        [self.delegate callBack:receivedStr];
    }
    
    [pool release];
}

-(void)connectWithServerInfo:(SODServerInfo *)info
{
}

-(void)disconnect
{
}
@end
