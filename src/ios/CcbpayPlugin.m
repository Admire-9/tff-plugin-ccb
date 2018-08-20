
#import "CcbpayPlugin.h"
#import "CcbOrder.h"
#import "MD5.h"

#import "DataSigner.h"
#import <CCBNetPaySDK/CCBNetPaySDK.h>
#import <CommonCrypto/CommonDigest.h>
@implementation CcbpayPlugin

-(void)pluginInitialize{
//    CDVViewController *viewController = (CDVViewController *)self.viewController;
//    self.partner = [viewController.settings objectForKey:@"partner"];
//    self.seller = [viewController.settings objectForKey:@"seller"];
//    self.privateKey = [viewController.settings objectForKey:@"privatekey"];
}

- (void) pay:(CDVInvokedUrlCommand *)command
{
    self.currentCallbackId = command.callbackId;
    
    /*
     *商户的唯一的parnter和seller。
     *签约后，支付宝会为每个商户分配一个唯一的 parnter 和 seller。
     */
    
    //partner和seller获取失败,提示
//    if ([self.partner length] == 0 ||
//        [self.seller length] == 0 ||
//        [self.privateKey length] == 0)
//    {
//        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示"
//                                                        message:@"缺少partner或者seller或者私钥。"
//                                                       delegate:self
//                                              cancelButtonTitle:@"确定"
//                                              otherButtonTitles:nil];
//        [alert show];
//        return;
//    }
    
    /*
     *生成订单信息及签名
     */
    
    //从API请求获取支付信息
    NSMutableDictionary *args = [command argumentAtIndex:0];
    NSString   *MERCHANTID  = @"105510148160150";
    NSString   *POSID  = @"809042103";
    NSString   *BRANCHID     = @"510000000";
    NSString   *ORDERID    = @"131222214551243";
    NSString   *PAYMENT    = @"0.01";
    NSString   *CURCODE    = @"01";
    NSString   *REMARK1    = [args objectForKey:@"REMARK1"];
    NSString   *REMARK2    = [args objectForKey:@"REMARK2"];
    NSString   *TXCODE    = @"520100";
    NSString   *TYPE    = @"1";
    NSString   *PUB    = @"9d3f3c6e3beac835b646359d020111";
    NSString   *GATEWAY    = @"0";
    CcbOrder *order = [[CcbOrder alloc] init];
    order.MERCHANTID = MERCHANTID;
    order.POSID = POSID;
    order.BRANCHID = BRANCHID;
    order.ORDERID = ORDERID;
    order.PAYMENT = PAYMENT;
    order.CURCODE = CURCODE;
    order.REMARK1 =  REMARK1; //回调URL
    order.REMARK2 = REMARK2;
    order.TXCODE = TXCODE;
    order.TYPE = TYPE;
    order.PUB = PUB;
    order.GATEWAY = GATEWAY;
    
    //将商品信息拼接成字符串
    NSString *orderSpec = [order description];
    NSLog(@"orderSpec = %@",orderSpec);
    //获取私钥并将商户信息签名,外部商户可以根据情况存放私钥和签名,只需要遵循RSA签名规范,并将签名字符串base64编码和UrlEncode
//    id<DataSigner> signer = CreateRSADataSigner(self.privateKey);
//    NSString *signedString = [signer signString:orderSpec];
    
    //将签名成功字符串格式化为订单字符串,请严格按照该格式
//    NSString *orderString = nil;
    if (orderSpec != nil) {
//        orderString = [NSString stringWithFormat:@"%@&sign=\"%@\"&sign_type=\"%@\"",
//                       orderSpec, signedString, @"RSA"];
        [[CCBNetPay defaultService] payOrder:orderSpec callback:^(NSDictionary *resultDic){
            NSLog(@"reslut = %@",resultDic);
        }];
        
        
//        [[AlipaySDK defaultService] payOrder:orderString fromScheme:[NSString stringWithFormat:@"a%@", self.partner] callback:^(NSDictionary *resultDic) {
//            if ([[resultDic objectForKey:@"resultStatus"]  isEqual: @"9000"]) {
//                [self successWithCallbackID:self.currentCallbackId messageAsDictionary:resultDic];
//            } else {
//                [self failWithCallbackID:self.currentCallbackId messageAsDictionary:resultDic];
//            }
//
//            NSLog(@"reslut = %@",resultDic);
//        }];
        
    }
}


- (void)successWithCallbackID:(NSString *)callbackID withMessage:(NSString *)message
{
    CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:message];
    [self.commandDelegate sendPluginResult:commandResult callbackId:callbackID];
}

- (void)failWithCallbackID:(NSString *)callbackID withMessage:(NSString *)message
{
    CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:message];
    [self.commandDelegate sendPluginResult:commandResult callbackId:callbackID];
}
- (void)successWithCallbackID:(NSString *)callbackID messageAsDictionary:(NSDictionary *)message
{
    CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:message];
    [self.commandDelegate sendPluginResult:commandResult callbackId:callbackID];
}

- (void)failWithCallbackID:(NSString *)callbackID messageAsDictionary:(NSDictionary *)message
{
    CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:message];
    [self.commandDelegate sendPluginResult:commandResult callbackId:callbackID];
}

@end
