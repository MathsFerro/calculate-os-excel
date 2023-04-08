package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RgbUtils {
    public static XSSFColor generateXSSFColor(Color color) {
        byte[] rgb = new byte[] { (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue() };
        return new XSSFColor(rgb);
    }
}
