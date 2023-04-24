package net.wvffle.rsi.services;

import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.MTOM;
import javax.xml.ws.soap.SOAPBinding;

import java.io.File;
import java.io.IOException;

@MTOM
@WebService
@BindingType(value = SOAPBinding.SOAP11HTTP_BINDING)
public class Image {
    @WebMethod
    public String hello(String text) {
        return String.format("Hello %s", text);
    }
    
    @WebMethod
    public java.awt.Image getImage(String name) {
        try {
            File file = new File(System.getProperty("user.home", ""), "/workspace/" + name);
            System.out.println("Reading file: " + file.getAbsolutePath());
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_RGB);
        }
    }
}
