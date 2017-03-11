package com.penpi.server.action.forAndroid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Decoder;

import com.opensymphony.xwork2.ActionSupport;
import com.penpi.server.domain.Bitmap;
import com.penpi.server.utils.InfoBean;
import com.penpi.server.utils.JacksonUtils;
import com.penpi.server.utils.StringUtils;

@SuppressWarnings("serial")
public class BitmapAction extends ActionSupport {

	public String uploadPhoto() throws IOException {
		Bitmap bitmap = null;
		// 获取存储路径
		HttpServletRequest request = ServletActionContext.getRequest();
		String receiveData = StringUtils.receive(request);
		String path = ServletActionContext.getServletContext().getRealPath("/")
				+ "upload";

		if (receiveData.length() != 0) {
			bitmap = JacksonUtils.readJson(receiveData, Bitmap.class);
			String imgStr = bitmap.getImgStr();
			String imgFilePath = path + "\\" + bitmap.getUsername() + "\\"
					+ bitmap.getImgName();

			File file = new File(path);
			if (!file.exists())
				file.mkdir();
			file = new File(path + "\\" + bitmap.getUsername());
			if (!file.exists())
				file.mkdir();

			if (string2Image(imgStr, imgFilePath)) {
				InfoBean info = new InfoBean();
				info.setValidate(true);
				ServletActionContext.getResponse().getWriter()
						.write(JacksonUtils.writeJSON(info));
				System.out.println("头像上传成功！");
			}
		}
		return null;
	}

	/**
	 * 通过BASE64Decoder 解码，并生成图片
	 * 
	 * @param imgStr
	 *            解码后的string
	 */
	public boolean string2Image(String imgStr, String imgFilePath) {
		System.out.println(imgFilePath);
		// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null)
			return false;
		try {
			// Base64解码
			byte[] b = new BASE64Decoder().decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成Jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
