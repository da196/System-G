package tcra.go.tv.radio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) {
		
		int fileSerialNo = 1100;
		String oldFile = "D:\\Test_Merge_AudiVisual\\audio\\CLOUDS - 21 April 2020 - 09-03-38 AM - 00079";
		String renameFile = "D:\\Test_Merge_AudiVisual\\audio\\CLOUDS"+ "_" + fileSerialNo + ".mp4";
		
		String moveFile = "D:\\Test_Merge_AudiVisual\\convertedMP3File\\CLOUDS"+ "_" + fileSerialNo + ".mp4";
	
		
		System.out.println(fileSerialNo + " " + oldFile + "" +renameFile);
		
		File file = new File(oldFile);
		File file2 = new File(renameFile);
		
		if(file.renameTo(file2)){
            System.out.println("File renamed");
            String _stringSourceFilePath = oldFile;
    		Path _SourceFilePath = Paths.get(_stringSourceFilePath);
    		Path _DestinationFilePath = Paths.get(moveFile);
            try {
				Files.move(_SourceFilePath,_DestinationFilePath);
			} catch (IOException e) {
			
				e.printStackTrace();
			}
        }else{
            System.out.println("Sorry! the file can't be renamed");
        }

	}

}
