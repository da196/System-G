package tcra.go.tv.radio;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Convertor {
	
	public Convertor() {
		
	}

	public static void ConvertMP4toMP3(String destinationFilePath, String channelName) {
		
		
        //Count number of renamed files in incoming files directory (radio files from the renamed Files)
        File incomingFilesDirectory = new File(destinationFilePath); 
        List<String> listOfIncomingFiles = Utility.listDirectory(incomingFilesDirectory, 0); 
        String str1 = listOfIncomingFiles.size() + " Radio files renamed found! \n";
        System.out.println(str1);
        
        for (int fileNumber = 0; fileNumber < listOfIncomingFiles.size(); fileNumber++) {
        	 
    		String str3 = "------------------------------------------------------------------------------\n";
            System.out.println(str3);		
    	    String fileName = Paths.get(listOfIncomingFiles.get(fileNumber)).getFileName().toString();
    	    
    	    if(fileName.toUpperCase().contains(channelName.toUpperCase())) {
    	    	String convertedMP3FilePath = Channels.GetChannelURL(channelName);
	    	    Runtime r = Runtime.getRuntime();
	    	    Process process = null;  
	    	   // String cmd = "D:\\Test_Merge_AudiVisual\\ffmpeg -i "+ destinationFilePath +fileName + " "+ convertedMP3FilePath+ fileName;
	    	    String cmd = "ffmpeg -i "+ destinationFilePath +fileName + " "+ convertedMP3FilePath+ fileName;
	    	    String _stringDestinationFilePath = destinationFilePath + fileName;
	    	    Path filePaths = Paths.get(_stringDestinationFilePath);
	    	    System.out.println(cmd);
	    	    
	    	    try {
	    	    	 process = r.exec(cmd);
	    			 int  exitVal= process.waitFor();
	    			
	    			 if (exitVal == 0) {
	    				 Files.delete(filePaths);
	    				 System.out.println(exitVal + "Radio Files Successfully Converted");
	    			 }else {
	    				 System.out.println(exitVal + "Radio Files Not Converted");
	    			 }
	    	    } catch (Exception e){     
	    	    	e.printStackTrace();
	    	    } 
    	    
    	    }
    	    
        }
		
	}
}
