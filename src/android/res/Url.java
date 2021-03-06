package org.apache.cordova.tffccb;

import java.util.Random;

import android.util.Log;


public class Url {

  public String INTER_FLAG = "3";// 接口类型，若为'1'则表示新接口，新接口必需有商户公钥的前30位PUB32  UAT--可用防钓鱼接口
  public String MERCHANTID = ""; //商户代码
  public String POSID = ""; //商户柜台代码
  public String BRANCHID = ""; //分行代码
  public String PUB32TR2 = "";
  public String ORDERID = ""; //定单号
  public String PAYMENT = ""; //付款金额
  public String CURCODE = "01"; //币种
  public String TXCODE = ""; //交易码
  public String OPERID = "";
  public String AUTHID = "";
  public String PASSWORD = "";
  public String REQUESTSN = "";
  public String REMARK1 = ""; //备注1
  public String REMARK2 = ""; //备注2
  public String PUB32 = "";
  public String GATEWAY = "0";
  public String REGINFO = "";
  public String CLIENTIP = "";
  public String PROINFO = "";//digital
  public String MER_REFERER = "";
  public String STOREINFO = "";
  public String PROTYPE = "";

  // 以下内容不要轻易改动
  public String strMD5;
  public String URL;
  public String URL0;// 网上银行支付
  public String URL1;// E付卡支付

  private String INSTALLNUM = "";

  public Url() {
  }

  public String make(String pub, String txcode, String merchantid, String posid, String branchid, String price, String ip, String orderid, String installnum, String remark1) {
    String tmp;
    String tmp0;
    String temp_New1 = "", urlResult = "";
    PUB32 = pub;
    PUB32TR2 = pub;
    CLIENTIP = ip;
    POSID = posid;
    PAYMENT = price;
    TXCODE = txcode;
    MERCHANTID = merchantid;
    BRANCHID = branchid;
    ORDERID = orderid;
    INSTALLNUM = installnum;
    REMARK1 = remark1;
    tmp = "MERCHANTID=" + MERCHANTID + "&POSID=" + POSID + "&BRANCHID="
            + BRANCHID + "&ORDERID=" + ORDERID + "&PAYMENT=" + PAYMENT
            + "&CURCODE=" + CURCODE + "&TXCODE=" + TXCODE + "&REMARK1="
            + REMARK1 + "&REMARK2=" + REMARK2;
    tmp0 = "MERCHANTID=" + MERCHANTID + "&POSID=" + POSID + "&BRANCHID="
            + BRANCHID + "&ORDERID=" + ORDERID + "&PAYMENT=" + PAYMENT
            + "&CURCODE=" + CURCODE + "&TXCODE=520100" + "&REMARK1="
            + REMARK1 + "&REMARK2=" + REMARK2;

    String index = "3";
    String temp_New = tmp;

    if ("1".equals(index)) {
      temp_New = tmp + "&PUB32=" + PUB32;
    }
    if ("3".equals(index)) {
      temp_New = tmp + "&TYPE=1&PUB=" + PUB32TR2 + "&GATEWAY=" + GATEWAY
              + "&CLIENTIP=" + CLIENTIP + "&REGINFO=" + escape(REGINFO)
              + "&PROINFO=" + escape(PROINFO) + "&REFERER=" + MER_REFERER;
      if (!"".equals(INSTALLNUM)) {
        if (1 < Integer.parseInt(INSTALLNUM))
          temp_New += "&INSTALLNUM=" + INSTALLNUM;
      }
      temp_New += "&THIRDAPPINFO=ccbDemo";
      temp_New1 = tmp + "&TYPE=1&GATEWAY=" + GATEWAY + "&CLIENTIP=" + CLIENTIP
              + "&REGINFO=" + escape(REGINFO).replaceAll("%", "%25")
              + "&PROINFO=" + escape(PROINFO).replaceAll("%", "%25")
              + "&REFERER=" + MER_REFERER + "&INSTALLNUM=" + INSTALLNUM
              + "&THIRDAPPINFO=ccbDemo";
    }
    if ("4".equals(index)) {
      temp_New = tmp + "&TYPE=1&PUB=" + PUB32TR2 + "&GATEWAY=" + GATEWAY
              + "&CLIENTIP=" + CLIENTIP + "&REGINFO=" + escape(REGINFO)
              + "&PROINFO=" + escape(PROINFO) + "&REFERER=" + MER_REFERER;
      if (!"".equals(INSTALLNUM)) {
        if (1 < Integer.parseInt(INSTALLNUM))
          temp_New += "&INSTALLNUM=" + INSTALLNUM;
      }
      temp_New += "&THIRDAPPINFO=ccbDemo";
      temp_New1 = tmp + "&TYPE=1&GATEWAY=" + GATEWAY + "&CLIENTIP=" + CLIENTIP
              + "&REGINFO=" + escape(REGINFO).replaceAll("%", "%25")
              + "&PROINFO=" + escape(PROINFO).replaceAll("%", "%25")
              + "&REFERER=" + MER_REFERER + "&INSTALLNUM=" + INSTALLNUM
              + "&THIRDAPPINFO=ccbDemo"
              + "&STOREINFO=" + escape(STOREINFO).replaceAll("%", "%25")
              + "&PROTYPE=" + escape(PROTYPE).replaceAll("%", "%25");
    }
    if ("2".equals(index)) {
      tmp = "MERCHANTID=" + MERCHANTID + "&POSID=" + POSID + "&BRANCHID="
              + BRANCHID + "&ORDERID=" + ORDERID + "&AUTHID=" + AUTHID
              + "&USERID=" + OPERID + "&PASSWORD=" + PASSWORD
              + "&PAYMENT=" + PAYMENT + "&REQUESTSN=" + REQUESTSN
              + "&CURCODE=" + CURCODE + "&TXCODE=" + TXCODE + "&REM1="
              + REMARK1 + "&REM2=" + REMARK2;
      temp_New = tmp + "&PUB32=" + PUB32;
    }
    Log.i("---商户参数串---", temp_New);
    strMD5 = Util.getInstance().hex_md5(temp_New);
    URL = "CCB_IBSVersion=V5&" + tmp + "&MAC=" + strMD5 + "&CallJs=0";
    URL0 = "CCB_IBSVersion=V5&" + tmp0 + "&MAC="
            + Util.getInstance().hex_md5(tmp0) + "&CallJs=0";

    String URL3 = temp_New1 + "&MAC="
            + Util.getInstance().hex_md5(temp_New);
    //+ "&CallJs=0";

    // 打开提交按钮
    if ("3".equals(index) || "4".equals(index)) {
      urlResult = URL3;
    }
    if ("0".equals(index)) {
      urlResult = URL0;
    }
    return urlResult;
  }

  public String escape(String str) {
      int i;
      char j;
      StringBuffer tmp = new StringBuffer();
      tmp.ensureCapacity(str.length() * 6);
      for (i = 0; i < str.length(); i++) {
          j = str.charAt(i);
          if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
              tmp.append(j);
          else if (j < 256) {
              tmp.append("%");
              if (j < 16)
                  tmp.append("0");
              tmp.append(Integer.toString(j, 16));
          } else {
              tmp.append("%u");
              tmp.append(Integer.toString(j, 16));
          }
      }
      return tmp.toString();
  }

}
