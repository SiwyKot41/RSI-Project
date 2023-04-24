package net.wvffle.rsi.services;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService
public class Hello {
    @Resource
    WebServiceContext context;

    @WebMethod
    public String hello(String text) {
        return String.format("Hello %s", text);
    }
    
}
