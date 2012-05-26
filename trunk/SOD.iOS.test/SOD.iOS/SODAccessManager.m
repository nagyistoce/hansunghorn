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
    [super init];
    conn = [[SODTransceiver alloc] init];
    serverInfo = [[SODServerInfo alloc] init];  
    serializer = [[SODSerializer alloc] init];
    serverList = [[NSMutableArray alloc] init];
    constants = [[SODConstants alloc] init];
    isRunning = NO;
    
    return self;
}

-(void)release
{
    [conn release];
    [serverInfo release];
    [serializer release];
    [serverList release];
    [constants release];
    [super release];
}

-(NSArray *)searchServer
{
    NSLog(@"버튼을 누름");
    
    int sockfd;
    unsigned int bEnableBroadcast = 1;
    char msg[1024];
    
    struct sockaddr_in multi_addr;
    
    multi_addr.sin_family = AF_INET;
    multi_addr.sin_addr.s_addr = INADDR_BROADCAST;
    multi_addr.sin_port = htons(6789);
    
    if ((sockfd = socket( AF_INET, SOCK_DGRAM, IPPROTO_UDP)) < 0 ) {
        NSLog(@"Error creaing socket");
    }
    
    if((setsockopt(sockfd, SOL_SOCKET, SO_BROADCAST, (void *)&bEnableBroadcast, sizeof(bEnableBroadcast))) < 0){
        NSLog(@"Error setsockopt");
    }
    
    SODPacket *sendingSignal = [[SODPacket alloc] init];
    [sendingSignal setSigniture:[constants REQUEST_PING]];
    [sendingSignal pushObject:[self getIPAddress] withType:constants.DATATYPE_STRING];
    [sendingSignal pushObject:@"6790" withType:constants.DATATYPE_INT];
    
    NSString *tempStr = [serializer serialize:sendingSignal];
    
    memset(msg, 0, sizeof(msg));
    strcpy(msg, [tempStr cStringUsingEncoding:NSUTF8StringEncoding]);
    
    if (sendto(sockfd, msg, strlen(msg), 0, (struct sockaddr *)&multi_addr, sizeof(multi_addr)) < 0) {
        perror("sendto() sent incorrect number of bytes");
    }
    
    close(sockfd);
    
    [serverList removeAllObjects];
    searchThread = [[NSThread alloc] initWithTarget:self selector:@selector(makeServerList:) object:nil];
    [searchThread start];
    
    [NSThread sleepForTimeInterval:2];
    [searchThread release];
    
    NSArray *list = [serverList copy];
    
    return list;
}

-(void)makeServerList:(id) sender
{
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    SODPacket *arg;
    NSString *receivedStr;
    
    int testLen;
    
    int searchSock;
    char msg[1024];
    struct sockaddr_in server;
    struct hostent *hp;
    socklen_t server_len = sizeof(server);
    server_len = sizeof(struct sockaddr_in);
    
    hp= gethostbyname((char *)[[self getIPAddress] cStringUsingEncoding:NSASCIIStringEncoding]);
    
    memcpy(&server.sin_addr, hp->h_addr, hp->h_length);
    
    server.sin_family = AF_INET;
    server.sin_port = htons(6790);
    
    if((searchSock = socket(PF_INET, SOCK_DGRAM, 0)) == -1){
        NSLog(@"fail to call sock()\n");
    }
    
    while(YES){        
        memset(msg, 0, sizeof(msg));
        
        if((testLen = recvfrom(searchSock, (void *)msg, 1024, 0, (struct sockaddr*)&server, &server_len)) < 0){
            NSLog(@"fail to receive message\n");
        }
        
        receivedStr = [[NSString alloc] initWithCString:msg encoding:NSUTF8StringEncoding];
        NSLog(receivedStr);
        NSLog(@"%d", testLen);
        
        arg = [serializer deSerialize:receivedStr];
        
        if([[arg getTopElementType] isEqual:@"string"] == YES){
            [serverList addObject:[arg popObject]];
        }
        
        else {
            continue;
        }
    }
    
    [pool release];
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

-(void)sendToServer:(SODPacket *)message
{
    char msg[1024];
    memset(msg, 0, sizeof(msg));
    
    NSString *tempMsg = [[NSString alloc] init];
    tempMsg = [serializer serialize:message];
    
    strcpy(msg, [tempMsg cStringUsingEncoding:NSUTF8StringEncoding]);
    
    [conn send:serverInfo withMessag:msg];
}

-(void)receiveFromServer:(SODPacket *)arg
{
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    NSString *receivedStr;
    while(isRunning){
        receivedStr = [conn receive:serverInfo];
        arg = [serializer deSerialize:receivedStr];
        [self.delegate callBack:arg];
    }
    
    [pool release];
}

-(void)disconnect
{
}


- (NSString *)getIPAddress
{
    NSString *address = @"error";
    struct ifaddrs *interfaces = NULL;
    struct ifaddrs *temp_addr = NULL;
    int success = 0;
    
    // retrieve the current interfaces - returns 0 on success
    success = getifaddrs(&interfaces);
    if (success == 0)
    {
        // Loop through linked list of interfaces
        temp_addr = interfaces;
        while(temp_addr != NULL)
        {
            if(temp_addr->ifa_addr->sa_family == AF_INET)
            {
                // Check if interface is en0 which is the wifi connection on the iPhone
                if([[NSString stringWithUTF8String:temp_addr->ifa_name] isEqualToString:@"en0"])
                {
                    // Get NSString from C String
                    address = [NSString stringWithUTF8String:inet_ntoa(((struct sockaddr_in *)temp_addr->ifa_addr)->sin_addr)];
                }
            }
            
            temp_addr = temp_addr->ifa_next;
        }
    }
    
    // Free memory
    freeifaddrs(interfaces);
    
    return address;
}
@end
