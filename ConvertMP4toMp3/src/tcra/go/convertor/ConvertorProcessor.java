package tcra.go.convertor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;

import tcra.go.convertor.Utility;


public class ConvertorProcessor extends TimerTask implements Runnable{

	private static File errorLogs ;
	private static String errorLogFile; 
	
	public ConvertorProcessor() {
		
	}
	
	@Override
	public void run() {
		
		    errorLogFile = Utility.getFilePath(1);
		 	errorLogs = new File(errorLogFile);

			String destinationFilePath = null;
			String convertedMP3FilePath = null;
			String channelName = null;
			String corruptedFilePath = null;

			//convertedMP3FilePath = "D:\\Test_Merge_AudiVisual\\convertedMP3File\\";
			destinationFilePath =   "/var/store/destinationFile/"; //"D:\\Test_Merge_AudiVisual\\fileRenamed\\";   //
			corruptedFilePath = "/var/store/corruptedFile/";
			System.out.println("Radio file convertor routine started at: " + new java.util.Date() + "\n");

			  //Count number of renamed files in incoming files directory (radio files from the renamed Files)
	        File incomingFilesDirectory = new File(destinationFilePath); 
	        List<String> listOfIncomingFiles = Utility.listDirectory(incomingFilesDirectory, 0); 
	        String str1 = listOfIncomingFiles.size() + " Radio files found! \n";
	        System.out.println(str1);
	        
	        for (int fileNumber = 0; fileNumber < listOfIncomingFiles.size(); fileNumber++) {
	    		
	    		String str3 = "------------------------------------------------------------------------------\n";
	            System.out.println(str3);		
	    	    String fileName = Paths.get(listOfIncomingFiles.get(fileNumber)).getFileName().toString();
	    	    
	    	   // if(fileName.toUpperCase().contains(channelName.toUpperCase())) {
	    	    	
	    	    	String fileTrimExtension = trimFileExtension(fileName);
	    	    	String fileTrimUnderscore = SecondTrimFileExtension(fileTrimExtension);
	    	    	
	    	    	List<Channels> channelList = Channels.getChannelList(fileTrimUnderscore);
	    			
	    			for(int i=0; i < channelList.size(); i++) {
	    				channelName = channelList.get(i).getAbbreviation();
	    	    	    convertedMP3FilePath = channelList.get(i).getUrlPath();
	    	    	 
	    	    	 System.out.println(fileName +" --> " + channelName +" --> "+ convertedMP3FilePath);
	    	    Runtime r = Runtime.getRuntime();
	    	    Process process = null;  
	    	   //String cmd = "D:\\Test_Merge_AudiVisual\\ffmpeg -i "+ destinationFilePath +fileName + " "+ convertedMP3FilePath+ fileName;
	    	    String cmd = "ffmpeg -i "+ destinationFilePath +fileName + " "+ convertedMP3FilePath+ fileName;
	    	    String _stringDestinationFilePath = destinationFilePath + fileName;
	    	    String _stringcorruptedFilePath = corruptedFilePath + fileName;
	    	    
	    	    Path filePaths = Paths.get(_stringDestinationFilePath);
	    	    Path corruptedPath = Paths.get(_stringcorruptedFilePath);
	    	    System.out.println(cmd);
	    	    
	    	    try {
	    	    	 process = r.exec(cmd);
	    			 int  exitVal= process.waitFor();
	    			 System.out.println(exitVal);
	    			
	    			 if (exitVal == 0) {
	    				 Files.delete(filePaths);
	    				 System.out.println( exitVal + " Radio Files Successfully converted");
	    			 }else {
	    				 Files.move(filePaths, corruptedPath);
	    				 System.out.println( exitVal + " Radio Files Not converted");
	    			 }
	    	    } catch (Exception e){     
	    	    	e.printStackTrace();
	    	    	ErrorLogsAppender.appendException("ConvertorProcessor class - run()",e, errorLogs);
	    	    } 
	    	    
	    	    
	          //}
			 }
			}
	}
	
	private static String trimFileExtension(String fileName) {
	     String[] splits = fileName.split( "\\." );
	     return StringUtils.remove( fileName, "." + splits[splits.length - 1] );
	  }
	
	private static String SecondTrimFileExtension(String fileName) {
	     String[] splits = fileName.split( "\\_" );
	     return StringUtils.remove( fileName, "_" + splits[splits.length - 1] );
	  }
}
