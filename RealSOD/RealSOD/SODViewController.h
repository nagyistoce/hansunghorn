//
//  SODViewController.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 4. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SODTestViewController.h"
#import "SODTVListViewController.h"
#import "SODTVLocationViewController.h"
#import "SODServiceListViewController.h"


@interface SODViewController : UIViewController
{
    id <SODServiceDeligate> delegate;
    IBOutlet UIButton *serverList;
    IBOutlet UIButton *locationView;
    IBOutlet UIButton *installedService;
}

@property (nonatomic, assign) id <SODServiceDeligate> delegate; 

-(IBAction)showTestMenu:(id)sender;
-(IBAction)showList:(id)sender;
-(IBAction)showTVLcationList:(id)sender;
-(IBAction)showServiceList:(id)sender;
@end
