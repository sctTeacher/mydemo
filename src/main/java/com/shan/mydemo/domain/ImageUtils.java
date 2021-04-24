package com.shan.mydemo.domain;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Image utils, process image.
 *
 * @author sc
 * @date 2020-12-30
 */
@Slf4j
public class ImageUtils {

    public static final String BASE64_IMAGE_DATA_URI_SCHEMA_PATTERN = "data:image/%s;base64,";
    public static final String DEFAULT_IMAGE_FORMAT_NAME = "png";

    private static final BufferedImage FALLBACK_IMAGE;
    private static final byte[] FALLBACK_IMAGE_BYTES;

    private static final Set<String> IMAGE_FILE_NAME_EXT_SET;

    static {
        // init default image
        int imageWidth = 16;
        int imageHeight = 16;

        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, imageWidth, imageHeight);
        FALLBACK_IMAGE = bi;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Try.of(() -> ImageIO.write(FALLBACK_IMAGE, DEFAULT_IMAGE_FORMAT_NAME, bos));
        FALLBACK_IMAGE_BYTES = bos.toByteArray();

        // init image file name ext set
        Set<String> imageFileNameExtSet = new HashSet<>();
        imageFileNameExtSet.add("png");
        imageFileNameExtSet.add("jpg");
        imageFileNameExtSet.add("jpeg");
        imageFileNameExtSet.add("gif");
        imageFileNameExtSet.add("bmp");

        IMAGE_FILE_NAME_EXT_SET = imageFileNameExtSet;

    }

    /**
     * fallback image
     *
     * @return BufferedImage
     */
    public static BufferedImage getFallbackImage() {
        return FALLBACK_IMAGE;
    }

    /**
     * check is image fle.
     * <p/>
     * <li> xxx.png  --> true </li>
     * <li> a/b/xxx.png  --> true </li>
     * <li> xxx.doc  --> false </li>
     * <li> a/b/xxx.doc  --> false </li>
     *
     * @param fileName file_name
     * @return is image file. true: is image; false: not image
     */
    public static boolean isImage(String fileName) {
        return FilenameUtils.isExtension(StringUtils.lowerCase(fileName),
            IMAGE_FILE_NAME_EXT_SET);
    }

    /**
     * image data url schema.
     *
     * @param imageFormatName image format name
     * @return image data url schema
     */
    public static String imageDataUrlSchema(String imageFormatName) {
        return String.format(BASE64_IMAGE_DATA_URI_SCHEMA_PATTERN, imageFormatName);
    }

    /**
     * image png data url schema.
     *
     * @return image png data url schema
     */
    public static String imageDataUrlSchema() {
        return String.format(BASE64_IMAGE_DATA_URI_SCHEMA_PATTERN, DEFAULT_IMAGE_FORMAT_NAME);
    }

    /**
     * image file to base64 data block. defaults to a default image.
     *
     * @param url url
     * @return base64 str. defaults to a default image.
     */
    public static String toBase64Str(URL url) {
        return Optional.ofNullable(url)
            .map(FileUtils::toFile)
            .map(
                f -> Try.of(() -> FileUtils.readFileToByteArray(f))
                    .getOrElse(FALLBACK_IMAGE_BYTES)
            )
            .map(Base64::encodeBase64String)
            .orElse(Base64.encodeBase64String(FALLBACK_IMAGE_BYTES));
    }

    /**
     * image file to base64 data block. defaults to a default image.
     *
     * @param url url
     * @return base64 str. defaults to a default image.
     */
    public static String toBase64DataURL(URL url) {
        String imageFormatName = Optional.ofNullable(url)
            .map(URL::toString)
            .map(s -> StringUtils.substringAfter(s, "."))
            .map(StringUtils::trim)
            .orElse(DEFAULT_IMAGE_FORMAT_NAME);
        String imageDataUrlSchema = imageDataUrlSchema(imageFormatName);
        return imageDataUrlSchema + toBase64Str(url);
    }

    /**
     * buffered_image to base64_data_url
     *
     * @param bi buffered_image
     * @return base64_data_url
     */
    public static String toBase64DataURL(BufferedImage bi) {
        BufferedImage bil = Optional.ofNullable(bi).orElse(FALLBACK_IMAGE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bil, DEFAULT_IMAGE_FORMAT_NAME, baos);
        } catch (IOException e) {
            log.warn("Write image error.");
        }

        return imageDataUrlSchema(DEFAULT_IMAGE_FORMAT_NAME)
            + TasFileUtils.streamToBase64(new ByteArrayInputStream(baos.toByteArray()));
    }

    /**
     * base64 string to byte array (image).
     *
     * @param base64Str base64 string, without date prefix.
     * @return byte array (image)
     */
    public static byte[] fromBase64Str(String base64Str) {
        return Optional.ofNullable(base64Str)
            .map(s -> StringUtils.substringAfter(s, ","))
            .map(StringUtils::trim)
            .map(Base64::decodeBase64)
            .orElse(null);
    }

}
