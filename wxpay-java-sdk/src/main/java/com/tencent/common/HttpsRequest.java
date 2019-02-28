package com.tencent.common;

import com.tencent.protocol.base_protocol.BaseReqData;
import com.tencent.service.IServiceRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:36
 */
public class HttpsRequest implements IServiceRequest {

    private static Log log = new Log(LoggerFactory.getLogger(HttpsRequest.class));
    //表示请求器是否已经做了初始化工作
    private static boolean hasInit = false;
    //连接超时时间，默认10秒
    private static int socketTimeout = 10000;
    //传输超时时间，默认30秒
    private static int connectTimeout = 30000;
    //请求器的配置
    private static RequestConfig requestConfig;
    private static Map<Class<? extends Configure>, CloseableHttpClient> httpClientMap = new HashMap<Class<? extends Configure>, CloseableHttpClient>();

    static {
        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 初始化 httpClient
     *
     * @param t
     * @param <T>
     * @return
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static synchronized <T extends BaseReqData> CloseableHttpClient initHttpClient(T t) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();

        if (t != null && !(t.getConfigure().getCertLocalPath() == null || "".equals(t.getConfigure().getCertLocalPath()))) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream instream = t.getConfigure().getCertLocalPath().openStream();//加载本地的证书进行https加密传输
            try {
                keyStore.load(instream, t.getConfigure().getCertPassword().toCharArray());//设置证书密码
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } finally {
                instream.close();
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, t.getConfigure().getCertPassword().toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpClientBuilder.setSSLSocketFactory(sslsf);
        }
        //HTTP请求器
        CloseableHttpClient httpClient;

        httpClient = httpClientBuilder.build();
        if (t == null) {
            httpClientMap.put(null, httpClient);
        } else {
            httpClientMap.put(t.getConfigure().getClass(), httpClient);
        }

        return httpClient;
    }

    /**
     * 获取连接对象
     *
     * @param t
     * @param <T>
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static <T extends BaseReqData> CloseableHttpClient getHttpClient(T t) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        CloseableHttpClient httpClient = null;
        if (t == null || t.getConfigure() == null) {
            httpClient = httpClientMap.get(null);
        } else {
            httpClient = httpClientMap.get(t.getConfigure().getClass());
        }
        if (httpClient == null) {
            return initHttpClient(t);
        }
        return httpClient;
    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */

    public <T extends BaseReqData> String sendPost(String url, T xmlObj) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        CloseableHttpClient httpClient = getHttpClient(xmlObj);

        String result = null;

        HttpPost httpPost = new HttpPost(url);

        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //过滤不需要的属性
        xStreamForRequestPostData.omitField(BaseReqData.class, "configure");
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

        Util.log("API，POST过去的数据是：");
        Util.log(postDataXML);

        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        httpPost.setConfig(requestConfig);

        Util.log("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            log.e("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            log.e("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            log.e("http get throw SocketTimeoutException");

        } catch (Exception e) {
            log.e("http get throw Exception");

        } finally {
            httpPost.abort();
        }

        return result;
    }

    /**
     * 执行url
     *
     * @param url
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     * @records <p>  fushun 2017年1月6日</p>
     */
    public String sendGet(String url) throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, IOException {

        CloseableHttpClient httpClient = getHttpClient(null);
        String result = null;

        HttpGet httpGet = new HttpGet(url);


        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        httpGet.addHeader("Content-Type", "text/plain");
        httpGet.setConfig(requestConfig);
        //设置请求器的配置
        Util.log("executing request" + httpGet.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            log.e("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            log.e("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            log.e("http get throw SocketTimeoutException");

        } catch (Exception e) {
            log.e("http get throw Exception");

        } finally {
            httpGet.abort();
        }

        return result;
    }

    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认10秒
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认30秒
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig() {
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 允许商户自己做更高级更复杂的请求器配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public interface ResultListener {


        public void onConnectionPoolTimeoutError();

    }
}
