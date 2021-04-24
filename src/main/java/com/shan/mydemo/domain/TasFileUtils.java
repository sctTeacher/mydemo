package com.shan.mydemo.domain;
import io.vavr.control.Try;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.Optional;

/**
 * FileUtils.
 *
 * @author zhangbr
 * @date 2021-01-02
 */
public class TasFileUtils extends FileUtils {

    /**
     * URL to File
     *
     * @param url url
     * @return File
     * @throws
     */
    public static File getFile(URL url) {
        return Optional.ofNullable(url)
            .map(u -> Try.of(u::toURI))
            .filter(Try::isSuccess)
            .map(Try::get)
            .map(File::new)
            .filter(File::exists)
            .orElseThrow(() -> new RuntimeException("error"));
    }


    /**
     * trim root dir, remove root dir.
     *
     * @param filePath file_path
     * @param rootDir root_dir
     * @return relative path, e.g. {@code /root/xxx/yyy} to {@code xxx/yyy}
     */
    public static String trimToRelativeDir(String filePath, String rootDir) {
        if (StringUtils.isBlank(filePath)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(rootDir)) {
            return filePath;
        }

        String fullRootDirName = FilenameUtils.normalize(
            StringUtils.join(rootDir, File.separator)
        );
        String filePathRelative = StringUtils.substringAfter(filePath, fullRootDirName);
        return FilenameUtils.normalize(filePathRelative);
    }

    /**
     * base64 string to output stream
     *
     * @param base64Str base64_str
     * @param os output_stream
     */
    public static void base64ToStream(String base64Str, OutputStream os) {
        if (os == null) {
            return;
        }
        try (OutputStream osl = os) {
            if (StringUtils.isNoneBlank(base64Str)) {
                byte[] bytes = Base64.decodeBase64(base64Str);
                osl.write(bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException("error");
        }
    }

    /**
     * stream to base64_string.
     *
     * @param is input_string
     * @return base64_string
     */
    public static String streamToBase64(InputStream is) {
        try (InputStream isl = is) {
            if ((is == null) || (is.available() <= 0)) {
                return StringUtils.EMPTY;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(isl, baos);
            return Base64.encodeBase64String(baos.toByteArray());
        } catch (IOException e) {
            throw  new RuntimeException("error");
        }
    }

}
