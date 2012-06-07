//
//  SODMapPin.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 31..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import <CoreLocation/CoreLocation.h>

@interface SODMapPin : MKPinAnnotationView <MKAnnotation>
{
    CLLocationCoordinate2D coordinate;
    NSString *title;
    NSString *subtitle;
    NSString *phone;
    NSString *email;
    //UIButton *rightCalloutAccessoryView;
}

@property (nonatomic, assign) CLLocationCoordinate2D coordinate;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *subtitle;
@property (nonatomic, copy) NSString *phone;
@property (nonatomic, copy) NSString *email;
//@property (nonatomic, retain) UIView * rightCalloutAccessoryView;
@end
