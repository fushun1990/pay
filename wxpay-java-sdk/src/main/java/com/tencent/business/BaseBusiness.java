package com.tencent.business;

import com.tencent.common.Signature;
import com.tencent.protocol.base_protocol.BaseReqData;
import com.tencent.protocol.base_protocol.BaseResData;

import java.util.Map;

public abstract class BaseBusiness<T extends BaseReqData, S extends BaseResData> {

    private void setSign(T t) {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = Signature.getSignMap((Class<T>) t.getClass(), t);
        t.setSign(Signature.getMD5Sign(map, t.getConfigure()));
    }

    protected abstract void execute(T t, ResultListener<S> resultListener) throws Exception;

    public void run(T t, ResultListener<S> resultListener) throws Exception {
        setSign(t);
        execute(t, resultListener);
    }
}
