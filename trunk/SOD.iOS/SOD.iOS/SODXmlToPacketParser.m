//
//  SODXmlToPacketParser.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 5..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODXmlToPacketParser.h"

@implementation SODXmlToPacketParser

-(SODXmlToPacketParser *)init
{
    xmlParser = [[NSXMLParser alloc] init];
    tempPacket = [[SODPacket alloc] init];
    
    return self;
}

-(void)dealloc
{
    [xmlParser release];
    [currentElement release];
    [tempPacket release];
    [super dealloc];
}



-(SODPacket *)startParseWithSource:(NSString *)src
{
    tempPacket = [[SODPacket alloc] init];
    xmlParser = [[NSXMLParser alloc] initWithData:[src dataUsingEncoding:NSUTF8StringEncoding]];
    [xmlParser setDelegate:self];
    [xmlParser setShouldProcessNamespaces:NO];
    [xmlParser setShouldReportNamespacePrefixes:NO];
    [xmlParser setShouldResolveExternalEntities:NO];
    [xmlParser parse];
    [tempPacket autorelease];
    
    return tempPacket;
}

-(void)parserDidStartDocument:(NSXMLParser *)parser
{
    NSLog(@"Document Started");
    currentElement = nil;
}

-(void)parser:(NSXMLParser *)parser parseErrorOccurred:(NSError *)parseError
{
    NSLog(@"ERROR");
}

-(void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict
{
    [currentElement release];
    currentElement = [elementName copy];
    
    if([currentElement isEqualToString:@"Packet"]) {
        [currentType release];
        currentType = [[NSString alloc] initWithString:[attributeDict objectForKey:@"signiture"]];
        [tempPacket setSigniture:[currentType intValue]];
    }
    
    else if([currentElement isEqualToString:@"Item"]) {
        
        if([[attributeDict objectForKey:@"type"] isEqualToString:@"int"]) {
            [currentType release];
            currentType = [[NSString alloc] initWithString:@"int"];
        }
        else if ([[attributeDict objectForKey:@"type"] isEqualToString:@"float"]) {
            [currentType release];
            currentType = [[NSString alloc] initWithString:@"float"];            
        }
        else if ([[attributeDict objectForKey:@"type"] isEqualToString:@"double"]) {
            [currentType release];
            currentType = [[NSString alloc] initWithString:@"double"];            
        }
        else if ([[attributeDict objectForKey:@"type"] isEqualToString:@"string"]) {
            [currentType release];
            currentType = [[NSString alloc] initWithString:@"string"];            
        }
        else if ([[attributeDict objectForKey:@"type"] isEqualToString:@"bytearray"]) {
            [currentType release];
            currentType = [[NSString alloc] initWithString:@"bytearray"];            
        }
    }
}

-(void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName
{
    [currentElement release];
    currentElement = [elementName copy];
    
    if([currentElement isEqualToString:@"Packet"]) {
    }
    
    else if([currentElement isEqualToString:@"Item"]) {
    }
}

-(void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string
{
    if([currentElement isEqualToString:@"Packet"]){
        [tempPacket setSigniture:[currentType intValue]];
    }
    
    else if([currentElement isEqualToString:@"Item"]) {
        
        if([currentType isEqualToString:@"int"]) {
            [tempPacket pushObject:string withType:@"int"];
        }
        else if ([currentType isEqualToString:@"float"]) { 
            [tempPacket pushObject:string withType:@"float"];
        }
        else if ([currentType isEqualToString:@"double"]) {    
            [tempPacket pushObject:string withType:@"double"];
        }
        else if ([currentType isEqualToString:@"string"]) {    
            [tempPacket pushObject:string withType:@"string"];
        }
        else if ([currentType isEqualToString:@"bytearray"]) {   
            [tempPacket pushObject:string withType:@"bytearray"];
        }
    }
    
}

-(void)parserDidEndDocument:(NSXMLParser *)parser
{
    NSLog(@"Document Finished");
}

@end
