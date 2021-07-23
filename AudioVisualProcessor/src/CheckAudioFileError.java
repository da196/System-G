import java.io.FileNotFoundException;

public class CheckAudioFileError {

	public static void main(String[] args) {
		try {
			CheckVideoFileError();
		//SplitFiles();
		}catch(Throwable e) {
			e.printStackTrace();
		}

	}
	public static void CheckVideoFileError() throws FileNotFoundException {
		long startRoutine = System.nanoTime();
		System.out.println("Start Checkking File Error");
		String inputFile = "D:\\Test_Merge_AudiVisual\\AZAM_ONE_50801.mp4";//_tcms_tv_azam_two_AZAM_TWO_15080.mp4";
		String outputFile = "D:\\Test_Merge_AudiVisual\\convertedMP3File\\AZAM_ONE_50802.mp4";

		Runtime r = Runtime.getRuntime();
	    Process process = null;
	    //String command = "C:\\audio\\ffmpeg -ss  00:00:50 -i "+ fistream1 +" -to 00:00:05 -c copy E:\\Test\\output\\result1.mp4";
	    // ffmpeg -v error -i _tcms_tv_azam_two_AZAM_TWO_15080.mp4 -f null - 2>error.txt
	    
	    //String cmd = "D:\\Test_Merge_AudiVisual\\ffmpeg -i \""+ inputFile + "\" \""+ outputFile + "\"";
	    String cmd = "D:\\Test_Merge_AudiVisual\\ffmpeg -v error -i "+ inputFile + " -f null - 2>D:\\Test_Merge_AudiVisualerror.txt";
	    
	    //D:\Test_Merge_AudiVisual\ffmpeg -v error -i D:\Test_Merge_AudiVisual\Channelten.mp4 -f null - 2>D:\Test_Merge_AudiVisual\error.txt
	    System.out.println(cmd);
	    try {
	    	process = r.exec(cmd);
	    	
	    	int  exitVal= process.waitFor();
	    	System.out.println(exitVal);
		 if (exitVal == 0) {
			 long endRoutine = System.nanoTime();
		     System.out.println("File successfully Checked and no Error at " + (endRoutine -startRoutine) / 1000000000 + " seconds");
	     
		 }else {
			  System.out.println("File having error");
		 }
	    } catch (Exception e){     
		
	     e.printStackTrace();
	    } 
	}
}
