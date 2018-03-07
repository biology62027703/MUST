package com.sertek.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**  
 * @Description: QRCODE     
 * @author：
 * @date：2017-06-07    
 */

public class QRCodeUtil {
	
    private static final int QRCOLOR = 0xff000000;   //藍色
    private static final int BGWHITE = 0xFFffe4b5;   //背景色


    public static void main(String[] args) throws WriterException  
    {  
        try  
        {  
            getLogoQRCode("https://www.facebook.com/kremzcafe/", "");
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  


    /**
     * 生成logo的二維碼圖片
     *
     * @param qrPic
     * @param logoPic
     */
    public static String getLogoQRCode(String qrUrl,String productName)
    {
//	      String filePath = (javax.servlet.http.HttpServletRequest)request.getSession().getServletContext().getRealPath("/") + "resources/images/logoImages/mustlogo.png";
        String filePath = "C:/S__29065267.jpg";  //TODO  
        String content = qrUrl;
        try
        {  
        	QRCodeUtil zp = new QRCodeUtil();
            BufferedImage bim = zp.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 200, 200, zp.getDecodeHintType());
            return zp.addLogo_QRCode(bim, new File(filePath), new LogoConfig(), productName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * QRCODE加Logo
     *
     * @param qrPic
     * @param logoPic
     */
    public String addLogo_QRCode(BufferedImage bim, File logoPic, LogoConfig logoConfig, String productName)
    {
        try
        {
            /**
             * 讀取QRCODE圖片，構建繪圖對象
             */
            BufferedImage image = bim;
            Graphics2D g = image.createGraphics();

            /**
             * 讀取Logo圖片
             */
            BufferedImage logo = ImageIO.read(logoPic);
            /**
             * 設置logo的大小
             */
            int widthLogo = logo.getWidth(null)>image.getWidth()*25/100?(image.getWidth()*40/100):logo.getWidth(null), 
                heightLogo = logo.getHeight(null)>image.getHeight()*25/100?(image.getHeight()*20/100):logo.getWidth(null);

            /**
             * logo放在中心
             */
             int x = (image.getWidth() - widthLogo) / 2;
             int y = (image.getHeight() - heightLogo) / 2;
             /**
             * logo放在右下角
             *  int x = (image.getWidth() - widthLogo);
             *  int y = (image.getHeight() - heightLogo);
             */

            //製作圖片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
//	            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
//	            g.setStroke(new BasicStroke(logoConfig.getBorder()));
//	            g.setColor(logoConfig.getBorderColor());
//	            g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();

            //寫名稱
            if (productName != null && !productName.equals("")) {
                //QRCODE下面加文字
                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                //畫圖匯出
                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                //設定文字到新的面板
                outg.setColor(Color.BLACK); 
                outg.setFont(new Font("ROMAN",Font.BOLD,30)); //字體、字型、大小 
                int strWidth = outg.getFontMetrics().stringWidth(productName);
                if (strWidth > 399) {
//	                  //長度超過就截取前面部分
//	                  outg.drawString(productName, 0, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 5 ); //画文字
                    //長度超過就換行
                    String productName1 = productName.substring(0, productName.length()/2);
                    String productName2 = productName.substring(productName.length()/2, productName.length());
                    int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
                    int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
                    outg.drawString(productName1, 200  - strWidth1/2, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 );
                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg2 = outImage2.createGraphics();
                    outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                    outg2.setColor(Color.BLACK); 
                    outg2.setFont(new Font("ROMAN",Font.BOLD,30)); //字體、字型、大小 
                    outg2.drawString(productName2, 200  - strWidth2/2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight())/2 + 5 );
                    outg2.dispose(); 
                    outImage2.flush();
                    outImage = outImage2;
                }else {
                    outg.drawString(productName, 200  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 ); //画文字 
                }
                outg.dispose(); 
                outImage.flush();
                image = outImage;
            }
            logo.flush();
            image.flush();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(image, "png", baos);

            //QRCODE 生成路徑
            //方法最终返回的是imageBase64字串
            //前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>  其中${imageBase64QRCode} QR CODE的imageBase64字符串
            ImageIO.write(image, "png", new File("C:/kremz_FB.png")); //TODO  

            String imageBase64QRCode =  Base64.encodeBase64URLSafeString(baos.toByteArray());

            baos.close();
            return imageBase64QRCode;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * constructor 初始化QRCODE
     *
     * @param bm
     * @return
     */
    public BufferedImage fileToBufferedImage(BitMatrix bm)
    {
        BufferedImage image = null;
        try
        {
            int w = bm.getWidth(), h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 生成QRCODE圖片
     *
     * @param content
     *            内容
     * @param barcodeFormat
     *            編碼類型
     * @param width
     *            圖片寬度
     * @param height
     *            圖片高度
     * @param hints
     *            設置參數
     * @return
     */
    public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints)
    {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try
        {
            multiFormatWriter = new MultiFormatWriter();
            // 參數：編碼內容，編碼類型，生成圖片寬度，生成圖片高度，設置參數
            bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 建立Bitmap圖片，黑（0xFFFFFFFF）白（0xFF000000）
            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 設置參數
     *
     * @return
     */
    public Map<EncodeHintType, Object> getDecodeHintType()
    {
        // 設置QR CODE參數
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 設置QR CODE Error Level（H為最高級別）具體級別訊息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);

        return hints;
    }
}

    class LogoConfig
    {
        
        public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
        
        public static final int DEFAULT_BORDER = 2;
        
        public static final int DEFAULT_LOGOPART = 5;

        private final int border = DEFAULT_BORDER;
        private final Color borderColor;
        private final int logoPart;

        /**
         * Creates a default config with on color {@link #BLACK} and off color
         * {@link #WHITE}, generating normal black-on-white barcodes.
         */
        public LogoConfig()
        {
            this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
        }

        public LogoConfig(Color borderColor, int logoPart)
        {
            this.borderColor = borderColor;
            this.logoPart = logoPart;
        }

        public Color getBorderColor()
        {
            return borderColor;
        }

        public int getBorder()
        {
            return border;
        }

        public int getLogoPart()
        {
            return logoPart;
        }
	    
}
