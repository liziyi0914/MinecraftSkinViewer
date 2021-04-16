package com.liziyi0914.lnmcl.msv;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Utils {

    public static Image resizeImg(Image source, Integer width, Integer height) {
        if(Objects.isNull(width)&&Objects.nonNull(height)) {
            width = (int) (height/source.getHeight()*source.getWidth());
        } else if(Objects.isNull(height)&&Objects.nonNull(width)) {
            height = (int)(width/source.getWidth()*source.getHeight());
        } else if(Objects.isNull(width) && Objects.isNull(height)) {
            return source;
        }
        java.awt.Image img = SwingFXUtils.fromFXImage(source, null).getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(img, 0, 0, null);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public static double[] rotate(double[] point, double delta) {
        double x = point[0];
        double y = point[1];
        double r = Math.sqrt(x * x + y * y);
        if (r == 0) return new double[]{0, 0};
        double theta = Math.acos(x / r);
        return new double[]{r * Math.cos(theta + delta / 180 * Math.PI), r * Math.sin(theta + delta / 180 * Math.PI)};
    }

    public static BufferedImage updateSkin(BufferedImage oldSkin) {
        BufferedImage newImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) newImg.getGraphics();
        g2.setColor(new java.awt.Color(255, 255, 255, 0));
        g2.fillRect(0, 0, 64, 64);
        g2.drawImage(oldSkin, 0, 0, null);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 16; j++) {
                copyPixel(oldSkin, newImg, 4 + i, 16 + j, 20 + (4 - i), 48 + j);
                copyPixel(oldSkin, newImg, 44 + i, 16 + j, 36 + (4 - i), 48 + j);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                copyPixel(oldSkin, newImg, 8 + i, 16 + j, 24 + (4 - i), 48 + j);
                copyPixel(oldSkin, newImg, 48 + i, 16 + j, 40 + (4 - i), 48 + j);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                copyPixel(oldSkin, newImg, i, 20 + j, 24 + (4 - i), 52 + j);
                copyPixel(oldSkin, newImg, 8 + i, 20 + j, 16 + (4 - i), 52 + j);
                copyPixel(oldSkin, newImg, 12 + i, 20 + j, 28 + (4 - i), 52 + j);

                copyPixel(oldSkin, newImg, 40 + i, 20 + j, 40 + (4 - i), 52 + j);
                copyPixel(oldSkin, newImg, 48 + i, 20 + j, 32 + (4 - i), 52 + j);
                copyPixel(oldSkin, newImg, 52 + i, 20 + j, 44 + (4 - i), 52 + j);
            }
        }
        return newImg;
    }

    public static void copyPixel(BufferedImage oldImg, BufferedImage newImg, int x, int y, int tx, int ty) {
        try {
            Graphics2D g2 = (Graphics2D) newImg.getGraphics();
            g2.setColor(new java.awt.Color(oldImg.getRGB(x, y), true));
            g2.fillRect(tx - 1, ty, 1, 1);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
