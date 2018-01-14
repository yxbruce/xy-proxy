package com.phicomm.compiler.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.phicomm.proxy.Moveable;
import com.phicomm.proxy.Tank;

public class Test {
	public static void main(String[] args) throws Exception {
		
		String rt = "\r\n";
		String src = 
				"package com.phicomm.proxy;" +  rt +
				"public class TankTimeProxy implements Moveable {" + rt +
				"    public TankTimeProxy(Moveable t) {" + rt +
				"        super();" + rt +
				"        this.t = t;" + rt +
				"    }" + rt +
				
				"    Moveable t;" + rt +
								
				"    public void move() {" + rt +
				"        long start = System.currentTimeMillis();" + rt +
				"        System.out.println(\"start time:\" + start);" + rt +
				"        t.move();" + rt +
				"        long end = System.currentTimeMillis();" + rt +
				"        System.out.println(\"time:\" + (end-start));" + rt +
				"    }" + rt +
				"}";
		
		String fileName = System.getProperty("user.dir")
				+"/src/com/phicomm/proxy/TankTimeProxy.java";
		
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
		
		URL[] urls = new URL[]{new URL("file:/"+System.getProperty("user.dir")+"/src")};
		URLClassLoader ul = new URLClassLoader(urls);
		Class c = ul.loadClass("com.phicomm.proxy.TankTimeProxy");
		System.out.println(c);
		
		//拿到构造方法
		Constructor ctr = c.getConstructor(Moveable.class);
		//通过构造方法得到对象实例，构造方法的参数为被代理的对象
		Moveable m = (Moveable)ctr.newInstance(new Tank());
		m.move();
	}
}









