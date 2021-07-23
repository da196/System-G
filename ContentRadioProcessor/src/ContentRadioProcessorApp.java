import java.util.Timer;

import tcra.go.tv.radio.RadioFileProcessor;

public class ContentRadioProcessorApp {

	public static void main(String[] args) {		
		try {
			
			RadioFileProcessor radioProcessorTask = new RadioFileProcessor();
			Timer timer = new Timer();
			timer.schedule(radioProcessorTask, 0, 1000);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
