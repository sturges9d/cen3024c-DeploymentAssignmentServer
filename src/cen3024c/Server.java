package cen3024c;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
		TextArea serverTextOutput = new TextArea();
		// Connect Server.java class with ServerUI.fxml to create UI.
		Scene scene = new Scene(new ScrollPane(serverTextOutput),480,200);
		primaryStage.setTitle("Prime Number Checker Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread( () -> {
			ServerSocket connection = null;
			boolean shutdown = false;
			
			// Open the server socket.
			try {
				connection = new ServerSocket(1236);
				System.out.println("Server started. Port bound. Accepting connections...");
				Platform.runLater(() -> serverTextOutput.appendText("Server started at " + new Date() + " Accepting connections...\n"));
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
					Platform.runLater(() -> serverTextOutput.appendText("Client requested: " + clientInput + "\n"));
					// Calculate reply.
					String result;
					if (determinePrime(clientInput) == true) {
						result = "Yes";
					} else {
						result = "No";
					}
					// Write to output.
					System.out.println("Server response: " + result);
					Platform.runLater(() -> serverTextOutput.appendText("Server response: " + result + "\n"));
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