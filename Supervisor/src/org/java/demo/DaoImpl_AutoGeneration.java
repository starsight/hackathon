package org.java.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

public class DaoImpl_AutoGeneration {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		File file = new File("src//org//java//entity");
		rename(file);

	}

	public static void rename(File file) throws IOException{
		File[] files = file.listFiles();
		
		File target=new File("src\\org\\java\\dao\\impl");
		
		for (File f : files) {
			if (f.isDirectory()) {
				rename(f);
			} else if (f.getName().endsWith(".java")&&(!f.getName().contains("Pagination"))) {
				String name=f.getName().substring(0,f.getName().indexOf('.'));
				String s = "" + "package org.java.dao.impl;\r\n\r\n"

				+ "import org.java.dao."+name+"Dao;\r\n"
				+ "import org.java.entity."+name+";\r\n"
				+ "import org.springframework.stereotype.Repository;\r\n\r\n"
				
				+ "@Repository\r\n"
				+ "public class "+name+"DaoImpl extends BaseDaoImpl<"+name
				+"> implements "+name+"Dao {\r\n\r\n"
				+"\tpublic "+name+"DaoImpl(){\r\n"
				+"\t\tsuper("+name+".class);\r\n"
				+"\t}\r\n"
				+ "}\r\n";
				FileOutputStream fos=new FileOutputStream(target+"\\"+name+"DaoImpl.java");
				
				System.out.println(s);// 此时需要根据名字中带有Hr的实体类自动生成其对应的接口
				fos.write(s.getBytes());
				
				// String old=f.getPath();

				// String old=f.getPath();
				// String ns=old.substring(0,old.indexOf('.'));
				// File fnew=new File(ns+".jsp");
				// System.out.println(f.renameTo(fnew));

				// System.out.println(f.getName()+"\t"+f.delete());
			}
		}
	}

}
