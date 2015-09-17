package com.stellaromeo.lightwaverf;


public class IPhoneSensor {
	
	public static long lastCheckTime = System.currentTimeMillis();
//	private static boolean currentStatus = false;
	
	public static boolean inRange()
	{
		return true;
//		if (System.currentTimeMillis() - lastCheckTime > 10000)
//		{
//				
//			int countFail = 0, countSucceed = 0;
//			for (int i = 0; i < 10; i++)
//			{
//				System.out.println("Try " + i);
//				if (inRange(""))
//				{
//					System.out.println("true");
//					countSucceed++;
//				}
//				else
//				{
//					countFail++;
//					System.out.println("false");
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			currentStatus = countSucceed > countFail;
//		}
//		return currentStatus;
	}

//	private static boolean inRange(String ip)
//	{
//		
//		try {
//			return(InetAddress.getByName("192.168.1.89").isReachable(1000));  
//
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	public static void main(String[] args) throws InterruptedException {
		
			System.out.println("overall" + inRange());
		
	}
	
}
