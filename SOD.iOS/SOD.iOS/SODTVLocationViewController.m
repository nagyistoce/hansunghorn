//
//  SODViewController.m
//  TestMapkit
//
//  Created by Project BJ on 12. 5. 29..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import "SODTVLocationViewController.h"

@implementation SODTVLocationViewController
@synthesize locManager, bestLocation;

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.locManager = [[[CLLocationManager alloc] init] autorelease];
    
    if([CLLocationManager locationServicesEnabled] == NO){
        NSLog(@"사용자가 우치 서비스 사용을 설정에서 꺼두었음");
        return;
    }
    
    else{
        self.locManager.delegate = self;
        self.locManager.desiredAccuracy = kCLLocationAccuracyBest;
        self.locManager.distanceFilter = 5.0f;
        [self.locManager startUpdatingLocation];
        MAX_TIME = 10;
        float rLatitude;
        float rLongitude;
        
        arrayLatitude = [[NSMutableArray alloc] init];
        arrayLongitude = [[NSMutableArray alloc] init];
        
        for(int i = 0; i < 5; i++)
        {
            int a = rand();
            
            NSLog(@"A : %d", a);
            
            if(i == 0){
                rLatitude = 37.58180;
                rLongitude = 127.00982;
            }
            
            else {
                rLatitude = ((rand()%376655427-376529902 + 376529902) * 0.0000001);
                rLongitude = ((rand()%1267663371-1267552399 + 1267552399) * 0.0000001);
            }
            
            NSNumber *temLatitude = [NSNumber numberWithFloat:rLatitude];
            NSNumber *temLongitude = [NSNumber numberWithFloat:rLongitude];
            
            [arrayLatitude addObject:temLatitude];
            [arrayLongitude addObject:temLongitude];
        }
        
        for(int i = 0; i < [arrayLatitude count]; i++)
        {
            SODMapPin *ann = [[SODMapPin alloc] init];
            ann.title = [NSString stringWithFormat:@"좌표 %d", i];
            ann.subtitle = [NSString stringWithFormat:@"제공 서비스 : testService"];
            CLLocationCoordinate2D center;
            center.latitude = [[arrayLatitude objectAtIndex:i] doubleValue];
            center.longitude = [[arrayLongitude objectAtIndex:i] doubleValue];
            ann.coordinate = center;
            [mapView addAnnotation:ann];
        }
    }
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

-(void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error
{
    NSLog(@"로케이션 매니저 에러 : %@", [error description]);
}

-(void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation
{
    if(!self.bestLocation)
        self.bestLocation = newLocation;
    
    else if(newLocation.horizontalAccuracy < bestLocation.horizontalAccuracy)
        self.bestLocation = newLocation;
    
    mapView.region = MKCoordinateRegionMake(self.bestLocation.coordinate, MKCoordinateSpanMake(0.1f, 0.1f));
    
    mapView.showsUserLocation = YES;
    mapView.zoomEnabled = YES;
    
    //NSLog(@"위도 : %f, 경도 : %f",  self.bestLocation.coordinate.latitude, self.bestLocation.coordinate.longitude);
}

-(void)mapView:(MKMapView *)mapView didAddAnnotationViews:(NSArray *)views
{
    for (SODMapPin *annotationView in views) {
        UIButton *button; 
        button = [UIButton buttonWithType : UIButtonTypeDetailDisclosure] ;
        annotationView.rightCalloutAccessoryView = button;
    }
}

-(void)mapView:(MKMapView *)mapView annotationView:(MKAnnotationView *)view calloutAccessoryControlTapped:(UIControl *)control
{
    SODMapPinViewController *tappedButton = [[SODMapPinViewController alloc] initWithNibName:@"SODMapPinView" bundle:nil];
    tappedButton.myPin = (SODMapPin *)[view.annotation retain];
    [self.navigationController pushViewController:tappedButton animated:YES];
}

-(void)tick:(NSTimer *)timer
{
    if(++timespent == MAX_TIME)
    {
        [timer invalidate];
        
        [self.locManager stopUpdatingLocation];
        self.locManager.delegate = nil;
        
        if(!self.bestLocation)
        {
            self.title = @"";
            return;
        }
        
        //[tvMessage setText:[NSString stringWithFormat:@"%0.1f 미터", self.bestLocation.horizontalAccuracy]];
        [mapView setRegion:MKCoordinateRegionMake(self.bestLocation.coordinate, MKCoordinateSpanMake(0.005f, 0.005f)) animated:YES];
        mapView.showsUserLocation = YES;
        mapView.zoomEnabled = YES;
    }
    
    else{
        //[tvMessage setText:[NSString stringWithFormat:@"%d 초 남았습니다.", MAX_TIME - timespent]];
    }    
}

- (IBAction)findme:(id)sender
{
    //최상의 위치를 검색한다.
    timespent = 0;
    self.bestLocation = nil;
    self.locManager.delegate = self;
    [self.locManager startUpdatingLocation];
    [NSTimer scheduledTimerWithTimeInterval :1.0f target:self selector:@selector(tick:) userInfo:nil repeats:YES];
}

@end
