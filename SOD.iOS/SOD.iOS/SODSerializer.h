//
//  SODSerializer.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SODPacket.h"
#import "SODXmlToPacketParser.h"
#import "SODPacketToXmlParser.h"

@interface SODSerializer : NSObject <NSXMLParserDelegate>
{
    @public
    SODXmlToPacketParser *xmlToPacketParser;
    SODPacketToXmlParser *packetToXmlParser;
}

-(void)serialize:(NSOutputStream *)dest withPacket:(SODPacket *)pkt;
-(void)deSerialize:(NSInputStream *)src withPacket:(SODPacket *)pkt;

@end
