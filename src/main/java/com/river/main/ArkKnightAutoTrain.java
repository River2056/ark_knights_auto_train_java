package com.river.main;


import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class ArkKnightAutoTrain {

    private Robot robot;
    private Rectangle rect;
    private static final int LONG_SECONDS = 2;
    private static final int SHORT_SECONDS = 1;
    private static int COMMENCE_BTN = Helper.getColorHashCode(0, 148, 213);
    private static int OPERATION_START = Helper.getColorHashCode(193, 70, 0);
    private static int OPERATION_END = Helper.getColorHashCode(255, 150, 2);
    private static int CANCEL_BTN = Helper.getColorHashCode(247, 247, 247);
    private static final java.util.List<Integer> COLOR_LIST = Arrays.asList(COMMENCE_BTN, OPERATION_START, OPERATION_END);

    public ArkKnightAutoTrain(int x, int y, int width, int height) throws AWTException {
        this.rect = new Rectangle(x, y, width, height);
        this.robot = new Robot();
    }

    public void clickOnElement(Robot robot, int x, int y) {
        System.out.println("found element! clicking on element");
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void setColorsAndPrintInfo(String colorR, String colorG, String colorB, int attribute) {
        int colorRInt = Integer.parseInt(colorR);
        int colorGInt = Integer.parseInt(colorG);
        int colorBInt = Integer.parseInt(colorB);
        System.out.printf(
                "color for %s: %s, %s, %s%n",
                attribute,
                colorRInt,
                colorGInt,
                colorBInt
        );
        attribute = Helper.getColorHashCode(colorRInt, colorGInt, colorBInt);
    }

    public void readPropertiesAsSettings() {
        try {
            // read from properties(color settings for different platforms)
            InputStream configInputStream = new FileInputStream("./settings.properties");
            Properties c = new Properties();

            c.load(configInputStream);
            if(!c.isEmpty()) {
                setColorsAndPrintInfo(
                        c.getProperty("color.commenceBtn.r"),
                        c.getProperty("color.commenceBtn.g"),
                        c.getProperty("color.commenceBtn.b"),
                        COMMENCE_BTN
                );

                setColorsAndPrintInfo(
                        c.getProperty("color.operationStart.r"),
                        c.getProperty("color.operationStart.g"),
                        c.getProperty("color.operationStart.b"),
                        OPERATION_START
                );

                setColorsAndPrintInfo(
                        c.getProperty("color.operationEnd.r"),
                        c.getProperty("color.operationEnd.g"),
                        c.getProperty("color.operationEnd.b"),
                        OPERATION_END
                );

                setColorsAndPrintInfo(
                        c.getProperty("color.cancelBtn.r"),
                        c.getProperty("color.cancelBtn.g"),
                        c.getProperty("color.cancelBtn.b"),
                        CANCEL_BTN
                );
            }

        } catch(Exception e) {
            System.err.println("read from settings file error, reverting back to default values");
            System.err.printf("Error: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }

    private int[] getScreenPixels(String message) {
        System.out.println(message);
        BufferedImage bufferedImage = robot.createScreenCapture(this.rect);
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        DataBufferInt dataBufferInt = (DataBufferInt) dataBuffer;
        int[] pixels = dataBufferInt.getData();
        return pixels;
    }

    public boolean checkForCancelBtn() throws InterruptedException {
        int[] pixels = getScreenPixels("checking for cancel button");
        for(int y = 0; y < rect.height; y++) {
            for(int x = 0; x < rect.width; x++) {
                int index = y * rect.width + x;
                int rgb = pixels[index];
                int confirmColor = pixels[index + 3 >= pixels.length - 1 ? pixels.length - 1 : index + 3];
                if(rgb == CANCEL_BTN && confirmColor == CANCEL_BTN) {
                    clickOnElement(robot, x, y);
                    Thread.sleep(LONG_SECONDS * 1000);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean run(int color) {
        try {
            // gets whole screen screenshot
//            BufferedImage bufferedImage = robot.createScreenCapture(this.rect);
//            ImageIO.write(bufferedImage, "png", new File("./screenshot.png"));

            int[] pixels = getScreenPixels("starting auto train.");
            for(int y = 0; y < rect.height; y++) {
                for(int x = 0; x < rect.width; x++) {
                    int index = y * rect.width + x;
                    int rgb = pixels[index];
                    int confirmColor = pixels[index + 15 >= pixels.length - 1 ? pixels.length - 1 : index + 15];
                    if(rgb == color && confirmColor == color) {
                        clickOnElement(robot, x, y);
                        Thread.sleep(LONG_SECONDS * 1000);
                        return true;
                    }
                }
            }

            System.out.println("element not found, waiting for next loop...");
            Thread.sleep(LONG_SECONDS * 1000);
            return false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException, AWTException {
        int count = 0;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();



        ArkKnightAutoTrain arkKnights = new ArkKnightAutoTrain(0, 0, (int) width, (int) height);
        System.out.println("starting auto train...");

        System.out.println("reading color properties from settings file...");
        arkKnights.readPropertiesAsSettings();

        for(int i = 5; i >= 0; i--) {
            System.out.printf("commence in %s seconds...%n", i);
            Thread.sleep(SHORT_SECONDS * 1000);
        }

        while(true) {
            int color = 0;
            switch(count % 3) {
                case 0: color = COMMENCE_BTN; System.out.printf("color is now: %s%n", "COMMENCE_BTN"); break;
                case 1: color = OPERATION_START; System.out.printf("color is now: %s%n", "OPERATION_START"); break;
                case 2: color = OPERATION_END; System.out.printf("color is now: %s%n", "OPERATION_END"); break;
            }
            System.out.printf("color: %s%n", color);
            System.out.printf("count: %s%n", count);
            boolean isClicked = arkKnights.run(color);
            if(isClicked) {
                count++;
            }
//            else {
//                arkKnights.checkForCancelBtn();
//                count--;
//            }
            Thread.sleep(LONG_SECONDS * 1000);
        }
    }
}
