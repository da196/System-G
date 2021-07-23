import java.util.Timer;

import tcra.go.tv.processor.*;
public class ContentTVProcessorApp {

	public static void main(String[] args) {		
		try {

			TVFileProcessor tvProcessorTask = new TVFileProcessor();
			
			Timer timer = new Timer();
			
			timer.schedule(tvProcessorTask, 0, 1000);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
