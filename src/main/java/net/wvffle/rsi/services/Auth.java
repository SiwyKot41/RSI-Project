package net.wvffle.rsi.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import net.wvffle.rsi.errors.InvalidInputException;

@WebService
public class Auth {
    @Resource
    WebServiceContext context;

    @WebMethod
    public String hello() throws InvalidInputException {
        MessageContext messageContext = context.getMessageContext();
        
        try {
            @SuppressWarnings("unchecked")
            Map<String, List<String>> headers = (Map<String, List<String>>) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
            
            String username = headers.get("username").get(0);
            String password = headers.get("password").get(0);

            if (username.equals("admin") && password.equals("admin")) {
                return String.format("Hello %s", username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        throw new InvalidInputException("Invalid username or password", "Could not authenticate user");
    }
}
