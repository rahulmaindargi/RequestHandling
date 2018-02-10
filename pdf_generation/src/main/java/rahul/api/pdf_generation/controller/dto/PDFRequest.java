package rahul.api.pdf_generation.controller.dto;

import lombok.Data;

@Data
public class PDFRequest {
    String pdfContent;
    String userPassoword;
    String adminPassword;
    String baseUrl;
/*
    public String getPdfContent() {
        return pdfContent;
    }

    public void setPdfContent(String pdfContent) {
        this.pdfContent = pdfContent;
    }

    public String getUserPassoword() {
        return userPassoword;
    }

    public void setUserPassoword(String userPassoword) {
        this.userPassoword = userPassoword;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }*/
}
