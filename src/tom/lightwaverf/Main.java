package tom.lightwaverf;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	private static final int DELAY_IN_MS = 1000;
	private static final int MS_TO_WAIT_BEFORE_CHECKING = 1000;
	
	//TODO : Implement ramdom/security period
	//TODO : HomeKit

	public static void main(String[] args) throws InterruptedException {

		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:tom/lightwaverf/spring-beans.xml")) {
			
			Executor executor = (Executor) context.getBean("executor");

			executor.sendInitCode();
			
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {

					executor.processItems();
				}
			}, DELAY_IN_MS, MS_TO_WAIT_BEFORE_CHECKING);
		}

	}

}
