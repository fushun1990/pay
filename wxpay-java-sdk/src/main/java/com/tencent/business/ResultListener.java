package com.tencent.business;

import com.tencent.protocol.base_protocol.BaseResData;

public interface ResultListener<T extends BaseResData> {


    /**
     * API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法
     *
     * @param unifiedorderResData
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    void onFail(T t);

    /**
     * api返回空
     *
     * @param response
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    void onFail(String response);


    /**
     * 调用成功
     *
     * @param unifiedorderResData
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    void onSuccess(T t);

    /**
     * 获取返回对象
     *
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    T getResData();

    /**
     * 返回签名验证失败
     *
     * @param t
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    void onFailBySignInvalid(T t);
}
