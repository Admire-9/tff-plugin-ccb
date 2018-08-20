
#import <Cordova/CDV.h>

@interface CcbpayPlugin : CDVPlugin

@property(nonatomic,strong)NSString *currentCallbackId;

- (void) pay:(CDVInvokedUrlCommand*)command;
@end
