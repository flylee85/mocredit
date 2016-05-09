package cn.m.mt.mocreditservice.filter;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 *
 * @author zkpursuit
 */
public class TextLineCodecFactory implements ProtocolCodecFactory {

    private final TextLineCodecEncoder encoder;
    private final TextLineCodecDecoder decoder;

    public TextLineCodecFactory() {
        this(Charset.defaultCharset());
    }

    public TextLineCodecFactory(Charset charSet) {
        this.encoder = new TextLineCodecEncoder(charSet);
        this.decoder = new TextLineCodecDecoder(charSet);
    }

    public TextLineCodecFactory(String charsetName) {
        this(Charset.forName(charsetName));
    }

    public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
        return this.decoder;
    }

    public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
        return this.encoder;
    }
}