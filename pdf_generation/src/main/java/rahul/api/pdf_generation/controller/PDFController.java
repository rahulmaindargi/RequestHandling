package rahul.api.pdf_generation.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rahul.api.pdf_generation.controller.dto.PDFRequest;
import rahul.api.pdf_generation.service.PDFGenerationService;
@Log4j2
@RestController
@RequestMapping("/pdf")
public class PDFController {

    private PDFGenerationService pdfGenerationService;

    @Autowired
    public PDFController(PDFGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }

    @PostMapping(value = "/generatePDF", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public byte[] generatePDF(@RequestBody PDFRequest pdfRequest) {

        byte[] pdf = pdfGenerationService.generatePDF(pdfRequest.getPdfContent(), pdfRequest.getUserPassoword(), pdfRequest.getAdminPassword(), pdfRequest.getBaseUrl());
        log.info("PDF generated fixed {}", pdf.length);
        return pdf;
    }

    @GetMapping(value = "/generatePDF", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public byte[] generatePDFGet() {
        String request = "<html><body>Hello World</body></html>";
        byte[] pdf = pdfGenerationService.generatePDF(request, "test", "test", "http://abc.com");
        log.info("PDF generated fixed {}", pdf.length);
        return pdf;
    }

}
