package org.com.visual;

import com.xuggle.xuggler.IContainer;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// first we create a Xuggler container object
		String filename = "D:\\Television\\tbc\\TBC1-28012020_104801 - 00220.mp4";
		  IContainer container = IContainer.make();
		 
		  // we attempt to open up the container
		 
		  int result = container.open(filename, IContainer.Type.READ, null);
		 
		  // check if the operation was successful
		 
		  if (result<0)
		 
		throw new RuntimeException("Failed to open media file");
		 
		  // query how many streams the call to open found
		 
		  int numStreams = container.getNumStreams();
		 
		  // query for the total duration
		 
		  long duration = container.getDuration();
		 
		  // query for the file size
		 
		  long fileSize = container.getFileSize();
		 
		  // query for the bit rate
		 
		  long bitRate = container.getBitRate();
		 
		  System.out.println("Number of streams: " + numStreams);
		 
		  System.out.println("Duration (ms): " + duration);
		 
		  System.out.println("File Size (bytes): " + fileSize);
		 
		  System.out.println("Bit Rate: " + bitRate);
		 
		 
	}

}
