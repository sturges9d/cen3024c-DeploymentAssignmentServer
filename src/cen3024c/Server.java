package cen3024c;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Stephen Sturges Jr
 * @version 07/22/2022
 *
 */
public class Server extends Application {

	public static boolean determinePrime(int value) {
		boolean result = true;
		if(value <= 1) {
			result = false;
        }
       for(int i=2; i < value / 2; i++) {
    	   if((value % i) == 0)
        	   result = false;
       }
       return result;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Connect Server.java class with ServerUI.fxml to create UI.
		Parent root = FXMLLoader.load(getClass().getResource("ServerUI.fxml"));
		Scene scene = new Scene(root,478,181);
		primaryStage.setTitle("Prime Number Checker");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread( () -> {
			ServerSocket connection = null;
			boolean shutdown = false;
			
			// Open the server socket.
			try {
				connection = new ServerSocket(1236);
				System.out.println("Server started. Port bound. Accepting connections...");
				
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			while(!shutdown) {
				Socket client = null;
				
				try {
					// Establish the connection with client and receive input.
					client = connection.accept();
					DataInputStream input = new DataInputStream(client.getInputStream());
					DataOutputStream output = new DataOutputStream(client.getOutputStream());

					// Receive input.
					int clientInput = input.readInt();
					System.out.println("Client requested: " + clientInput);
					// Calculate reply.
					String result;
					if (determinePrime(clientInput) == true) {
						result = "Yes";
					} else {
						result = "No";
					}
					// Write to output.
					System.out.println("Server response: " + result);
					output.write(result.getBytes());
					
					// Close the connection with client.
					client.close();
					
					// Exit the while loop and shutdown server if condition met.
					if(clientInput == -999) {
						System.out.println("Server shutting down.");				
						shutdown = true;
					}
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
			}
		}).start();;
		
	}

}