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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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

    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;
    File file;

    @FXML
    public void print() {
        /*   ObservableList playerList
         = FXCollections.observableArrayList();*/
        ObservableList playerList
                = fxPlayerList.getItems();
        try {
            //   fxPlayerList=new ListView(playerList);
            System.out.println("");
            file = new File("notes.xml");
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("note");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node actualNode = nodeList.item(i);
                playerList.add(((Element) actualNode).getAttribute("player"));

            }

            fxPlayerList.setItems(playerList);

            Callback<TableColumn<Object, String>, TableCell<Object, String>> cellFactory
                    = new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
                        public TableCell call(TableColumn p) {
                            TableCell cell = new TableCell<Object, String>() {
                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    setText(empty ? null : getString());
                                    setStyle("-fx-background-color: black" );
                                }

                                private String getString() {
                                    return getItem() == null ? "" : getItem().toString();
                                }
                            };

                            return cell;
                        }
                    };
            
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        playerLabel.setText(playerName + " : " + playerNotes + " color: " + color);
        playerLabel.setStyle("-fx-background-color: " + color);

    }

}
