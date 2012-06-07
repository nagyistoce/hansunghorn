//
//  SODConstants.h
//  SOD.iOS
//
//  Created by Project BJ on 12. 5. 25..
//  Copyright (c) 2012년 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SODConstants : NSObject
{
    @public
    NSString *DATATYPE_INT;
    NSString *DATATYPE_FLOAT;
    NSString *DATATYPE_DOUBLE;
    NSString *DATATYPE_STRING;
    NSString *DATATYPE_BYTEARRAY;
    
    int REQUEST_ACCEPT;
    int RESPONSE_ACCEPT;
    int REQUEST_PING;
    int RESPONSE_PING;
    int RESPONSE_SERVICE_NAME;
    int REQUEST_CLIENT_ALIVE;
    int RESPONSE_CLIENT_ALIVE;
    int REQUEST_SERVICE_DATA;
    int RESPONSE_SERVICE_DATA;
}

@property (nonatomic, retain) NSString *DATATYPE_INT;
@property (nonatomic, retain) NSString *DATATYPE_FLOAT;
@property (nonatomic, retain) NSString *DATATYPE_DOUBLE;
@property (nonatomic, retain) NSString *DATATYPE_STRING;
@property (nonatomic, retain) NSString *DATATYPE_BYTEARRAY;

@property (nonatomic)int REQUEST_ACCEPT;
@property (nonatomic)int RESPONSE_ACCEPT;
@property (nonatomic)int REQUEST_PING;
@property (nonatomic)int RESPONSE_PING;
@property (nonatomic)int RESPONSE_SERVICE_NAME;
@property (nonatomic)int REQUEST_CLIENT_ALIVE;
@property (nonatomic)int RESPONSE_CLIENT_ALIVE;
@property (nonatomic)int REQUEST_SERVICE_DATA;
@property (nonatomic)int RESPONSE_SERVICE_DATA;


@end
