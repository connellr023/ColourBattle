package com.connell.colourbattle.networking.server;

public class SocketServerManager {
	private static final int MAX_ROOM_COUNT = 1;
	
	private static ServerSocketStream server;
	private static Thread serverThread;
	
	private static boolean init(int port) {
		try {			
			setServer(new ServerSocketStream(port, MAX_ROOM_COUNT));
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public static void start(int port) {
		if (init(port)) {
			setServerThread(new Thread(getServer()));
			getServerThread().start();
		}
		else {
			System.out.println("Failed to Start Server on Port: " + port);
		}
	}
	
	public static void stopServer() {
		getServer().stop();
	}

	public static ServerSocketStream getServer() {
		return server;
	}

	public static void setServer(ServerSocketStream server) {
		SocketServerManager.server = server;
	}

	public static Thread getServerThread() {
		return serverThread;
	}

	public static void setServerThread(Thread serverThread) {
		SocketServerManager.serverThread = serverThread;
	}
}
