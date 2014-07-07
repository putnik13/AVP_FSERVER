package com.atanor.fserver.monitor;

import java.io.IOException;

import org.apache.commons.io.FileSystemUtils;

public class DiskSpaceTest {

	public static void main(String[] args) throws IOException {
		final double freeDiskSpace = FileSystemUtils.freeSpaceKb("/home/fserver/recordings");
		System.out.println("Disk Space: " + Math.round(freeDiskSpace / 1024) + " mb");
	}

}
