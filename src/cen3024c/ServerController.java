package cen3024c;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class ServerController implements Initializable {

	@FXML
	private TextArea serverOutput;
	
	public void startServerTextOutput(String text) {
		serverOutput.setText(text);
	}
	
	public void appendServerTextOutput(String text) {
		serverOutput.appendText(text);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		startServerTextOutput("Server started at " + new Date() + "\n");
	}

}
