package com.application.icafapi.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Log4j2
@Service
public class QRUtil {

    public static InputStreamSource generateQR(String name, String email, String role) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode("Name: " + name + "\n" + "Email: " + email + "\n" + "Role: " + role, BarcodeFormat.QR_CODE, 350, 350 );
        } catch (WriterException e) {
            log.error(e.getMessage());
        }
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "PNG", os);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        byte[] bArray = os.toByteArray();
        InputStreamSource inputStreamSource = new ByteArrayResource(bArray);
        return inputStreamSource;
    }

}
