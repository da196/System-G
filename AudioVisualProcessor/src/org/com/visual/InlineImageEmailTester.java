package org.com.visual;


import java.util.HashMap;
import java.util.Map;

/**
* This program tests out the EmbeddedImageEmailUtil utility class.
* @author www.codejava.net
*
*/
public class InlineImageEmailTester {

   /**
    * main entry of the program
    */
   public static void main(String[] args) {
       // SMTP info
       String host = "mail.tcra.go.tz";
       String port = "25";
       String mailFrom = "otas";
       String password = "m@w@s1l1@n0";
       
       
       //style="width:60px; height:50px; float:left;"

       // message info
       String mailTo = "albaloush88@gmail.com";
       String subject = "Test e-mail with inline images";
       StringBuffer body = new StringBuffer("<html><center><img src=\"cid:image1\" style=\"width:60px; height:50px;\" /><br>");
       body.append("THE UNITED REPUBLIC OF TANZANIA<br>");
       body.append("TANZANIA COMMUNICATIONS REGULATORY AUTHORITY<br>");
       body.append("ISO 9001:2015 CERTIFIED<br>");
       body.append("Message body:<br>");
      // body.append("<img src=\"cid:image2\" width=\"15%\" height=\"15%\" /><br>");
       body.append("End of message.");
       body.append("</center></html>");

       // inline images
       Map<String, String> inlineImages = new HashMap<String, String>();
       inlineImages.put("image1", "D:\\test\\tcra2.png");
      // inlineImages.put("image2", "D:\\test\\image2.png");

       try {
           EmbeddedImageEmailUtil.send(host, port, mailFrom, password, mailTo,
               subject, body.toString(), inlineImages);
           System.out.println("Email sent.");
       } catch (Exception ex) {
           System.out.println("Could not send email.");
           ex.printStackTrace();
       }
   }
}