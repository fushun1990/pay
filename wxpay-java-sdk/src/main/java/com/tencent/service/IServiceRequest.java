package com.tencent.service;

import com.tencent.protocol.base_protocol.BaseReqData;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * User: rizenguo
 * Date: 2014/12/10
 * Time: 15:16
 * 这里定义服务层需要请求器标准接口
 */
public interface IServiceRequest {

    /**
     * Service依赖的底层https请求器必须实现这么一个接口
     *
     * @param api_url
     * @param xmlObj
     * @return
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     * @records <p>  fushun 2017年1月6日</p>
     */
    public <T extends BaseReqData> String sendPost(String api_url, T xmlObj) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;


    /**
     * 执行get请求
     *
     * @param api_url
     * @return
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     * @records <p>  fushun 2017年1月6日</p>
     */
    public String sendGet(String api_url) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;
}
