package com.tencent.common;

/**
 * 微信App支付对象
 *
 * @author fushun
 * @version dev706
 * @creation 2017年5月31日
 */
public class AppCConfigure extends Configure {

    private static AppCConfigure appCConfigure = null;

    /**
     * 创建单利对象
     */
    static {
        appCConfigure = new AppCConfigure();
    }

    /**
     * 私有化默认无参初始化方法
     */
    private AppCConfigure() {
    }

    /**
     * spring 工厂初始化方法
     *
     * @return
     * @author fushun
     * @version dev706
     * @creation 2017年5月31日
     * @records <p>  fushun 2017年5月31日</p>
     */
    public static AppCConfigure initMethod() {
        return appCConfigure;
    }

    ;

    @Override
    public Configure getConfigure() {
        return appCConfigure;
    }


}
