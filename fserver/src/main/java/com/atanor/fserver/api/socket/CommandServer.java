package com.atanor.fserver.api.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandServer {

	private static final Logger LOG = LoggerFactory.getLogger(CommandServer.class);

	private static final String SESSION_CLOSED = "***** Session with FServer closed. *****";
	private static final String SESSION_OPENED = "***** Session with FServer opened. *****";

	private final IoAcceptor acceptor;
	
	public CommandServer() throws IOException {

		acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.setHandler(new IoHandlerAdapter() {

			@Override
			public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
				LOG.error("Error occured in process of socket communication", cause);
			}

			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				final String msg = ((String) message).trim();
				LOG.info(">>> Socket msg received: " + msg);

				switch (msg) {
				case "help":
					writeHelp(session);
					break;
				case "cmnds":
					writeUsage(session);
					break;
				case "q":
					session.write(SESSION_CLOSED);
					session.close(false);
					break;
				default:
					session.write("Command unknown! Please use 'help' to send accepted command");
					break;
				}
			}

			@Override
			public void messageSent(IoSession session, Object message) throws Exception {
				LOG.info("<<< Socket msg sent: " + (String) message);
			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
				LOG.info(SESSION_CLOSED);
			}

			@Override
			public void sessionOpened(IoSession session) throws Exception {
				LOG.info(SESSION_OPENED);
				session.write(SESSION_OPENED);
			}

		});
		
		acceptor.bind(new InetSocketAddress(12345));
	}
	
	public void send(final String msg){
		acceptor.broadcast(msg);
	}
	
	private void writeHelp(final IoSession session) {
		session.write("\n");
		session.write("-- FSERVER API --");
		session.write("'help' - help options");
		session.write("'cmnds' - list of control commands");
		session.write("'q' - close session");
		session.write("\n");
	}
	
	private void writeUsage(final IoSession session) {
		session.write("\n");
		session.write("-- USAGE --");
		session.write("'startRecording' - starts video recording");
		session.write("'stopRecording' - stops video recording");
		session.write("'addChapter' - adds chapter tag for video");
		session.write("\n");
	}
	
}
