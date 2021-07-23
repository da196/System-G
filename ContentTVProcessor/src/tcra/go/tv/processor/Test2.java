package tcra.go.tv.processor;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.*;

public class Test2 {
	
	public static void main(String[] args) {
		
		String sourceFilePath = "D:\\Channel_10\\";
				//Utility.getFilePath(2);
		   //Count number of files in incoming files directory (TV files from the rawfiles)
        File incomingFilesDirectory = new File(sourceFilePath); 
        List<String> listOfIncomingFiles = listDirectory(incomingFilesDirectory, 2); 
        String str2 = listOfIncomingFiles.size() + " TV files found! \n";
        System.out.println(str2); 
        System.out.println(sourceFilePath);
        for (int fileNumber = 0; fileNumber < listOfIncomingFiles.size(); fileNumber++) {
    		
    		String str3 = "------------------------------------------------------------------------------\n";
            System.out.println(str3);		
    	    String fileName = Paths.get(listOfIncomingFiles.get(fileNumber)).getFileName().toString();
    	    
    	    System.out.println(fileName);
    	    
        }
		
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

}
