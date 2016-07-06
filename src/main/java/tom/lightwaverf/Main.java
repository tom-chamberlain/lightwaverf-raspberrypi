package tom.lightwaverf;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class Main {

	private static final int DELAY_IN_MS = 1000;
	private static final int MS_TO_WAIT_BEFORE_CHECKING = 1000;

	public static void main(String[] args) throws InterruptedException {

		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring-beans.xml")) {
			
			Executor executor = (Executor) context.getBean("executor");

			executor.sendPairingCode();
			
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
