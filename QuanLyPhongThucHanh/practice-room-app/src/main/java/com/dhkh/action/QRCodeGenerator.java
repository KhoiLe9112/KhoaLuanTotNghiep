package com.dhkh.action;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class QRCodeGenerator {
    public static byte[] generateQRCode(String qrCodeData, int width, int height, ErrorCorrectionLevel errorCorrectionLevel) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new EnumMap<>(EncodeHintType.class);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix qrCodeMatrix;
		try {
			qrCodeMatrix = writer.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height, hintMap);
			BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(qrCodeMatrix);
			ImageIO.write(qrCodeImage, "png", out);
		} catch (WriterException e) {
			e.printStackTrace();
		}

        return out.toByteArray();
    }
}