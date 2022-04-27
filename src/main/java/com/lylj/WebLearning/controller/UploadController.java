/**
 * @date 2019年10月28日
 * @time 下午10:38:21
 * @author LiangHB
 */
package com.lylj.WebLearning.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
	private static final String FORMAT = "yyyy/MM/dd/";
	SimpleDateFormat sft = new SimpleDateFormat(FORMAT);

	@PostMapping(value = "/imageupload")
	public String upload(MultipartFile image, HttpServletRequest req) {
		// 区分生成的需打印的订单图片,但后期需要改成静态位置，或者设置security
		String path = "/image/uploadImage/";
		String realPath = req.getSession().getServletContext().getRealPath(path);
		String format = sft.format(new Date());
		// 日期文件夹
		File folder = new File(realPath + format);
		if (!folder.isDirectory()) {
			folder.mkdirs();
		}
		String oldName = image.getOriginalFilename();

		String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
		try {
			image.transferTo(new File(folder, newName));
			String filepath = "../.." + path + format + newName;
			return filepath;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "上传失败！";
	}
	// 访问静态图片（包括上传来的图片、生成的订单图片）

}
