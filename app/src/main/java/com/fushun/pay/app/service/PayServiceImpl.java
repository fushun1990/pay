package com.fushun.pay.app.service;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.fushun.pay.app.api.PayServiceI;
import com.fushun.pay.app.dto.CreatePayCmd;
import com.fushun.pay.app.dto.PayFindByCriteriaQry;
import com.fushun.pay.app.dto.PayNotifyCmd;
import com.fushun.pay.app.dto.PaySyncResponseCmd;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangfushun
 * @version 1.0
 * @description 支付服务
 * @creation 2019年01月19日21时43分
 */
@Service
public class PayServiceImpl implements PayServiceI {

    @Autowired
    private CommandBusI commandBus;

    /**
     * @param payCmd
     * @return com.alibaba.cola.dto.SingleResponse<com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO>
     * @description 创建支付信息
     * @date 2019年02月15日21时57分
     * @author wangfushun
     * @version 1.0
     */
    @Override
    public SingleResponse<CreatedPayRequestBodyCO> createPay(CreatePayCmd payCmd) {
        SingleResponse<CreatedPayRequestBodyCO> response = (SingleResponse<CreatedPayRequestBodyCO>) commandBus.send(payCmd);
        return response;
    }

    @Override
    public SingleResponse<PayCO> pay(PayFindByCriteriaQry payFindByCriteriaQry) {
        return (SingleResponse<PayCO>) commandBus.send(payFindByCriteriaQry);
    }

    /**
     * @param payNotifyCmd
     * @return com.alibaba.cola.dto.SingleResponse<java.lang.String>
     * @description 支付异步通知验证
     * @date 2019年02月15日21时57分
     * @author wangfushun
     * @version 1.0
     */
    @Override
    public SingleResponse<String> payNotifyAlipayReust(PayNotifyCmd payNotifyCmd) {
        SingleResponse<String> response = (SingleResponse<String>) commandBus.send(payNotifyCmd);
        return response;
    }

    /**
     * @param paySyncResponseCmd
     * @return com.alibaba.cola.dto.SingleResponse<java.lang.String>
     * @description 支付同步返回验证
     * @date 2019年02月15日22时00分
     * @author wangfushun
     * @version 1.0
     */
    @Override
    public SingleResponse<String> payResponseValidator(PaySyncResponseCmd paySyncResponseCmd) {
        SingleResponse<String> response = (SingleResponse<String>) commandBus.send(paySyncResponseCmd);
        return response;
    }

}
