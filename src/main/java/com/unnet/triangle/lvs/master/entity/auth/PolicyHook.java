package com.unnet.triangle.lvs.master.entity.auth;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class PolicyHook {

	public String path;
	public String method;
	public String info;
	public String username;
	public String service;
	public String gid;

	public PolicyHook(String path, String method, String info, String username, String service, String gid) {
		this.path = path;
		this.method = method;
		this.info = info;
		this.username = username;
		this.gid = gid;
	}

	public String getPath() {
		return path;
	}

	public String getMethod() {
		return method;
	}

	public String getInfo() {
		return info;
	}

	public String getUsername() {
		return username;
	}

	public String getService() {
		return service;
	}

	public String getGid() {
		return gid;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public static String post(String uri, String body, NameValuePair[] headers, boolean needResp) throws Exception{
		PostMethod postMethod = null;
		try {

			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(200);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(2000);

			postMethod = new PostMethod(uri);
			if (null != headers) {
				for (NameValuePair header : headers) {
					postMethod.setRequestHeader(header.getName(), header.getValue());
				}
			}

			StringRequestEntity requestEntity = new StringRequestEntity(body, "application/json", "UTF-8");

			// 把参数值放入postMethod中
			postMethod.setRequestEntity(requestEntity);
			// 执行
			int retCode = httpClient.executeMethod(postMethod);
			System.out.println("code: " + retCode);
			if (HttpStatus.SC_OK != retCode) {
				throw new Exception("response code not 200 " + retCode);
			}
			if (needResp) {
				String resp = postMethod.getResponseBodyAsString();
				return resp;
			} else {
				return "";
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != postMethod) {
				postMethod.releaseConnection();
			}
		}
	}
}
