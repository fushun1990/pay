package test;

import com.fushun.framework.util.util.JsonUtil;
import com.tencent.common.AppCConfigure;
import com.tencent.common.GZHConfigure;
import com.tencent.common.HttpsRequest;
import com.tencent.protocol.base_protocol.BaseReqData;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2017/6/13.
 */
public class TestHttpClient {

//    @Test
    public void testHttpsRequest() throws InterruptedException {

        Class class1 = HttpsRequest.class;
        Method format = null;
        Field field = null;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            format = class1.getDeclaredMethod("getHttpClient", BaseReqData.class);

            field = class1.getDeclaredField("httpClientMap");
            format.setAccessible(true);//设为可见
            field.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 返回对象
         */
        class Result {
            private String result = null;

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }
        }

        /**
         * 执行完成 之后结果检查方法
         */
        class CyclicBarrierRunnable implements Runnable {

            private Field field;
            private Result result;

            public CyclicBarrierRunnable(Field field, Result result) {
                this.field = field;
                this.result = result;
            }

            @Override
            public void run() {
                try {
                    Set<Class<?>> set = ((Map) field.get(HttpsRequest.class)).keySet();
                    List<String> list = new ArrayList<String>();
                    for (Class<?> cls : set) {
                        if (cls == null) {
                            continue;
                        }
                        list.add(cls.toString());
                    }
                    Arrays.sort(list.toArray());
                    List<String> list2 = new ArrayList<String>();
                    list2.add(null);
                    list2.addAll(list);
                    result.setResult(JsonUtil.classToJson(list2));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        }

        Result result = new Result();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new CyclicBarrierRunnable(field, result));

        //检查方法

        new Thread(new MyRunable(format, null, cyclicBarrier)).start();

        BaseReqData baseReqData = new BaseReqData(null, GZHConfigure.initMethod());
        new Thread(new MyRunable(format, baseReqData, cyclicBarrier)).start();

        baseReqData = new BaseReqData(null, AppCConfigure.initMethod());
        new Thread(new MyRunable(format, baseReqData, cyclicBarrier)).start();


        countDownLatch.await();
        Assert.assertEquals("[null,\"class com.tencent.common.AppCConfigure\",\"class com.tencent.common.GZHConfigure\"]", result.getResult());
    }

    /**
     * 初始化 对象
     */
    class MyRunable implements Runnable {

        private Method format;
        private BaseReqData baseReqData;
        private CyclicBarrier cyclicBarrier;

        public MyRunable(Method format, BaseReqData baseReqData, CyclicBarrier cyclicBarrier) {
            this.format = format;
            this.baseReqData = baseReqData;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    CloseableHttpClient httpClient = (CloseableHttpClient) format.invoke(HttpsRequest.class, this.baseReqData);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
