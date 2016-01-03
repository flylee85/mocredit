package cn.mocredit.gateway.thirdparty.monitor;

import static java.util.Collections.list;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ControllerMonitor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before("execution(public * cn.mocredit.gateway.thirdparty.controller.*.*(..))")
	public void before(JoinPoint jp) {
		final Object target = jp.getTarget();
		String log = "访问日志: 用户“" + getLoginName() + "”开始访问 "
				+ (target == null ? jp.toString() : (target.getClass().getName() + "#" + jp.getSignature().getName()))
				+ " 参数为： " + Arrays.toString(jp.getArgs())+"\r\n"+getHttpParameters();
		if (!log.contains(".lambda$")) {
			logger.info(log);
		}
	}

	@AfterReturning(value = "execution(public * cn.mocredit.gateway.thirdparty.controller.*.*(..))", returning = "ret")
	public void afterReturning(JoinPoint jp, Object ret) {
		final Object target = jp.getTarget();
		String log = "访问日志: 用户“" + getLoginName() + "”访问完毕了 "
				+ (target == null ? jp.toString() : (target.getClass().getName() + "#" + jp.getSignature().getName()))
				+ " 参数为： " + Arrays.toString(jp.getArgs()) +"\r\n"+getHttpParameters()+  "返回值为：" + ret;
		if (!log.contains(".lambda$")) {
			logger.info(log);
		}
	}

	@AfterThrowing(value = "execution(* cn.mocredit.gateway.thirdparty.controller.*.*(..))", throwing = "ex")
	public void afterThrowing(JoinPoint jp, Throwable ex) {
		final Object target = jp.getTarget();
		String log = "访问日志: 用户“" + getLoginName() + "”访问该方法时出错： "
				+ (target == null ? jp.toString() : (target.getClass().getName() + "#" + jp.getSignature().getName()))
				+ "\r\n参数为： " + Arrays.toString(jp.getArgs()) +"\r\n"+getHttpParameters()+ "\r\n异常信息为：\r\n" + getStackTrace(ex);
		logger.info(log);
	}

	private String getLoginName() {
		return ((ServletRequestAttributes) currentRequestAttributes()).getRequest().getRemoteAddr();
	}
	private String getHttpParameters() {
		RequestAttributes ra = currentRequestAttributes();
		if (ra==null||!(ra instanceof ServletRequestAttributes)) {
			return "";
		}
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		if (request==null) {
			return "";
		}
		ArrayList<String> list = list(request.getParameterNames());
		Map<String, String[]> m = request.getParameterMap();
		if (m==null) {
			return "";
		}
		String collect = list.stream() .map(s -> "\r\n\t"+s + ":" + (m.get(s) == null ? "[]" : Arrays.toString(m.get(s)))) .collect(joining(";"));
		return "HTTP参数为：{" + collect + "\r\n}";
	}
}
