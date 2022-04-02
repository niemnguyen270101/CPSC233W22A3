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
import javafx.stage.Stage;
import mvh.enums.WeaponType;
import mvh.world.*;
import mvh.util.Reader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import mvh.world.Monster;
import mvh.world.Hero;

/**
 * Dylan (Niem Nguyen) Luc (UCID: 30132429)
 * CPSC 233 Intro CPSC for CPSC Majors II
 * Lab 03
 * Instructor: Jonathan Hudson
 * TA: Sarthak
 * 1st April 2022
 */

public class MainController {

    //Store the data of editor
    private World world;
    @FXML
    private TextArea details;

    @FXML
    private TextField hero_row;

    @FXML
    private TextField hero_column;

    @FXML
    private TextField hero_armor;


    @FXML
    private TextField hero_symbol;

    @FXML
    private TextField hero_weapon;

    @FXML
    private TextField hero_health;

    //Create a list for Monster's weapons
    ObservableList<String> weaponList = FXCollections.observableArrayList("Sword (4)","Axe (3)","Club (2)");
    @FXML
    private TextField monster_health;

    @FXML
    private TextField monster_symbol;

    @FXML
    private TextField monster_column;

    @FXML
    private TextField monster_row;


    @FXML
    private ChoiceBox<String> weapon_status;

    @FXML
    private TextArea view;

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

    private String updated_string;

    @FXML
    private TextField entity_column;

    @FXML
    private TextField entity_row;

    @FXML
    private Label rightStatus;

    @FXML
    private Label leftStatus;

    /**
     * Setup the window state
     */

    public void initialize() {
        weapon_status.setItems(weaponList);
    }


    /**
     * Read rows and columns
     * Print the new world to view
     * @param event
     */
    @FXML
    void Create_new_world(MouseEvent event) {

        String cc = number_column.getText();
        String rr = number_row.getText();
        int c = Integer.parseInt(cc);
        int r = Integer.parseInt(rr);
        World new_World = new World(c,r);
        view.appendText(new_World.worldString());
        updated_string = new_World.worldText();
        world = new_World;
        leftStatus.setText("A new world was created");
        rightStatus.setText("World drawn");
    }

    /**
     * Alert Information for Help => About MvHMapEditor
     * @param event
     */
    @FXML
    void Info_button(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Author: Nguyen Luc \nEmail: nguyen.luc@ucalgary.ca \nVersion: v1.0 \nThis is world editor for Monster versus Heroes");
        alert.show();
    }

    /**
     * Use FileChooser to read data, process and print it to view
     * @param event
     * @throws IOException
     */
    @FXML
    void Load(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            world = Reader.loadWorld(selectedFile);
            view.appendText(world.worldString());
        } else {
            System.out.println("File is not valid");
        }
        updated_string = world.worldText();
    }

    /**
     * Immediately quit and close the scene
     * @param event
     */
    @FXML
    void Quit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void saveAction(ActionEvent event) throws IOException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("world.txt");
        File fileSave = fileChooser.showSaveDialog((new Stage()));
        System.out.println(fileSave);
        if (fileSave != null){
            SaveFile(updated_string, fileSave);
        }
    }

    private void SaveFile(String content, File file) throws IOException {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
    }

    /**
     * add Monster
     * print details
     * @param event
     * @throws IOException
     */
    @FXML
    void addMonster(MouseEvent event) throws IOException  {
        System.out.println("x");
        String s_r = monster_row.getText();
        String s_c = monster_column.getText();
        int r = Integer.parseInt(s_r);
        int c = Integer.parseInt(s_c);
        String symbol = monster_symbol.getText();
        String s_health = monster_health.getText();
        int health = Integer.parseInt(s_health);
        String s_weapon = weapon_status.getValue();

        int weapon_strength = 0;
        String weapon_type = "";
        if (s_weapon.equals("Sword (4)")){
            s_weapon = "S";
            weapon_strength = 4;
            weapon_type ="SWORD";
        } else if (s_weapon.equals("Axe (3)")) {
            s_weapon = "A";
            weapon_strength = 3;
            weapon_type = "AXE";
        } else if (s_weapon.equals("Club (2)")) {
            s_weapon = "C";
            weapon_strength = 2;
            weapon_type = "CLUB";
        }


        //int health, char symbol, WeaponType weaponType
        Monster monster = new Monster(health, symbol.charAt(0), WeaponType.getWeaponType(s_weapon.charAt(0)));
        world.addEntity(r, c, monster);
        view.clear();
        view.appendText(world.worldString());

        details.clear();
        details.appendText("Type: Monster\n"+"Symbol: "+symbol+"\n"+"Health: "+health+"\n"+"Weapon Type: "+weapon_type+"\n"+"Weapon Strength: "+ weapon_strength +"\n" );
        leftStatus.setText("Added a new monster!");
        rightStatus.setText("World drawn");
    }

    @FXML
    void addHero(MouseEvent event) throws IOException {
        //read location
        String hero_s_r = hero_row.getText();
        String hero_s_c = monster_column.getText();
        int hero_r = Integer.parseInt(hero_s_r);
        int hero_c = Integer.parseInt(hero_s_c);

        //read symbol,health,weapon and armor
        String s_hero_symbol = hero_symbol.getText();

        String s_hero_health = hero_health.getText();
        int i_hero_health = Integer.parseInt(s_hero_health);

        String s_hero_weapon = hero_weapon.getText();
        int i_hero_weapon = Integer.parseInt(s_hero_weapon);

        String s_hero_armor = hero_armor.getText();
        int i_hero_armor = Integer.parseInt(s_hero_armor);

        //int health, char symbol, int weaponStrength, int armorStrength
        Hero hero = new Hero(i_hero_health, s_hero_symbol.charAt(0), i_hero_weapon, i_hero_armor);
        world.addEntity(hero_r, hero_c, hero);

        view.clear();
        view.appendText(world.worldString());

        details.clear();
        details.appendText("Type: Hero\n"+"Symbol: "+s_hero_symbol+"\n"+"Health: "+i_hero_health+"\n"+"Weapon Strength: "+i_hero_weapon+"\n"+"Armor Strength: "+ i_hero_armor +"\n");
        leftStatus.setText("Added a new hero!");
        rightStatus.setText("World drawn");
    }
    @FXML
    void Delete(MouseEvent event) {
        String s_entity_r = entity_row.getText();
        String s_entity_c = entity_column.getText();
        int i_entity_r = Integer.parseInt(s_entity_r);
        int i_entity_c = Integer.parseInt(s_entity_c);
        world.addEntity(i_entity_r, i_entity_c, null);
        view.clear();
        view.appendText(world.worldString());
        details.clear();
        leftStatus.setText("Deleted an entity");
        rightStatus.setText("");
    }

    @FXML
    void viewDetails(MouseEvent event) {
        String s_entity_r = entity_row.getText();
        String s_entity_c = entity_column.getText();
        int i_entity_r = Integer.parseInt(s_entity_r);
        int i_entity_c = Integer.parseInt(s_entity_c);
        if (world.getEntity(i_entity_r,i_entity_c) == null) {
            details.clear();
            details.appendText("Null");
        } else if (world.isHero(i_entity_c,i_entity_r)) {
            details.clear();
            details.appendText(world.sHero(i_entity_r,i_entity_c));
        } else if (world.isMonster(i_entity_r, i_entity_c)) {
            details.clear();
            details.appendText(world.sMonster(i_entity_r,i_entity_c));
        }
        leftStatus.setText("");
        rightStatus.setText("Details shown");
    }

}
