package com.phicomm.proxy;

public class Tank3 extends Tank2 {

	@Override
	public void move() {
		System.out.println("Tnak start...");
		super.move();
		System.out.println("Tank stop...");
	}
}
