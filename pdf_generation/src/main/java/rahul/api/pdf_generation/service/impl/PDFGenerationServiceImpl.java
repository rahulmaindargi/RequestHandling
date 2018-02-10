package rahul.api.pdf_generation.service.impl;

import com.lowagie.text.DocumentException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;
import rahul.api.pdf_generation.service.PDFGenerationService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service("pdfGenerationService")
@Log4j2
public class PDFGenerationServiceImpl implements PDFGenerationService {

    @Override
    public byte[] generatePDF(String pdfContent, String userPassword, String adminPassword, String baseUrl) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        //renderer.getSharedContext().setBaseURL(baseUrl);
        //renderer.getSharedContext().getUserAgentCallback().setBaseURL(baseUrl);
        renderer.setDocumentFromString(pdfContent, baseUrl);
        renderer.layout();
        byte[] foDoc = null;
        try {
            /*https://stackoverflow.com/questions/4782876/resolving-protected-resources-with-flying-saucer-itextrenderer*/
            //Add response from password protected img or resource.
            renderer.setPDFEncryption(new PDFEncryption(getPassword(userPassword), getPassword(adminPassword)));
            renderer.createPDF(os);
            foDoc = os.toByteArray();
            os.close();
        } catch (DocumentException e) {
            log.error("DocumentException while creating PDF", e);
        } catch (IOException e) {
            log.error("IOException while creating PDF", e);
        }
        return foDoc;
    }

    private byte[] getPassword(String password) {
        Optional<String> passwordOptional = Optional.ofNullable(password);
        return passwordOptional.map(String::getBytes).orElse(null);
    }
}
