package com.jworx.web;

import java.io.IOException;
import java.util.Timer;

import org.eclipse.jetty.websocket.WebSocket;

import com.jworx.logfacade.Log;
import com.jworx.logfacade.LogFactory;

public class StockQuotesSocket implements WebSocket, HttpResponseListener {
    private Log log = LogFactory.getLog(StockQuotesSocket.class);

    public static final int REQUEST_INTERVAL = 3000;

    public static int threadNo = 1;

    private Timer timer;

    private Outbound outbound;

    private static String symbols = "BBDB.TO+NT.TO+GE+MSFT+HM-B.ST+RATO-B.ST";

    public void onConnect(Outbound outbound) {
        this.outbound = outbound;
        timer = new Timer(true);
    }

    public void onDisconnect() {
        outbound.disconnect();
        timer.cancel();
        log.debug("Client disconnected");
    }

    public void onFragment(boolean arg0, byte arg1, byte[] arg2, int arg3, int arg4) {

    }

    public void onMessage(byte arg0, String data) {
        if (data.indexOf("disconnect") >= 0) {
            log.debug("Client requesting to disconnect.");
            outbound.disconnect();
        } else {
            timer = new Timer("T-Quote[" + threadNo++ + "]", true);
            YahooStockTickerRequestTask service = new YahooStockTickerRequestTask(symbols);
            service.addHttpResponseListener(this);
            timer.schedule(service, 0, REQUEST_INTERVAL);
        }
    }

    public void onMessage(byte arg0, byte[] arg1, int arg2, int arg3) {

    }

    public void responseReceived(String response) {
        try {
            outbound.sendMessage(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
