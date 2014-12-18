package com.atanor.net.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpSimpleClient {

	private FTPClient client;
	private int reply;
	private String messagesWhileConnection;
	private List<String> filesAtTheRoot;

	public FtpSimpleClient(String host) throws SocketException, IOException {
		client = new FTPClient();
		client.connect(host);
		reply = client.getReplyCode();

		// check if socket opened
		if (!FTPReply.isPositiveCompletion(reply)) {
			client.disconnect();
			messagesWhileConnection = "FTP server refused connection.";
		}else{
			messagesWhileConnection = "FTP server connection established.";
		}

		client.login("anonymous", "anonymous");

		client.enterLocalPassiveMode();
		client.setFileType(FTP.BINARY_FILE_TYPE);
	}

	public FtpSimpleClient(String host, String login, String password) throws SocketException, IOException {
		client = new FTPClient();
		client.connect(host);
		reply = client.getReplyCode();

		// check if socket opened
		if (!FTPReply.isPositiveCompletion(reply)) {
			client.disconnect();
			messagesWhileConnection = "FTP server refused connection.";
		}else{
			messagesWhileConnection = "FTP server connection established.";
		}

		client.login(login, password);

		client.enterLocalPassiveMode();
		client.setFileType(FTP.BINARY_FILE_TYPE);
	}

	public String getClientConnectionMessage(){
		return messagesWhileConnection;
	}
	
	// list files
	public List<String> getFiles() throws IOException {
		filesAtTheRoot = new ArrayList<String>();
		FTPFile[] files = client.listFiles();

		for (FTPFile file : files) {
			if (file.isFile()) {
				filesAtTheRoot.add(file.getName());
			}
		}

		return filesAtTheRoot;
	}

	// store file to the server
	public String sendFile(String file) throws IOException {
		File secondLocalFile = new File(file);
		String secondRemoteFile = secondLocalFile.getName();
		InputStream inputStream = new FileInputStream(secondLocalFile);

//		System.out.println("Start uploading file");
		OutputStream outputStream = client.storeFileStream(secondRemoteFile);
		byte[] bytesIn = new byte[4096];
		int read = 0;

		while ((read = inputStream.read(bytesIn)) != -1) {
			outputStream.write(bytesIn, 0, read);
		}
		inputStream.close();
		outputStream.close();

		boolean completed = client.completePendingCommand();
		if (completed) {
			return "The file is uploaded successfully.";
		}
		return "Upload failed!";
	}

	// retrive file from server
	public String retriveFile(String file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		boolean completed = client.retrieveFile(file, fos);
		if (completed) {
			return "Retrive successfully.";
		}
		return "Retrive failed!";
	}

	public void disconnect() throws IOException {
		if (client.isConnected()) {
			client.logout();
			client.disconnect();
		}
	}
}
