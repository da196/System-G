package tcra.go.tv.radio;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;


//import javax.mail.MessagingException;

//import org.apache.commons.fileupload.FileUploadException;

import java.io.File;

public class ErrorLogsAppender {

    private static FileWriter fileStream = null;
        private static BufferedWriter out = null;
        public static void appendException(String pageDescription, Exception errorMessage, File logFile) {
        try{
               fileStream = new FileWriter(logFile, true);
               out = new BufferedWriter(fileStream);
               out.write(pageDescription + " : " +errorMessage + " " + new java.util.Date()+"\n");
                   out.close();
                   fileStream.close();
        } catch (Exception ex){
              System.err.println("Error while writing to file: " +
          ex.getMessage());
        } finally {

                }
    }

    public static void appendSQLException(String pageDescription, SQLException errorMessage, File logFile) {
        FileWriter fileStream = null;
        BufferedWriter out = null;
           try{
              fileStream = new FileWriter(logFile, true);
              out = new BufferedWriter(fileStream);
              out.write(pageDescription + " : " +errorMessage + " " + new java.util.Date()+"\n");
              out.close();
              fileStream.close();
           } catch (Exception ex){
             System.err.println("Error while writing to file: " +
             ex.getMessage());
           } finally {
             
           }
   
       }

       public static void appendIOException(String pageDescription, IOException errorMessage, File string) {
        FileWriter fileStream = null;
        BufferedWriter out = null;
           try{
              fileStream = new FileWriter(string, true);
              out = new BufferedWriter(fileStream);
              out.write(pageDescription + " : " +errorMessage + " " + new java.util.Date()+"\n");
              out.close();
              fileStream.close();
           } catch (Exception ex){
             System.err.println("Error while writing to file: " +
             ex.getMessage());
           } finally {
             
           }
   
       }
             
       public static void appendNoSuchProviderException(String pageDescription, NoSuchProviderException errorMessage, File logFile) {

           FileWriter fileStream = null;
           BufferedWriter out = null;
              try{
                 fileStream = new FileWriter(logFile, true);
                 out = new BufferedWriter(fileStream);
                 out.write(pageDescription + " : " +errorMessage + " " + new java.util.Date()+"\n");
                 out.close();
                 fileStream.close();

              } catch (Exception ex){
                System.err.println("Error while writing to file: " +
                ex.getMessage());
              } finally {
              
              }

          }
       

}
