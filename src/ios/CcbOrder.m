
//
//
//

#import "CcbOrder.h"
#import <CommonCrypto/CommonDigest.h>
@implementation CcbOrder

- (NSString *)description {
    NSMutableString * discription = [NSMutableString string];
    [discription appendFormat:@"MERCHANTID=\"%@\"", self.MERCHANTID];
    [discription appendFormat:@"&POSID=\"%@\"", self.POSID];
    [discription appendFormat:@"&BRANCHID=\"%@\"", self.BRANCHID];
    [discription appendFormat:@"&ORDERID=\"%@\"", self.ORDERID];
    [discription appendFormat:@"&PAYMENT=\"%@\"", self.PAYMENT];
    [discription appendFormat:@"&CURCODE=\"%@\"", self.CURCODE];
    [discription appendFormat:@"&TXCODE=\"%@\"", self.TXCODE];
    if(self.REMARK1){
        [discription appendFormat:@"&REMARK1=\"%@\"",self.REMARK1];
    }
    if(self.REMARK2) {
        [discription appendFormat:@"&REMARK2=\"%@\"",self.REMARK2];
    }
    [discription appendFormat:@"&TYPE=\"%@\"",self.TYPE];//1
    [discription appendFormat:@"&PUB=\"%@\"",self.PUB];//1
    [discription appendFormat:@"&GATEWAY=\"%@\"",self.GATEWAY];//1
    [discription appendFormat:@"&CLIENTIP=\"%@\"",self.CLIENTIP];//1
    [discription appendFormat:@"&REGINFO=\"%@\"",self.REGINFO];//1
    [discription appendFormat:@"&PROINFO=\"%@\"",self.PROINFO];//1
    NSString * MAC = [self md5:discription];
    for (NSString * key in [self.extraParams allKeys]) {
        [discription appendFormat:@"&%@=\"%@\"", key, [self.extraParams objectForKey:key]];
    }
    [discription appendFormat:@"&MAC=\"%@\"",MAC];
    return discription;
}

- (NSString *) md5:(NSString *)orderDesc
{
    const char* str = [orderDesc UTF8String];
    unsigned char result[16];
    CC_MD5(str, (uint32_t)strlen(str), result);
    NSMutableString *ret = [NSMutableString stringWithCapacity:16 * 2];
    for(int i = 0; i<16; i++) {
        [ret appendFormat:@"%02x",(unsigned int)(result[i])];
    }
    return ret;
}

@end
