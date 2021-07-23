package org.com.visual;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class TVFileProcessor {

	public static void main(String[] args) {

		double FileSizeInKB = 0;
 		String lastModifiedDate = null;
 		FileTime fileTime;
 		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		timestamp2.getTime();
		
		String sourceFilePath = "D:\\Television\\tbc\\";
		String destinationFilePath = "D:\\Television\\processedFiles\\";
		String str1 = "New TV file checking routine started at: " + new java.util.Date() + "\n";
        System.out.println(str1);
        
      //Count number of files in incoming files directory (vodacom files are incrypted)
        File incomingFilesDirectory = new File(sourceFilePath); 
        List<String> listOfIncomingFiles = listDirectory(incomingFilesDirectory, 0); 
        String str2 = listOfIncomingFiles.size() + " TV files found! \n";
        System.out.println(str2); 
        
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
    		
    		try {
				Files.move(SourceFilePath, DestinationFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			} 
    		List<String> list = split(lastModifiedDate);
    		String date = list.get(0);
    		String time = list.get(1);
    		
    		String fileExtension = FilenameUtils.getExtension(fileName);
    		
    		if(fileName.toUpperCase().contains("TBC1")) {
    			//System.out.println(fileName + " --> " + fileExtension + " --> " +  FileSizeInKB + " --> " + date + " --> " + time);
    		}
    		if(fileName.toUpperCase().contains("AZAM1")) {
    			System.out.println(fileName + " --> " + fileExtension + " --> " +  FileSizeInKB + " --> " + date + " --> " + time);
        		
    		}
    		if(fileName.toUpperCase().contains("ITV")) {
    			System.out.println(fileName + " --> " + fileExtension + " --> " +  FileSizeInKB + " --> " + date + " --> " + time);
        		
    		}
    	}
	}

	//This method for Reading the file in directory manner(folder under folder all the CSV, TXT, TSV, CSV.sha1)
	private static List<String> listDirectory(File file, int level) {
	
	  List<String> lstFiles = new ArrayList<String>();
	
	  File[] firstLevelFiles = file.listFiles();
	  if (firstLevelFiles != null && firstLevelFiles.length > 0) {
	   for (File aFile : firstLevelFiles) {
	    if (aFile.isDirectory() && !"ProcressedEMLs".equalsIgnoreCase(aFile.getName())
	      && !"FailedEMLs".equalsIgnoreCase(aFile.getName())) {
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

	
	//we use the older file i/o operations for this rather than the newer jdk7+ Files.move() operation
	private static boolean moveFileToDirectory(File sourceFile, String targetPath) {
	  File tDir = new File(targetPath);
	  if (tDir.exists()) {
	      String newFilePath = targetPath+File.separator+sourceFile.getName();
	      File movedFile = new File(newFilePath);
	      if (movedFile.exists())
	          movedFile.delete();
	      return sourceFile.renameTo(new File(newFilePath));
	  } else {
	      System.out.println("unable to move file "+sourceFile.getName()+" to directory "+targetPath+" -> target directory does not exist");
	      return false;
	  }       
	
	} 
	
	private static List<String> split(String string) {
	  List<String> splitted = new ArrayList<>( Arrays.asList(StringUtils.splitByWholeSeparator(string, " ")));
	  splitted.removeAll(Collections.singleton(" "));
	  return splitted;
	}
	
	private static String trimFileExtension(String fileName) {
	   String[] splits = fileName.split( "\\." );
	   return StringUtils.remove( fileName, "." + splits[splits.length - 3] );
	}
	
	private static String getFileExtension( String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
  }
}