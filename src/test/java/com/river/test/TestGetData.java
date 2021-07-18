package com.river.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;

public class TestGetData {
    public static void main(String[] args) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream("./test.png"));
        BufferedImage image = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        g2d.dispose();

        DataBuffer dataBuffer = image.getRaster().getDataBuffer();
        DataBufferInt dataBufferInt = (DataBufferInt) dataBuffer;
        int[] pixels = dataBufferInt.getData();

        System.out.println("pixels length: " + pixels.length);
        for(int i: pixels) {
            System.out.println(i);
        }
    }
}
