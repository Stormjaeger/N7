package GameEditor.Architecture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.*;
import java.io.BufferedWriter;

import GameEditor.Map.Tile.Coordinate;
import GameEditor.Map.MapModel;

public class FileAdapter {
    private String projectPath;

    public FileAdapter(String path) {
        this.projectPath = path;
    }
    
    public void setSpawn(String mapPath, Coordinate coords) {
    	String mapFile = mapPath.replace("/example_", "");
    	String mapName = mapFile.replace(".map", "");
    	mapName.replaceAll("/", "");
    	String[] parts = mapName.split("/", 2);
    	mapName = parts[1];
    	
    	File mapSpawn = new File(this.projectPath + "/src/example_" + mapName +"/", "Spawn.txt");
    	// Processus très fastidieux pour écrire dans le même dossier....
    	try {
            mapSpawn.createNewFile();
            mapSpawn.setReadable(true, false); // Lecture pour tout le monde
            mapSpawn.setWritable(true, false); // Écriture pour tout le monde
            writeIntegersToFile(mapSpawn, coords.getX(), coords.getY());
        } catch (IOException e) {
            System.err.println("Une erreur est survenue lors de la création du fichier.");
            e.printStackTrace();
        }
    }
    
    public Coordinate getSpawn(String mapPath) {
    	String mapFile = mapPath.replace("/example_", "");
    	String mapName = mapFile.replace(".map", "");
    	mapName.replaceAll("/", "");
    	String[] parts = mapName.split("/", 2);
    	mapName = parts[1];
    	System.out.println(mapName);
    	File mapSpawn= new File(this.projectPath + "/src/example_" + mapName + "/","Spawn.txt");
    	try {
            // Tenter de créer le fichier
            if (mapSpawn.createNewFile()) { //il reussi à créer le fichier
            	mapSpawn.setReadable(true, false); // Lecture pour tout le monde
                mapSpawn.setWritable(true, false); // Écriture pour tout le monde
            	Coordinate coord = new Coordinate(0,0);
            	return coord;
            } else {
            	
            	mapSpawn.setReadable(true, false); // Lecture pour tout le monde
                mapSpawn.setWritable(true, false); // Écriture pour tout le monde
            }
        } catch (IOException e) {
            System.err.println("Une erreur est survenue lors de la création du fichier.");
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(mapSpawn))) {
            String[] part= reader.readLine().split("\\s+");;
            Integer x = Integer.parseInt(part[0].trim());
            Integer y = Integer.parseInt(part[1].trim());
            Coordinate coord = new Coordinate(x,y);
            return coord;
        } catch (IOException e) {
        	System.out.print("exception1");
            Integer x = 0;
            Integer y = 0;
            Coordinate coord = new Coordinate(x,y);
            return coord;
        } catch (NumberFormatException e) {
        	System.out.print("Exception2");
        	Integer x = 0;
            Integer y = 0;
            Coordinate coord = new Coordinate(x,y);
            return coord;
        }    	
    }

    public void createFolder(String pathString) {
        try {
            Path path = Paths.get(projectPath + pathString);
            Files.createDirectories(path);
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }

    /**
     * Créer une map vide
     */
    public void createMap(String mapPath, Integer size_x, Integer size_y) {
        Map<Coordinate, Integer> map = new HashMap<Coordinate, Integer>();
        for (int y = 0; y < size_y; y++) {
            for (int x = 0; x < size_x; x++) {
                Coordinate coord = new Coordinate(x, y);
                map.put(coord, 0);
            }
        }
        this.createMap(mapPath, map);
    }

    /**
     * Creer une map à partir d'une map de coord et d'id
     */
    public void createMap(String mapPath, Map<Coordinate, Integer> map) {
        try {
            FileWriter writter = new FileWriter(projectPath + "/src/" + mapPath);
            String mapString = "";
            Integer size_x = 0;
            Integer size_y = 0;
            for (Coordinate coord : map.keySet()) {
                if (coord.getX() > size_x) {
                    size_x = coord.getX();
                }
                if (coord.getY() > size_y) {
                    size_y = coord.getY();
                }
            }
            for (int y = 0; y <= size_y; y++) {
                for (int x = 0; x <= size_x; x++) {
                    Coordinate coord = new Coordinate(x, y);
                    mapString = mapString + map.get(coord) + " ";
                }
                mapString = mapString + "\n";
            }
            writter.write(mapString);
            writter.close();
            
            this.setSpawn(mapPath, this.getSpawn(mapPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setPortal(MapModel map, Map<Coordinate, String> portal){
    	String mapFile = map.getMapPath().replace("/example_", "");
    	String mapName = mapFile.replace(".map", "");
    	mapName.replaceAll("/", "");
    	String[] parts = mapName.split("/", 2);
    	mapName = parts[1];
    	File mapPortal = new File(this.projectPath + "/src/example_" + mapName +"/", "Portals.txt");
    	// Processus très fastidieux pour écrire dans le même dossier....
    	try {
    		if (mapPortal.exists()) {
    			
    		} else {
    			mapPortal.createNewFile();
    		}
            mapPortal.setReadable(true, false); // Lecture pour tout le monde
            mapPortal.setWritable(true, false); // Écriture pour tout le monde
        	FileWriter fw = new FileWriter(mapPortal);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Coordinate pCoord : portal.keySet()) {
                bw.write(Integer.toString(pCoord.getX()));
                bw.write(" ");
                bw.write(Integer.toString(pCoord.getY()));
                bw.write(" ");
            	bw.write(portal.get(pCoord));
            	bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.err.println("Une erreur est survenue lors de la création du fichier.");
            e.printStackTrace();
        }
    }


    public Map<Coordinate, String> getPortal(String mapPath){
    	Map<Coordinate, String> Portal = new HashMap<Coordinate, String>();
        String[] directoryPathSlice = mapPath.split("/");
        String directoryPath = "/" + directoryPathSlice[1];
        try {
        	FileReader portalReader = new FileReader(this.projectPath + "/src" + directoryPath + "/Portals.txt");
        	BufferedReader buffer = new BufferedReader(portalReader);
        	String line;
        	while ((line = buffer.readLine()) != null) {
        		String[] param = line.split(" ");
        		Coordinate coord = new Coordinate(Integer.parseInt(param[0].trim()),Integer.parseInt(param[1].trim()));
        		String mapPaths = param[2];
        		Portal.put(coord, mapPaths);
        		}
        	return Portal;
        }
        catch (FileNotFoundException error) {
        	System.out.println("nop, pb avec le fichier");
           	System.out.println(this.projectPath + "/src" + directoryPath + "/Portals.txt");
        } catch (IOException e) {
        	System.out.println("nop, pb avec les file adapter");
        }
        return Portal;
    }
    
    /**
     * Decoder une map
     */
    public Map<Coordinate, Integer> getMap(String mapPath) {
        Map<Coordinate, Integer> map = new HashMap<Coordinate, Integer>();
        try {
            File mapFile = new File(projectPath + "/src/" + mapPath);
            Scanner reader = new Scanner(mapFile);
            int y = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] cells = line.split(" ");
                for (int x = 0; x < cells.length; x++) {
                    Coordinate coord = new Coordinate(x, y);
                    Integer id = Integer.parseInt(cells[x]);
                    map.put(coord, id);
                }
                y++;
            }
            reader.close();
            
            
            //gestion du spawn
            
            this.setSpawn(mapPath, this.getSpawn(mapPath));            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Changer une tuile
     */
    public void changeTile(String mapPath, Coordinate coord, Integer id) {
        Map<Coordinate, Integer> map = this.getMap(mapPath);
        map.put(coord, id);
        this.createMap(mapPath, map);
    }
    
    private static void writeIntegersToFile(File file, int integer1, int integer2) {
        // Utilisé pour setspawn
        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw)) {
            // Écrire les deux entiers dans le fichier
            bw.write(Integer.toString(integer1));
            bw.write(" ");
            bw.write(Integer.toString(integer2));
            bw.write(" ");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier.");
            e.printStackTrace();
        }
    }
    
    
}
