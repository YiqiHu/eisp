/**
 * @date 2019年11月2日
 * @time 上午9:30:19
 * @author YiqiHu
 */
package com.lylj.WebLearning.Util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;

import com.lylj.WebLearning.ORM.entity.Order;

public class PictureFactory {
	private static int width = 500;
	private static int height = 800;

	// 插入图片包括logo,条码（有缩放）
	private static Image insertImage(String imgPath, int imgWidth, int imgHeight) throws Exception {
		File fileimage = new File(imgPath);
		Image src = ImageIO.read(fileimage);
		Image image = src.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
		return image;
	}

	public static void generate(String company, Order order, File barcodefile, File orderimage) throws Exception {
		// 条形码
		Barcode.getBarCode(order.getNumber(), barcodefile);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		// 设置背景色为白色
		g.setColor(Color.WHITE);
		// 设置颜色区域大小
		g.fillRect(0, 0, width, height);

		// 框架
		g.setColor(Color.BLACK);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillRect(499 - 60, 10, 60, 60);
		g.drawRect(0, 80, 500 - 1, 540 - 1);
		g.drawLine(0, 280, 500 - 1, 280);
		g.drawLine(0, 460, 500 - 1, 460);
		g.drawLine(0, 560, 500 - 1, 560);
		g.drawLine(350, 490, 500 - 1, 490);
		g.drawLine(80, 280, 80, 560);
		g.drawLine(350, 460, 350, 620);

		// 填入图片
		String logoLocat = "D:\\java\\WorkPlace\\WebLearning\\src\\main\\webapp\\image\\logo";

		Image logoImg = insertImage(logoLocat + "\\"+company + ".png", 180, 60);
		g.drawImage(logoImg, 0, 10, null);

		Image codeImg = insertImage(barcodefile.getPath(), 399, 150);
		g.drawImage(codeImg, 50, 108, null);

		// 插入文字
		Font font = new Font("黑体", Font.BOLD, 36);
		Font font1 = new Font("黑体", Font.BOLD, 26);
		Font font2 = new Font("黑体", Font.BOLD, 18);
		Font font3 = new Font("黑体", Font.BOLD, 12);
		Font font4 = new Font("黑体", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("收", 22, 380);
		g.drawString("寄", 22, 515);
		g.setColor(Color.WHITE);
		g.setFont(font1);
		g.drawString("快递", 442, 32);
		g.drawString("包裹", 442, 62);

		g.setColor(Color.BLACK);
		g.setFont(font2);
		g.drawString("备注：", 10, 580);
		g.drawString("签字栏", 360, 485);
		g.drawString("打印时间：", 355, 585);
		g.setFont(font3);
		// order.getSolveTime()
		g.drawString("", 355, 605);

		g.setFont(font4);
		g.drawString(order.getSendName() + " " + order.getSendPhoneNum(), 90, 495);
		g.drawString(order.getSendDistrict(), 90, 525);
		g.setFont(font1);
		g.drawString(order.getReceiveName() + " " + order.getReceivePhoneNum(), 90, 330);
		g.drawString(order.getReceiveDistrict(), 90, 365);
		// 如果长度大于15，换行
		String address = order.getReceiveAddress();
		if (address.length() <= 15) {
			g.drawString(address, 90, 400);
		} else {
			g.drawString(address.substring(0, 16), 90, 400);
			g.drawString(address.substring(16, address.length()), 90, 435);

		}

		// 保存图片 JPEG表示保存格式
		ImageIO.write(image, "jpeg", new FileOutputStream(orderimage));

		// 删除该文件，省空间
		barcodefile.delete();
	}
}
