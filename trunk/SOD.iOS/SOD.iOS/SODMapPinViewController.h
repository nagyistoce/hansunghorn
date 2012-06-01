//
//  SODMapPinViewController.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 6. 1..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SODMapPin.h"

@interface SODMapPinViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>
{
    SODMapPin *myPin;
}

@property (nonatomic, retain)SODMapPin *myPin;

@end
