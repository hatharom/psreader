/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psreader;

import javafx.fxml.FXML;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JFileChooser;

/**
 *
 * @author zolih
 */
public class Controller {

    @FXML
    ListView fxPlayerList;
    @FXML
    Label playerLabel;
    @FXML
    TextField fxSearchBox;
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;
    File file = new File("notes.xml");
    ;
    NodeList nodeList;
    String filterValue;
    private static Stage primaryStage;

    public static int counter = 0;

    /*
     *populates the list, both filtered and unfiltered
     */
    @FXML
    public void print() {
        fxPlayerList.getItems().clear();
        ObservableList playerList = fxPlayerList.getItems();
        try {

            System.out.println("");

            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            document.getDocumentElement().normalize();
            nodeList = document.getElementsByTagName("note");

            for (int i = 0; i < nodeList.getLength(); i++) {

                //fetching data from xml and creates player object for that
                Node actualNode = nodeList.item(i);
                NodeList colorList = document.getElementsByTagName("label");
                int colorIndex = Integer.parseInt(((Element) actualNode).getAttribute("label"));
                Node colorNode = colorList.item(colorIndex - 1);
                String color ="#" +(((Element) colorNode).getAttribute("color"));                
                if (color.length() < 6) {
                    color = completeColor(color);
                }
                Player player = new Player(((Element) actualNode).getAttribute("player"), actualNode.getTextContent(), color);

                //decides whether filterrule is applied and add the players to the list accordingly
                if (filterValue != null) {
                    if (((Element) actualNode).getAttribute("player").toLowerCase().startsWith(filterValue.toLowerCase())) {
                        playerList.add(player);
                    }
                } else {
                    playerList.add(player);
                }
            }

            setCellBackground();
            fxPlayerList.setItems(playerList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setCellBackground() {
        fxPlayerList.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {

            @Override
            public ListCell<Player> call(ListView<Player> param) {
                ListCell<Player> cell = new ListCell<Player>() {

                    @Override
                    protected void updateItem(Player item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            String color = item.getColor();
                            setStyle("-fx-background-color: " + color);
                            setText(item.toString());
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    public void listenToSelection(MouseEvent event) {

        Player selectedPlayer = ((Player) fxPlayerList.getSelectionModel().getSelectedItem());
        if (selectedPlayer != null) {
            playerLabel.setText(selectedPlayer.getPlayerName()+"\n---------------\n"+selectedPlayer.getNotes());
            playerLabel.setStyle("-fx-background-color: " + selectedPlayer.getColor());
        }
    }

    @FXML
    public void search() {
        filterValue = fxSearchBox.getText();
        print();
    }

    @FXML
    public void chooseFile() {
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        file = fc.getSelectedFile();

    }

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    private String completeColor(String color) {
        StringBuilder sb=new StringBuilder(color);
       int start=color.length();
        for (int i = start; i < 7; i++) {
            sb.append("0");
        }
        return sb.toString();
    }

}
