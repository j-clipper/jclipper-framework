package jclipper.common.utils;

import jclipper.common.base.HeaderParams;
import jclipper.common.enums.DeviceType;
import jclipper.common.enums.HeaderEnum;
import jclipper.common.enums.IDeviceType;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/20 15:05.
 */
public class HttpUtils {

    public static final String HEADER_AUTH_KEY = "Authorization";
    public static final String HEADER_AUTH_KEY_SECOND = "token";
    public static final String TRACE_ID_KEY = "X-Trace-Id";
    public final static Gson GSON = new Gson();


    /**
     * 获取登录用户远程主机ip地址
     *
     * @return ip address
     */
    public static String getIpAddress() {

        HttpServletRequest request = httpServletRequest();

        return getIpAddress(request);
    }


    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @return
     */
    public static String getClientIp() {
        return getIpAddress(httpServletRequest());
    }

    /**
     * 获取设备类型,会先通过 从header头中取device字段来获取，如果无法确认，则再通过User-Agent来判断
     */
    public static IDeviceType getDeviceType() {
        return getDeviceType(httpServletRequest());
    }

    /**
     * 获取设备类型,会先通过 从header头中取device字段来获取，如果无法确认，则再通过User-Agent来判断
     *
     * @param request
     * @return
     */
    public static IDeviceType getDeviceType(HttpServletRequest request) {
        if (request == null) {
            return DeviceType.UNKNOWN;
        }
        String device = null;
        try {
            device = StringUtils.trim(request.getParameter(IDeviceType.DEVICE));
        } catch (Exception e) {
            return DeviceType.UNKNOWN;
        }
        if (!Strings.isNullOrEmpty(device)) {
            if (IDeviceType.DEVICE_MOBILE.equalsIgnoreCase(device)) {
                return DeviceType.MOBILE;
            }
            if (IDeviceType.DEVICE_MOBILE_ANDROID.equalsIgnoreCase(device)) {
                return DeviceType.ANDROID;
            }
            if (IDeviceType.DEVICE_MOBILE_IOS.equalsIgnoreCase(device)) {
                return DeviceType.IOS;
            }
        }

        return getDeviceTypeByDefault(request);
    }

    /**
     * 通过User-Agent获取设备类型
     *
     * @param request
     * @return
     */
    public static DeviceType getDeviceTypeByDefault(HttpServletRequest request) {
        if (request == null) {
            return DeviceType.UNKNOWN;
        }
        String agentString;
        try {
            agentString = request.getHeader("User-Agent");
        } catch (Exception e) {
            return DeviceType.UNKNOWN;
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(agentString);
        OperatingSystem operatingSystem = userAgent.getOperatingSystem(); // 操作系统信息
        eu.bitwalker.useragentutils.DeviceType deviceType = operatingSystem.getDeviceType(); // 设备类型

        switch (deviceType) {
            case COMPUTER:
                return DeviceType.WEB;
            case TABLET: {
                if (agentString.contains("Android")) {
                    return DeviceType.ANDROID;
                }
                if (agentString.contains("iOS")) {
                    return DeviceType.IOS;
                }
                return DeviceType.UNKNOWN;
            }
            case MOBILE: {
                if (agentString.contains("Android")) {
                    return DeviceType.ANDROID;
                }
                if (agentString.contains("iOS")) {
                    return DeviceType.IOS;
                }
                return DeviceType.MOBILE;
            }
            default:
                return DeviceType.UNKNOWN;
        }

    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     *
     * @param request http request
     * @return ip address
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        try {

            String ip = request.getHeader("X-Forwarded-For");

            if (invalidIp(ip)) {
                if (invalidIp(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (invalidIp(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (invalidIp(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (invalidIp(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (invalidIp(ip)) {
                    ip = request.getRemoteAddr();
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = ips[index];
                    if (!("unknown".equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
            return ip;
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean invalidIp(String ip) {
        return null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
    }

    /**
     * 获取 http servlet request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest httpServletRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes.getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置 authorization header
     *
     * @param accessToken 帐号校验token
     */
    public static void setAuthorization(String accessToken) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletResponse response = attributes.getResponse();
        response.setHeader(HEADER_AUTH_KEY, accessToken);
    }

    /**
     * 获取 access token,获取顺序：
     * 1、先从 request header 中 获取 Authorization 的值；
     * 2、如果不存在则从 Cookie 中获取 token 的值；
     * 3、如果不存在则从 url parameter 中获取 token 的值；
     *
     * @param request HttpServletRequest
     * @return String
     */

    public static String getAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTH_KEY);
        if (token != null) {
            return token;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(HEADER_AUTH_KEY_SECOND)).findFirst().orElse(null);
            if (cookie != null) {
                return cookie.getValue();
            }
        }
        return request.getParameter(HEADER_AUTH_KEY_SECOND);
    }

    /**
     * 获取 access token
     *
     * @return String
     */
    public static String getAccessToken() {

        HttpServletRequest request = httpServletRequest();

        return getAccessToken(request);
    }

    /**
     * header 参数
     *
     * @param request HttpServletRequest
     * @return Map
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {

        Map<String, String> headerMap = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }

        return headerMap;
    }

    public static HeaderParams getHeaderParams() {

        HttpServletRequest request = httpServletRequest();

        return getHeaderParams(request);
    }

    public static HeaderParams getHeaderParams(HttpServletRequest request) {

        HeaderParams params = new HeaderParams();

        String clientIp = request.getHeader(HeaderEnum.X_CLIENT_IP.getName());
        String authorization = request.getHeader(HeaderEnum.AUTHORIZATION.getName());
        String deviceName = request.getHeader(HeaderEnum.DEVICE_TYPE.getName());
        String deviceToken = request.getHeader(HeaderEnum.DEVICE_TOKEN.getName());

        params.setXClientIp(null == clientIp ? "" : clientIp);
        params.setAuthorization(null == authorization ? "" : authorization);
        params.setXDeviceType(null == deviceName ? "" : deviceName);
        params.setXDeviceToken(null == deviceToken ? "" : deviceToken);

        return params;
    }


    /**
     * 编码
     *
     * @param text
     * @return
     */
    public static String encode(String text) {
        if (text == null) {
            return null;
        }
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 解码
     *
     * @param text
     * @return
     */
    public static String decode(String text) {
        if (text == null) {
            return null;
        }
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
