package com.river.test;

import com.river.main.Helper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestDrawRect {
    public static void main(String[] args) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = bufferedImage.createGraphics();

        for(int y = 0; y < bufferedImage.getHeight(); y++) {
            int r = 0, g = 0, b = 0;
            switch(y % 3) {
                case 0:
                    r = 0;
                    g = 138;
                    b = 204;
                    break;
                case 1:
                    r = 76;
                    g = 10;
                    b = 0;
                    break;
                case 2:
                    r = 255;
                    g = 150;
                    b = 2;
                    break;
                default:
                    r = 0;
                    g = 0;
                    b = 0;
            }
            graphics.setColor(new Color(Helper.getColorHashCode(r, g, b)));
            for(int x = 0; x < bufferedImage.getWidth(); x++) {
                graphics.fillRect(x, y, 1, 1);
            }
        }

        graphics.dispose();

        ImageIO.write(bufferedImage, "png", new File("./test.png"));

    }
}
