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

-(NSData *)serialize:(NSData *)dest withPacket:(SODPacket *)pkt;
-(SODPacket *)deSerialize:(NSData *)src withPacket:(SODPacket *)pkt;

@end
