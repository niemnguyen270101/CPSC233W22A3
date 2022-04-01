package mvh.app;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import mvh.world.*;
import mvh.util.Reader;
import java.io.File;
import java.io.IOException;

public class MainController {

    //Store the data of editor
    private World world;

    ObservableList<String> weaponList = FXCollections.observableArrayList("","Sword (2)","Club (2)","Axe (2)");
    @FXML
    private ChoiceBox<String> weapon_status;

    @FXML
    private TextArea view;

    @FXML
    private MenuItem Info_button;

    @FXML
    private TextField number_column;

    @FXML
    private TextField number_row;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    /**
     * Setup the window state
     */


    public void initialize() {
        weapon_status.setItems(weaponList);
    }

    @FXML
    void Create_new_world(MouseEvent event) {
        String cc = number_column.getText();
        String rr = number_row.getText();
        int c = Integer.parseInt(cc);
        int r = Integer.parseInt(rr);
        World world = new World(c,r);
        view.appendText(world.worldString());
    }

    @FXML
    void Info_button(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Author: Nguyen Luc \nEmail: nguyen.luc@ucalgary.ca \nVersion: v1.0 \nThis is world editor for Monster versus Heroes");
        alert.show();
    }

    @FXML
    void Load(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            World world = new World(1, 1);
            world = Reader.loadWorld(selectedFile);
            view.appendText(world.worldString());
        } else {
            System.out.println("File is not valid");
        }
    }

    @FXML
    void Quit(ActionEvent event) {
        System.exit(0);
    }

}
