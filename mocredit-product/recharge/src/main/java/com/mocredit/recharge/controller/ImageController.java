package com.mocredit.recharge.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octo.captcha.service.image.ImageCaptchaService;

@Controller
@RequestMapping("/")
public class ImageController {
	@Autowired
	private ImageCaptchaService imageCaptchaService;

	@RequestMapping("imageview")
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		// the output stream to render the captcha image as jpeg into
		// get the session id that will identify the generated captcha.
		// the same id must be used to validate the response, the session id
		// is a good candidate!
		String captchaId = httpServletRequest.getSession().getId();
		// call the ImageCaptchaService getChallenge method
		BufferedImage challenge = imageCaptchaService.getImageChallengeForID(captchaId, httpServletRequest.getLocale());
		// a jpeg encoder

		// flush it in the response
		httpServletResponse.setHeader("Cache-Control", "no-store");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		httpServletResponse.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
		ImageIO.write(challenge, "JPEG", responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();
		return null;
	}

}
