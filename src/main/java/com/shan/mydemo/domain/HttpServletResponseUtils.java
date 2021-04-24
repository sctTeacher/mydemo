package com.shan.mydemo.domain;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * HttpServletResponseUtils.
 *
 * @author zhangbr
 * @date 2021-01-02
 */
@Slf4j
public class HttpServletResponseUtils {

    public static final String RESPONSE_HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * set download header
     *
     * @param fileName file_name
     * @param response HttpServletResponse
     */
    public static void setDownloadHeader(String fileName, HttpServletResponse response) {
        String fileNameInHeader = fileName;
        try {
            fileNameInHeader = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            log.debug("Encoding file name failed, fallback to no encoding.", e);
        }
        response.setHeader(RESPONSE_HEADER_CONTENT_DISPOSITION,
            "attachment;filename=" + fileNameInHeader);
        response.setContentType("application/octet-stream");
    }

}
