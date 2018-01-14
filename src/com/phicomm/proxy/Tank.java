package com.phicomm.proxy;

public class Tank implements Moveable {

	public void move() {
		try {
			Thread.sleep(1000);
			System.out.println("Tank moving");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void stop() {
		try {
			Thread.sleep(500);
			System.out.println("Tank stoping...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
