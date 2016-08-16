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
import javafx.util.Callback;

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
    File file;
    NodeList nodeList;
    String filterValue;
    public static int counter = 0;

    @FXML
    public void print() {
        fxPlayerList.getItems().clear();
        ObservableList playerList = fxPlayerList.getItems();
        try {

            System.out.println("");
            file = new File("notes.xml");
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            document.getDocumentElement().normalize();
            nodeList = document.getElementsByTagName("note");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node actualNode = nodeList.item(i);
                if (filterValue != null) {
                    if (((Element) actualNode).getAttribute("player").contains(filterValue)) {
                        playerList.add(((Element) actualNode).getAttribute("player"));
                    }
                } else {
                    playerList.add(((Element) actualNode).getAttribute("player"));
                }

            }
            setCellBackground();
            fxPlayerList.setItems(playerList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setCellBackground() {
        fxPlayerList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> cell = new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            String color = getColor(item);
                            setStyle("-fx-background-color: " + color);
                            setText(item);
                        }

                    }
                };
                return cell;
            }
        });
    }

    @FXML
    public void listenToSelection(MouseEvent event) {
        Integer playerId = fxPlayerList.getSelectionModel().getSelectedIndex();
        NodeList nodeList = document.getElementsByTagName("note");
        Node playerNode = nodeList.item(playerId);
        String playerName = (((Element) playerNode).getAttribute("player"));
        String playerNotes = playerNode.getTextContent();

        NodeList colorList = document.getElementsByTagName("label");
        int colorIndex = Integer.parseInt(((Element) playerNode).getAttribute("label"));
        Node colorNode = colorList.item(colorIndex - 1);
        String color = (((Element) colorNode).getAttribute("color"));
        playerLabel.setText(playerName + " : " + playerNotes);
        playerLabel.setStyle("-fx-background-color: " + color);

    }

    public String getColor(String player) {
        String color = null;
        Node playerNode = null;
        int actualIndex = 0;
        playerNode = nodeList.item(actualIndex);
        while (!(((Element) playerNode).getAttribute("player")).equalsIgnoreCase(player)) {
            playerNode = nodeList.item(actualIndex);
            actualIndex++;
        }
        NodeList colorList = document.getElementsByTagName("label");
        int colorIndex = Integer.parseInt(((Element) playerNode).getAttribute("label"));
        Node colorNode = colorList.item(colorIndex - 1);
        color = (((Element) colorNode).getAttribute("color"));
        return color;
    }

    @FXML
    public void search() {
        filterValue = fxSearchBox.getText();
        print();
    }

}
