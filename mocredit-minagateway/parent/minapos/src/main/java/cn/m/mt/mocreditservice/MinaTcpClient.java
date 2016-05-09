package cn.m.mt.mocreditservice;

import cn.m.mt.mocreditservice.filter.TextLineCodecFactory;
import cn.m.mt.mocreditservice.util.BcdUtils;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

import static cn.m.mt.mocreditservice.util.BcdUtils.str2Bcd;

public class MinaTcpClient extends IoHandlerAdapter  {
    private  CountDownLatch latch;
    public MinaTcpClient(CountDownLatch latch) {
        this.latch=latch;
    }

    public static void main(String[] args) throws Exception {
        String receive = send("localhost", 9125, 40000, "005E6000320000603100310005128000A0048020C00011424F4230303220202020000089022000371001888888888801145D99125219260000000038323339323934383030303030303030303031313134380008030000304331354443343643");
        System.out.println(receive);
    }

    public static String send(String hostname, int port, int timeoutMillis, String message) {
        IoConnector c = null;
        IoSession session = null;
        String receive = null;
        try {
            c = new NioSocketConnector();
            c.setConnectTimeoutMillis(timeoutMillis);
            c.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
            CountDownLatch latch = new CountDownLatch(1);
            MinaTcpClient h = new MinaTcpClient(latch);
            c.setHandler(h);
            ConnectFuture cf = c.connect(new InetSocketAddress(hostname, port));
            cf.awaitUninterruptibly();
            session = cf.getSession();
            session.write(str2Bcd(message));
            latch.await();
            receive = h.getReceive();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.dispose();
            session.close(true);
        }
        return receive;
    }
    private String receive;

    public String getReceive() {
        return receive;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        receive= message.toString();
        latch.countDown();
    }
}