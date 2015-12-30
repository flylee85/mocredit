package com.mocredit.recharge.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mocredit.recharge.service.RechargeService;
import com.octo.captcha.service.image.ImageCaptchaService;

@Controller
@RequestMapping("/")
public class RechargeController {
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private RechargeService rechargeService;

	/**
	 * 充值
	 * 
	 * @param request
	 * @param phone
	 * @param phone2
	 * @param charcode
	 * @param rand
	 * @return
	 */
	@RequestMapping("/recharge")
	public ModelAndView recharge(HttpServletRequest request, String phone, String phone2, String charcode,
			String rand) {
		ModelAndView modelAndView = new ModelAndView("recharge");
		if ((phone == null || "".equals(phone)) && (phone2 == null || "".equals(phone2))
				&& (charcode == null || "".equals(charcode)) && (rand == null || "".equals(rand))) {
			return modelAndView;
		}
		boolean isOk = imageCaptchaService.validateResponseForID(request.getSession().getId(), rand);
		// boolean isOk = true;
		if (!isOk) {
			modelAndView.addObject("message", "Error:输入有误，验证码错误");
			return modelAndView;
		}
		if (phone == null || "".equals(phone) || phone2 == null || "".equals(phone2)) {
			modelAndView.addObject("message", "Error:输入有误，手机号不能为空");
			return modelAndView;
		}
		if (!isMobileNO(phone)) {
			modelAndView.addObject("message", "Error:输入有误，手机号格式错误");
			return modelAndView;
		}
		if (!phone.equals(phone2)) {
			modelAndView.addObject("message", "Error:输入有误，两次输入手机号不同");
			return modelAndView;
		}
		if (charcode == null || "".equals(charcode)) {
			modelAndView.addObject("message", "Error:输入有误，礼券号不能为空");
			return modelAndView;
		}
		String message = rechargeService.rechargeByCode(charcode, phone, request.getServerName());
		if (message.startsWith("Success:")) {
			return new ModelAndView("win");
		}
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	/**
	 * 充值异步通知
	 * 
	 * URL:http://XXXX/notice?channel=1
	 * 
	 * @param request
	 * @param channel
	 *            通道:1欧飞充值；2:易充宝
	 * @param param
	 * @return
	 */
	@RequestMapping("/notice")
	@ResponseBody
	public String notice(HttpServletRequest request, String channel, @RequestParam Map<String, Object> param) {
		if (StringUtils.isEmpty(channel) || null == param || param.isEmpty()) {
			return "error";
		}
		rechargeService.notice(param, channel);
		return "success";
	}

	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
