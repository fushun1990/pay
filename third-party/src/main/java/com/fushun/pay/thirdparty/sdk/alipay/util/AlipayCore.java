package com.fushun.pay.thirdparty.sdk.alipay.util;

import com.fushun.framework.util.exception.base.SpringContextUtil;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.thirdparty.sdk.alipay.config.AlipayConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/* *
 *类名：AlipayFunction
 *功能：支付宝接口公用函数类
 *详细：该类是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改
 *版本：3.3
 *日期：2012-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayCore {

    private static final String yu = "&";
    private static final String deng = "=";
    private static AlipayConfig alipayConfig = SpringContextUtil.getBean(AlipayConfig.class);
    private static Map<String, Object> alipayErrorMap = new HashMap<String, Object>();

    /**
     * 初始化 错误参数 支付宝的
     */
    static {
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("isp.unknow-error", "服务暂不可用（业务系统不可用）	稍后重试");
        map2.put("aop.unknow-error", "服务暂不可用（网关自身的未知错误）	稍后重试");
        alipayErrorMap.put("20000", map2);

        map2 = new HashMap<String, Object>();
        map2.put("aop.invalid-auth-token", "无效的访问令牌	请刷新授权令牌或重新授权获取新的令牌");
        map2.put("aop.auth-token-time-out", "访问令牌已过期	请刷新授权令牌或重新授权获取新的令牌");
        map2.put("aop.invalid-app-auth-token", "无效的应用授权令牌	请刷新应用授权令牌或重新授权获取新的令牌");
        map2.put("aop.invalid-app-auth-token-no-api", "商户未授权当前接口	请重新授权获取新的应用授权令牌");
        map2.put("aop.app-auth-token-time-out", "商户未授权当前接口	请重新授权获取新的应用授权令牌");
        map2.put("aop.no-product-reg-by-partner", "应用授权令牌已过期	请刷新应用授权令牌或重新授权获取新的令牌");
        map2.put("aop.invalid-app-auth-token-no-api", "商户未签约任何产品	ISV代理调用的场景，请上线商户的服务窗");
        alipayErrorMap.put("20001", map2);

        map2 = new HashMap<String, Object>();
        map2.put("isv.missing-method", "缺少方法名参数	请求参数里面必须要有method参数");
        map2.put("isv.missing-signature", "缺少签名参数	检查请求参数，缺少sign参数");
        map2.put("isv.missing-signature-type", "缺少签名类型参数	检查请求参数，缺少sign_type参数");
        map2.put("isv.missing-signature-key", "缺少签名配置	未上传公钥配置");
        map2.put("isv.missing-app-id", "缺少appId参数	检查请求参数，缺少app_id参数");
        map2.put("isv.missing-timestamp", "缺少时间戳参数	检查请求参数，缺少timestamp参数");
        map2.put("isv.missing-version", "缺少版本参数	检查请求参数，缺少version参数");
        map2.put("isv.decryption-error-missing-encrypt-type", "解密出错, 未指定加密算法	检查参数，缺少encrypt_type参数");
        alipayErrorMap.put("40001", map2);

        map2 = new HashMap<String, Object>();
        map2.put("isv.invalid-parameter", "参数无效	检查参数，格式不对、非法值、越界等");
        map2.put("isv.upload-fail", "文件上传失败	文件写入失败，重试");
        map2.put("isv.invalid-file-extension", "文件扩展名无效	检查传入的文件扩展名称，目前支持格式：csv,txt,zip,rar,gz,doc,docx,xls,xlsx,pdf,bmp,gif,jpg,jpeg,png");
        map2.put("isv.invalid-file-size", "文件大小无效	检查文件大小，目前支持最大为：50MB");
        map2.put("isv.invalid-method", "不存在的方法名	检查入参method是否正确");
        map2.put("isv.invalid-format", "无效的数据格式	检查入参format，目前只支持json和xml 2种格式");
        map2.put("isv.invalid-signature-type", "无效的签名类型	检查入参sign_type,目前只支持RSA,RSA2,HMAC_SHA1");
        map2.put("isv.invalid-signature", "无效签名	1.公私钥是否是一对,2.检查公钥上传是否与私钥匹配,3.存在中文需要做urlencode,4.签名算法是否无误");
        map2.put("isv.invalid-encrypt-type", "无效的加密类型	检查入参encrypt_type，目前只支持AES");
        map2.put("isv.invalid-encrypt", "解密异常	重试");
        map2.put("isv.invalid-app-id", "无效的appId参数	检查入参app_id，app_id不存在，或者未上线");
        map2.put("isv.invalid-timestamp", "非法的时间戳参数	时间戳参数timestamp非法，请检查格式需要为\"yyyy-MM-dd HH:mm:ss\"");
        map2.put("isv.invalid-charset", "字符集错误	请求参数charset错误，目前支持格式：GBK,UTF-8");
        map2.put(" isv.invalid-digest", "摘要错误	检查请求参数，文件摘要参数必填");
        map2.put("isv.decryption-error-not-valid-encrypt-type", "解密出错，不支持的加密算法	检查入参encrypt_type，目前只支持AES");
        map2.put("isv.decryption-error-not-valid-encrypt-key", "解密出错, 未配置加密密钥或加密密钥格式错误	没有配置加密秘钥");
        map2.put("isv.decryption-error-unknown", "解密出错，未知异常	重试");
        map2.put("isv.missing-signature-config", "验签出错, 未配置对应签名算法的公钥或者证书	没有配置应用公钥");
        map2.put("isv.not-support-app-auth", "本接口不支持第三方代理调用	本接口不支持第三方代理调用");
        alipayErrorMap.put("40002", map2);

        alipayErrorMap.put("40004", "业务处理失败	对应业务错误码，明细错误码和解决方案请参见具体的API接口文档");

        map2 = new HashMap<String, Object>();
        map2.put("isv.insufficient-isv-permissions", "ISV权限不足	请检查配置的账户是否有当前接口权限");
        map2.put("isv.insufficient-user-permissions", "用户权限不足	代理的商户没有当前接口权限");
        alipayErrorMap.put("40006", "参数无效	检查参数，格式不对、非法值、越界等");
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            if (sArray.get(key) != null) {
                String value = sArray.get(key).toString();
                if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                        || key.equalsIgnoreCase("sign_type")) {
                    continue;
                }
                result.put(key, value);
            }
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        return createLinkString(params, keys, false);
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串<br>
     * map.value 使用URLEncode编码
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringByEncode(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        return createLinkString(params, keys, true);
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringBySort(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        // key排序
        Collections.sort(keys);
        return createLinkString(params, keys, false);
    }

    /**
     * 拼接字符串
     *
     * @param params   参数对象
     * @param keys     参数Map中的key顺序  ASCII排序
     * @param isEncode 参数的value是否需要编码
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月13日
     * @records <p>  fushun 2017年1月13日</p>
     */
    private static String createLinkString(Map<String, String> params, List<String> keys, boolean isEncode) {
        StringBuffer sb = new StringBuffer();
        boolean one = false;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (isEncode) {
                value = buildValue(value);
            }
            if (one) {
                sb.append(yu);
            }
            one = true;
            sb.append(key).append(deng).append(value);
        }
        return sb.toString();
    }

    /**
     * Url 编码值
     *
     * @param value
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月14日
     * @records <p>  fushun 2016年9月14日</p>
     */
    private static String buildValue(String value) {
        try {
            return URLEncoder.encode(value, alipayConfig.getInputCharset());
        } catch (UnsupportedEncodingException e) {
            throw new PayException(e, PayException.Enum.PAY_CREATE_FAILED_EXCEPTION);
        }
    }

    /**
     * 获取支付宝错误码，对应的错误
     *
     * @param code
     * @param sub_code
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月13日
     * @records <p>  fushun 2016年9月13日</p>
     */
    @SuppressWarnings("unchecked")
    public static String errorMessage(String code, String sub_code) {
        Object error = alipayErrorMap.get(code);
        if (error instanceof String) {
            return error.toString();
        }
        return errorMessage((Map<String, Object>) error, sub_code);
    }

    /**
     * 获取sub_code 对应的错误信息
     *
     * @param map
     * @param sub_code
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月13日
     * @records <p>  fushun 2016年9月13日</p>
     */
    private static String errorMessage(Map<String, Object> map, String sub_code) {
        Object error = map.get(sub_code);
        if (error instanceof String) {
            return error.toString();
        }
        return null;
    }
}
