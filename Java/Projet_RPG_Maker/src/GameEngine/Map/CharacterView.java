package GameEngine.Map;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameEditor.Architecture.DatabaseAdapter;
import GameEditor.Architecture.DatabaseAdapter;
import GameEngine.Main;
import GameEngine.Map.Tile.Coordinate;
import GameEngine.Map.Tile.TileModel;

public class CharacterView {

    private TileModel characterTile;
    private String projectPath;
    JLabel characterLabel;
    BufferedImage tileSprite;
    ImageIcon spriteImg;
    String player; 

    //chargement des différentes images du héros
    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
 
    public CharacterView(TileModel characterTile,  String projectPath, String joueur) {  
        this.projectPath =  projectPath;
        this.characterTile = characterTile;
        this.player = joueur;
    }

    public void getPlayerImage(){
        DatabaseAdapter base = new DatabaseAdapter(this.projectPath); 
        this.player = base.getPlayer();
        String chemin = this.projectPath + "/assets/img/heros/" + player + "/" + player;
        System.out.println(chemin);
        File file_down1 = new File(chemin + "_down_1.png");
        File file_down2 = new File(chemin + "_down_2.png");
        File file_up1 = new File(chemin + "_up_1.png");
        File file_up2 = new File(chemin +"_up_2.png");
        File file_left1 = new File(chemin + "_left_1.png");
        File file_left2 = new File(chemin + "_left_2.png");
        File file_right1 = new File(chemin +"_right_1.png");
        File file_right2 = new File(chemin + "_right_2.png");

        try {
            down1 = ImageIO.read(file_down1);
            down2 = ImageIO.read(file_down2);
            up1 = ImageIO.read(file_up1);
            up2 = ImageIO.read(file_up2);
            left1 = ImageIO.read(file_left1);
            left2 = ImageIO.read(file_left2);
            right1 = ImageIO.read(file_right1);
            right2 = ImageIO.read(file_right2);

           } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // Quitter le programme si l'image ne peut pas être chargée
        }
    }

    public void display(JPanel main, Coordinate coordinateOfTile) {
        try{
        
        String chemin = this.projectPath + "/assets/img/heros/" + player + "/" + player;
        System.out.println(this.characterTile.getSpritePath());
        tileSprite = ImageIO
                .read(new File(chemin + "_down_1.png"));
        
        spriteImg = new ImageIcon(tileSprite); 

        this.characterLabel = new JLabel(spriteImg);

        this.characterLabel.setBounds(coordinateOfTile.getX() * characterTile.getSize(), coordinateOfTile.getY() * characterTile.getSize() , characterTile.getSize(),
        characterTile.getSize());

        main.add(characterLabel);

        // affiche le personnage au premier plan du JPanel main
        main.setComponentZOrder(characterLabel, 0);

            } catch (IOException e) {
                System.out.println("Failed to load sprite !");
                e.printStackTrace();
            }
        main.repaint();
    } 


    public void changeTile(){
        switch (Main.direction) {
            case "down":
                if (Main.spriteNum == 1){
                tileSprite = down1;
                }
                if (Main.spriteNum == 2){
                    tileSprite = down2;
                    }
                break;
            case "up":
                if (Main.spriteNum == 1){
                    tileSprite = up1;
                    }
                if (Main.spriteNum == 2){
                    tileSprite = up2;
                    }
                    break;
            case "left":
                if (Main.spriteNum == 1){
                    tileSprite = left1;
                    }
                if (Main.spriteNum == 2){
                    tileSprite = left2;
                    }
                    break;
            case "right":
                if (Main.spriteNum == 1){
                    tileSprite = right1;
                    }
                if (Main.spriteNum == 2){
                    tileSprite = right2;
                    }
                break;
        }

        spriteImg.setImage(tileSprite);
        this.characterLabel.setIcon(spriteImg);
    }
    
    
    public void refresh(JPanel main, Coordinate coordinateOfTile) {

        this.characterLabel.setBounds(coordinateOfTile.getX() * characterTile.getSize(), coordinateOfTile.getY() * characterTile.getSize() + 5, characterTile.getSize(),
        characterTile.getSize());
        //
        // test de coordoné
        //
        //System.out.println(coordinateOfTile.getX() + ";" + coordinateOfTile.getY());
        //
        //
        main.add(characterLabel);

        // affiche le personnage au premier plan du JPanel main
        main.setComponentZOrder(characterLabel, 0);

        main.repaint();
    }
}
