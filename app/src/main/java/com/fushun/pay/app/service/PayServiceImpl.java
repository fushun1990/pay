package com.fushun.pay.app.service;

import com.alibaba.cola.dto.SingleResponse;
import com.fushun.pay.app.command.PayCmdExe;
import com.fushun.pay.app.command.PayNotifyCmdExe;
import com.fushun.pay.app.command.PaySyncResponseCmdExe;
import com.fushun.pay.app.command.query.PayFindByCriteriaQueryExe;
import com.fushun.pay.client.api.PayServiceI;
import com.fushun.pay.client.dto.CreatePayCmd;
import com.fushun.pay.client.dto.PayFindByCriteriaQry;
import com.fushun.pay.client.dto.PayNotifyCmd;
import com.fushun.pay.client.dto.PaySyncResponseCmd;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.dto.clientobject.createpay.response.CreatedPayVO;
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
    private PayCmdExe payCmdExe;

    @Autowired
    private PayFindByCriteriaQueryExe payFindByCriteriaQueryExe;

    @Autowired
    private PayNotifyCmdExe payNotifyCmdExe;

    @Autowired
    private PaySyncResponseCmdExe paySyncResponseCmdExe;

    /**
     * @param payCmd
     * @return com.alibaba.cola.dto.SingleResponse<com.fushun.pay.dto.clientobject.createpay.CreatedPayRequestBodyCO>
     * @description 创建支付信息
     * @date 2019年02月15日21时57分
     * @author wangfushun
     * @version 1.0
     */
    @Override
    public SingleResponse<CreatedPayVO> createPay(CreatePayCmd payCmd) {
        SingleResponse<CreatedPayVO> response = payCmdExe.createPay(payCmd);
        return response;
    }

    @Override
    public SingleResponse<PayDTO> pay(PayFindByCriteriaQry payFindByCriteriaQry) {
        return (SingleResponse<PayDTO>) payFindByCriteriaQueryExe.execute(payFindByCriteriaQry);
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
        SingleResponse<String> response = payNotifyCmdExe.execute(payNotifyCmd);
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
        SingleResponse<String> response = paySyncResponseCmdExe.execute(paySyncResponseCmd);
        return response;
    }

}
