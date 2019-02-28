package com.tencent.service.impl;

import com.tencent.common.ApiConfigure;
import com.tencent.protocol.unifiedorder_protocol.AppUnifiedOrderReqData;
import com.tencent.service.BaseService;


/**
 * 统一下单
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月4日
 */
public class UnifiedorderService extends BaseService {

    public UnifiedorderService()
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(ApiConfigure.UNIFIEDORDER_API);

    }


    /**
     * 统一下单
     *
     * @param unifiedorderReqData
     * @return
     * @throws Exception
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    public String request(AppUnifiedOrderReqData unifiedorderReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(unifiedorderReqData);

        return responseString;
    }
}
