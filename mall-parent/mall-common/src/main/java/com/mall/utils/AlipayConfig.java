package com.mall.utils;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016102400748799";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCMHkBVU88zq37LvYGs3A5DAmHLezs20YR6YlBtqd1Q8wRv8zBUQ8JbN0WzSKItSz0SZ2cRgUY8F7U7L9qPI0lxHwlfaZ5qvcrHLHq6fqg+G9XgHuD0ut7ToRu03+7i6BBfApiQRMyA8tVVrmUfdi8ghcyD4YhaV2zzx6buCLUkIKaGhInOUi90WflBIxep+j0cSTfaFXL61SWn7q/kkz5CtShBa6t5nbBl8qPy0jZ9MZytu0UJjLxXVk3pR/SF7whAHTNJ/5FzlhGdIOiWFjYjrJEJKfbzoOU6M4fmC8y9t7M1Pg0kwfR3xg0H+TWTcrmoK2j1VE25RQpjiWQ2uuOZAgMBAAECggEAFzQnSTog1RiwXcMbR2oFvpkVYuIccz6NJstDMHXx3abw9C0cssep1vs3QrJjkQalrXeMPPzgibEPZSbOleetENun7LIL9vdJLmSySlIRQH/RklTqwfx0TC2g7forkE4H8AP5CeMqzqvoIlFIE8a5lTQzIx/lObjCpThJ759nh/zVP0rHbXO427Vn0Y9sDp1GHFf3UQixcZA5pOxriCg8/py4nFVjoyQtjeQc6uyAO6QG+bs6J+q2Zdx1WfIWiiH7848LdZVLxqLIE5xA6I8oHeYoyoWZ9ChRLE1D9tLXDr9bf2H2N8pYHJnW7/kJ58CwVG7X/ZYSV8Lp0ZROmT1ABQKBgQDOpb2HjtafX5CTgw3BgyDpNsJNkvzkGvpH0HQAx2nmKqOvTf2KyVs3pWQXMu2yEmYqYc9xb279v31oeP4oRTLGaVv3wz/Tb/Nfk8E4ep1sh3BTg3Oo6YBN3FgVinOW0moxhbVEl+jEP1XB6uV6nwoLSbTV9DyAHOb7IzJqIDxoVwKBgQCtlPcQZTI+gQCCkVrb9x9p++L2nT0nw/VNw1VngsinTk28ApjksRz5Wh9KOw8dByEmiatvIzZi8MDtBkAFYK9IJcxRhOQtWdYZskd/fzlF2MFmzwmtUSTsdRS8TczcmfApzZkdnTqPzNTiIMfr55YuKmaGQbLrT92cCUJJPHNdjwKBgQCjdXb2Q4aBipZSGNhIufDOiQIpWpuv06cmJz2bYCHkbtYLVSW5Io2E9s61ixN6SBOIctYkZIDnNxF8Ejj4ocQILk8RLODP3z4j3Dx68IKV1oyDgvd6JTnYXPNQFrRYGRjnE1zPZVo54d90BNqApEzNoP/qPcedjz8maDD4wYcHAQKBgQCn606irrLPYm5C4SibWFIsbf7fAbeSi2/3nTo2W3+O0iJC+eQ8f9X+pp9UaROyyh3YsR+F8POA/VQ1N3GhgimuytoAFmXCNYD39M5T2ZhnKEvYD/tVjgUBNjYr/7tpwQVi+0DfNCXdzJI4euvMG4nd+SibQl06ZFWGyDI2tOMJowKBgC7qRPHVGYDfLkjbCyrPL8H1otXL2aSNbhjDgnUEgO+/XEANfUxYkUkk6UWeNtUb91wAx/2WaYBDC0xm4mm7rlUCqiJfSdPEYHikkzd9vQhBUH3rAEbX+61EseMO8jbvr/ps1pIXBxkEwhzTPlcChC02/oTw4WCBWz3Cdvsz2vx3";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnLwsFhE6u0vVemG7vr66cchzQ5rMirQdMdSxNYuA16s68QMODaSPx3d7Jp/sHlU1ug2MjneifVzB7LemwJjWm0CmGYevNVIwZnBL8xknqidgSr292/fnQiYBcS3t38n7HGGSnECmFneiqbILEeGTcAIW3yh+xN+apKlNEJSSqqlBrKOrJx7yWaGlQ+KAPD4cLuBZffwIFmADczMeVYW0s3xdaadQUJhILmmEvlCIwQWbldzC8VU0H2Na6uVzqMRuceE6dgXx8QvKphy9jZVPICaRHL1QeIbzyuppklfdMvjQF93SEWxiCx/x8oDTE+cP8X2Lf6ZSvpSDopMGQL9U+QIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8082/index";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

