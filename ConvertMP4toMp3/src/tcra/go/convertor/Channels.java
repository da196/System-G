package tcra.go.convertor;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Channels {

	private String channelID;
	private String channelName;
	private String licenseCategoryID;
	private String regionID;
	private String contentTypeID;
	private String urlPath;
	private String isActive;
	private String abbreviation;
	
	private static File errorLogs ;
	private static String errorLogFile; 
	
	public Channels(String channelID, String channelName, String licenseCategoryID, String regionID, String contentTypeID, String urlPath, 
			String isActive, String abbreviation) {
		this.channelID = channelID;
		this.channelName = channelName;
		this.licenseCategoryID = licenseCategoryID;
		this.regionID = regionID;
		this.contentTypeID = contentTypeID;
		this.urlPath  = urlPath;
		this.isActive = isActive;
		this.abbreviation = abbreviation;
	}

	public String getChannelID() {
		return channelID;
	}

	public String getChannelName() {
		return channelName;
	}

	public String getLicenseCategoryID() {
		return licenseCategoryID;
	}

	public String getRegionID() {
		return regionID;
	}

	public String getContentTypeID() {
		return contentTypeID;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public String getIsActive() {
		return isActive;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	
	public static java.util.List<Channels> getChannelList(String abbreviation) {
		java.util.List<Channels> list = new ArrayList<Channels>();
		errorLogFile = Utility.getFilePath(1);
	 	errorLogs = new File(errorLogFile);
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
	/*	String query = "SELECT configurations.chanel_info.id,configurations.chanel_info.channel_name,configurations.content_type.content_type_name,"
				+ "configurations.license_category.license_category_name,configurations.region.region_name,"
				+ "configurations.chanel_info.path_name,configurations.chanel_info.status "
				+ "FROM configurations.content_type "
				+ "INNER JOIN configurations.chanel_info ON (configurations.content_type.id = configurations.chanel_info.content_type_id) "
				+ "INNER JOIN configurations.region ON (configurations.chanel_info.region_id = configurations.region.id) "
				+ "INNER JOIN configurations.license_category ON (configurations.chanel_info.license_category_id = configurations.license_category.id)";
	*/
		String query = "SELECT * FROM configurations.chanel_info WHERE content_type_id = 2 AND abbreviation = '"+ abbreviation + "'";
		System.out.println(query);
		
		try {	
	    connection = PostgresDBConnection.connect();
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(query);
	    Channels  Channels;
	    while (resultSet.next()) {
	    	Channels = new Channels(
	    			resultSet.getString("id"),
	    	    	resultSet.getString("channel_name"),
	    	    	resultSet.getString("license_category_id"),
	    	    	resultSet.getString("region_id"),
	    	    	resultSet.getString("content_type_id"),
	    	    	resultSet.getString("path_name"),
	    	    	resultSet.getString("url_realtime"),
	    	    	resultSet.getString("abbreviation")
	    	);
	    	list.add(Channels);	
	    }

	    } catch(SQLException ex) {
	    	ex.printStackTrace();
	    	ErrorLogsAppender.appendSQLException("Channels class - getChannelList()",ex, errorLogs);
	    }
	    finally {
		  try {
			    if (resultSet != null) {
			  		resultSet.close();
				}
				if (statement != null) {
					connection.close();
				}
				if (statement != null) {
					connection.close();
				}
		  } catch (SQLException ex) {
			  ex.printStackTrace();
			 ErrorLogsAppender.appendSQLException("Channels class - getChannelList()",ex, errorLogs);
		  }
		}		
		  return list;  
	  }
	
	public static String GetChannelURL(String abbreviation) {
		String url = null;
		errorLogFile = Utility.getFilePath(1);
	 	errorLogs = new File(errorLogFile);
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
	
		String query = "SELECT * FROM configurations.chanel_info WHERE abbreviation = '" + abbreviation + "'";
		System.out.println(query);
		
		try {	
	    connection = PostgresDBConnection.connect();
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(query);
	   
	    while (resultSet.next()) {
	    	url = resultSet.getString("path_name");
	    }

	    } catch(SQLException ex) {
	    	ex.printStackTrace();
	    	ErrorLogsAppender.appendSQLException("Channels class - GetChannelURL()",ex, errorLogs);
	    }
	    finally {
		  try {
			    if (resultSet != null) {
			  		resultSet.close();
				}
				if (statement != null) {
					connection.close();
				}
				if (statement != null) {
					connection.close();
				}
		  } catch (SQLException ex) {
			  ex.printStackTrace();
			 ErrorLogsAppender.appendSQLException("Channels class - GetChannelURL()",ex, errorLogs);
		  }
		}		
		  return url;  
	}
}
