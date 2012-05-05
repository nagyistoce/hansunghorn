//
//  SODTVListViewController.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 3..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SODAccessManager.h"

@interface SODTVListViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>
{
    @public
    SODAccessManager *TestManager;
    NSArray *serverList;
}
@property (retain, nonatomic)NSArray *serverList;

@end
