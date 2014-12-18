package com.atanor.drawer.draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.atanor.drawer.draw.Canvas.CanvasPane;
import com.atanor.drawer.main.Constants;
import com.atanor.net.ftp.FtpSimpleClient;
import com.atanor.upnp.UpnpSearch;

public class FtpPanelGet extends JFrame {

	private JPanel contentPane;
	private List<String> files;
	private JList list;
	private DefaultListModel<String> listModel;
	private DefaultListModel<String> listModelLog;
	private JProgressBar progressBar;

	private String correctFtpHost;

	private FtpSimpleClient client;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FtpPanelGet frame = new FtpPanelGet();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FtpPanelGet() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 613, 484);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnGet = new JButton("Get List");
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					public void run() {
						try {
							progressBar.setString("Running...");
							progressBar.setStringPainted(true);
							progressBar.setIndeterminate(true);

							listModelLog.addElement("Start searching UPNP hosts...");
							UpnpSearch upnpSearch = new UpnpSearch();
							List<String> ftpHosts = upnpSearch.getAllHosts();

							listModelLog.addElement("Host found:");
							listModelLog.addElement("-------------------------------------------------");
							for (String host : ftpHosts) {
								listModelLog.addElement(host);
							}

							upnpSearch.finish();

							for (String ftpHostMessage : ftpHosts) {
								String ftpHost = ftpHostMessage.split(" - ip: ")[1].trim();

								try {
									client = new FtpSimpleClient(ftpHost, Constants.ftpLogin, Constants.ftpPassword);
									String serverResponse =client.getClientConnectionMessage();
									// System.out.println(client.sendFile("/home/incu6us/test1.png"));
									// System.out.println(client.retriveFile("test1.png"));
									files = client.getFiles();
									for (String file : files) {
										listModel.addElement(file);
									}
									listModelLog.addElement("-------------------------------------------------");
									listModelLog.addElement(serverResponse);
									
									correctFtpHost = ftpHost;

									client.disconnect();

									progressBar.setStringPainted(false);
									progressBar.setIndeterminate(false);

								} catch (Exception e2) {
									progressBar.setStringPainted(false);
									progressBar.setIndeterminate(false);
								}
							}
						} catch (Exception e1) {
							progressBar.setStringPainted(false);
							progressBar.setIndeterminate(false);
						}
					}
				});

				t.setDaemon(true);
				t.start();
			}
		});
		btnGet.setBounds(22, 12, 117, 25);
		contentPane.add(btnGet);

		JButton getFileButton = new JButton("Get File");
		getFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					public void run() {
						try {
							listModelLog.addElement("Geting "+list.getSelectedValue().toString()+" file...");

							client = new FtpSimpleClient(correctFtpHost, Constants.ftpLogin, Constants.ftpPassword);
							// System.out.println(client.sendFile("/home/incu6us/test1.png"));
							// System.out.println(client.retriveFile("test1.png"));

							listModelLog.addElement(client.retriveFile(list.getSelectedValue().toString()));

							client.disconnect();
							
//							Canvas c = new Canvas();
							
							File fileImg = new File(list.getSelectedValue().toString());
							System.out.println(fileImg.getAbsolutePath());
							Canvas.extDrawImg(fileImg.getAbsolutePath());
							
						} catch (IOException e) {
							listModelLog.addElement("IOError...");
						}
					}
				});

				t.setDaemon(true);
				t.start();
			}
		});

		getFileButton.setBounds(175, 12, 117, 25);
		contentPane.add(getFileButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(304, 3, 295, 414);
		contentPane.add(scrollPane);

		listModel = new DefaultListModel<String>();

		list = new JList(listModel);
		scrollPane.setViewportView(list);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(22, 49, 270, 368);
		contentPane.add(scrollPane_1);

		listModelLog = new DefaultListModel<String>();

		JList list_1 = new JList(listModelLog);
		list_1.setBackground(Color.gray);
		scrollPane_1.setViewportView(list_1);

		progressBar = new JProgressBar();
		progressBar.setBounds(12, 429, 587, 14);
		contentPane.add(progressBar);
	}
}
