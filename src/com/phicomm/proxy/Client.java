package com.phicomm.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;

public class Client {
	public static void main(String[] args) throws Exception {
//		TankTimeLog ttl = new TankTimeLog();
//		ttl.move();
		
		
		
//		TankTimeProxy ttp = new TankTimeProxy(t);
//		TankLogProxy tlp = new TankLogProxy(ttp);
//		Moveable m = ttp;
//		m.move();
		
		Tank t = new Tank();
		InvocationHandler h = new TimeHandler(t);
		Moveable m = (Moveable) Proxy.newProxyInstance(Moveable.class,h);
		m.move();
	}
}

