//
//  SODXmlToPacketParser.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 5..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SODPacket.h"

@interface SODXmlToPacketParser : NSObject <NSXMLParserDelegate>
{
    @private
    NSXMLParser *xmlParser;
    NSString *currentElement;
    NSString *currentType;
    SODPacket *tempPacket;
}

-(SODPacket *)startParseWithSource:(NSString *)src;

@end
