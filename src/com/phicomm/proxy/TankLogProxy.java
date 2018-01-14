package com.phicomm.proxy;

public class TankLogProxy implements Moveable {

	Moveable t;
	
	public TankLogProxy(Moveable t) {
		this.t = t;
	}
	
	public void move() {
		System.out.println("Tank start...");
		t.move();
		System.out.println("Tank stop...");
	}


}
