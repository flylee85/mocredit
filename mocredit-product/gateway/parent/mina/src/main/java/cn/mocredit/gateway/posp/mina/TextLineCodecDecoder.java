package cn.mocredit.gateway.posp.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
public class TextLineCodecDecoder extends CumulativeProtocolDecoder {

	private Charset charset;

	public TextLineCodecDecoder(Charset charset) {
		this.charset = charset;
	}

	/**
	 * 数据单步解码
	 * 
	 * @param session
	 * @param in
	 * @param out
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		if (session.isConnected() == false) {
			in.clear();
			return false;
		}
		if (session.isClosing()) {
			in.clear();
			return false;
		}
		in.mark();
		Object boolObj = session.getAttribute("bool");
		if (boolObj == null) {
			if (in.remaining() >= 23) {
				byte[] bytes = new byte[23];
				in.get(bytes, 0, 23);
				session.setAttribute("bool", true);
				try {
					String data = new String(bytes, charset);
					if (data.indexOf("<policy-file-request/>") != -1) {
						out.write(data);
						in.clear();
						return false;
					}
				} catch (Exception exception) {
					session.setAttribute("bool", true);
				}
				in.reset();
				in.mark();
			}
		}
		IoBuffer buff = IoBuffer.allocate(in.remaining());
		String str = in.getHexDump().replace(" ","");
		while (in.hasRemaining()) {
			byte b = in.get();
			buff.put(b);
		}
		in.mark();
		buff.flip();
		out.write(str);
		buff.clear();
		return true;
	}
}