package com.unnet.triangle.lvs.master.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.lvs.master.property.ContainerProperties;
import com.unnet.triangle.utils.codec.HmacShaUtil;



@Configuration
public class AuthConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(AuthConfiguration.class);

  private static final String HMAC_SHA_256 = "HmacSHA256";

  private AntPathMatcher antPathMatcher = new AntPathMatcher();

  private List<String> excludeUrlPatterns = new ArrayList<>();
/*
  @Autowired
  private ContainerProperties containerProperties;

  @Bean
  public FilterRegistrationBean<AuthenticateFilter> filter4AuthorizationRegistration() {
    FilterRegistrationBean<AuthenticateFilter> registration = new FilterRegistrationBean<AuthenticateFilter>();
    // 注入过滤器
    registration.setFilter(new AuthenticateFilter());
    // 拦截规则
    registration.addUrlPatterns("/*");
    // 过滤器名称
    registration.setName("authenticateFilter");
    // 是否自动注册 false 取消Filter的自动注册
    registration.setEnabled(true);
    // 过滤器顺序
    registration.setOrder(1);
    return registration;
  }

  public class AuthenticateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      // v1 apis
      excludeUrlPatterns.add(Constants.V1_URI_API_PREFIX + "*" + "/alarm");
      excludeUrlPatterns.add(Constants.V1_URI_API_PREFIX + "*" + "/alarm/*");
      excludeUrlPatterns.add(Constants.V1_URI_API_PREFIX + "*" + "/monitor");
      excludeUrlPatterns.add(Constants.V1_URI_API_PREFIX + "*" + "/monitor/*");
      */
  /*
    //  excludeUrlPatterns.add(Constants.V1_URI_API_PREFIX + "*" + "/node/*/ //config");
      //excludeUrlPatterns.add(Constants.V1_URI_API_PREFIX + "*" + "/task/node");
     // excludeUrlPatterns.add(Constants.V1_URI_API_PREFIX + "*" + "/task/node/*");
      // root
    /*
      excludeUrlPatterns.add("/");
      // swagger
      excludeUrlPatterns.add("/swagger-ui.html");
      excludeUrlPatterns.add("/webjars/**");
      excludeUrlPatterns.add("/swagger-resources/**");
      excludeUrlPatterns.add("/v2/api-docs");

      // extra
      excludeUrlPatterns.add("/favicon.ico");
      logger.info("exclude uri patterns: ");
      for (String pattern : excludeUrlPatterns) {
        logger.info(pattern);
      }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      HttpServletResponse httpServletResponse = (HttpServletResponse) response;

      // logic debug mode
      if (containerProperties.isDebug()) {
        chain.doFilter(request, response);
        return;
      }

      // check exclude
      for (String pattern : excludeUrlPatterns) {
        if (antPathMatcher.match(pattern, httpServletRequest.getRequestURI())) {
          chain.doFilter(request, response);
          return;
        }
      }

      // check auth
      if (doAuth(httpServletRequest, httpServletResponse)) {
        chain.doFilter(request, response);
        return;
      } else {
        String failedMessage = httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI() + " auth failed";
        logger.info(failedMessage);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(failedMessage);
        httpServletResponse.getWriter().flush();
        return;
      }
    }

    @Override
    public void destroy() {}

    private boolean doAuth(HttpServletRequest request, HttpServletResponse response) {
      try {

        String requestSignature = request.getParameter("signature");
        String signatureMethod = request.getParameter("signatureMethod");

        String requestURI = request.getRequestURI();
        String secretKey = containerProperties.getSecretKey() == null || containerProperties.getSecretKey().equals("") ? Constants.DEFAULT_SECRET_KEY : containerProperties.getSecretKey();
        // 获得所有参数的名字
        Enumeration<String> names = request.getParameterNames();
        // 得到key集合
        Set<String> keySet = new HashSet<>();
        while (names.hasMoreElements()) {
          String key = names.nextElement();
          if (!"signature".equals(key) && !"signatureMethod".equals(key)) {
            keySet.add(key);
          }
        }
         参数表的key,按照字典顺序排序 
        // 无序变有序
        List<String> keyList = new ArrayList<>(keySet);
        // 排序
        Collections.sort(keyList);
        // 依次拼装
        StringBuffer charList = new StringBuffer();
        for (int i = 0; i < keyList.size(); i++) {
          String key = keyList.get(i);
          String value = request.getParameter(key);
          value = value.replace('_', '.');
          charList.append(key + "=" + value + "&");
        }
        if (charList.length() != 0) {
          charList.deleteCharAt(charList.length() - 1);
        }
        // 加入请求方法+请求路径+？+请求字符串
        StringBuffer charListHead = new StringBuffer();
        charListHead.append(request.getMethod());
        charListHead.append(requestURI);
        if (charList.length() != 0) {
          charListHead.append("?");
        }
        charList.insert(0, charListHead);
        // 生成签名串
        String signStr = "";
        if (HMAC_SHA_256.equals(signatureMethod)) {
          signStr = HmacShaUtil.signature(charList.toString(), secretKey, "UTF-8", "HmacSHA256");
        } else {
          signStr = HmacShaUtil.signature(charList.toString(), secretKey, "UTF-8", "HmacSHA1");
        }
        // 进行URL编码
        if (signStr.equals(requestSignature)) {
          return true;
        } else {
          return false;
        }
      } catch (InvalidKeyException e) {
        return false;
      } catch (NoSuchAlgorithmException e) {
        return false;
      } catch (UnsupportedEncodingException e) {
        return false;
      }
    }

  }*/
}
