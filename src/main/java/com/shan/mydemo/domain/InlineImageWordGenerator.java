package com.shan.mydemo.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.StringBuilderWriter;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Word doc generator.
 *
 * @author sc
 * @date 2020-12-31
 */
@Component
@Slf4j
public class InlineImageWordGenerator {

    @Resource
    private SpringTemplateEngine templateEngine;

    /**
     * generate doc and write to OutputStream
     *
     * @param templateName templateName
     * @param ctx ctx, to set variables
     * @param os OutputStream, e.g. {@code HttpServletResponse.getOutputStream()}
     * @throws
     */
    public void generate(String templateName, IContext ctx, OutputStream os) {
        try (OutputStream os2 = os) {
            // generate html string
            StringBuilderWriter sbw2 = new StringBuilderWriter();
            templateEngine.process(templateName, ctx, sbw2);
            String htmlString = sbw2.toString();
            if (log.isDebugEnabled()) {
                log.debug("Template generated html:{}{}{}",
                    System.lineSeparator(),
                    htmlString,
                    System.lineSeparator());
            }

            // convert (x)html string to word doc
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
            XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
            xhtmlImporter.setHyperlinkStyle("Hyperlink");
            wordMLPackage.getMainDocumentPart().getContent()
                .addAll(xhtmlImporter.convert(htmlString, null));

            // write to output stream
            wordMLPackage.save(os2);
        } catch (Docx4JException | IOException e) {
            throw  new RuntimeException("error");
        }
    }

}
