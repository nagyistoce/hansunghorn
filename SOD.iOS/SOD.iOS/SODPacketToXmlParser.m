//
//  SODPacketToXmlParser.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 5..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODPacketToXmlParser.h"

@implementation SODPacketToXmlParser

-(NSString *)startParseWithSource:(SODPacket *)src
{
    NSMutableString *xmlBuff = [[NSMutableString alloc] init];
    
    [xmlBuff appendString:@"<?xml version=\"1.0\" encoding=\"UTF-8\"?><Packet>"];
    
    
    while ([src getElementCount] != 0) {
        
        NSString *tempItemSrc = [NSString alloc];
        
        if([[src getTopElementType] isEqualToString:@"int"]) {
            NSString *tempItem = [src popObject];
            [tempItemSrc initWithFormat:@"<Item type=\"int\"> %@ </Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];
        }
        else if ([[src getTopElementType] isEqualToString:@"float"]) {
            NSString *tempItem = [src popObject];
            [tempItemSrc initWithFormat:@"<Item type=\"float\"> %@ </Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];         
        }
        else if ([[src getTopElementType] isEqualToString:@"double"]) {
            NSString *tempItem = [src popObject];
            [tempItemSrc initWithFormat:@"<Item type=\"double\"> %@ </Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];               
        }
        else if ([[src getTopElementType] isEqualToString:@"string"]) {
            NSString *tempItem = [src popObject];
            [tempItemSrc initWithFormat:@"<Item type=\"string\"> %@ </Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];               
        }
        else if ([[src getTopElementType] isEqualToString:@"bytearray"]) {
            NSString *tempItem = [src popObject];
            [tempItemSrc initWithFormat:@"<Item type=\"bytearray\"> %@ </Item>", tempItem];
            [xmlBuff appendString:tempItemSrc];
            [tempItem release];                 
        }
        
        [tempItemSrc release];
        [xmlBuff appendString:@"</Packet>"];
    }
    
    return xmlBuff;
}

@end
