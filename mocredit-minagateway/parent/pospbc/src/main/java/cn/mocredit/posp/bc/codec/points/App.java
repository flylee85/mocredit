package cn.mocredit.posp.bc.codec.points;

import org.apache.log4j.Logger;

import cn.mocredit.posp.bc.binding.BinarySource;
import cn.mocredit.posp.bc.binding.XMLResult;
import cn.mocredit.posp.bc.cipher.MacECBUtils;
import cn.mocredit.posp.bc.util.BCD;
import cn.mocredit.posp.bc.util.FormatException;
import cn.mocredit.posp.bc.util.HexBinary;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger logger = Logger.getLogger(App.class);
    public static void main( String[] args ) {
    	/**消费*/
//    	String message="00B460000583816022000000000200302404C030C09815000000000000000999000161350102100406354218710000002182D35011010593158700000080994218710000002182D1561560000000003254003000000034343435010DDD00000000593158700030303030303030313330353439313037303131303036333135362E1BDD09A3C4897D260000000000000000132200039900050000194143544E3030303030303030303030303332313046364332343444";
    	/**消费撤销*/
    	String message="0071600005838360220000000004007024048002C0801516356857011000005600000000000000099900016234060210043938303030303030303133303534393130373031313030363331353600132200039900050000194143544E3030303030303030303030303332314543333045303434";
    	PointsDecoder decoder = new PointsDecoder(null);
    	XMLResult posResult = new XMLResult();
		BinarySource posSource = new BinarySource();
    	try {
			decoder.decode(message, posSource, posResult, "0");
		} catch (FormatException e) {
			e.printStackTrace();
		}
    }
}
