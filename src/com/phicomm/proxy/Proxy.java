package com.phicomm.proxy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;


public class Proxy {
	public static Object newProxyInstance(Class clazz,InvocationHandler h) throws Exception {
		
		String methodStr = "";
		String rt = "\r\n";
		
		Method[] methods = clazz.getMethods();
		/*
		 for(Method m : methods) {
			methodStr += "@Override" + rt +
					"    public void "+ m.getName() + "() {" + rt +
					"        long start = System.currentTimeMillis();" + rt +
					"        System.out.println(\"start time:\" + start);" + rt +
					"        t."+	m.getName() + "();"+rt +
					//"        t.move();" + rt +
					"        long end = System.currentTimeMillis();" + rt +
					"        System.out.println(\"time:\" + (end-start));" + rt +
					"    }" ;
		} 
		*/
		
		 for(Method m : methods) {
				methodStr += "@Override" + rt +
						"    public void "+ m.getName() + "() {" + rt +
						"    try {" + rt +
						 "    Method md = " + clazz.getName() + ".class.getMethod(\"" + m.getName() + "\");" + rt +
						 "    h.invoke(this, md);" + rt +
						 "    }catch(Exception e) {e.printStackTrace();}" + rt +
						
						 "}";
			} 
		 
		 
		String src = 
				"package com.phicomm.proxy;" +  rt +
				"import java.lang.reflect.Method;" + rt +
				"public class $Proxy1 implements "+ clazz.getName() + "{" + rt +
				"    public $Proxy1(InvocationHandler h) {" + rt +
				"        this.h = h;" + rt +
				"    }" + rt +
				"    com.phicomm.proxy.InvocationHandler h;" + rt +
				methodStr +
								
//				"    public void move() {" + rt +
//				"        long start = System.currentTimeMillis();" + rt +
//				"        System.out.println(\"start time:\" + start);" + rt +
//				"        t.move();" + rt +
//				"        long end = System.currentTimeMillis();" + rt +
//				"        System.out.println(\"time:\" + (end-start));" + rt +
//				"    }" + rt +
				"}";
		
		String fileName = "d:/src/com/phicomm/proxy/$Proxy1.java";
		
		File file = new File(fileName);
		FileWriter fw = new FileWriter(file);
		fw.write(src);
		fw.flush();
		fw.close();
		
		/**                 以下代码为动态编译                                       **/
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		//System.out.println(compiler);
		StandardJavaFileManager fileMgr = 
				compiler.getStandardFileManager(null,null, null);
		Iterable units = fileMgr.getJavaFileObjects(fileName);
		CompilationTask ct = compiler.
				getTask(null, fileMgr, null, null, null, units);
		ct.call();
		fileMgr.close();
		
		URL[] urls = new URL[]{new URL("file:/" + "d:/src/")};
		URLClassLoader ul = new URLClassLoader(urls);
		Class c = ul.loadClass("com.phicomm.proxy.$Proxy1");
		System.out.println(c);
		
		//拿到构造方法
		Constructor ctr = c.getConstructor(InvocationHandler.class);
		//通过构造方法得到对象实例，构造方法的参数为被代理的对象
		return ctr.newInstance(h);

		//m.move();
	}
}
