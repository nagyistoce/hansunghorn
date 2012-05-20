//
//  SODAccessManager.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 2..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SODTransceiver.h"

@protocol TestViewDeligate <NSObject>
-(void)callBack:(NSString *)msg;
@end

@interface SODAccessManager : NSObject 
{
    @public
    NSThread *receiveThread;
    SODServerInfo *serverInfo;
    SODTransceiver *conn;
    BOOL isRunning;
    
    id <TestViewDeligate> delegate;  
}

@property (nonatomic, assign) id <TestViewDeligate> delegate; 

-(NSArray *)searchServer;
//-(void)connectToServer:(NSString *)msg;

-(BOOL)connectToServer:(NSString *) ipAddr withPort:(int) portNum;
-(void)sendToServer:(NSString *)message;
-(void)receiveFromServer:(NSString *)arg;

-(void)connectWithServerInfo:(SODServerInfo *)info;
-(void)disconnect;
@end
