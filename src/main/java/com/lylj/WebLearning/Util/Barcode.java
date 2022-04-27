/**
 * @date 2019年11月2日
 * @time 下午4:31:59
 * @author YiqiHu
 */
package com.lylj.WebLearning.Util;

import java.awt.image.BufferedImage;
import java.io.*;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;



/**
 * 条形码生成
 */
public class Barcode {
	public static void getBarCode(String msg, File file) {
		try {
			OutputStream ous = new FileOutputStream(file);

			// 选择条形码类型(好多类型可供选择)
			Code128Bean bean = new Code128Bean();
			// 设置长宽
			final double moduleWidth = 0.20;
			final int resolution = 300;
			bean.setModuleWidth(moduleWidth);
			bean.doQuietZone(false);
			String format = "image/png";
			// 输出流
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, resolution,
					BufferedImage.TYPE_BYTE_BINARY, false, 0);
			// 生成条码
			bean.generateBarcode(canvas, msg);
			canvas.finish();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}