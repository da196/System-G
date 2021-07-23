package tcra.go.tv.processor;
import java.sql.*;
import java.io.*;


public class FileChecker {
	
	private static File errorLogs ;
	private static String errorLogFile; 
	public FileChecker()
	{
		
	}
	public static long checker(String fileName) {
		long cn = -1;
		Connection connection = null;
	    Statement statement = null;
	    errorLogFile = Utility.getFilePath(1);
	 	errorLogs = new File(errorLogFile);
	    try {
		    connection = PostgresDBConnection.connect();
		    statement = connection.createStatement();
		    String sql = "SELECT * FROM tcms_tv.file_metadata WHERE tcms_tv.file_metadata.file_name = '" + fileName + "'";
		   
		    ResultSet resultSet = statement.executeQuery(sql);
		    if (resultSet.next()) {
			    cn = Long.parseLong(resultSet.getString(1));
		    }
		} catch (SQLException ex) {
			ErrorLogsAppender.appendSQLException("FileChecker class - checker()",ex, errorLogs);
		    ex.printStackTrace();
		} finally {
		  try {
		  if (connection != null) {
			connection.close();
		  }
		  } catch (SQLException ex) {
			  ErrorLogsAppender.appendSQLException("FileChecker class - checker()",ex, errorLogs);
			  ex.printStackTrace();
		  }	
		  try {
		  if (statement != null) {
			statement.close();
		  }
		  } catch (SQLException ex) {
			  ErrorLogsAppender.appendSQLException("FileChecker class - checker()",ex, errorLogs);
			  ex.printStackTrace();
		  }	
		}
	    
	   return cn;
	}

}
