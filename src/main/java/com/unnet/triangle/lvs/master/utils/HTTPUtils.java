package com.unnet.triangle.lvs.master.utils;

import java.io.ByteArrayOutputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 * 修改 HTTPUtils , 提升效率 @2017-05-11
 * 
 * @author ricl
 * @date 2016年10月23日
 * @date 2017年8月16日 更新
 */
public class HTTPUtils {

    private static int HTTP_STATUS_OK = 200;

    /**
     * 如果返回码位200，成功，否则失败
     * 
     * @param uri
     * @param needResp 成功的情況下, 如果需要返回值(true), 那麼返回HTTP Response 內容, 否則返回""
     * @return
     */
    public static String get(String uri, boolean needResp) {
        GetMethod getMethod = null;
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(200);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(2000);

            getMethod = new GetMethod(uri);
            int retCode = httpClient.executeMethod(getMethod);
            if (HTTP_STATUS_OK != retCode) {
                throw new Exception("retcode isn't 200 [" + retCode + "]");
            }
            if (needResp) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i = -1;
                while ((i = getMethod.getResponseBodyAsStream().read()) != -1) {
                    baos.write(i);
                }
                return baos.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (null != getMethod) {
                getMethod.releaseConnection();
            }
        }

    }

    public static String post(String uri, String body, NameValuePair[] headers, boolean needResp) {
        PostMethod postMethod = null;
        try {

            HttpClient httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(200);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(2000);

            postMethod = new PostMethod(uri);
            for (NameValuePair header : headers) {
                postMethod.setRequestHeader(header.getName(), header.getValue());
            }

            StringRequestEntity requestEntity = new StringRequestEntity(body, "application/json", "UTF-8");

            // 把参数值放入postMethod中
            postMethod.setRequestEntity(requestEntity);
            // 执行
            int retCode = httpClient.executeMethod(postMethod);

            if (HTTP_STATUS_OK != retCode) {
                throw new Exception("response code not 200 " + retCode);
            }
            if (needResp) {
                String resp = postMethod.getResponseBodyAsString();
                return resp;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != postMethod) {
                postMethod.releaseConnection();
            }
        }

    }

}
