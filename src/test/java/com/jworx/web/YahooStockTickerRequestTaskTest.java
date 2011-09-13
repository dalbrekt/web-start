package com.jworx.web;

import java.io.IOException;
import java.util.Timer;

import org.junit.Test;

public class YahooStockTickerRequestTaskTest {

    @Test
    public void testExecuteRequest() throws IOException, InterruptedException {
        YahooStockTickerRequestTask q = new YahooStockTickerRequestTask("BBDB.TO+NT.TO+GE+MSFT+HM-B.ST+RATO-B.ST");

        Timer timer = new Timer(true);
        timer.schedule(q, 0, 2000);

        Thread.sleep(10000L);
    }
}
