package tcra.go.convertor;

import org.apache.commons.lang3.StringUtils;

public class Test {

	public static void main(String[] args) {

		String fileName = "D:\\Test_Merge_AudiVisual\\convertedMP3File\\MORNING_STAR_1000.mp3";
		
		
		//System.out.println(SecondTrimFileExtension(SecondTrimFileExtension(SecondTrimFileExtension(trimFileExtension(fileName)))));
		
		System.out.println((SecondTrimFileExtension(trimFileExtension(fileName))));
	}

	private static String trimFileExtension(String fileName) {
	     String[] splits = fileName.split( "\\." );
	     return StringUtils.remove( fileName, "." + splits[splits.length - 1] );
	  }
	
	private static String SecondTrimFileExtension(String fileName) {
	     String[] splits = fileName.split( "\\_" );
	     return StringUtils.remove( fileName, "_" + splits[splits.length - 1] );
	  }
	private static String getFileExtension( String fileName) {
	        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	        return fileName.substring(fileName.lastIndexOf(".")+1);
	        else return "";
	  }
}
