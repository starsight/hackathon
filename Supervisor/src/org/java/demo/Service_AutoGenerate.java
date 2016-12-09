package org.java.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

public class Service_AutoGenerate {

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
		
		File target=new File("src\\org\\java\\service");
		
		for (File f : files) {
			if (f.isDirectory()) {
				rename(f);
			} else if (f.getName().endsWith(".java")&&(!f.getName().contains("Pagination"))) {
				String name=f.getName().substring(0,f.getName().indexOf('.'));//截取实体类中的名字，无前标后标
				String nameShort=getUpLetter(name.substring(0)).toLowerCase();//获得实体类的缩写
				String s = "" + "package org.java.service;\r\n\r\n"

				+ "import java.util.List;\r\n\r\n"
				
				+ "import org.java.entity."+name+";\r\n"
				+ "import org.java.entity.Pagination;\r\n\r\n"
				
				
				+ "public interface "+name+"Service {\r\n\r\n"
				
				+"\tpublic abstract void add("+name+" "+nameShort+");\r\n\r\n"
				
				+"\tpublic abstract void update("+name+" "+nameShort+");\r\n\r\n"
				
				+"\tpublic abstract void del(Integer id);\r\n\r\n"
				
				+"\tpublic abstract "+name+" findById(Integer id);\r\n\r\n"
				
				+"\tpublic abstract int findMaxByCondition("+name+" "+nameShort+");\r\n\r\n"
				
				+"\tpublic abstract int findMax();\r\n\r\n"
				
				+"\tpublic abstract List<"+name+"> findByPage(Pagination p);\r\n\r\n"
				
				+"\tpublic abstract List<"+name+"> findByConditionPage("+name+" "+nameShort+", Pagination p);\r\n\r\n"
				
				+"\tpublic abstract List<"+name+"> findByCondition("+name+" "+nameShort+");\r\n\r\n"
				
				+ "}\r\n";
				FileOutputStream fos=new FileOutputStream(target+"\\"+name+"Service.java");
				
//				System.out.println(name+"\t"+nameShort);// 此时需要根据名字中带有Hr的实体类自动生成其对应的接口
//				System.out.println(s);
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
	
	public static String getUpLetter(String s){
		StringBuffer sb=new StringBuffer();
		for(char c:s.toCharArray()){
			if(c>='A'&&c<='Z'){
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

}
