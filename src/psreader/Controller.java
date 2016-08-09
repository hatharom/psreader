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
import javafx.scene.control.ListView;

/**
 *
 * @author zolih
 */
public class Controller {

    @FXML
    ListView fxPlayerList;

    DocumentBuilderFactory factory;

    DocumentBuilder builder;
    File file;

    @FXML
    public void print() {
        ObservableList playerList
                = FXCollections.observableArrayList();
        try {
            System.out.println("");
            file = new File("notes.xml");
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("note");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node actualNode = nodeList.item(i);
                playerList.add(((Element) actualNode).getAttribute("player"));

            }

            fxPlayerList.setItems(playerList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
