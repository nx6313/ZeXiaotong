package cn.gk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

@WebServlet(asyncSupported = true, urlPatterns = { "/ServletDownload.do" })
public class ServletDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletDownload() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		// 获得请求文件名
		String filename = request.getParameter("filename");
		String fullFileName = getServletContext().getRealPath("/download/" + filename);
		File downloadFile = new File(fullFileName);

		if (downloadFile.exists()) {
			long fileSize = FileUtils.sizeOf(downloadFile);
			response.reset();
			// response.setContentType(getServletContext().getMimeType(filename));
			response.setContentType("multipart/form-data;charset=utf-8");
			response.addHeader("pargma", "NO-cache");
			response.addHeader("Cache-Control", "no-cache");
			response.addDateHeader("Expries", 0);
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			response.addHeader("Content-Length", String.valueOf(fileSize));
			// 读取目标文件，通过response将目标文件写到客户端
			// 读取文件
			FileInputStream in = new FileInputStream(fullFileName);
			OutputStream out = response.getOutputStream();

			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}

			in.close();
			out.close();
		}
	}
}
