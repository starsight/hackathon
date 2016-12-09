package org.java.web;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.java.entity.Pic;
import org.java.entity.User;
import org.java.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/pic.do")
public class PicController {

	@Autowired
	private PicService pserv;

	@Autowired
	private HttpServletRequest req;

	@Autowired
	private HttpSession ses;

	@Autowired
	private ServletContext ctx;

	// 此方法为保存远端上传的图片
	@RequestMapping(params = "method=save")
	public String save() {

		return "";
	}

	// 保存表单上传的图片
	@RequestMapping(params = "method=upload")
	public String upload(
			@RequestParam(value = "myfile", required = false) CommonsMultipartFile file)
			throws Exception {
		System.out.println("————————————————————————");

		User user = (User) ses.getAttribute("user");// 从ses中获得user的name
		String userName = user.getName();

		String folder = ctx.getRealPath("upload");// 获得upload的绝对路径

		File userFolder = new File(folder, "/" + userName);// 为登录的用户创建用户的个人目录

		if (userFolder.exists()) { // 如果用户目录存在，则不操作，否则创建目录
			System.out.println("exist " + userName);
		} else {
			System.out.println("not exist " + userName);
			userFolder.mkdir();
		}

		String fname = file.getOriginalFilename();// 获得上传文件的文件名
		File newFile = new File(userFolder, fname);// 将用户保存图片的路径和图片名构造成一个新文件

		// System.out.println(">>>>>"+newFile);
		// System.out.println("folder:\t" + folder);
		// System.out.println("fname:\t" + fname);
		// System.out.println("newFile:\t" + newFile);

		file.getFileItem().write(newFile);// 将文件写入目标目录

		// 以下解决时间戳文通，此处尚有问题

		Timestamp time = new Timestamp(System.currentTimeMillis()); // 获得系统的时间
//		System.out.println(time);
//		Timestamp time=null;
		Pic p = new Pic(user, fname, newFile.toString(), time);
		pserv.add(p);// 添加图片

		// pserv.add(p);
		return "upload";

	}

	// 读取该用户的全部图片
	@RequestMapping(params = "method=findAll")
	public String findAll() {

		User user = (User) ses.getAttribute("user");
		List<Pic> plist;
		if (user.getUserRight().getId() == 3) {
			Pic p = new Pic(user);
			plist = pserv.findByCondition(p);
		} else {
			plist = pserv.findAll();
		}

		req.setAttribute("picList", plist);

		return "picList";
	}

}
