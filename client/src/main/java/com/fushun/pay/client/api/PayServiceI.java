package com.fushun.pay.client.api;

import com.alibaba.cola.dto.SingleResponse;
import com.fushun.pay.client.dto.CreatePayCmd;
import com.fushun.pay.client.dto.PayFindByCriteriaQry;
import com.fushun.pay.client.dto.PayNotifyCmd;
import com.fushun.pay.client.dto.PaySyncResponseCmd;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.dto.clientobject.createpay.response.CreatedPayVO;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月19日21时43分
 */
public interface PayServiceI {

    /**
     * @param payCmd
     * @return com.alibaba.cola.dto.SingleResponse<com.fushun.pay.dto.clientobject.createpay.response.CreatedPayVO>
     * @description 创建支付信息
     * @date 2019年02月15日21时57分
     * @author wangfushun
     * @version 1.0
     */
    SingleResponse<CreatedPayVO> createPay(CreatePayCmd payCmd);

    SingleResponse<PayDTO> pay(PayFindByCriteriaQry payFindByCriteriaQry);

    /**
     * @param payNotifyCmd
     * @return com.alibaba.cola.dto.SingleResponse<java.lang.String>
     * @description 支付异步通知验证
     * @date 2019年02月15日21时57分
     * @author wangfushun
     * @version 1.0
     */
    SingleResponse<String> payNotifyAlipayReust(PayNotifyCmd payNotifyCmd);

    /**
     * @param paySyncResponseCmd
     * @return com.alibaba.cola.dto.SingleResponse<java.lang.String>
     * @description 支付同步返回验证
     * @date 2019年02月15日22时00分
     * @author wangfushun
     * @version 1.0
     */
    SingleResponse<String> payResponseValidator(PaySyncResponseCmd paySyncResponseCmd);
}
