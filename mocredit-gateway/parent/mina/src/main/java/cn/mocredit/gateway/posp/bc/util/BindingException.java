package cn.mocredit.gateway.posp.bc.util;

public class BindingException extends Exception {
	  private static final long serialVersionUID = -8299414497465040391L;

	  public BindingException(String msg)
	  {
	    super(msg);
	  }

	  public BindingException(String msg, Throwable cause) {
	    super(msg, cause);
	  }

	  public BindingException(Throwable cause) {
	    super(cause);
	  }
}
