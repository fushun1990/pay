package com.fushun.pay.domain.pay.async;

import cn.hutool.http.HttpUtil;
import com.fushun.pay.client.config.PayConfig;
import com.fushun.pay.client.dto.enumeration.ERecordPayNotityStatus;
import com.fushun.pay.infrastructure.pay.tunnel.database.PayDBTunnel;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;


/**
 * 支付异步通知出来
 */
@Service
public class PayAsyncService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayDBTunnel payDBTunnel;
    /**
     * 项目启动配置
     */
    @Autowired
    private PayConfig payConfig;

    /**
     * 支付成功 异步通知
     * @return
     */
    @Async
    public Future<String> asyncPayNotify(String outTradeNo,String orderPayNo) {

        try {
            RecordPayId recordPayId=new RecordPayId();
            recordPayId.setOutTradeNo(outTradeNo);
            Optional<RecordPayDO> recordPayDOOptional= payDBTunnel.findById(recordPayId);
            if(!recordPayDOOptional.isPresent()){
                logger.error("没有支付信息，outTradeNo：[{}],orderPayNo:[{}]",outTradeNo,orderPayNo);
                return new AsyncResult<String>("fail");
            }
            RecordPayDO recordPayDO=recordPayDOOptional.get();

            if(payConfig.getStartWeb()){
                //项目独立运行。通过http通知其他系统
                String url = recordPayDO.getNotifyUrl();
                Map<String, Object> parameter = new HashMap<>();
                parameter.put("outTradeNo", outTradeNo);
                parameter.put("orderPayNo", orderPayNo);
                String result = HttpUtil.post(url, parameter);
                if("success".equals(result)){
                    recordPayDO.setNotityStatus(ERecordPayNotityStatus.YES);
                    payDBTunnel.save(recordPayDO);
                }
                logger.info("异步通知完成，返回：[{}]",result);
                return new AsyncResult<String>("success");
            }else{
                //TODO 内部集成
                return new AsyncResult<String>("fail");
            }

        } catch (Exception e) {
            logger.error("异步错误，",e);
            return new AsyncResult<String>("fail");
        }
    }
}
