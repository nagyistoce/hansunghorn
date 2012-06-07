//
//  SODViewController.h
//  TestMapkit
//
//  Created by Project BJ on 12. 5. 29..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import <Mapkit/Mapkit.h>
#import "SODMapPin.h"
#import "SODMapPinViewController.h"

@interface SODTVLocationViewController : UIViewController <CLLocationManagerDelegate, MKMapViewDelegate>
{    
    IBOutlet MKMapView *mapView;
    int timespent;
    int MAX_TIME;
    
    NSMutableArray *arrayLatitude;
    NSMutableArray *arrayLongitude;
    
    CLLocationManager *locManager;
    CLLocation *bestLocation;
}

@property (retain) CLLocationManager *locManager;
@property (retain) CLLocation *bestLocation;

-(void)tick:(NSTimer *)timer;
-(IBAction)findme:(id)sender;
@end
