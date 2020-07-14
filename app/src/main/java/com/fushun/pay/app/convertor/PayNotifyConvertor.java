package com.fushun.pay.app.convertor;

import com.alibaba.cola.extension.BizScenario;
import com.fushun.framework.base.SpringContextUtil;
import com.fushun.pay.app.convertor.extensionpoint.PayNotifyConvertorExtPt;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyDTO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日22时45分
 */
@Component
public class PayNotifyConvertor implements PayNotifyConvertorExtPt<PayNotifyThirdPartyDTO> {

    @Override
    public PayE clientToEntity(PayNotifyThirdPartyDTO payNotifyThirdPartyDTO, BizScenario bizScenario) {
        PayE payE = SpringContextUtil.getBean(PayE.class);
        payE.setOutTradeNo(payNotifyThirdPartyDTO.getOutTradeNo());
        payE.setPayMoney(payNotifyThirdPartyDTO.getPayMoney());
        payE.setPayNo(payNotifyThirdPartyDTO.getPayNo());
        payE.setStatus(payNotifyThirdPartyDTO.getStatus());
        payE.setBizScenario(bizScenario);
        payE.setReceiveWay(payNotifyThirdPartyDTO.getReceiveWay());
        return payE;
    }

    public PayDTO dataToClient(RecordPayDO dataObject) {
        return null;
    }
}
