//
//  SODSerializer.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODSerializer.h"

@implementation SODSerializer

-(SODSerializer *)init
{
    packetToXmlParser = [[SODPacketToXmlParser alloc] init];
    xmlToPacketParser = [[SODXmlToPacketParser alloc] init];
    
    return self;
}

-(NSData *)serialize:(NSData *)dest withPacket:(SODPacket *)pkt
{
    dest = [[packetToXmlParser startParseWithSource:pkt]dataUsingEncoding:NSUTF8StringEncoding];//NSString -> NSOutputStream
    
    return dest;
}

-(SODPacket *)deSerialize:(NSData *)src withPacket:(SODPacket *)pkt
{
    NSString *sourceString = [[NSString alloc] initWithData:src encoding:NSUTF8StringEncoding];//NSInputStream -> NSData
    pkt = [xmlToPacketParser startParseWithSource:sourceString];
    
    return pkt;
}

@end
