package com.unnet.triangle.lvs.master.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;

public class PostJsontDataThread extends Thread {
  private JSONObject object;
  private String notifyApiAddr;

  public PostJsontDataThread(JSONObject obj, String notifyApiAddr) {
    this.object = obj;
    this.notifyApiAddr = notifyApiAddr;
  }

  public String httpPostWithJSON(JSONObject jsonData) {

    HttpPost httpPost = new HttpPost(this.notifyApiAddr);

    String respContent = null;

    // 设置代理
    // HttpHost proxy = new HttpHost("133.37.54.2", 808);
    // RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
    // httpPost.setConfig(config);

    try {
      CloseableHttpClient client = HttpClients.createDefault();
      StringEntity entity = new StringEntity(jsonData.toString(), "utf-8");
      entity.setContentType("application/json");
      httpPost.setEntity(entity);

      HttpResponse resp = client.execute(httpPost);
      if (resp.getStatusLine().getStatusCode() == 200) {
        HttpEntity he = resp.getEntity();
        respContent = EntityUtils.toString(he, "UTF-8");
      }
    } catch (Exception e) {
      return null;
    }
    return respContent;
  }

  public void run() {
    System.out.println(object.toJSONString());
    String s = httpPostWithJSON(this.object);
    System.out.println(s);
  }
}
