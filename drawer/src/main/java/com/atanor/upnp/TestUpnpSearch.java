package com.atanor.upnp;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.atanor.net.ftp.FtpSimpleClient;

public class TestUpnpSearch {

	// private static String ftpHost;
	private static List<String> ftpHosts;

	public static void main(String... args) throws InterruptedException, SocketException, IOException {
		String login = "videofs";
		String password = "videofs";

		UpnpSearch upnpSearch = new UpnpSearch();

		// host 1: uuid:f58f1418-77ac-44fc-81f0-88ec11899859 - ip: 192.168.56.1
		// ftpHost = upnpSearch.getAllHosts().get(1).split(" - ip: ")[1].trim();

		ftpHosts = upnpSearch.getAllHosts();

		upnpSearch.finish();

		for (String ftpHostMessage : ftpHosts) {
			String ftpHost = ftpHostMessage.split(" - ip: ")[1].trim();

			try {
				FtpSimpleClient client = new FtpSimpleClient(ftpHost, login, password);
				System.out.println(client.getClientConnectionMessage());
				System.out.println(client.sendFile("/home/incu6us/test1.png"));
				System.out.println(client.retriveFile("test1.png"));
				client.disconnect();
			} catch (Exception e) {
			}
		}
	}
}
