package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static final String STAGE_TITLE = "File creator";
    private static final int SCENE_WIDTH = 720;
    private static final int SCENE_HEIGHT = 360;
    public static final String PATH_PROGRAM_FILES = "C:\\Program Files\\Copy_Paste\\";
    public static String sStartDirectory = "C:\\_Data\\_Cloud9\\test\\";
    private ObservableList<MyRow> rows;
    private TextField tfNameNewFile;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        rows = FXCollections.observableArrayList(createNewRow(primaryStage));
        ListView<MyRow> listView = (ListView<MyRow>) root.lookup("#lvRows");
        listView.setItems(rows);

//        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldElement, newElement)
//                -> System.out.println(newElement.getFile()));

        Button bAddRow = (Button) root.lookup("#bAddRow");
        bAddRow.setOnAction(event -> {
            if (isFieldsFilling()) {
                System.out.println("fill field");
                displayError();
            } else {
                rows.add(createNewRow(primaryStage));
            }
        });

        Button bRemoveRow = (Button) root.lookup("#bRemoveRow");
        bRemoveRow.setOnAction(event -> {
            if (rows.size() > 1) rows.remove(rows.size() - 1);
        });

        Button bCreateFile = (Button) root.lookup("#bCreateFile");
        bCreateFile.setOnAction(event -> {
            boolean isError = false;

            List<MyFiles> myFilesList = new ArrayList<>();

            for(MyRow row : rows) {
//                System.out.print(row.getFilePathString());
//                System.out.print(" * " + row.getStartAddressString());
//                System.out.println(" * " + row.getFinalAddressString());
                myFilesList.add(new MyFiles(
                        row.getFilePathString(), row.getStartAddressString(), row.getFinalAddressString()));

                if (isAllFieldsFilled(row) || isError) isError = true;
            }

            if (tfNameNewFile.getText().isEmpty()){
                tfNameNewFile.setBorder(new Border(
                        new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                isError = true;
            } else {
                tfNameNewFile.setBorder(Border.EMPTY);
            }

            if (!isError) {
//                System.out.println("Is good");
                new FileCreator(myFilesList, PATH_PROGRAM_FILES + tfNameNewFile.getText());
            }
        });

        tfNameNewFile = (TextField) root.lookup("#tfNameNewFile");

        primaryStage.setTitle(STAGE_TITLE);
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.show();
    }

    private boolean isAllFieldsFilled(MyRow row) {
        boolean isInvalidInput = false;

        if (row.getFilePathString().isEmpty()) {
            row.displayInputError(row.getFilePathTextField(), "Выберите файл");
            isInvalidInput = true;
        }

        if (!isDigit(row.getStartAddressString())) {
            row.displayInputError(row.getStartAddressTextField(), "Некорректное число");
            isInvalidInput = true;
        }

        if (!isDigit(row.getFinalAddressString())) {
            row.displayInputError(row.getFinalAddressTextField(), "Некорректное число");
            isInvalidInput = true;
        }

        return isInvalidInput;
    }

    private boolean isDigit(String s) throws NumberFormatException {
        try {
            Long.parseLong(s, 16);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void displayError() {
        if (rows.get(rows.size() - 1).getFilePathString().isEmpty()) {
            rows.get(rows.size() - 1).displayInputError(rows.get(rows.size() - 1).getFilePathTextField(), "Выберите файл");
        } else {
            rows.get(rows.size() - 1).removeInputError(rows.get(rows.size() - 1).getFilePathTextField());
        }
        if (rows.get(rows.size() - 1).getStartAddressString().isEmpty()) {
            rows.get(rows.size() - 1).displayInputError(rows.get(rows.size() - 1).getStartAddressTextField(), "Введите адрес");
        } else {
            rows.get(rows.size() - 1).removeInputError(rows.get(rows.size() - 1).getStartAddressTextField());
        }
        if (rows.get(rows.size() - 1).getFinalAddressString().isEmpty()) {
            rows.get(rows.size() - 1).displayInputError(rows.get(rows.size() - 1).getFinalAddressTextField(), "Введите адрес");
        } else {
            rows.get(rows.size() - 1).removeInputError(rows.get(rows.size() - 1).getFilePathTextField());
        }
    }

    private boolean isFieldsFilling() {
        return rows.get(rows.size() - 1).getFilePathString().isEmpty() ||
                rows.get(rows.size() - 1).getStartAddressString().isEmpty() ||
                rows.get(rows.size() - 1).getFinalAddressString().isEmpty();
    }

    private MyRow createNewRow(Stage primaryStage) {
        return new MyRow(primaryStage).getFlowPane();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
