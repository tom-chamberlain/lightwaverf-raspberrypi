package tom.lightwaverf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("file:src/tom/lightwaverf/spring-beans.xml");
		
		Executor executor = (Executor) context.getBean("executor");
		
		executor.sendInitCode();
		
		while (true)
		{
			executor.processItems();
			Thread.sleep(1000);
		}
		
		
	}

}
