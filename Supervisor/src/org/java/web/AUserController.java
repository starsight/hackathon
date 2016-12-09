package org.java.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.java.entity.User;
import org.java.entity.UserRight;
import org.java.service.UserRightService;
import org.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 此控制类用于响应安卓请求
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/Auser.do")
public class AUserController {

	@Autowired
	UserService userv;
	
	@Autowired
	UserRightService urserv;
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	HttpSession ses;
	
	//登录
	@RequestMapping(params="method=login")
	public void login(String uname,String upwd,Integer userRight,HttpServletResponse res){
		//System.out.println(uname+" "+upwd+" "+userRight);
		User user=userv.login(uname, upwd,userRight);
		String resStr;
		if(user==null){
			req.setAttribute("loginErr", "用户名或密码错误");
			resStr="fail";
		}else{
			ses.setAttribute("user", user);
			resStr="succeed";
		}
		
		//将登录验证结果输出反馈
		PrintWriter out=null;
		try {
			out=res.getWriter();
			out.write(resStr);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
		
	}
	
	//注册
	@RequestMapping(params="method=register")
	public String register(String uname,String upwd){
		UserRight ur=urserv.findById(3);
		User u=new User(ur, uname, upwd);
		userv.add(u);
		return "login";
	}
	
	//登出
	@RequestMapping(params="method=logout")
	public String logout(){
		ses.removeAttribute("user");
		return "redirect:login.jsp";
		
	}
	
	
	//接受控制端请求，创建文件
	@RequestMapping(params="method=ctrl")
	public String ctrl() throws IOException{
//		System.out.println("——————————————————————————");
		File file=new File("/yjdata/www/www/dragonBoard/request");
//		File file=new File("D:\\MyEclipse8.5\\apache-tomcat-6.0.16\\webapps\\Supervisor\\request");
//		if(file.exists()){
//			System.out.println("请求文件已存在");
//			file.delete();
//			System.out.println("旧请求文件已删除");
//			file.createNewFile();
//			System.out.println("新请求以创建");
//		}else{
//			System.out.println("请求文件不存在");
//			file.createNewFile();
//			System.out.println("请求文件创建成功！");
//		}
		file.createNewFile();
//		System.out.println(file.getAbsolutePath());
//		System.out.println(file.getPath());
		return "Welcome";
	}
	
	
	
}
