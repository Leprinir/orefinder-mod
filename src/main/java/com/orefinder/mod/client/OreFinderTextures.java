package com.orefinder.mod.client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OreFinderTextures {

    public static void generateAll() throws Exception {
        String path = "src/main/resources/assets/orefinder/textures/item/";
        Files.createDirectories(Paths.get(path));

        // Stone OreFinder — grey/brown compass
        generateItemTexture(path + "stone_ore_finder.png",
            new Color[]{ // 16x16 pixel art — stone compass
                new Color(139,135,120), new Color(160,155,140), new Color(180,175,160), new Color(140,136,122),
                new Color(100,95,85), new Color(120,115,105), new Color(90,85,75), new Color(70,65,58),
                new Color(60,55,48), new Color(80,75,68), new Color(110,105,95), new Color(130,125,112),
                new Color(150,145,130), new Color(170,165,150), new Color(190,185,170), new Color(210,205,190)
            });

        // Iron OreFinder — silver/steel
        generateItemTexture(path + "iron_ore_finder.png",
            new Color[]{
                new Color(180,180,185), new Color(200,200,210), new Color(220,220,230), new Color(185,185,195),
                new Color(140,140,150), new Color(160,160,170), new Color(120,120,130), new Color(95,95,110),
                new Color(75,75,95), new Color(110,110,130), new Color(145,145,165), new Color(165,165,185),
                new Color(190,190,210), new Color(210,210,230), new Color(230,230,250), new Color(250,250,255)
            });

        // Gold OreFinder — gold/yellow
        generateItemTexture(path + "gold_ore_finder.png",
            new Color[]{
                new Color(220,180,30), new Color(240,200,50), new Color(255,220,70), new Color(225,185,35),
                new Color(180,140,20), new Color(200,160,40), new Color(160,120,10), new Color(130,95,5),
                new Color(100,70,0), new Color(140,105,15), new Color(175,140,30), new Color(195,160,50),
                new Color(215,180,70), new Color(235,200,90), new Color(255,225,115), new Color(255,240,150)
            });

        // Diamond OreFinder — blue/cyan glow
        generateItemTexture(path + "diamond_ore_finder.png",
            new Color[]{
                new Color(40,180,220), new Color(60,200,240), new Color(80,220,255), new Color(45,190,230),
                new Color(20,150,190), new Color(35,170,210), new Color(10,130,170), new Color(5,100,140),
                new Color(0,70,110), new Color(15,110,150), new Color(30,140,180), new Color(50,165,210),
                new Color(70,190,235), new Color(90,215,250), new Color(115,235,255), new Color(140,250,255)
            });

        // Emerald Master Finder — bright green glow
        generateItemTexture(path + "emerald_master_finder.png",
            new Color[]{
                new Color(30,200,80), new Color(50,220,100), new Color(70,240,120), new Color(35,210,90),
                new Color(15,170,60), new Color(25,190,75), new Color(5,150,40), new Color(0,120,20),
                new Color(0,90,5), new Color(10,130,30), new Color(20,160,55), new Color(40,185,70),
                new Color(60,210,95), new Color(80,235,115), new Color(100,255,140), new Color(120,255,165)
            });

        // Upgrade Crystal — purple
        generateItemTexture(path + "ore_finder_upgrade.png",
            new Color[]{
                new Color(160,80,200), new Color(180,100,220), new Color(200,120,240), new Color(165,85,210),
                new Color(130,60,175), new Color(150,80,195), new Color(110,45,155), new Color(85,25,125),
                new Color(60,10,95), new Color(95,35,140), new Color(125,55,165), new Color(145,75,185),
                new Color(170,100,205), new Color(190,125,225), new Color(210,150,240), new Color(230,175,255)
            });

        System.out.println("Generated 6 item textures.");
    }

    private static void generateItemTexture(String filename, Color[] colors) throws Exception {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        int idx = 0;
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                Color c = colors[idx++ % colors.length];
                // Circle shape
                double cx = x - 7.5, cy = y - 7.5;
                double dist = Math.sqrt(cx*cx + cy*cy);
                if (dist < 7) {
                    // Dark border
                    if (dist > 5.5) {
                        img.setRGB(x, y, new Color(30,30,30,220).getRGB());
                    } else {
                        img.setRGB(x, y, new Color(c.getRed()/4, c.getGreen()/4, c.getBlue()/4, 255).getRGB());
                    }
                } else {
                    img.setRGB(x, y, 0); // transparent
                }
            }
        }
        ImageIO.write(img, "PNG", new File(filename));
    }

    public static void main(String[] args) throws Exception {
        generateAll();
    }
}
