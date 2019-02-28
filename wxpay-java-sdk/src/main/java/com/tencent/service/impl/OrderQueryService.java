package com.tencent.service.impl;

import com.tencent.common.ApiConfigure;
import com.tencent.protocol.order_query_protocol.OrderQueryReqData;
import com.tencent.service.BaseService;

/**
 * 订单查询接口
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月6日
 */
public class OrderQueryService extends BaseService {

    public OrderQueryService() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(ApiConfigure.PAY_QUERY_API);
    }

    public String request(OrderQueryReqData orderQueryReqData) throws Exception {
        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(orderQueryReqData);

        return responseString;
    }

}
