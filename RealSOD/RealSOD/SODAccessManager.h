//
//  SODAccessManager.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SODTransceiver.h"
#import "SODSerializer.h"
#include <ifaddrs.h>
#include <arpa/inet.h>

@protocol TransceiveDeligate <NSObject>
-(void)callBack:(SODPacket *)msg;
@end

@interface SODAccessManager : NSObject 
{
    @public
    NSThread *receiveThread;
    NSThread *searchThread;
    SODServerInfo *serverInfo;
    SODServerInfo *searchInfo;
    SODTransceiver *conn;
    SODSerializer *serializer;
    SODConstants *constants;
    BOOL isRunning;
    
    NSMutableArray *serverList;
    
    id <TransceiveDeligate> delegate;  
}

@property (nonatomic, assign) id <TransceiveDeligate> delegate; 
@property (nonatomic, retain) SODServerInfo *serverInfo;
@property (nonatomic, retain) SODServerInfo *searchInfo;

-(NSArray *)searchServer;
//-(void)connectToServer:(NSString *)msg;

-(BOOL)connectToServer:(NSString *) ipAddr withPort:(int) portNum;
-(BOOL)sendToServer:(SODPacket *)message;
-(void)makeServerList:(SODPacket *)arg;
-(void)receiveFromServer:(SODPacket *)arg;
-(NSString *)getLocalAddress;
-(void)disconnect;
@end
