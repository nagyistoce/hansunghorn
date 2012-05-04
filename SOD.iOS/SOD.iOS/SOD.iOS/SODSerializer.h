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

-(id)serializeWithJson:(NSStream *)dest withPacket:(SODPacket *)pkt;
-(id)deSerializeWithJson:(NSStream *)src withPacket:(SODPacket *)pkt;

@end
