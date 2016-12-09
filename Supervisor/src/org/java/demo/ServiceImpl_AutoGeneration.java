package org.java.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

public class ServiceImpl_AutoGeneration {

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
		
		File target=new File("src\\org\\java\\service\\impl");
		
		for (File f : files) {
			if (f.isDirectory()) {
				rename(f);
			} else if (f.getName().endsWith(".java")&&(!f.getName().contains("Pagination"))) {
				
				//截取实体类中的名字，无前标后标
				String name=f.getName().substring(0,f.getName().indexOf('.'));
				
				//获得实体类的缩写
				String nameShort=getUpLetter(name.substring(0)).toLowerCase();
				String s = "" 
				+ "package org.java.service.impl;\r\n\r\n"

				+ "import java.util.List;\r\n\r\n"
				
				+"import org.hibernate.criterion.DetachedCriteria;\r\n"
				+"import org.hibernate.criterion.Restrictions;\r\n"
				+"import org.java.dao."+name+"Dao;\r\n"
				+"import org.java.entity."+name+";\r\n"
				+"import org.java.entity.Pagination;\r\n"
				+"import org.java.service."+name+"Service;\r\n"
				+"import org.springframework.beans.factory.annotation.Autowired;\r\n"
				+"import org.springframework.stereotype.Service;\r\n\r\n"
				
				+ "@Service\r\n"
				+ "public class "+name+"ServiceImpl implements "+name+"Service {\r\n\r\n"
				
				+ "\t@Autowired\r\n"
				+ "\tprivate "+name+"Dao dao;\r\n\r\n"
				
				
				+"\tpublic void add("+name+" "+nameShort+"){\r\n"
				+ "\t\tdao.save("+nameShort+");\r\n"
				+ "\t}\r\n\r\n"
				
				+"\tpublic void del(Integer id) {\r\n"
				+"\t\tdao.delById(id);\r\n"
				+"\t}\r\n\r\n"

				+"\tpublic List<"+name+"> findByCondition("+name+" "+nameShort+") {\r\n"
				+"\t\tDetachedCriteria dc=DetachedCriteria.forClass("+name+".class);\r\n"
				+"\t\treturn dao.findByCondition(dc);\r\n"
				+"\t}\r\n\r\n"

				+"\tpublic List<"+name+"> findByConditionPage("+name+" "+nameShort+", Pagination p) {\r\n"
				+"\t\tDetachedCriteria dc=DetachedCriteria.forClass("+name+".class);\r\n"
				+"\t\treturn dao.findByConditionPage(dc, p);\r\n"
				+"\t}\r\n\r\n"

				+"\tpublic "+name+" findById(Integer id) {\r\n"
				+"\t\treturn dao.findById(id);\r\n"
				+"\t}\r\n\r\n"

				+"\tpublic List<"+name+"> findByPage(Pagination p) {\r\n"
				+"\t\treturn dao.findAllByPage(p);\r\n"
				+"\t}\r\n\r\n"

				+"\tpublic int findMax() {\r\n"
				+"\t\treturn dao.findMax();\r\n"
				+"\t}\r\n\r\n"

				+"\tpublic int findMaxByCondition("+name+" "+nameShort+") {\r\n"
				+"\t\tDetachedCriteria dc=DetachedCriteria.forClass("+name+".class);\r\n"
				+"\t\treturn dao.findMaxByCondition(dc);\r\n"
				+"\t}\r\n\r\n"

				+"\tpublic void update("+name+" "+nameShort+") {\r\n"
				+"\t\tdao.update("+nameShort+");\r\n"
				+"\t}\r\n\r\n"

				+ "}\r\n";
				FileOutputStream fos=new FileOutputStream(target+"\\"+name+"ServiceImpl.java");
				
//				System.out.println(name+"\t"+nameShort);// 此时需要根据名字中带有Hr的实体类自动生成其对应的接口
				System.out.println(s);
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
