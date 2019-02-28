package com.tencent.service.impl;

import com.tencent.common.ApiConfigure;
import com.tencent.protocol.order_query_protocol.OrderQueryReqData;
import com.tencent.service.BaseService;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 16:04
 */
public class ScanPayQueryService extends BaseService {

    public ScanPayQueryService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(ApiConfigure.PAY_QUERY_API);
    }

    /**
     * 请求支付查询服务
     *
     * @param orderQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String request(OrderQueryReqData orderQueryReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(orderQueryReqData);

        return responseString;
    }


}
