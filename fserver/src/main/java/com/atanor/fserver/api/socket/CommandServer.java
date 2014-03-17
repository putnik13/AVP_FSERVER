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
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atanor.fserver.api.Error;
import com.atanor.fserver.api.Signal;
import com.atanor.fserver.config.Config;
import com.atanor.fserver.events.ProcessInterruptedEvent;
import com.atanor.fserver.events.RecordingAlarmEvent;
import com.atanor.fserver.facades.VideoFacade;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class CommandServer {

	private static final Logger LOG = LoggerFactory.getLogger(CommandServer.class);

	private static final String SESSION_CLOSED = "***** Session with FServer closed *****";
	private static final String SESSION_OPENED = "***** Session with FServer opened *****";
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@Inject
	private VideoFacade videoFacade;

	private final IoAcceptor acceptor;

	@Inject
	public CommandServer(final EventBus eventBus, final Config config) throws IOException {
		eventBus.register(this);

		acceptor = new NioSocketAcceptor();

		// acceptor.getFilterChain().addLast("logger", new LoggingFilter());
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
				case "startRecordingAndRedirect":
					handleStartRecordingAndRedirect(session);
					break;
				case "stopRecordingAndRedirect":
					handleStopRecordingAndRedirect(session);
					break;
				case "signals":
					writeSignals(session);
					break;
				case "q":
					writeResponse(session, SESSION_CLOSED);
					session.close(false);
					break;
				default:
					writeResponse(session, "Command unknown! Please use 'help' to send accepted command");
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
				writeResponse(session, SESSION_OPENED);
			}

		});
		
		LOG.info("##### Try to bind socket port: " + config.getSocketApiPort());
		acceptor.bind(new InetSocketAddress(config.getSocketApiPort()));
		
		addShutdownHook();
	}

	public void send(final String msg) {
		acceptor.broadcast(msg + LINE_SEPARATOR);
	}

	private void writeHelp(final IoSession session) {
		final StringBuilder sb = new StringBuilder();
		sb.append(LINE_SEPARATOR);
		sb.append("-- FSERVER API --").append(LINE_SEPARATOR);
		sb.append("'help' - help options").append(LINE_SEPARATOR);
		sb.append("'cmnds' - list of control commands").append(LINE_SEPARATOR);
		sb.append("'signals' - list of signal codes").append(LINE_SEPARATOR);
		sb.append("'q' - close session").append(LINE_SEPARATOR);
		writeResponse(session, sb.toString());
	}

	private void writeCommands(final IoSession session) {
		final StringBuilder sb = new StringBuilder();
		sb.append(LINE_SEPARATOR);
		sb.append("-- USAGE --").append(LINE_SEPARATOR);
		sb.append("'startRecording' - Starts video recording").append(LINE_SEPARATOR);
		sb.append("'stopRecording' - Stops video recording").append(LINE_SEPARATOR);
		sb.append("'addChapter' - Adds video chapter tag").append(LINE_SEPARATOR);
		sb.append("'startRedirect' - Redirects incoming stream to specified URL").append(LINE_SEPARATOR);
		sb.append("'stopRedirect' - Stops stream redirect").append(LINE_SEPARATOR);
		sb.append("'startRecordingAndRedirect' - Starts video recording and redirects to another URL").append(
				LINE_SEPARATOR);
		sb.append("'stopRecordingAndRedirect' - Stops video recording and redirection").append(LINE_SEPARATOR);
		writeResponse(session, sb.toString());
	}

	private void writeSignals(final IoSession session) {
		final StringBuilder sb = new StringBuilder();
		sb.append(LINE_SEPARATOR);
		sb.append("-- SIGNAL CODES --").append(LINE_SEPARATOR);
		sb.append("'OK' - Operation executed successfully").append(LINE_SEPARATOR);
		sb.append("'err0' - Internal server error").append(LINE_SEPARATOR);
		sb.append("'err1' - Operation in progress").append(LINE_SEPARATOR);
		sb.append("'err2' - Operation not in progress").append(LINE_SEPARATOR);
		sb.append("'err3' - Operation interrupted").append(LINE_SEPARATOR);
		sb.append("'warn1' - Low disk space").append(LINE_SEPARATOR);
		sb.append("'warn2' - Recording file is empty").append(LINE_SEPARATOR);
		sb.append("'warn3' - Recording size is not changed").append(LINE_SEPARATOR);
		writeResponse(session, sb.toString());
	}

	private void handleStartRecording(final IoSession session) {
		final Signal response = videoFacade.startRecording();
		writeResponse(session, response.getCode());
	}

	private void handleStopRecording(final IoSession session) {
		final Signal response = videoFacade.stopRecording();
		writeResponse(session, response.getCode());
	}

	private void handleAddChapter(final IoSession session) {
		final Signal response = videoFacade.addChapterTag();
		writeResponse(session, response.getCode());
	}

	private void handleStartRedirect(final IoSession session) {
		final Signal response = videoFacade.startStreamRedirect();
		writeResponse(session, response.getCode());
	}

	private void handleStopRedirect(final IoSession session) {
		final Signal response = videoFacade.stopStreamRedirect();
		writeResponse(session, response.getCode());
	}

	private void handleStartRecordingAndRedirect(final IoSession session) {
		final Signal response = videoFacade.startRecordingAndRedirect();
		writeResponse(session, response.getCode());
	}

	private void handleStopRecordingAndRedirect(final IoSession session) {
		final Signal response = videoFacade.stopRecordingAndRedirect();
		writeResponse(session, response.getCode());
	}

	private void writeResponse(final IoSession session, final String response) {
		session.write(response + LINE_SEPARATOR);
	}

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				LOG.warn("!!! Shutdown hook to release socket port");
				try {
					acceptor.unbind();
					acceptor.dispose();
				} catch (Exception e) {
					LOG.error("Unexpected exception while shutting down", e);
				}
			}
		}));
	}

	@Subscribe
	public void onProcessInterrupted(final ProcessInterruptedEvent event) {
		send(Error.OPERATION_INTERRUPTED.getCode());
	}
	
	@Subscribe
	public void onRecordingAlarm(final RecordingAlarmEvent event) {
		send(event.getAlarm().getCode());
	}
}
