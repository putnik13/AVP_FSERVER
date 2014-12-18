package com.atanor.upnp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

public class UpnpSearch {

	private String upnpInfo;
	private List<String> upnpHosts;
	private UpnpService upnpService;
	private Iterator<RemoteDevice> iter;

	private RemoteDevice rd;
	
	public UpnpSearch() throws InterruptedException {
		RegistryListener listener = new RegistryListener() {

			public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
				// System.out.println("Discovery started: " +
				// device.getDisplayString() + " " + device.getIdentity());
			}

			public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device, Exception ex) {
				// System.out.println("Discovery failed: " +
				// device.getDisplayString() + " => " + ex + " " +
				// device.getDetails());
			}

			public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
				// System.out.println("Remote device available: " +
				// device.getDisplayString() + " " + device.getDetails() + " " +
				// device.getIdentity());
			}

			public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
				// System.out.println("Remote device updated: " +
				// device.getDisplayString() + " " + device.getDetails() + " " +
				// device.getIdentity());
			}

			public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
				// System.out.println("Remote device removed: " +
				// device.getDisplayString() + " " + device.getDetails());
			}

			public void localDeviceAdded(Registry registry, LocalDevice device) {
				// System.out.println("Local device added: " +
				// device.getDisplayString() + " " + device.getDetails());
			}

			public void localDeviceRemoved(Registry registry, LocalDevice device) {
				// System.out.println("Local device removed: " +
				// device.getDisplayString() + " " + device.getDetails());
			}

			public void beforeShutdown(Registry registry) {
				// System.out.println("Before shutdown, the registry has devices: "
				// + registry.getDevices().size());
			}

			public void afterShutdown() {
				// System.out.println("Shutdown of registry complete!");

			}
		};

		// This will create necessary network resources for UPnP right away
		// System.out.println("Starting Cling...");
		upnpService = new UpnpServiceImpl(listener);

		// Send a search message to all devices and services, they should
		// respond soon
		// System.out.println("Sending SEARCH message to all devices...");
		upnpService.getControlPoint().search(new STAllHeader());

		// Let's wait 10 seconds for them to respond
		// System.out.println("Waiting 10 seconds before shutting down...");
		Thread.sleep(10000);

		final ControlPoint cp = upnpService.getControlPoint();
		cp.getConfiguration();

		Registry reg = cp.getRegistry();
		// System.out.println("Devices found: " + reg.getDevices().size());

		iter = reg.getRemoteDevices().iterator();
	}
	

	public List<String> getAllHosts() throws InterruptedException {

		upnpHosts = new ArrayList<String>();
		int counter = 0;
		while (iter.hasNext()) {
			rd = iter.next();
			// System.out.println("Device: " + rd);
			// System.out.println("UDN: " + rd.getIdentity().getUdn());
			// System.out.println("URL: " +
			// rd.getIdentity().getDescriptorURL());
			// System.out.println("Age(sec): " +
			// rd.getIdentity().getMaxAgeSeconds());
			// System.out.println("UUID: "+rd.getIdentity().getUdn().getIdentifierString());

			upnpInfo = "host " + (counter + 1) + ": " + rd.getIdentity().getUdn() + " - ip: " + rd.getIdentity().getDescriptorURL().getHost();

			upnpHosts.add(upnpInfo);
			counter++;
		}

		return upnpHosts;
	}
	
	public void finish(){
		upnpService.shutdown();
	}
}
