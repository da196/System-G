import java.util.Timer;

import tcra.go.convertor.ConvertorProcessor;



public class ConvertorApp {

	public static void main(String[] args) {
		
		try {
			
			ConvertorProcessor convertorProcessorTask = new ConvertorProcessor();
			
			Timer timer = new Timer();
			timer.schedule(convertorProcessorTask, 0, 1000);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
