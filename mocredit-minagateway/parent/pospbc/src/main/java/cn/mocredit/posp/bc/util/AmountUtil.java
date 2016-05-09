package cn.mocredit.posp.bc.util;

import java.math.BigDecimal;


/**
 * 货币金额处理
 * @author 杜超
 */
public class AmountUtil {

	/**
	 * 
	 * @param firstAmount 金额一
	 * @param secondAmount　金额二
	 * @return 金额一 + 金额二
	 * @throws RuntimeException
	 */
	public static String sumAmount(String firstAmount, String secondAmount)throws RuntimeException{
		
			BigDecimal bigFirstAmount = new BigDecimal(firstAmount);
			BigDecimal bigSecondAmount = new BigDecimal(secondAmount);
			return bigFirstAmount.add(bigSecondAmount).toString();
	}
}
