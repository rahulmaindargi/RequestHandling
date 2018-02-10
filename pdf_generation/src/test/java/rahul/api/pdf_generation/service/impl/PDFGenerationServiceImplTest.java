package rahul.api.pdf_generation.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rahul.api.pdf_generation.service.PDFGenerationService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PDFGenerationServiceImplTest {

    String request="<html><body>Hello World</body></html>";
    @Autowired
    PDFGenerationService pdfGenerationService;
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void generatePDF() {
        byte [] pdf=pdfGenerationService.generatePDF(request, "test", "test", "http://abc.com");
        System.out.println(pdf.length);
        String outputFile = "seconddoc.pdf";
        try {
            OutputStream os = new FileOutputStream(outputFile);
            os.write(pdf);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}