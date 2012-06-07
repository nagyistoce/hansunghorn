//
//  SODSerializer.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SODXmlToPacketParser.h"
#import "SODPacketToXmlParser.h"

@interface SODSerializer : NSObject
{
    @public
    SODXmlToPacketParser *xmlToPacketParser;
    SODPacketToXmlParser *packetToXmlParser;
}

-(NSString *)serialize:(SODPacket *)pkt;
-(SODPacket *)deSerialize:(NSString *)src;

@end
