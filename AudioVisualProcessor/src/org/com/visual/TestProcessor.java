package org.com.visual;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestProcessor {

	public static void main(String[] args) throws IOException {
		//String fileName = "D:\\Television\\tbc\\TBC1 - 28 January 2020 - 03-16-30 AM - 00000.mp4";
		String fileName = "D:\\Channel_10\\Channelten TV Capture 30-10-2019 00.30 to 01-30.mp4";
		
        File myfile = new File(fileName);
        Path path = myfile.toPath();
        
        BasicFileAttributes fatr = Files.readAttributes(path,BasicFileAttributes.class);
        FileTime fileTime = null;
        SimpleDateFormat df = null;
        
       //Creation date time
  		fileTime = fatr.lastAccessTime();
  		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		String fileCreationTime = df.format(fileTime.toMillis());
      		
        //Modified date time
        fileTime = fatr.lastModifiedTime();
		df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastModifiedDate = df.format(fileTime.toMillis());

		System.out.printf("File creation time: %s%n", fileCreationTime);
        System.out.printf("File Modified time: %s%n", lastModifiedDate);

	}

}
