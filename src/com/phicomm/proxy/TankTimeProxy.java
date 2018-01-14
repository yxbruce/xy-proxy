package com.phicomm.proxy;
import java.lang.reflect.Method;
public class TankTimeProxy implements com.phicomm.proxy.test.UserMgr{
    public TankTimeProxy(InvocationHandler h) {
        this.h = h;
    }
    com.phicomm.proxy.InvocationHandler h;
@Override
    public void addUser() {
    try {
    Method md = com.phicomm.proxy.test.UserMgr.class.getMethod("addUser");
    h.invoke(this, md);
    }catch(Exception e) {e.printStackTrace();}
}}