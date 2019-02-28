package com.fushun.pay.thirdparty.sdk.alipay.util;

import com.alipay.api.*;
import com.fushun.framework.util.exception.base.SpringContextUtil;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.thirdparty.sdk.alipay.config.AlipayConfig;
import com.fushun.pay.thirdparty.sdk.alipay.sign.RSA;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipaySubmitApp {

    private static AlipayConfig alipayConfig = SpringContextUtil.getBean(AlipayConfig.class);

    /**
     * 生成签名结果
     *
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    private static String buildRequestMysign(Map<String, String> sPara, String privateKey) {
        String prestr = AlipayCore.createLinkStringBySort(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = RSA.signPKCS8(prestr, privateKey, alipayConfig.getInputCharset());
        return mysign;
    }

    /**
     * 建立请求参数，提供给app支付
     *
     * @param sParaTemp 参数字符串
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月12日
     * @records <p>  fushun 2016年9月12日</p>
     */
    public static String buildRequest(Map<String, String> sParaTemp, String privateKey) {

        String str = AlipayCore.createLinkStringByEncode(sParaTemp);
        //生成签名结果
        String mysign = createMysign(sParaTemp, privateKey);
        //签名结果与签名方式加入请求提交参数组中
        return str + "&sign=" + mysign;
    }


    /**
     * 生成sign RSA
     *
     * @param sParaTemp
     * @param privateKey
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月14日
     * @records <p>  fushun 2016年9月14日</p>
     */
    private static String createMysign(Map<String, String> sParaTemp, String privateKey) {
        String mysign = buildRequestMysign(sParaTemp, privateKey);
        try {
            mysign = URLEncoder.encode(mysign, alipayConfig.getInputCharset());
        } catch (UnsupportedEncodingException e) {
            throw new PayException(e, PayException.Enum.PAY_CREATE_FAILED_EXCEPTION);
        }
        return mysign;
    }


    public static <T extends AlipayResponse> T execute(AlipayRequest<T> alipayRequest) throws AlipayApiException {
        //实例化客户端
        AlipayClient client = new DefaultAlipayClient(alipayConfig.getGateway(), alipayConfig.getPartner(), alipayConfig.getRsaPrivateKeyPkcs8(), "json", alipayConfig.getInputCharset(), alipayConfig.getAliPayPublicKey(), alipayConfig.getSignType());
        return client.execute(alipayRequest);
    }
}
