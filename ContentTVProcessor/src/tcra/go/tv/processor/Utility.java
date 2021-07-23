package tcra.go.tv.processor;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Utility {
	
	private static String errorLogsPath = null;
	private static File errorLogs ;
	private static String errorLogFile;
	
	public Utility() {
		
	}
	
	public static void utility(long id) {
		errorLogsPath = getFilePath(id);
	}
	
	public static String getErrorLogPath() {
		return errorLogsPath;
	}
	
	/*
	 * Get Error log URL and File
	 */
	public static String getFilePath(long id) {
		 	
		 	String logPath = null;
		    Connection connection = null;
		    Statement statement = null;
		    ResultSet resultSet = null;
		    String query="SELECT path_name FROM configurations.file_path  WHERE path_id=" + id;
			try {
		    connection = PostgresDBConnection.connect();
		    statement = connection.createStatement();
		    resultSet = statement.executeQuery(query);
		    while (resultSet.next()) {
		    	logPath = resultSet.getString("path_name");
		    }
		    } catch(SQLException ex) {
		      ex.printStackTrace();
		    }
		    finally {
			  try {
				if (connection != null) {
					connection.close();
				}
			  } catch (SQLException ex2) {
				  ex2.printStackTrace();
			  }
			}	
			return logPath; 
		  
	  }
	
	public static String formatDate(String inputDate, String inputDateFormat, String outputDateFormat) throws ParseException{
        Date date = new SimpleDateFormat(inputDateFormat).parse(inputDate);
    return new SimpleDateFormat(outputDateFormat).format(date);
   }
	
	//This method for Reading the file in directory manner(folder under folder all the CSV, TXT, TSV, CSV.sha1)
	public static List<String> listDirectory(File file, int level) {
			
			  List<String> lstFiles = new ArrayList<String>();
			
			  File[] firstLevelFiles = file.listFiles();
			  if (firstLevelFiles != null && firstLevelFiles.length > 0) {
			   for (File aFile : firstLevelFiles) {
			    if (aFile.isDirectory() && !"ProcressedEMLs".equalsIgnoreCase(aFile.getName()) && !"FailedEMLs".equalsIgnoreCase(aFile.getName())) {
			     lstFiles.addAll(listDirectory(aFile, level + 1));
			        } else if (!aFile.isDirectory()) {
			     if (aFile.toString().toLowerCase().endsWith(".mp3") || aFile.toString().toLowerCase().endsWith(".mp4")) {
			      lstFiles.add(aFile.getAbsolutePath());
			     }
			    }
			   }
			  }
			  firstLevelFiles = null;
			  return lstFiles;
		}
		
		public static String trimFileExtension(String fileName) {
		     String[] splits = fileName.split( "\\." );
		     return StringUtils.remove( fileName, "." + splits[splits.length - 1] );
		}
		
		public static List<String> split(String string) {
			  List<String> splitted = new ArrayList<>( Arrays.asList(StringUtils.splitByWholeSeparator(string, " ")));
			  splitted.removeAll(Collections.singleton(" "));
			  return splitted;
		}
		
		/*
		 * 
		 * this function will rename the file from the source and give the new name
		 * 
		 */
		public static String renameFile(String fileName, String sourceFilePath, String channelName, String channelID) {
			
			Long fileSerialNo = getFileSerialNo(channelID);
			String oldFile = sourceFilePath + fileName;
			String renameFile = sourceFilePath + channelName.toUpperCase() + "_" + fileSerialNo + ".mp4";
			
			
			File file = new File(oldFile);
			File file2 = new File(renameFile);
			
			if(file.renameTo(file2)){
	            System.out.println("File renamed");
	            
	        }else{
	            System.out.println("Sorry! the file can't be renamed");
	            
	        }
			return renameFile;
		}
		
		/*
		 * 
		 * Generate the new serial number to bind to the new file name created
		 * 
		 */
		 public static long getFileSerialNo(String channelID) {
			  java.util.ArrayList<String> applicationList = new ArrayList<String>();
			    Connection connection = null;
			    Statement statement = null;
			    ResultSet resultSet = null;
			    errorLogFile = getFilePath(1);
				errorLogs = new File(errorLogFile);
				String query = "SELECT id FROM configurations.fileserial WHERE channel_id=" + Long.parseLong(channelID);
				
				try {
			    connection = PostgresDBConnection.connect();
			    statement = connection.createStatement();
			    resultSet = statement.executeQuery(query);
			    while (resultSet.next()) {
			    	applicationList.add(resultSet.getString("id"));
			    }
			    } catch(SQLException ex) {
			    	 ErrorLogsAppender.appendSQLException("Utility class - getFileSerialNo()",ex, errorLogs);
			    }
			    finally {
				  try {
					  if (statement != null) {
						  statement.close();
						}
					  if (resultSet != null) {
						  resultSet.close();
						}
					  if (connection != null) {
							connection.close();
						}
				  } catch (SQLException ex) {
					  ErrorLogsAppender.appendSQLException("Utility class - getFileSerialNo()",ex, errorLogs);
				  }
				}	
			    long serialNo = 1000 + applicationList.size();
				
				return serialNo; 	  
		  }
}
