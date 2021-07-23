package tcra.go.tv.processor;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class Test {

	public static void main(String[] args) throws IOException {
		/// File (or directory) with old name
		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		Long id = timestamp2.getTime();
		String oldFile = "D:\\Television\\compressedFiles\\TBC1-28012020_104801-00221.mp4";
		String newFile = "D:\\Television\\compressedFiles\\TV1_" + id + ".mp4";
		
		File file = new File(oldFile);
		System.out.println(file.getName());
		// File (or directory) with new name
		
		File file2 = new File(newFile);
		System.out.println(file2.getName());
		
		if (file2.exists())
		   throw new java.io.IOException("file exists");

		if(file.renameTo(file2)){
            System.out.println("File renamed");
        }else{
            System.out.println("Sorry! the file can't be renamed");
        }

	}

}
