//
//  SODTVListViewController.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 3..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SODAccessManager.h"

@protocol SODServiceDeligate <NSObject>
-(void)callBack:(id)msg;
@end

@interface SODTVListViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>
{    
    id <SODServiceDeligate> delegate;
    SODAccessManager *TestManager;
    NSArray *serverList;
}

@property (nonatomic, assign) id <SODServiceDeligate> delegate; 
@property (retain, nonatomic)NSArray *serverList;

@end
