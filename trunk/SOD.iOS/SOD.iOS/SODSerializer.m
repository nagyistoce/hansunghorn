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

-(void)dealloc
{
    [packetToXmlParser release];
    [xmlToPacketParser release];
    [super dealloc];
}

-(NSString *)serialize:(SODPacket *)pkt
{
    //dest = [[packetToXmlParser startParseWithSource:pkt]dataUsingEncoding:NSUTF8StringEncoding];//NSString -> NSOutputStream
    NSString *dest = [[NSString alloc] init];
    dest = [packetToXmlParser startParseWithSource:pkt];
    [dest autorelease];
    
    return dest;
}

-(SODPacket *)deSerialize:(NSString *)src
{
    //NSString *sourceString = [[NSString alloc] initWithData:src encoding:NSUTF8StringEncoding];//NSInputStream -> NSData
    SODPacket *pkt = [[SODPacket alloc] init];
    pkt = [xmlToPacketParser startParseWithSource:src];
    [pkt autorelease];
    
    return pkt;
}

@end
