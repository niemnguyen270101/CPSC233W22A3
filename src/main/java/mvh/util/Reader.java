package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class to assist reading in world file
 *
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Reader {

    /**
     * Load the world from the given file
     * (Do not expect students to create anything near as robust as this file reading method!)
     * (A better design would also use sub-functions.)
     *
     * @param fileWorld The world file to load
     * @return A World created from the world file
     */
    public static World loadWorld(File fileWorld) throws IOException {

        //read file
        FileReader file_reader = new FileReader(fileWorld);
        BufferedReader reader = new BufferedReader(new FileReader(fileWorld));
        //read rows and columns
        int rows = Integer.parseInt(reader.readLine());
        int columns = Integer.parseInt(reader.readLine());

        //create new World
        World world = new World(rows, columns);

        //read line by line and distribute the data
        for (int i = 0; i <= rows * columns - 1; i++) {
            String s = "";
            s = reader.readLine();
            String[] s_ar = s.split(",");
            // monster and hero
            // do nothing with null cuz its automatically null
            if (s_ar.length > 2) {
                int r = Integer.parseInt(s_ar[0]);
                int c = Integer.parseInt(s_ar[1]);
                if (s_ar[2].equals("MONSTER")) {
                    String ss = s_ar[5];
                    char wp = ss.charAt(0);
                    ss = s_ar[3];
                    char sb = ss.charAt(0);
                    Monster monster = new Monster(Integer.parseInt(s_ar[4]), sb, WeaponType.getWeaponType(wp));
                    world.addEntity(r, c, monster);

                }
                if (s_ar[2].equals("HERO")) {
                    String ss = s_ar[3];
                    char sb = ss.charAt(0);
                    Hero hero = new Hero(Integer.parseInt(s_ar[4]), sb, Integer.parseInt(s_ar[5]), Integer.parseInt(s_ar[6]));
                    world.addEntity(r, c, hero);
                }

            }
        } return world;
    }
}
