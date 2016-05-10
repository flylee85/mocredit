package com.mocredit.security.service;

import com.mocredit.security.entity.Captcha;
import com.mocredit.security.utils.RandCharUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * Date: 2010-3-23
 * Time: 11:11:43
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

//    private Logger logger = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    private int width = 150;
    private int height = 50;
    private Font dfont = new Font("Arial", Font.BOLD, 30);

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 生成随机颜色
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 产生随机字体
     *
     * @return
     */
    private Font getFont(int fontStyle, int fontSize) {
        Random random = new Random();
        Font font[] = new Font[5];
        font[0] = new Font("Arial", fontStyle, fontSize);
        font[1] = new Font("Helvetica", fontStyle, fontSize);
        font[2] = new Font("Geneva", fontStyle, fontSize);
        font[3] = new Font("sans-serif", fontStyle, fontSize);
        font[4] = new Font("Verdana", fontStyle, fontSize);
        return font[random.nextInt(5)];
    }


    /**
     * 生成随机验证码
     *
     * @return
     */
    private String generateCaptchaCode() {
        // 该变量用于保存系统生成的随机字符串
        String randStr = "";
        for (int i = 0; i < 6; i++) {
            // 获得一个随机字符
            String tmp = RandCharUtil.getRandChar();
            randStr += tmp;
        }
        return randStr;
    }


    private BufferedImage getBackground(BufferedImage baseImage) {

        Graphics2D graph = (Graphics2D) baseImage.getGraphics();
        int imageHeight = baseImage.getHeight();
        int imageWidth = baseImage.getWidth();

        // want lines put them in a variable so we might configure these later
        int horizontalLines = imageHeight / 7;
        int verticalLines = imageWidth / 7;

        // calculate space between lines
        int horizontalGaps = imageHeight / (horizontalLines + 1);
        int verticalGaps = imageWidth / (verticalLines + 1);

        Color c = new Color(200, 200, 200);
        // draw the horizontal stripes
        for (int i = horizontalGaps; i < imageHeight; i = i + horizontalGaps) {
            graph.setColor(c);
            graph.drawLine(0, i, imageWidth, i);
        }

        c = new Color(10, 10, 10);
        // draw the vertical stripes
        for (int i = verticalGaps; i < imageWidth; i = i + verticalGaps) {
            graph.setColor(c);
            graph.drawLine(i, 0, i, imageHeight);
        }
        return baseImage;
    }

    private BufferedImage getBackground2(BufferedImage baseImage) {

        Graphics2D graph = (Graphics2D) baseImage.getGraphics();
        int imageHeight = baseImage.getHeight();
        int imageWidth = baseImage.getWidth();
        Random random = new Random();

        // want lines put them in a variable so we might configure these later
        int horizontalLines = imageHeight / 7;
        int verticalLines = imageWidth / 7;

        // calculate space between lines
        int horizontalGaps = imageHeight / (horizontalLines + 1);
        int verticalGaps = imageWidth / (verticalLines + 1);

        Color c = new Color(10, 10, 10);
        // draw the horizontal stripes
//        for (int i = horizontalGaps; i < imageHeight; i = i + horizontalGaps) {
//            graph.setColor(c);
//            graph.drawLine(0, i, imageWidth, i);
//        }

        // draw the vertical stripes
//        for (int i = verticalGaps; i < imageWidth; i = i + verticalGaps) {
//            graph.setColor(c);
//            graph.drawLine(i, 0, i, imageHeight);
//        }

        int startv = imageWidth / 2;
        // draw the vertical stripes
        for (int i = 0; i < imageWidth; i = i + 5) {
            graph.setColor(c);
            startv = startv - random.nextInt(5);
            graph.drawLine(startv, i, startv, i + 5);
        }

        return baseImage;
    }

    private BufferedImage getDistortedImage(BufferedImage baseImage) {

        int imageHeight = baseImage.getHeight();
        int imageWidth = baseImage.getWidth();

        // create a pixel array of the original image.
        // we need this later to do the operations on..
        int pix[] = new int[imageHeight * imageWidth];
        int j = 0;

        for (int j1 = 0; j1 < imageWidth; j1++) {
            for (int k1 = 0; k1 < imageHeight; k1++) {
                pix[j] = baseImage.getRGB(j1, k1);
                j++;
            }
        }

        double distance = ranInt(imageWidth / 4, imageWidth / 3);

        // put the distortion in the (dead) middle
        int widthMiddle = baseImage.getWidth() / 2;
        int heightMiddle = baseImage.getHeight() / 2;

        // again iterate over all pixels..
        for (int x = 0; x < baseImage.getWidth(); x++) {
            for (int y = 0; y < baseImage.getHeight(); y++) {

                int relX = x - widthMiddle;
                int relY = y - heightMiddle;

                double d1 = Math.sqrt(relX * relX + relY * relY);
                if (d1 < distance) {

                    int j2 = widthMiddle
                            + (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (double) (x - widthMiddle));
                    int k2 = heightMiddle
                            + (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (double) (y - heightMiddle));
                    baseImage.setRGB(x, y, pix[j2 * imageHeight + k2]);
                }
            }

        }

        return baseImage;
    }

    /**
     * @param i
     * @param j
     * @return
     */
    private int ranInt(int i, int j) {
        double d = Math.random();
        return (int) ((double) i + (double) ((j - i) + 1) * d);
    }

    /**
     * implementation of: g(s) = - (3/4)s3 + (3/2)s2 + (1/4)s, with s from 0 to
     * 1
     *
     * @param s
     * @return
     */
    private double fishEyeFormula(double s) {
        if (s < 0.0D)
            return 0.0D;
        if (s > 1.0D)
            return s;
        else
            return -0.75D * s * s * s + 1.5D * s * s + 0.25D * s;
    }


    public BufferedImage createTextImg2(String word) {

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 在图片中绘制内容
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, width, height);

        image = getBackground2(image);

        int fontSize = 30;
        Color color = new Color(0, 0, 0);
        Graphics2D g2D = image.createGraphics();
        g2D.setColor(color);

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        g2D.setRenderingHints(hints);

        FontRenderContext frc = g2D.getFontRenderContext();

        int startPosX = width / (2 + word.length());
        int startPosY = (height - fontSize) / 5 + fontSize;

        char[] wordChars = word.toCharArray();
        for (int i = 0; i < wordChars.length; i++) {
            Font chosenFont = getFont(Font.BOLD, fontSize);
            g2D.setFont(dfont);

            char[] charToDraw = new char[]{
                    wordChars[i]
            };
            GlyphVector gv = chosenFont.createGlyphVector(frc, charToDraw);
            double charWidth = gv.getVisualBounds().getWidth();

//            g2D.drawChars(charToDraw, 0, charToDraw.length, startPosX, startPosY);
            g2D.drawChars(charToDraw, 0, charToDraw.length, startPosX - random.nextInt(3), random.nextInt(height - fontSize) / 2 + fontSize);
            startPosX = startPosX + (int) charWidth + 2;
        }

        g.dispose();
        image = getDistortedImage(image);

        return image;
    }


    private BufferedImage createTextImg(String text) {
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 在图片中绘制内容
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, width, height);

        image = getBackground(image);

        int x = 0;
        for (int i = 0; i < text.length(); i++) {
            // 设置图形验证码中字符串的字体和大小
            int fontStyle = random.nextInt(3);
            int fontSize = random.nextInt(2) + 29;
            Font font = getFont(Font.BOLD, fontSize);
            g.setFont(font);
            String tmp = String.valueOf(text.charAt(i));
            // 将系统生成的随机字符添加到图形验证码上
            g.setColor(new Color(0, 0, 0));
//            g.setColor(new Color(20 + random.nextInt(110), 20 + random
//                    .nextInt(110), 20 + random.nextInt(110)));

//            x = x - random.nextInt(fontSize / 2) + fontSize + 4;
            x = x - random.nextInt(3) + fontSize;
            int y = random.nextInt(height - fontSize) / 2 + fontSize;

            g.drawString(tmp, x, y);
//            logger.debug("tmp:[" + tmp + "]   x:[" + x + "]   y:[" + y + "]  fontStyle :[" + fontStyle + "]   fontSize  :  [" + fontSize + "]");

        }

        g.dispose();
        image = getDistortedImage(image);

        return image;
    }

    @Override
    public Captcha generateCaptcha() {
//         测试固定为123456,目前正式使用前换成随机生成的验证码
//        String captchaCode = "123456";
        String captchaCode = RandCharUtil.getRandStr(4);
        BufferedImage image = createTextImg2(captchaCode);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPEG", bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Captcha captcha = new Captcha(captchaCode, bos.toByteArray());
        return captcha;
    }
}
