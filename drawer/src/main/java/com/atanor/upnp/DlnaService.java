package com.atanor.upnp;

import java.util.Iterator;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.RemoteService;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;
import org.fourthline.cling.support.contentdirectory.DIDLParser;
import org.fourthline.cling.support.contentdirectory.callback.Browse;
import org.fourthline.cling.support.model.BrowseFlag;
import org.fourthline.cling.support.model.DIDLContent;

public class DlnaService {

	private DIDLParser parser = new DIDLParser();
	
	public static void main(String... args) throws InterruptedException {
		RegistryListener listener = new RegistryListener() {

			public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
				System.out.println("Discovery started: " + device.getDisplayString() + " " + device.getIdentity());
			}

			public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device, Exception ex) {
				System.out.println("Discovery failed: " + device.getDisplayString() + " => " + ex + " " + device.getDetails());
			}

			public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
				System.out.println("Remote device available: " + device.getDisplayString() + " " + device.getDetails() + " " + device.getIdentity());
			}

			public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
				System.out.println("Remote device updated: " + device.getDisplayString() + " " + device.getDetails() + " " + device.getIdentity());
			}

			public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
				System.out.println("Remote device removed: " + device.getDisplayString() + " " + device.getDetails());
			}

			public void localDeviceAdded(Registry registry, LocalDevice device) {
				System.out.println("Local device added: " + device.getDisplayString() + " " + device.getDetails());
			}

			public void localDeviceRemoved(Registry registry, LocalDevice device) {
				System.out.println("Local device removed: " + device.getDisplayString() + " " + device.getDetails());
			}

			public void beforeShutdown(Registry registry) {
				System.out.println("Before shutdown, the registry has devices: " + registry.getDevices().size());
			}

			public void afterShutdown() {
				System.out.println("Shutdown of registry complete!");

			}
		};

		// This will create necessary network resources for UPnP right away
		System.out.println("Starting Cling...");
		UpnpService upnpService = new UpnpServiceImpl(listener);

		// Send a search message to all devices and services, they should
		// respond soon
		System.out.println("Sending SEARCH message to all devices...");
		upnpService.getControlPoint().search(new STAllHeader());

		// Let's wait 10 seconds for them to respond
		System.out.println("Waiting 10 seconds before shutting down...");
		Thread.sleep(10000);

		ControlPoint cp = upnpService.getControlPoint();
		cp.getConfiguration();

		Registry reg = cp.getRegistry();
		System.out.println("Devices found: "+reg.getDevices().size());

		Iterator<RemoteDevice> iter = reg.getRemoteDevices().iterator();
		RemoteDevice rd;
		while (iter.hasNext()){
			rd = iter.next();
			System.out.println("Device: "+ rd);
			System.out.println("UDN: "+ rd.getIdentity().getUdn());
			System.out.println("URL: "+ rd.getIdentity().getDescriptorURL());
			System.out.println("Age(sec): "+ rd.getIdentity().getMaxAgeSeconds());
			if(rd.getType().getType().equals("MediaServer")){
				for(RemoteService remService : rd.getServices()){
					try{
//					if(remService.getServiceType().getType().equals("ContentDirectory")){
						System.out.println(remService);
						
						ActionCallback simpleBrowseAction = new Browse(remService, "0", BrowseFlag.DIRECT_CHILDREN) {
							
							@Override
							public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
								// TODO Auto-generated method stub
								System.out.println("fail: "+arg2);
							}
							
							@Override
							public void updateStatus(Status arg0) {
								// TODO Auto-generated method stub
								System.out.println("update: "+arg0);
							}
							
							@Override
							public void received(ActionInvocation arg0, DIDLContent arg1) {
								// TODO Auto-generated method stub
								System.out.println("receive");
								System.out.println(arg1);
							}
						};
						
						simpleBrowseAction.setControlPoint(cp);
						simpleBrowseAction.run();
					}catch(IllegalArgumentException e){
						
					}
					}
//				}
			}
		}
		
		
		// Release all resources and advertise BYEBYE to other UPnP devices
		System.out.println("Stopping Cling...");
		upnpService.shutdown();
	}
}
