package cn.mocredit.gateway.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class Util {

	/**
	 * 可选每页条数列表
	 */
	public static final int[] PAGE_SIZE_OPTIONS = { 10, 20, 50, 100, 200 };
    private static final ConcurrentHashMap<String,SimpleDateFormat> SDF_MAP = new ConcurrentHashMap<>();
	/**
	 * 默认每页条数
	 */
	public static final String DEFAULT_PAGE_SIZE = "20";

	public static String trimToNull(String str) {
		return str == null || str.trim().length() == 0 ? null : str.trim();
	}

	public static Long trimToNullLong(String str) {
		String s = trimToNull(str);
		return s == null ? null : Long.parseLong(s);
	}

	public static Date fmtStr2Date(String source, String pattern) {
        SimpleDateFormat sdf = SDF_MAP.computeIfAbsent(pattern, p -> new SimpleDateFormat(p));
        Date date = null;
		try {
			date = sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

    public static String fmtDate2Str(Date date ,String pattern){
        return SDF_MAP.computeIfAbsent(pattern, p -> new SimpleDateFormat(p)).format(date);
	}
	
	/**
	 * 去掉字符串末尾的一段
	 * @param str 待处理的字符串
	 * @param tail 应该去掉的末尾部分
	 * @return
	 */
	public static String cutTail(String str, final String tail) {
		return str == null ? null : (!str.endsWith(tail) ? str : str.substring(0, str.length() - tail.length()));
	}
    public static String uuid() {
        return randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 隐藏中间的字符
     *
     * @param s       字符
     * @param headLen 前面明文的长度
     * @param hide    中间用于隐藏的占位符
     * @param tailLen 末尾显示明文的长度
     * @return
     */
    public static String hide(String s, int headLen, String hide, int tailLen) {
        if (s.length() <= headLen + tailLen) return s;
        String head = s.substring(0, headLen);
        String collect = range(0, s.length() - tailLen - headLen).mapToObj(i -> hide).collect(joining());
        String tail = s.substring(s.length() - tailLen, s.length());
        return head + collect + tail;
    }

    public static String qianZhui(String s, int totalLength, char qianZhui) {
        String qz = qianZhui + "";
        return range(0, totalLength - s.length()).mapToObj(i -> qz).collect(joining("", "", s));
    }

    public static String houZhui(String s, int totalLength, char houZhui) {
        String s1 = new StringBuffer(s).reverse().toString();
        String s2 = qianZhui(s1, totalLength, houZhui);
        return new StringBuffer(s2).reverse().toString();
    }

    public static String sign(Map<String,String> map) {
        return md5Hex(map.keySet().stream().sorted().map(k -> k + "=" + map.get(k)).collect(joining("&"))).toUpperCase();
    }
}
