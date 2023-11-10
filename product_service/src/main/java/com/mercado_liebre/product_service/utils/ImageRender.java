package com.mercado_liebre.product_service.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//https://stackoverflow.com/questions/665406/how-to-make-a-color-transparent-in-a-bufferedimage-and-save-as-png
public class ImageRender {
    public byte[] removeBackground(byte[] image) {
        try {
            BufferedImage bufferedImage = arrayByteToBufferedImage(image);
//            Color colorToMakeTransparent = new Color(255, 255, 255);
            int color = bufferedImage.getRGB(0,0);
            Image imageTransformed = transformColorToTransparency(bufferedImage, new Color(color));

            BufferedImage imageWithTransparentBackground = imageToBufferedImage(imageTransformed, bufferedImage.getWidth(), bufferedImage.getHeight());

            ByteArrayOutputStream imageConverted = convertToPNG(imageWithTransparentBackground);
            byte[] imageConvertedArrayByte = imageConverted.toByteArray();

            return imageConvertedArrayByte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private BufferedImage arrayByteToBufferedImage(byte[] image) {
        try {
            ByteArrayInputStream imageByte = new ByteArrayInputStream(image);
            BufferedImage bufferedImage = ImageIO.read(imageByte);

            return bufferedImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private BufferedImage imageToBufferedImage(Image image, int width, int height) {
        try {
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();

            return bufferedImage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Image transformColorToTransparency(BufferedImage image, Color color) {
        int tolerance = 30;
        ImageFilter filter = new RGBImageFilter() {
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                int alpha = (rgb & 0xFF000000) >>> 24;
                int red = (rgb & 0x00FF0000) >> 16;
                int green = (rgb & 0x0000FF00) >> 8;
                int blue = rgb & 0x000000FF;

                int colorRed = color.getRed();
                int colorGreen = color.getGreen();
                int colorBlue = color.getBlue();

                if (Math.abs(red - colorRed) <= tolerance &&
                        Math.abs(green - colorGreen) <= tolerance &&
                        Math.abs(blue - colorBlue) <= tolerance) {
                    // Make the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // Nothing to do, return the original color
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    private ByteArrayOutputStream convertToPNG(BufferedImage image) {
        try {
            ByteArrayOutputStream imageOutput = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imageOutput);

            return imageOutput;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

