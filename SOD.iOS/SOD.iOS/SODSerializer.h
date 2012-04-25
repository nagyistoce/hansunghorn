//
//  SODSerializer.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SODPacket.h"

@interface SODSerializer : NSObject

-(void) serializeWithJson:(NSStream *)dest withPAcket:(SODPacket *)pkt;
-(void) deSerializeWithJson:(NSStream *)src withPAcket:(SODPacket *)pkt;

@end
