package rahul.api.pdf_generation.service;

public interface PDFGenerationService {
    byte[] generatePDF(String pdfContent, String userPassoword, String adminPassword, String baseUrl);
}
