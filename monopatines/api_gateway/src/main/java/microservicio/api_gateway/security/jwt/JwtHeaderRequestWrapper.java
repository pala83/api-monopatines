package microservicio.api_gateway.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class JwtHeaderRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders;

    public JwtHeaderRequestWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public void addHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet<>(customHeaders.keySet());
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            set.add(n);
        }
        return Collections.enumeration(set);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Set<String> values = new HashSet<>();
        if (customHeaders.containsKey(name)) {
            values.add(customHeaders.get(name));
        }
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaders(name);
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            values.add(n);
        }
        return Collections.enumeration(values);
    }
}