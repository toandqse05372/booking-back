package com.capstone.booking.common.helper;

import com.capstone.booking.entity.*;
import com.capstone.booking.repository.OrderItemRepository;
import com.capstone.booking.service.impl.EmailSenderService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
public class PdfPrinter {

    public File printPDF(List<PrintRequest> printRequests) throws IOException, DocumentException, URISyntaxException {

        //create pdf
        Document document = new Document();
        File file = new File("Test.pdf");
        FileOutputStream fos = new FileOutputStream(file);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);

        //mở streaming data vào file
        document.open();

        for (PrintRequest printRequest : printRequests) {
            //lấy file
            Path path = Paths.get(ClassLoader.getSystemResource("test.txt").toURI());
            Charset charset = StandardCharsets.UTF_8;

            //gate file
            String content = new String(Files.readAllBytes(path), charset);
            //change text file
            content = content.replace("NAMEx",
                    printRequest.getTicketType().getTypeName()
                            + "[" + printRequest.getVisitorType().getTypeName() + "]")
                    .replace("PRICEx",
                            String.valueOf(printRequest.getVisitorType().getPrice()) + " VND")
                    .replace("REDEMPTION_DATEx", printRequest.getRedemptionDate().toString())
                    .replace("PLACEx", printRequest.getPlace().getName());

            for (Ticket ticket : printRequest.getTickets()) {
                PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
                //đưa ảnh vào
                Path pathI = Paths.get(ClassLoader.getSystemResource("images.jpg").toURI());
                Image img = Image.getInstance(pathI.toAbsolutePath().toString());
                img.scaleAbsolute(200, 40);
                document.add(img);
                //đưa chữ vào
                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                Chunk chunk1 = new Chunk(content, font);
                document.add(new Paragraph("\n"));
                document.add(chunk1);
                //gen barcode
                Barcode128 barcode128 = new Barcode128();
                barcode128.setCode(ticket.getCode());
                barcode128.setCodeType(Barcode128.CODE128);
                Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);
                code128Image.setInterpolation(true);
                document.add(code128Image);
            }
        }

        document.close();
//
//        fos.close();
        return file;
    }

}
