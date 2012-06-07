//
//  SODAccessManager.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODAccessManager.h"

@implementation SODAccessManager
@synthesize delegate, serverInfo, searchInfo; 

-(id)init
{
    [super init];
    conn = [[SODTransceiver alloc] init];
    serverInfo = [[SODServerInfo alloc] init];  
    searchInfo = [[SODServerInfo alloc] init]; 
    serializer = [[SODSerializer alloc] init];
    constants = [[SODConstants alloc] init];
    isRunning = NO;
    searchThread = nil;
    
    return self;
}

-(void)dealloc
{
    [super dealloc];
    [conn release];
    [serverInfo release];
    [searchInfo release]; 
    //[serverList release];
    [serializer release];
    [constants release];
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
    multi_addr.sin_port = htons(4020);
    
    if ((sockfd = socket( AF_INET, SOCK_DGRAM, IPPROTO_UDP)) < 0 ) {
        NSLog(@"Error creaing socket");
    }
    
    if((setsockopt(sockfd, SOL_SOCKET, SO_BROADCAST, (void *)&bEnableBroadcast, sizeof(bEnableBroadcast))) < 0){
        NSLog(@"Error setsockopt");
    }
    
    SODPacket *sendingSignal = [[SODPacket alloc] init];
    [sendingSignal setSigniture:[constants REQUEST_PING]];
    [sendingSignal pushObject:[self getLocalAddress] withType:constants.DATATYPE_STRING];
    [sendingSignal pushObject:@"4021" withType:constants.DATATYPE_INT];
    
    NSString *tempStr = [serializer serialize:sendingSignal];
    
    memset(msg, 0, sizeof(msg));
    strcpy(msg, [tempStr cStringUsingEncoding:NSUTF8StringEncoding]);
    
    
    if([searchInfo initWithSearchPort:4021] == YES) {
        
        searchThread = [[NSThread alloc] initWithTarget:self selector:@selector(makeServerList:) object:nil];
        [searchThread start];
        
        if (sendto(sockfd, msg, strlen(msg), 0, (struct sockaddr *)&multi_addr, sizeof(multi_addr)) < 0) {
            perror("sendto() sent incorrect number of bytes");
        }
        
        [NSThread sleepForTimeInterval:2];
        [searchThread cancel];
    }
    
    [sendingSignal release];
    
    close(sockfd);    
    close(searchInfo.sockfd);
    
    return serverList;
}

-(void)makeServerList:(SODPacket *)arg
{
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    struct sockaddr_in server;
//    socklen_t server_len = sizeof(server);
    NSString *receivedStr;
    
    server = searchInfo.server;
    
//    if(bind(searchInfo.sockfd, (struct sockaddr *)&server, server_len) < 0){
//        NSLog(@"Error bind!!");
//    }
    
    //while([[NSThread currentThread] isCancelled] == NO){
        
        receivedStr = [conn receive:searchInfo];
        NSLog(@"%@", receivedStr);
        
        arg = [[serializer deSerialize:receivedStr] retain];
        NSString *tempstr;
        
        if([arg getSigniture] == constants.RESPONSE_PING){
            serverList = [[NSMutableArray alloc] init];
            while([arg getElementCount] > 0){
                tempstr = [arg popObject];
                [serverList addObject:tempstr];
            }
        }
   // }
    [pool release];
}

-(BOOL)connectToServer:(NSString *) ipAddr withPort:(int) portNum
{  
    if(isRunning != YES){
        if([serverInfo initWithAddress:ipAddr withPort:portNum] == YES){
            isRunning = YES;
            SODPacket *tempPacket = [[SODPacket alloc] init];
            [tempPacket setSigniture:constants.REQUEST_ACCEPT];
            [self sendToServer:tempPacket];
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

-(BOOL)sendToServer:(SODPacket *)message
{
    char msg[1024];
    memset(msg, 0, sizeof(msg));
    
    NSString *tempMsg = [[NSString alloc] init];
    tempMsg = [serializer serialize:message];
    
    strcpy(msg, [tempMsg cStringUsingEncoding:NSUTF8StringEncoding]);
    
    return [conn send:serverInfo withMessag:msg];
}

-(void)receiveFromServer:(SODPacket *)arg
{
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    NSString *receivedStr;
    int tempSigniture;
    
    while([[NSThread currentThread] isCancelled] == NO){
        receivedStr = [conn receive:serverInfo];
        arg = [serializer deSerialize:receivedStr];
        
        tempSigniture = [arg getSigniture];
        if(tempSigniture == [constants RESPONSE_ACCEPT]);
        else if(tempSigniture == [constants REQUEST_CLIENT_ALIVE]);
        else if(tempSigniture == [constants RESPONSE_SERVICE_DATA]);
        else if(tempSigniture == [constants RESPONSE_SERVICE_NAME]);
        else
            [self.delegate callBack:arg];
    }
    
    [pool release];
}

-(void)disconnect
{
}


- (NSString *)getLocalAddress
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
