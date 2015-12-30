package cn.mocredit.gateway.posp.bc.util;

import java.io.ByteArrayOutputStream;

public class BCD {
	
	public static byte[] str2Bcd(String asc) {
	    int len = asc.length();
	    int mod = len % 2;
	
	    if (mod != 0) {
	     asc = "0" + asc;
	     len = asc.length();
	    }
	
	    byte abt[] = new byte[len];
	    if (len >= 2) {
	     len = len / 2;
	    }
	
	    byte bbt[] = new byte[len];
	    abt = asc.getBytes();
	    int j, k;
	
	    for (int p = 0; p < asc.length()/2; p++) {
	     if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
	      j = abt[2 * p] - '0';
	     } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
	      j = abt[2 * p] - 'a' + 0x0a;
	     } else {
	      j = abt[2 * p] - 'A' + 0x0a;
	     }
	
	     if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
	      k = abt[2 * p + 1] - '0';
	     } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
	      k = abt[2 * p + 1] - 'a' + 0x0a;
	     }else {
	      k = abt[2 * p + 1] - 'A' + 0x0a;
	     }
	
	     int a = (j << 4) + k;
	     byte b = (byte) a;
	     bbt[p] = b;
	    }
	    return bbt;
	}
	
	public static String bcd2str(byte[] bt, int len, String compressAlign)
		    throws FormatException
		  {
		    StringBuffer sb = new StringBuffer();

		    for (int i = 0; i < bt.length; i++) {
		      if (len % 2 != 0) {
		        if ("R".equals(compressAlign)) {
		          if (i == 0) {
		            byteL2Char(bt[i], sb);
		          }
		          else {
		            byteH2Char(bt[i], sb);
		            byteL2Char(bt[i], sb);
		          }
		        }
		        else if (i != bt.length - 1) {
		          byteH2Char(bt[i], sb);
		          byteL2Char(bt[i], sb);
		        }
		        else {
		          byteH2Char(bt[i], sb);
		        }
		      }
		      else {
		        byteH2Char(bt[i], sb);
		        byteL2Char(bt[i], sb);
		      }
		    }
		    return sb.toString();
		  }

		  private static void byteH2Char(byte bt, StringBuffer sb)
		    throws FormatException
		  {
		    if (((bt & 0xF0) >>> 4 >= 10) && ((bt & 0xF0) >>> 4 <= 16))
		      sb.append((char)(((bt & 0xF0) >>> 4) - 10 + 97));
		    else if (((bt & 0xF0) >>> 4 >= 0) && ((bt & 0xF0) >>> 4 <= 9))
		      sb.append((char)(((bt & 0xF0) >>> 4) + 48));
		    else
		      throw new FormatException("decode error char!");
		  }

		  private static void byteL2Char(byte bt, StringBuffer sb)
		    throws FormatException
		  {
		    if (((bt & 0xF) >= 10) && ((bt & 0xF) <= 16))
		      sb.append((char)((bt & 0xF) - 10 + 97));
		    else if (((bt & 0xF) >= 0) && ((bt & 0xF) <= 9))
		      sb.append((char)((bt & 0xF) + 48));
		    else
		      throw new FormatException("decode error char!");
		  }

		  public static byte[] str2bcd(String string, String align, int blank)
		    throws FormatException
		  {
		    int len = string.length() % 2;
		    if (len != 0) {
		      char compressBlank = (char)blank;
		      if ("R".equals(align))
		        string = compressBlank + string;
		      else
		        string = string + compressBlank;
		    }
		    ByteArrayOutputStream bs = new ByteArrayOutputStream();
		    char[] ch = string.toCharArray();
		    for (int i = 0; i < ch.length; i += 2) {
		      int hight = 0;
		      int low = 0;
		      hight = char2Int(ch[i]);
		      low = char2Int(ch[(i + 1)]);
		      bs.write(hight << 4 | low);
		    }

		    return bs.toByteArray();
		  }
		  private static int char2Int(char ch) throws FormatException {
		    int intValue = 0;
		    if ((ch >= '0') && (ch <= '9'))
		    {
		      intValue = ch - '0';
		    } else if ((ch >= 'a') && (ch <= 'f')) {
		      intValue = ch - 'a' + 10;
		    }
		    else if ((ch >= 'A') && (ch <= 'F'))
		      intValue = ch - 'A' + 10;
		    else {
		      throw new FormatException("error char [" + ch + "]");
		    }
		    return intValue;
		  }

		  public static byte[] getBinaryBuf(int len, int compress)
		  {
		    byte[] temp;
		    if (compress > 0)
		    {
		      if (len % 2 == 0)
		        temp = new byte[len / 2];
		      else
		        temp = new byte[len / 2 + 1];
		    }
		    else
		    {
		      if (compress < 0) {
		        temp = new byte[len * 2];
		      }
		      else
		        temp = new byte[len];
		    }
		    return temp;
		  }
}
