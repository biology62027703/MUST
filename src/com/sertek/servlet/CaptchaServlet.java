package com.sertek.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 8392460346242205479L;

	//SecurityCode
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int codechklength = 6;

		// 設置網頁是不暫存的!
		res.setContentType("image/jpeg");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		HttpSession session = req.getSession();

		// 我們建立一個寬60px,高20px的圖形
		int width = codechklength * 14, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 隨機的context上下文
		Graphics g = image.getGraphics();

		Random random = new Random();

		// 設定背景顏色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 字體
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 產生155條干擾線條, 避免
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 200; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 隨機取四位數驗證碼
		String sRand = "";
		for (int i = 0; i < codechklength; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 畫出四位數驗證碼
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));

			g.drawString(rand, 13 * i + 6, 16);
		}

		// 記得將驗證碼數字存到sesseion裡
		session.setAttribute("sol_valid_code", sRand);

		// 執行繪製圖像
		g.dispose();
		
		// 輸出圖片
		ImageIO.write(image, "JPEG", res.getOutputStream());
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	// 給定隨機顏色
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}