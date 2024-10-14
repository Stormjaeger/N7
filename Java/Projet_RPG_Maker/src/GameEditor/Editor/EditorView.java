package GameEditor.Editor;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GameEditor.AssetsView.*;
import GameEditor.HeaderSettings.HeaderView;
import GameEditor.Map.MapModel;
import GameEditor.Settings.SettingsView;
import GameEditor.ViewModels.ViewAttribut;
import GameEditor.ViewModels.ViewCharacter;
import GameEditor.ViewModels.ViewCompetences;
import GameEditor.ViewModels.ViewMap;
import GameEditor.ViewModels.ViewModel;
import GameEditor.ViewModels.ViewMonster;


public class EditorView {

    private JFrame window;
    private JPanel mapPanel;
    private JPanel headerPanel;
    private JPanel mainPanel;
    private JPanel assetsPanel;
    private JPanel settingsPanel;
    private ViewModel centerView;
    private HeaderView headerView;
    private AssetsView assetsView;
    private SettingsView settingsView;
    private String path;
    private MapModel map; 
    
    public JFrame getWindow(){
    	return this.window;
    }
    
    public MapModel getMap(){
    	return this.map;
    }
//    
//    public void setMap(MapModel map){
//    	this.map = map;
//    	
//    }
//    
//    public void reloadMapPanel(){
//    	this.cleanEditor();
//    	this.centerView = new ViewMap(map, this.settingsView);
//    	this.displayMap();
//    }


    public EditorView(String path, MapModel map) {
        this.window = new JFrame("Ystoria - Game Maker");
        this.mainPanel = new JPanel(new BorderLayout());
        this.headerPanel = new JPanel();
        this.mapPanel = new JPanel();
        this.assetsPanel = new JPanel();
        this.settingsPanel = new JPanel();
        this.path = path;
        this.map = map; 

        this.headerView = new HeaderView(this);
        this.settingsView = new SettingsView(this.settingsPanel);
        this.centerView = new ViewMap(map, this.settingsView);
        this.assetsView = new AssetsTilesView(path,this, (ViewMap)centerView);
        this.headerView.display(headerPanel);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screensize = toolkit.getScreenSize();
        window.setSize(screensize);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayMap() {
        this.centerView = new ViewMap(map, settingsView);
        this.assetsView = new AssetsTilesView(path,this, (ViewMap)centerView);
        this.centerView.display(mapPanel);
        this.assetsView.display(assetsPanel);
        this.settingsView.display(settingsPanel);
        this.mainPanel.add(this.mapPanel, BorderLayout.CENTER);
        this.mainPanel.add(this.headerPanel, BorderLayout.NORTH);
        this.mainPanel.add(this.assetsPanel, BorderLayout.WEST);
        this.mainPanel.add(this.settingsPanel, BorderLayout.EAST);
        this.window.add(mainPanel);
        this.window.setVisible(true);
        
    }
    public void cleanEditor() {
        this.mapPanel.removeAll();
        this.assetsPanel.removeAll();
        this.settingsPanel.removeAll();
        this.mainPanel.removeAll();
        this.mainPanel.repaint();

    }

    public void displayCharacter() {
        
        this.assetsView = new AssetsCharactersView(this.path);
        this.centerView = new ViewCharacter(this.path, (AssetsCharactersView)assetsView);
        ((AssetsCharactersView) this.assetsView).setListener((ViewCharacter)this.centerView); 
        this.centerView.display(mapPanel);
        this.assetsView.display(assetsPanel);
        this.settingsView.display(settingsPanel);
        this.mainPanel.add(this.mapPanel, BorderLayout.CENTER);
        this.mainPanel.add(this.headerPanel, BorderLayout.NORTH);
        this.mainPanel.add(this.assetsPanel, BorderLayout.WEST);
        this.mainPanel.add(this.settingsPanel, BorderLayout.EAST);
        this.window.add(mainPanel);
        this.window.setVisible(true);
    }

    public void displayMonster() {
        this.assetsView = new AssetsMonstersView(this.path);
        this.centerView = new ViewMonster(this.path, (AssetsMonstersView) assetsView); 
        ((AssetsMonstersView) this.assetsView).setListener((ViewMonster)this.centerView);
        this.centerView.display(mapPanel);
        this.assetsView.display(assetsPanel);
        this.settingsView.display(settingsPanel);
        this.mainPanel.add(this.mapPanel, BorderLayout.CENTER);
        this.mainPanel.add(this.headerPanel, BorderLayout.NORTH);
        this.mainPanel.add(this.assetsPanel, BorderLayout.WEST);
        this.mainPanel.add(this.settingsPanel, BorderLayout.EAST);
        this.window.add(mainPanel);
        this.window.setVisible(true);
    }

    public void displayAbility() {
        
        this.assetsView = new AssetsCompetencesView(this.path);
        this.centerView = new ViewCompetences(this.path, (AssetsCompetencesView) assetsView); 
        ((AssetsCompetencesView) this.assetsView).setListener((ViewCompetences)this.centerView); 
        this.centerView.display(mapPanel);
        this.assetsView.display(assetsPanel);
        this.settingsView.display(settingsPanel);
        this.mainPanel.add(this.mapPanel, BorderLayout.CENTER);
        this.mainPanel.add(this.headerPanel, BorderLayout.NORTH);
        this.mainPanel.add(this.assetsPanel, BorderLayout.WEST);
        this.mainPanel.add(this.settingsPanel, BorderLayout.EAST);
        this.window.add(mainPanel);
        this.window.setVisible(true);
    }

    public void displayAttribute() {
        
        this.assetsView = new AssetsAttributsView(this.path);
        this.centerView = new ViewAttribut(this.path, (AssetsAttributsView) assetsView); 
        ((AssetsAttributsView) this.assetsView).setListener((ViewAttribut)this.centerView);
        this.centerView.display(mapPanel);
        this.assetsView.display(assetsPanel);
        this.settingsView.display(settingsPanel);
        this.mainPanel.add(this.mapPanel, BorderLayout.CENTER);
        this.mainPanel.add(this.headerPanel, BorderLayout.NORTH);
        this.mainPanel.add(this.assetsPanel, BorderLayout.WEST);
        this.mainPanel.add(this.settingsPanel, BorderLayout.EAST);
        this.window.add(mainPanel);
        this.window.setVisible(true);
    }
}

 
