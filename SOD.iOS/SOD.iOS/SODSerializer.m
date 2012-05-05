//
//  SODSerializer.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODSerializer.h"

@implementation SODSerializer
-(void)serialize:(NSOutputStream *)dest withPacket:(SODPacket *)pkt
{
    dest = [packetToXmlParser startParseWithSource:pkt];//NSString -> NSOutputStream
}

-(void)deSerialize:(NSInputStream *)src withPacket:(SODPacket *)pkt
{
    NSString *sourceString = [[NSString alloc] initWithData:src encoding:NSUTF8StringEncoding];//NSInputStream -> NSData
    pkt = [xmlToPacketParser startParseWithSource:sourceString];
}

@end
