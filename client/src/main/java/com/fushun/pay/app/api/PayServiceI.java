package com.fushun.pay.app.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.fushun.pay.app.dto.CreatePayCmd;
import com.fushun.pay.app.dto.PayFindByCriteriaQry;
import com.fushun.pay.app.dto.PayNotifyCmd;
import com.fushun.pay.app.dto.PaySyncResponseCmd;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月19日21时43分
 */
public interface PayServiceI {

    /**
     * @param payCmd
     * @return com.alibaba.cola.dto.SingleResponse<com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO>
     * @description 创建支付信息
     * @date 2019年02月15日21时57分
     * @author wangfushun
     * @version 1.0
     */
    SingleResponse<CreatedPayRequestBodyCO> createPay(CreatePayCmd payCmd);

    MultiResponse<PayCO> pay(PayFindByCriteriaQry payFindByCriteriaQry);

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
