package cn.mocredit.gateway.posp.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class TextLineCodecEncoder extends ProtocolEncoderAdapter {

    @SuppressWarnings("unused")
	private Charset charset;

    public TextLineCodecEncoder(Charset charset) {
        this.charset = charset;
    }

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
            throws Exception {
        if (message == null) {
            return;
        }
        if (session.isClosing()) {
            return;
        }
        if (message instanceof byte[]) {
           
//            if (data.indexOf("<cross-domain-policy>") == -1) {
//                String enterStr = "\n";
//                if (data.lastIndexOf(enterStr) == -1) {
//                    data += enterStr;
//                }
//            }
            byte[] bytes = (byte[]) message;
            IoBuffer buff = IoBuffer.allocate(bytes.length);
            buff.put(bytes);
            buff.flip();
            session.write(buff);
        } else if (message instanceof IoBuffer) {
            session.write(message);
        }
    }
}