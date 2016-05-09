package cn.m.mt.util;

public class PurViewUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static boolean checkPower(int userPurview, int optPurview) {
		int purviewValue = (int) Math.pow(2, optPurview);
		boolean bool = (userPurview & purviewValue) == purviewValue;
//		//System.out.println("purviewValue---------"+purviewValue);
//		//System.out.println(optPurview+"++++++++++++++++++"+userPurview+"--------------"+bool);
		return bool;
	}
}
