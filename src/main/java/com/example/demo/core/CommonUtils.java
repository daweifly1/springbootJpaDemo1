package com.example.demo.core;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author f00lish
 * @version 2018/5/6
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
@Slf4j
public class CommonUtils {

    private static String injector;
    private static boolean isLogicDelete;

    /**
     * 直接使用response向浏览器写json
     *
     * @param responseMessage
     */
    public static void responseJson(Object responseMessage) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            String json = JsonUtil.beanToJson(responseMessage);
            out = response.getWriter();
            out.write(json);    //写给response
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 全局获取httpRequest
     *
     * @return
     */

    public static HttpServletRequest getHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null)
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        return null;
    }

    /**
     * 全局获取httpHeader
     *
     * @return
     */

    public static String getRequestHeader(String header) {
        HttpServletRequest request = getHttpRequest();
        if (request != null)
            return request.getHeader(header);
        return null;
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            String[] ips = ip.split(",");
            if (ips.length > 0)
                ip = ips[0];
        }
        return ip;
    }


    /**
     * 将驼峰命名格式转为下划线格式
     *
     * @param value
     * @return
     */
    public static String toUnderLineName(String value) {
        if (StringUtils.isEmpty(value))
            return "";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            String s = value.substring(i, i + 1);
            // 在大写字母前添加下划线
            if (s.equals(s.toUpperCase())
                    && !Character.isDigit(s.charAt(0)) && !"_".equals(s)) {
                result.append("_");
                s = s.toLowerCase();
            }
            result.append(s);
        }
        return result.toString();
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线方式命名的字符串为空，则返回空字符串。</br>
     *
     * @param name 转换前的下划线方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 获取异常栈中最终的异常
     *
     * @param e
     * @return
     */
    public static Throwable loadCause(Throwable e) {
        Throwable result = e;
        while (result.getCause() != null) {
            result = result.getCause();
        }
        return result;
    }

    /**
     * 获取异常栈中最终的异常信息
     *
     * @param e
     * @return
     */
    public static String loadCauseMsg(Throwable e) {
        String msg = loadCause(e).getMessage();
        return msg != null ? msg : e.getMessage();
    }

}
