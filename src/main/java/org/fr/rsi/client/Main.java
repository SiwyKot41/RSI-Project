package org.fr.rsi.client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.bind.DatatypeConverter;
import javax.xml.ws.soap.MTOMFeature;

import org.fr.rsi.client.generated.Image;
import org.fr.rsi.client.generated.ImageService;

public class Main {
    public static void main(String[] args) {
        ImageService service  = new ImageService();

        Image port = service.getImagePort(new MTOMFeature());
        byte[] imageBytes = port.getImage("shrek.jpg");

        try {
            JFrame frame = new JFrame();
            frame.setSize(300, 300);
            JLabel label = new JLabel(new ImageIcon(imageBytes));
            frame.add(label);
            frame.setVisible(true);
        } catch (Exception | java.awt.AWTError e) {
            System.out.println("Cannot spawn a new window. Downloading file instead.");
            saveImage(imageBytes);
        } finally {
            System.out.println("imageServer.downloadImage() : Download Successful");
        }
    }

    // Save the imageBytes as jpg file to the local disk
    public static void saveImage(byte[] imageBytes) {
        String imageString = DatatypeConverter.printBase64Binary(imageBytes);
        String imageStringWithHeader = "data:image/jpg;base64," + imageString;
        String imageStringWithoutHeader = imageStringWithHeader.substring(imageStringWithHeader.indexOf(",") + 1);
        byte[] imageBytesWithoutHeader = DatatypeConverter.parseBase64Binary(imageStringWithoutHeader);
        try {
            java.io.FileOutputStream fos = new java.io.FileOutputStream("image-from-wsdl.jpg");
            fos.write(imageBytesWithoutHeader);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}