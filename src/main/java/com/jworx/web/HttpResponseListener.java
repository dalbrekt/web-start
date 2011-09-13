package com.jworx.web;

import java.util.EventListener;

public interface HttpResponseListener extends EventListener {
    
    void responseReceived(String response);

}
