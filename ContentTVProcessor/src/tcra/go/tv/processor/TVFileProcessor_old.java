package tcra.go.tv.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.FilenameUtils;

public class TVFileProcessor_old {
	
	private static File errorLogs ;
	private static String errorLogFile; 
	
	public TVFileProcessor_old() {
		
	}
	
	public static void processTVFiles() {
		
		String destinationFilePath = null;
		String channelName = null;
		String channelID = null;
		double FileSizeInKB = 0;
 		String lastModifiedDate = null;
 		FileTime fileTime;
		
		System.out.println("New TV file checking routine started at: " + new java.util.Date() + "\n");
		// Get the source file from the database
		String sourceFilePath = Utility.getFilePath(2);
		
		// Get the list of channel and URL Path
		List<Channels> channelList = Channels.getChannelList();
	
		for(int i=0; i < channelList.size(); i++) {
			channelName = channelList.get(i).getAbbreviation();
			channelID = channelList.get(i).getChannelID();
			destinationFilePath = channelList.get(i).getUrlPath();
		
	      //Count number of files in incoming files directory (TV files from the rawfiles)
	        File incomingFilesDirectory = new File(sourceFilePath); 
	        List<String> listOfIncomingFiles = Utility.listDirectory(incomingFilesDirectory, 0); 
	        String str2 = listOfIncomingFiles.size() + " TV files found! \n";
	        System.out.println(str2); 
	        
	        if (listOfIncomingFiles.size() == 0) {
	        	System.out.println("No File Found in the directory");
	        }else {
	        for (int fileNumber = 0; fileNumber < listOfIncomingFiles.size(); fileNumber++) {
	    		
	    		String str3 = "------------------------------------------------------------------------------\n";
	            System.out.println(str3);		
	    	    String fileName = Paths.get(listOfIncomingFiles.get(fileNumber)).getFileName().toString();
	    		String stringSourceFilePath = sourceFilePath + fileName;
	    		String stringDestinationFilePath = destinationFilePath + fileName;

	    		//Create file paths from string paths
	    		Path SourceFilePath = Paths.get(stringSourceFilePath);
	    		Path DestinationFilePath = Paths.get(stringDestinationFilePath);
	    		
	    		//Get file size and lastModifiedDate from the origional file
	    		
	    		try {
	    			FileSizeInKB = Files.size(SourceFilePath)/1024;
	    			fileTime = Files.getLastModifiedTime(SourceFilePath);
	    			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                lastModifiedDate = df.format(fileTime.toMillis());

	    		} catch (IOException fileSizeException) {
	    			fileSizeException.printStackTrace();
	    			//ErrorLogsAppender.appendIOException(fileSizeException, vodacomErrorLog);	
	    			continue;
	    		}
	    		
	    		List<String> list = Utility.split(lastModifiedDate);
	    		String date = list.get(0);
	    		String time = list.get(1);
	    		
	    		String fileExtension = FilenameUtils.getExtension(fileName);
	    		Long fileSerialNo = Utility.getFileSerialNo(channelID);
	    		String fileRename = Utility.renameFile(fileName, stringSourceFilePath, channelName, channelID);
	    		
	    		if(fileName.toUpperCase().contains(channelName)) {
	    			try {
						Files.move(SourceFilePath, DestinationFilePath);
					} catch (IOException e) {
						e.printStackTrace();
					} 
	    			
	    			System.out.println(fileName + " --> " + fileRename + " --> " +  FileSizeInKB + " --> " + date + " --> " + time + " --> " + 
    			    		destinationFilePath + fileName +  " --> " + channelID + " --> " + fileSerialNo);
	    			
	    			int status = insertFileMetadata(destinationFilePath + fileRename, Double.toString(FileSizeInKB),fileExtension,date, time,time, fileName,channelID,Long.toString(fileSerialNo));
	    			if (status == 1) {
	    				System.out.println(fileName + " --> " + fileRename + " --> " +  FileSizeInKB + " --> " + date + " --> " + time + " --> " + 
	    			    		destinationFilePath + fileName +  " --> " + channelID);
	    				System.out.println("Files Successfully Processed");
	    			}else {
	    				System.out.println("Error File is not inserted into database");
	    			}
	    			
	    		}
	    	
	    	}
		}
		}
	}
	
	private static int insertFileMetadata(String URLPathName, String fileSize, String fileType, String fileDate, String startTime, String endTime, 
			String fileName, String channelID, String FileSerialNo) {
		
		errorLogFile = Utility.getFilePath(1);
	 	errorLogs = new File(errorLogFile);
		int status = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatementSerial = null;
        String query = "INSERT INTO tcms_tv.file_metadata (file_path_file_name,file_size,file_type,file_date,start_time,end_time,chanel_id,file_name) VALUES (?,?,?,?,?,?,?,?);";
        String querySerial = "INSERT INTO configurations.fileserial (serial_number,channel_id) VALUES (?,?);";
        
	try {
		connection = PostgresDBConnection.connect();
		connection.setAutoCommit(false);
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, URLPathName);
		preparedStatement.setString(2, fileSize);
		preparedStatement.setString(3, fileType);
		preparedStatement.setString(4, fileDate);
		preparedStatement.setString(5, startTime);
		preparedStatement.setString(6, endTime);
		preparedStatement.setLong(7, Long.parseLong(channelID));
		preparedStatement.setString(8, fileName);
		Long check = (long) preparedStatement.executeUpdate();
		
		preparedStatementSerial = connection.prepareStatement(querySerial);
		preparedStatementSerial.setLong(1,Long.parseLong(FileSerialNo));
		preparedStatementSerial.setLong(2,Long.parseLong(channelID));
		Long checkSerial = (long) preparedStatementSerial.executeUpdate();
				
	    connection.commit();
		
		if (check > 0 && checkSerial > 0) {
		  status =  1;
		 System.out.println("IMEPITA");
		} 
      } catch(SQLException ex) {
    	  ex.printStackTrace();
    	  ErrorLogsAppender.appendSQLException("TVFileProcessor class - insertFileMetadata()",ex, errorLogs);
		   try {
		   connection.rollback();
		   } catch (SQLException ex1) {
			ErrorLogsAppender.appendSQLException("TVFileProcessor class - insertFileMetadata()",ex1, errorLogs);
		   }
	} finally {
		try {
		if (preparedStatement != null) {
			preparedStatement.close();
		}if (preparedStatementSerial != null) {
			preparedStatementSerial.close();
		}
		if (connection != null) {
			connection.close();
		}
		} catch (SQLException ex) {
			 ex.printStackTrace();
		ErrorLogsAppender.appendSQLException("TVFileProcessor class - addChannels()",ex, errorLogs);
		}
		}	
	   return status;	
	}
}
