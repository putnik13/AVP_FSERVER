package com.atanor.fserver.api.socket;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CommandServerTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		CommandServer server = new CommandServer();
		TimeUnit.SECONDS.sleep(10);
		server.send("ZZZZZZ");
	}

}
