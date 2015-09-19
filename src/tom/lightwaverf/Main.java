package tom.lightwaverf;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	private static final int DELAY_IN_MS = 1000;
	private static final int MS_TO_WAIT_BEFORE_CHECKING = 1000;
	
	//TODO : Home mode / Away mode
	//TODO : HomeKit

	public static void main(String[] args) throws InterruptedException {

		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:tom/lightwaverf/spring-beans.xml")) {
			
			Executor executor = (Executor) context.getBean("executor");

			executor.sendInitCode();
			
			CountDownLatch latch = new CountDownLatch(1);
			
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {

					executor.processItems();
					executor.processAllOffEvents();
					executor.processRandomItems();
					
				}
			}, DELAY_IN_MS, MS_TO_WAIT_BEFORE_CHECKING);
			
			latch.await();
		}

	}

}
