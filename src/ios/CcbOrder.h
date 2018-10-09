
//
//  Order.h
//  AlixPayDemo
//
//  Created by 方彬 on 11/2/13.
//
//

#import <Foundation/Foundation.h>

@interface CcbOrder : NSObject

@property(nonatomic, copy) NSString * MERCHANTID;
@property(nonatomic, copy) NSString * POSID;
@property(nonatomic, copy) NSString * BRANCHID;
@property(nonatomic, copy) NSString * ORDERID;
@property(nonatomic, copy) NSString * PAYMENT;
@property(nonatomic, copy) NSString * CURCODE;
@property(nonatomic, copy) NSString * REMARK1;
@property(nonatomic, copy) NSString * REMARK2;
@property(nonatomic, copy) NSString * TXCODE;
@property(nonatomic, copy) NSString * MAC;
@property(nonatomic, copy) NSString * TYPE;
@property(nonatomic, copy) NSString * PUB;
@property(nonatomic, copy) NSString * GATEWAY;
/*
 * 以下可选
 */
@property(nonatomic, copy) NSString * CLIENTIP;
@property(nonatomic, copy) NSString * REGINFO;
@property(nonatomic, copy) NSString * PROINFO;
@property(nonatomic, copy) NSString * REFERER;
@property(nonatomic, copy) NSString * THIRDAPPINFO;

@property(nonatomic, readonly) NSMutableDictionary * extraParams;


@end
