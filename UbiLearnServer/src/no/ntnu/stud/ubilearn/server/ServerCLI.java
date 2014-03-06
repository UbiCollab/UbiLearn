package no.ntnu.stud.ubilearn.server;
/**
 * this is a command line interface for running the server
 * @author espen
 *
 */
public class ServerCLI {
	
	private Server server;
	
	public static void main(String[] args) {
		ServerCLI sc = new ServerCLI();
		sc.run();
	}

	private void run() {
		server = new Server();
	}

}
