package rahul.api.pdf_generation;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import rahul.api.pdf_generation.controller.PDFController;

@SpringBootApplication
@Log4j2
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class PdfGenerationApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PdfGenerationApplication.class, args);
        log.info("Application Started {},{}", 1, 2);

      //  ctx.getBean(PDFController.class).generatePDFGet();
    }
}
