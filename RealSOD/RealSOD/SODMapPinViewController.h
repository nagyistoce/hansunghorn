//
//  SODMapPinViewController.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 6. 1..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SODMapPin.h"

@interface SODMapPinViewController : UIViewController
{
    SODMapPin *myPin;
    IBOutlet UILabel *tvName;
    IBOutlet UILabel *serviceName;
    IBOutlet UILabel *email;
    IBOutlet UILabel *phoneNumber;
}

@property (nonatomic, retain)SODMapPin *myPin;

@end
