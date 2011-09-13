package com.jworx.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

public class YahooStockTickerRequestTask extends TimerTask {

    private static final String URL_TEMPLATE = "http://download.finance.yahoo.com/d/quotes.csv?s=%s&f=%s";

    private static final String FIELDS = "snl1d1t1hgr";

    private final String url;

    private final List<HttpResponseListener> listeners = new ArrayList<HttpResponseListener>();

    public YahooStockTickerRequestTask(String symbols) {
        this.url = String.format(URL_TEMPLATE, symbols, FIELDS);
    }

    public void executeRequest() throws IOException {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.prepareGet(url).execute(new AsyncCompletionHandler<Response>() {

            @Override
            public Response onCompleted(Response response) throws Exception {
                notifyListeners(new StockQuotesConverter(response.getResponseBody()).toJson());
                return response;
            }

            @Override
            public void onThrowable(Throwable t) {
                throw new RuntimeException("HttpRequest failed", t);
            }
        });
    }

    @Override
    public void run() {
        try {
            executeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHttpResponseListener(HttpResponseListener listener) {
        listeners.add(listener);
    }

    public void removeHttpResponseListener(HttpResponseListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(String response) {
        for (HttpResponseListener listener : listeners) {
            listener.responseReceived(response);
        }
    }
    
    public String getUrl() {
        return url;
    }
}
