package GameEditor.AddMaps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.*;

import GameEditor.AddTiles.NameException;
import GameEditor.AddTiles.ValidateListener;
import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.Architecture.FileAdapter;
import GameEditor.AssetsView.AssetsTilesView;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.swing.JTextField;


public class AddMapFolder implements ActionListener {
    private Map<String, JTextField> textFields; 
    private ValidateListener listener;
    private JFrame frame; 
    private String path; 
    
    public AddMapFolder(String path, JFrame frame, Map<String, JTextField> textFields, ValidateListener listener) {
        this.listener = listener; 
        this.textFields = textFields;
        this.frame = frame;
        this.path = path; 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
             
            String name = this.textFields.get("nom").getText();
            int sizeX = Integer.parseInt(textFields.get("x").getText());
            int sizeY = Integer.parseInt(textFields.get("y").getText());
            // Afficher les valeurs dans une boîte de dialogue
            JOptionPane.showMessageDialog(frame,
                    "Nom: " + name + "\nLongueur: " + sizeX + "\nLargeur: " + sizeY,
                    "Nom de la Map",
                    JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();

            //Création du dossier et de la map
            FileAdapter fileAdapter = new FileAdapter(path);
            fileAdapter.createFolder("/src/example_" + name);
            fileAdapter.createMap("example_" + name + "/" + name + ".map", sizeX, sizeY);
            this.listener.action();
            
        }  catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Entrez des entiers pour la longueur et la largeur",
                    "Erreur en entrée",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
    


}
