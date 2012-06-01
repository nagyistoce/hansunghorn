//
//  SODMapPin.m
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 31..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODMapPin.h"

@implementation SODMapPin
@synthesize coordinate, title, subtitle, pinColor, rightCalloutAccessoryView;

-(void)dealloc
{
    [title release];
    [subtitle release];
    [super dealloc];
}

@end
