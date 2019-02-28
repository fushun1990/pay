package com.tencent;

import com.tencent.business.ResultListener;
import com.tencent.business.impl.*;
import com.tencent.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tencent.protocol.downloadbill_protocol.DownloadBillResData;
import com.tencent.protocol.oauth20_protocol.OAuth20ReqData;
import com.tencent.protocol.oauth20_protocol.OAuth20ResData;
import com.tencent.protocol.order_query_protocol.OrderQueryReqData;
import com.tencent.protocol.order_query_protocol.OrderQueryResData;
import com.tencent.protocol.pay_protocol.ScanPayReqData;
import com.tencent.protocol.pay_protocol.ScanPayResData;
import com.tencent.protocol.refund_protocol.RefundReqData;
import com.tencent.protocol.refund_protocol.RefundResData;
import com.tencent.protocol.refund_query_protocol.RefundQueryReqData;
import com.tencent.protocol.refund_query_protocol.RefundQueryResData;
import com.tencent.protocol.reverse_protocol.ReverseReqData;
import com.tencent.protocol.unifiedorder_protocol.AppUnifiedOrderReqData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
import com.tencent.service.impl.*;

/**
 * SDK总入口
 */
public class WXPay {


    /**
     * 请求支付服务
     *
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public static String requestScanPayService(ScanPayReqData scanPayReqData) throws Exception {
        return new ScanPayService().request(scanPayReqData);
    }

    /**
     * 请求支付查询服务
     *
     * @param orderQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestScanPayQueryService(OrderQueryReqData orderQueryReqData) throws Exception {
        return new ScanPayQueryService().request(orderQueryReqData);
    }

    /**
     * 请求退款服务
     *
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestRefundService(RefundReqData refundReqData) throws Exception {
        return new RefundService().request(refundReqData);
    }

    /**
     * 请求退款查询服务
     *
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestRefundQueryService(RefundQueryReqData refundQueryReqData) throws Exception {
        return new RefundQueryService().request(refundQueryReqData);
    }

    /**
     * 请求撤销服务
     *
     * @param reverseReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestReverseService(ReverseReqData reverseReqData) throws Exception {
        return new ReverseService().request(reverseReqData);
    }

    /**
     * 请求对账单下载服务
     *
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestDownloadBillService(DownloadBillReqData downloadBillReqData) throws Exception {
        return new DownloadBillService().request(downloadBillReqData);
    }

    /**
     * 直接执行被扫支付业务逻辑（包含最佳实践流程）
     *
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public static void doScanPayBusiness(ScanPayReqData scanPayReqData, ResultListener<ScanPayResData> resultListener) throws Exception {
        new ScanPayBusiness().run(scanPayReqData, resultListener);
    }

    /**
     * 调用退款业务逻辑
     *
     * @param refundReqData  这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
    public static void doRefundBusiness(RefundReqData refundReqData, ResultListener<RefundResData> resultListener) throws Exception {
        new RefundBusiness().run(refundReqData, resultListener);
    }

    /**
     * 运行退款查询的业务逻辑
     *
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener     商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public static void doRefundQueryBusiness(RefundQueryReqData refundQueryReqData, ResultListener<RefundQueryResData> resultListener) throws Exception {
        new RefundQueryBusiness().run(refundQueryReqData, resultListener);
    }

    /**
     * 请求对账单下载服务
     *
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener      商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return API返回的XML数据
     * @throws Exception
     */
    public static void doDownloadBillBusiness(DownloadBillReqData downloadBillReqData, ResultListener<DownloadBillResData> resultListener) throws Exception {
        new DownloadBillBusiness().run(downloadBillReqData, resultListener);
    }

    /**
     * 统一下单接口
     *
     * @param unifiedorderReqData
     * @param resultListener
     * @throws Exception
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    public static <T extends AppUnifiedOrderReqData> void unifiedorderBusiness(T unifiedorderReqData, ResultListener<UnifiedorderResData> resultListener) throws Exception {
        new UnifiedorderBusiness<T>().run(unifiedorderReqData, resultListener);
    }

    /**
     * 获取授权信息
     *
     * @param auth20ReqData
     * @param resultListener
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws Exception
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     * @records <p>  fushun 2017年1月6日</p>
     */
    public static void oAuth20Business(OAuth20ReqData oauth20ReqData, ResultListener<OAuth20ResData> resultListener) throws IllegalAccessException, ClassNotFoundException, InstantiationException, Exception {
        new OAuth20Business().run(oauth20ReqData, resultListener);
    }

    /**
     * 查询订单支付状态
     *
     * @param orderQueryReqData
     * @param resultListener
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws Exception
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     * @records <p>  fushun 2017年1月6日</p>
     */
    public static void orderQueryBusiness(OrderQueryReqData orderQueryReqData, ResultListener<OrderQueryResData> resultListener) throws IllegalAccessException, ClassNotFoundException, InstantiationException, Exception {
        new OrderQueryBusiness().run(orderQueryReqData, resultListener);
    }

}
