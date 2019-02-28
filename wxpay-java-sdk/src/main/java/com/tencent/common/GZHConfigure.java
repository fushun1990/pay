package com.tencent.common;

/**
 * 公众号支付配置对象
 *
 * @author fushun
 * @version dev706
 * @creation 2017年5月31日
 */
public class GZHConfigure extends Configure {

    private static GZHConfigure GZHConfigure = null;

    /**
     * 创建单利对象
     */
    static {
        GZHConfigure = new GZHConfigure();
    }

    /**
     * 私有化默认无参初始化方法
     */
    private GZHConfigure() {
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
    public static GZHConfigure initMethod() {
        return GZHConfigure;
    }

    ;

    @Override
    public Configure getConfigure() {
        return GZHConfigure;
    }

}
