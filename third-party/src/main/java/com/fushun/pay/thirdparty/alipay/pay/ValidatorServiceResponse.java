package com.fushun.pay.thirdparty.alipay.pay;

import com.alipay.api.AlipayResponse;
import com.fushun.pay.domain.exception.BasePayException;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.thirdparty.sdk.alipay.utils.AlipayCore;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 验证服务器的返回结果
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2016年9月18日
 */
@Component
public class ValidatorServiceResponse {
    /**
     * 验证返回参数
     *
     * @param alipayResponse 返回对象
     * @param errorMap       接口自定义错误信息
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月18日
     * @records <p>  fushun 2016年9月18日</p>
     */
    public void validatorServiceResponse(AlipayResponse alipayResponse, Map<String, String> errorMap) {
        try {
            Object code = alipayResponse.getCode();
            //支付失败
            if (code == null) {
                throw new PayException(PayException.PayExceptionEnum.PAY_RETURN_STATUS_ERROR);
            }
            // 支付失败
            if (!"10000".equals(code)) {
                errorCode(String.valueOf(code), alipayResponse.getSubCode(), errorMap);
                return;
            }
            return;
        } catch (BasePayException e) {
            throw e;
        } catch (Exception e) {
            throw new PayException(e, PayException.PayExceptionEnum.PAY_FAILED);
        }
    }


    /**
     * 解析错误码
     *
     * @param code
     * @param sub_code
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月13日
     * @records <p>  fushun 2016年9月13日</p>
     */
    private void errorCode(String code, String sub_code, Map<String, String> errorMap) {
        String error = errorMap.get(sub_code);
        if (!StringUtils.isEmpty(error)) {
            throw new PayException(PayException.PayExceptionEnum.PAY_FAILED,error);
        }
        error = AlipayCore.errorMessage(code, sub_code);
        if (!StringUtils.isEmpty(error)) {
            throw new PayException(PayException.PayExceptionEnum.PAY_FAILED,error);
        }
        throw new PayException(PayException.PayExceptionEnum.PAY_FAILED);
    }

}
