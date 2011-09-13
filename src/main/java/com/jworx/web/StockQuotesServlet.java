package com.jworx.web;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class StockQuotesServlet extends WebSocketServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected WebSocket doWebSocketConnect(HttpServletRequest request, String arg) {
        return new StockQuotesSocket();
    }
}
