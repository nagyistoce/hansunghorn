//
//  SODPacketToXmlParser.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 5..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODPacketToXmlParser.h"

@implementation SODPacketToXmlParser

-(SODPacketToXmlParser *)init{
    xmlBuff = [[NSMutableString alloc] init];
    
    return self;
}

-(void)dealloc
{
    [xmlBuff release];
    [super dealloc];
}

-(NSString *)startParseWithSource:(SODPacket *)src
{
    
    xmlBuff = [[NSMutableString alloc] init];
    
    NSString *header = [[NSString alloc] initWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Packet signiture=\"%d\">", [src getSigniture]];
    
    [xmlBuff appendString:header];
    
    
    while ([src getElementCount] != 0) {
        
        if([[src getTopElementType] isEqualToString:@"int"]) {
            tempItem = [src popObject];
            tempItemSrc = [[NSString alloc] initWithFormat:@"<Item type=\"int\">%@</Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];
        }
        else if ([[src getTopElementType] isEqualToString:@"float"]) {
            tempItem = [src popObject];
            tempItemSrc = [[NSString alloc] initWithFormat:@"<Item type=\"float\">%@</Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];         
        }
        else if ([[src getTopElementType] isEqualToString:@"double"]) {
            tempItem = [src popObject];
            tempItemSrc = [[NSString alloc] initWithFormat:@"<Item type=\"double\">%@</Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];               
        }
        else if ([[src getTopElementType] isEqualToString:@"string"]) {
            tempItem = [src popObject];
            tempItemSrc = [[NSString alloc] initWithFormat:@"<Item type=\"string\">%@</Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];               
        }
        else if ([[src getTopElementType] isEqualToString:@"bytearray"]) {
            tempItem = [src popObject];
            tempItemSrc = [[NSString alloc] initWithFormat:@"<Item type=\"bytearray\">%@</Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];                 
        }
    } 
    
    
    [xmlBuff appendString:@"</Packet>"];
    
    return xmlBuff;
}

@end
