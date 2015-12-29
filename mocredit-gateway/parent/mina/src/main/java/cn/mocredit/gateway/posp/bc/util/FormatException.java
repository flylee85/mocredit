package cn.mocredit.gateway.posp.bc.util;

public class FormatException extends BindingException {
	
	  private static final long serialVersionUID = 8755294324454694865L;

	  public FormatException(String message)
	  {
	    super(message);
	  }

	  public FormatException(Throwable cause) {
	    super(cause);
	  }

	  public FormatException(String message, Throwable cause) {
	    super(message, cause);
	  }
}
