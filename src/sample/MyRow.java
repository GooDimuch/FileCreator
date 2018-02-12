package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MyRow extends FlowPane {

    private static final String SELECT_FILES = "Выбор файла";

    private String sStartDirectory = Main.sStartDirectory;

    private Stage stage;
    private MyRow flowPane;
    private final TextField tfFilePath;
    private final TextField tfStartAddress;
    private final TextField tfFinalAddress;

    public MyRow(Stage primaryStage) {
        super();
        flowPane = this;
        stage = primaryStage;

        tfFilePath = new TextField();
        tfFilePath.setPromptText(Constants.FILE_PATH);
        setOnChangeListener(tfFilePath, "Выберите файл");

        tfStartAddress = new TextField();
        tfStartAddress.setPromptText(Constants.START_ADDRESS);
        setOnChangeListener(tfStartAddress, "Введите адрес");

        tfFinalAddress = new TextField();
        tfFinalAddress.setPromptText(Constants.FINAL_ADDRESS);
        setOnChangeListener(tfFinalAddress, "Введите адрес");

        Button bFindFile = new Button(Constants.FIND_FILE);
        bFindFile.setOnAction(event -> {
            File selectedFile = selectFile();
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                tfFilePath.setText(filePath);
                Main.sStartDirectory = sStartDirectory = selectedFile.getParent();
            }
        });

        flowPane.getChildren().addAll(tfFilePath, tfStartAddress, tfFinalAddress, bFindFile);
    }

    private void setOnChangeListener(TextField textField, String errorMessage) {
        textField.textProperty().addListener(event -> {
            if (textField.getText().isEmpty()) {
                displayInputError(textField, errorMessage);
            } else {
                removeInputError(textField);
            }
        });
    }

    public MyRow getFlowPane() {
        return flowPane;
    }

    public String getFilePathString() {
        return tfFilePath.getText();
    }

    public String getStartAddressString() {
        return tfStartAddress.getText();
    }

    public String getFinalAddressString() {
        return tfFinalAddress.getText();
    }

    public void displayInputError(TextField textField, String errorMessage) {
        textField.setBorder(new Border(
                new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        textField.setPromptText(errorMessage);
    }

    public void removeInputError(TextField textField) {
        textField.setBorder(Border.EMPTY);
    }

    public TextField getFilePathTextField() {
        return tfFilePath;
    }

    public TextField getStartAddressTextField() {
        return tfStartAddress;
    }

    public TextField getFinalAddressTextField() {
        return tfFinalAddress;
    }

    private File selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(SELECT_FILES);
        fileChooser.setInitialDirectory(new File(sStartDirectory));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("SO", "*.so"));
        return fileChooser.showOpenDialog(stage);
    }
}
