package com.als.framework.filter;

import com.als.framework.protocol.ALSProtocolHandler;
import com.als.framework.servletapi.ProxyRequestHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lvtu on 2017/6/27.
 */
public class ALSFilter implements Filter {

    private ALSProtocolHandler protocolHandler = new ALSProtocolHandler();

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (protocolHandler.isHandshakePhase((HttpServletRequest) request)) {
            protocolHandler.processHandshake((HttpServletRequest) request, (HttpServletResponse) response);
            return;
        }
        if (!needDecrypt((HttpServletRequest) request)) {
            chain.doFilter(request, response);
            return;
        }

        ProxyRequestHolder holder = protocolHandler.processRequest((HttpServletRequest) request, (HttpServletResponse) response);
        if (holder != null) {
            chain.doFilter(holder.getAlsHttpServletRequestWrapper(), holder.getAlsHttpServletResponseWrapper());
        }
        holder.outputToSocket();
    }

    private boolean needDecrypt(HttpServletRequest request) {
        String header = request.getHeader("x-als-encrypt");
        return "true".equalsIgnoreCase(header);
    }

    public void destroy() {

    }
}
