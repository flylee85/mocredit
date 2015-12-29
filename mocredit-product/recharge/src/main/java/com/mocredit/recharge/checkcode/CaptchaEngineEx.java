package com.mocredit.recharge.checkcode;

import java.awt.Font;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * Captcha��ǿ�汾
 * 
 * @author david.turing@gmail.com
 * @modifyTime 21:01:52
 * @description
 * 
 * <pre>
 *   
 *  ��װ Captcha Instruction
 * <br>
 *   
 *  1.add captchaValidationProcessingFilter   
 *    to applicationContext-acegi-security.xml
 * <br>
 *   
 *  2.modify applicationContext-captcha-security.xml  
 * <ul>  
 *    <li>
 *  make sure that captchaValidationProcessingFilter Call captchaService  
 * <li>
 *  config CaptchaEngine for captchaService (refer imageCaptchaService)   
 * <li>
 *  write your own CaptchaEngine  
 * <li>
 *  config the following, so that We use CaptchaEngineEx to generate the   
 *  captcha image.   
 * </ul>
 *   
 *  &lt;constructor-arg  
 *              type=&quot;com.octo.captcha.engine.CaptchaEngine&quot; index=&quot;1&quot;&gt;   
 *              &lt;ref bean=&quot;captchaEngineEx&quot;/gt; &lt;/constructor-arg&gt;   
 * </pre>
 */
public class CaptchaEngineEx extends ListImageCaptchaEngine {
	/**
	 * ...
	 */
	/*
	protected void buildInitialFactories() {

		// Set Captcha Word Length Limitation which should not over 6
		Integer minAcceptedWordLength = new Integer(4);
		Integer maxAcceptedWordLength = new Integer(5);
		// Set up Captcha Image Size: Height and Width
		Integer imageHeight = new Integer(40);
		Integer imageWidth = new Integer(100);

		// Set Captcha Font Size
		Integer minFontSize = new Integer(20);
		Integer maxFontSize = new Integer(22);
		// We just generate digit for captcha source char Although you can use
		// abcdefghijklmnopqrstuvwxyz
		WordGenerator wordGenerator = new RandomWordGenerator("0123456789");

		// cyt and unruledboy proved that backgroup not a factor of Security. A
		// captcha attacker won't affaid colorful backgroud, so we just use
		// white
		// color, like google and hotmail.
		BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(
				imageWidth, imageHeight, Color.white, Color.white);

		// font is not helpful for security but it really increase difficultness
		// for
		// attacker
		FontGenerator fontGenerator = new RandomFontGenerator(minFontSize,
				maxFontSize);
		// Note that our captcha color is Blue
		SingleColorGenerator scg = new SingleColorGenerator(Color.blue);

		// decorator is very useful pretend captcha attack. we use two line text
		// decorators.

		LineTextDecorator lineDecorator = new LineTextDecorator(1, Color.blue);
		// LineTextDecorator line_decorator2 = new LineTextDecorator(1,
		// Color.blue);
		TextDecorator[] textdecorators = new TextDecorator[1];

		textdecorators[0] = lineDecorator;
		// textdecorators[1] = line_decorator2;

		TextPaster textPaster = new DecoratedRandomTextPaster(
				minAcceptedWordLength, maxAcceptedWordLength, scg,
				new TextDecorator[] { new BaffleTextDecorator(new Integer(1),
						Color.white) });

		// ok, generate the WordToImage Object for logon service to use.
		WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
				backgroundGenerator, textPaster);
		addFactory(new GimpyFactory(wordGenerator, wordToImage));
	}
	*/
	protected void buildInitialFactories() {
		// �����ɵ��ַ�
		WordGenerator wgen = new RandomWordGenerator(
		"0123456789");
		RandomRangeColorGenerator cgen =
		new RandomRangeColorGenerator(
		new int[] { 0, 100 }, new int[] { 0, 100 },
		new int[] { 0, 100 });
		// ������ʾ�ĸ���
		TextPaster textPaster = new RandomTextPaster(new Integer(4),
		new Integer(4), cgen, true);
		// ͼƬ�Ĵ�С
		BackgroundGenerator backgroundGenerator =
		new FunkyBackgroundGenerator(
		new Integer(75), new Integer(34));
		// �����ʽ
		Font[] fontsList = new Font[] {
		new Font("Times New Roman", Font.PLAIN, 18)};
		// ���ֵĴ�С
		FontGenerator fontGenerator =
		new RandomFontGenerator(new Integer(18),
		new Integer(18), fontsList);    

		WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
		backgroundGenerator, textPaster);
		this.addFactory(new GimpyFactory(wgen, wordToImage));
		}

}
