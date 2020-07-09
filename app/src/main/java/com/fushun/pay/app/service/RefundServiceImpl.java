package com.fushun.pay.app.service;

import com.alibaba.cola.dto.Response;
import com.fushun.pay.app.command.RefundCmdExe;
import com.fushun.pay.client.api.RefundServiceI;
import com.fushun.pay.client.dto.RefundCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月15日22时01分
 */
@Service
public class RefundServiceImpl implements RefundServiceI {

    @Autowired
    private RefundCmdExe refundCmdExe;


    /**
     * @param refundCmd
     * @return com.alibaba.cola.dto.Response
     * @description 退款
     * @date 2019年02月15日22时05分
     * @author wangfushun
     * @version 1.0
     */
    @Override
    public Response refund(RefundCmd refundCmd) {
        Response response = refundCmdExe.execute(refundCmd);
        return response;
    }
}
