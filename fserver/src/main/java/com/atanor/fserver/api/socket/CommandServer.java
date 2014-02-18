package com.atanor.fserver.api.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.Signal;
import com.atanor.fserver.config.Config;
import com.atanor.fserver.facades.VideoFacade;

public class CommandServer {

	private static final Logger LOG = LoggerFactory.getLogger(CommandServer.class);

	private static final String SESSION_CLOSED = "***** Session with FServer closed *****";
	private static final String SESSION_OPENED = "***** Session with FServer opened *****";

	@Inject
	private VideoFacade videoFacade;

	private final IoAcceptor acceptor;

	@Inject
	public CommandServer(final Config config) throws IOException {

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
					writeCommands(session);
					break;
				case "startRecording":
					handleStartRecording(session);
					break;
				case "stopRecording":
					handleStopRecording(session);
					break;
				case "addChapter":
					handleAddChapter(session);
					break;
				case "startRedirect":
					handleStartRedirect(session);
					break;
				case "stopRedirect":
					handleStopRedirect(session);
					break;
				case "signals":
					writeSignals(session);
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

		LOG.info("##### Try to bind socket port: " + config.getSocketApiPort());
		acceptor.bind(new InetSocketAddress(config.getSocketApiPort()));
	}

	public void send(final String msg) {
		acceptor.broadcast(msg);
	}

	private void writeHelp(final IoSession session) {
		session.write("\n");
		session.write("-- FSERVER API --");
		session.write("'help' - help options");
		session.write("'cmnds' - list of control commands");
		session.write("'signals' - list of signal codes");
		session.write("'q' - close session");
		session.write("\n");
	}

	private void writeCommands(final IoSession session) {
		session.write("\n");
		session.write("-- USAGE --");
		session.write("'startRecording' - Starts video recording");
		session.write("'stopRecording' - Stops video recording");
		session.write("'addChapter' - Adds video chapter tag");
		session.write("'startRedirect' - Redirects incoming stream to specified URL");
		session.write("'stopRedirect' - Stops stream redirect");
		session.write("'startRecordingAndRedirect' - Starts video recording and redirects to another URL");
		session.write("'stopRecordingAndRedirect' - Stops video recording and redirection");
		session.write("\n");
	}

	private void writeSignals(final IoSession session) {
		session.write("\n");
		session.write("-- SIGNAL CODES --");
		session.write("'info0' - Operation executed successfully");
		session.write("'err0' - Internal server error");
		session.write("'err1' - Operation in progress");
		session.write("'err2' - Operation not in progress");
		session.write("\n");
	}

	private void handleStartRecording(final IoSession session) {
		final Signal response = videoFacade.startRecording();
		session.write(response.getCode());
	}

	private void handleStopRecording(final IoSession session) {
		final Signal response = videoFacade.stopRecording();
		session.write(response.getCode());
	}

	private void handleAddChapter(final IoSession session) {
		final Signal response = videoFacade.addChapterTag();
		session.write(response.getCode());
	}

	private void handleStartRedirect(final IoSession session) {
		final Signal response = videoFacade.startStreamRedirect();
		session.write(response.getCode());
	}

	private void handleStopRedirect(final IoSession session) {
		final Signal response = videoFacade.stopStreamRedirect();
		session.write(response.getCode());
	}

}
