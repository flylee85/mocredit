package cn.mocredit.gateway.decode;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mocredit.gateway.message.MessageObject;
import cn.mocredit.gateway.posp.bc.util.FormatException;
@Service
@Transactional
public class TrancsctImpl implements Trancsct {
	@Autowired
	private HandleAction qrhandle;
	@Autowired
	private IHandleAction integralHandleAction;
	@Autowired
	private VCHandleAction valueCardhandle;
	 
	@Override
	public MessageObject qrHandle(MessageObject request) throws FormatException {
		MessageObject response = null;
		if(request.getTranCode()==null && "".equals(request.getTranCode())) {
			return response;
		}else if("1100".equals(request.getMesstype())||"1110".equals(request.getMesstype())) {
			response = qrhandle.signIn(request);
		}else if(request.getMesstype().equals("1260")){
			response = qrhandle.down(request);
		}else {
			throw new FormatException("getTranCode Exception");
		}
		return response; 
	}

	@Override
	public MessageObject integralHandle(MessageObject request) throws FormatException {
		MessageObject response = null;
		if(request.getTranCode()==null && "".equals(request.getTranCode())) {
			return response;
		}else if("1280".equals(request.getMesstype())||"1290".equals(request.getMesstype())) {
			response = integralHandleAction.purchase(request);
		}else if("1320".equals(request.getMesstype())||"1330".equals(request.getMesstype())) {
			response = integralHandleAction.reversalPurchase(request);
		}else if("1400".equals(request.getMesstype())||"1410".equals(request.getMesstype())) {
			response = integralHandleAction.revokePurchase(request);
		}
		return response;
	}

	@Override
	public MessageObject valueCardHandle(MessageObject request) throws FormatException {
		MessageObject response = null;
		if(request.getTranCode()==null && "".equals(request.getTranCode())) {
			return response;
		}else if("080000".equals(request.getTranCode())||"081000".equals(request.getTranCode())) {
			response = valueCardhandle.signIn(request);
		}else{
			throw new FormatException("getTranCode Exception");
		}
		return response; 
	}

}
