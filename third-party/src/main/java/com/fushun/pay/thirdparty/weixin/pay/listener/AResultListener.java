package com.fushun.pay.thirdparty.weixin.pay.listener;

import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.domain.exception.PayException;
import com.tencent.business.ResultListener;
import com.tencent.protocol.base_protocol.BaseResData;

/**
 * 微信 接口 ，监听接口
 *
 * @param <T>
 * @author fushun
 * @version dev706
 * @creation 2017年6月1日
 */
public abstract class AResultListener<T extends BaseResData> implements ResultListener<T> {

    @Override
    public void onFail(T t) {
        throw new PayException(PayException.PayExceptionEnum.PAY_FAILED,"接口调用错误：" + JsonUtil.classToJson(t));
    }

    @Override
    public void onFail(String response) {
        throw new PayException(PayException.PayExceptionEnum.PAY_FAILED,"接口调用错误：" + response);
    }


    @Override
    public void onFailBySignInvalid(T t) {
        throw new PayException(PayException.PayExceptionEnum.PAY_FAILED,"签名错误:" + JsonUtil.classToJson(t));
    }

    @Override
    public abstract T getResData();


}
