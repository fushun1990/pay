package com.fushun.pay.thirdparty.sdk.weixinpay;

import com.tencent.common.Configure;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;

/**
 * 微信证书
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月17日
 */
public class CertLocalPathResourceBean {

    private Resource resource;

    private Configure configure;

    public CertLocalPathResourceBean(Configure configure, Resource resource) throws IOException {
        URL inputStream = resource.getURL();
        this.resource = resource;
        configure.setCertLocalPath(inputStream);

    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Configure getConfigure() {
        return configure;
    }

    public void setConfigure(Configure configure) {
        this.configure = configure;
    }


}
