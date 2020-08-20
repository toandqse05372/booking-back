package com.capstone.booking.common.helper.pdf;

import com.capstone.booking.entity.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class PdfPrinter {

    @Value("${aws.bucketLink}")
    private String bucketLink;

    public File printPDF(List<PrintRequest> printRequests, String placeKey) throws IOException, DocumentException, URISyntaxException {
        if(placeKey == null){
            placeKey = "";
        }
        switch(placeKey) {
            case "VIN":
                return vinFile(printRequests);
            case "SUNWORLD":
                return sunWorldFile(printRequests);
            default:
                return defaultFile(printRequests);
        }
    }

    private File defaultFile(List<PrintRequest> printRequests)throws IOException, DocumentException, URISyntaxException{
        //create pdf
        Document document = new Document();
        File file = new File("Test.pdf");
        FileOutputStream fos = new FileOutputStream(file);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);

        String content = getContent("default.txt");
        Image img = getImage("default.png");

        //start stream file
        document.open();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (PrintRequest printRequest : printRequests) {
            //get template file

            //change text file
            String fillContent = content.replace("NAMEx",
                    printRequest.getTicketType().getTypeName()
                            + "[" + printRequest.getVisitorType().getTypeName() + "]")
                    .replace("PRICEx",
                            String.valueOf(printRequest.getVisitorType().getPrice()) + " VND")
                    .replace("REDEMPTION_DATEx", dateFormat.format(printRequest.getRedemptionDate()))
                    .replace("PLACEx", printRequest.getPlace().getName());

            for (Ticket ticket : printRequest.getTickets()) {
                PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
                //enter image
                document.add(img);
                //enter text
                Chunk chunk1 = new Chunk(fillContent, getFont("vuArial.ttf"));
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
        return file;
    }

    private File vinFile(List<PrintRequest> printRequests)throws IOException, DocumentException, URISyntaxException{
        //create pdf
        Document document = new Document();
        File file = new File("Test.pdf");
        FileOutputStream fos = new FileOutputStream(file);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);

        String content = getContent("vin.txt");
        Image img = getImage("vin.png");

        //start stream file
        document.open();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (PrintRequest printRequest : printRequests) {
            //change text file
            content = content.replace("NAMEx",
                    printRequest.getTicketType().getTypeName()
                            + "[" + printRequest.getVisitorType().getTypeName() + "]")
                    .replace("PRICEx",
                            String.valueOf(printRequest.getVisitorType().getPrice()) + " VND")
                    .replace("REDEMPTION_DATEx", dateFormat.format(printRequest.getRedemptionDate()))
                    .replace("PLACEx", printRequest.getPlace().getName());

            for (Ticket ticket : printRequest.getTickets()) {

                PdfContentByte pdfContentByte = pdfWriter.getDirectContent();

                document.add(img);
                //enter text
                Chunk chunk1 = new Chunk(content, getFont("vuArial.ttf"));
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
        return file;
    }

    private File sunWorldFile(List<PrintRequest> printRequests)throws IOException, DocumentException, URISyntaxException{
        //create pdf
        Document document = new Document();
        File file = new File("Test.pdf");
        FileOutputStream fos = new FileOutputStream(file);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);

        String content = getContent("default.txt");
        Image img = getImage("default.png");

        //start stream file
        document.open();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (PrintRequest printRequest : printRequests) {

            //change text file
            content = content.replace("NAMEx",
                    printRequest.getTicketType().getTypeName()
                            + "[" + printRequest.getVisitorType().getTypeName() + "]")
                    .replace("PRICEx",
                            String.valueOf(printRequest.getVisitorType().getPrice()) + " VND")
                    .replace("REDEMPTION_DATEx", dateFormat.format(printRequest.getRedemptionDate()))
                    .replace("PLACEx", printRequest.getPlace().getName());

            for (Ticket ticket : printRequest.getTickets()) {
                PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
                document.add(img);
                //enter text
                Chunk chunk1 = new Chunk(content, getFont("vuArial.ttf"));
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
        return file;
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public String getContent(String contentFile) throws IOException {
        URL url = new URL(bucketLink+"ticketForm/"+contentFile);
        File f = new File("default.txt");
        FileUtils.copyURLToFile(url, f);
        Path path = f.toPath();
        Charset charset = StandardCharsets.UTF_8;
        //get content
        return new String(Files.readAllBytes(path), charset);
    }

    public Image getImage(String imageName) throws IOException, BadElementException {
        String imageUrl = bucketLink+"image/"+imageName;
        String imageStr = "image.png";
        saveImage(imageUrl, imageStr);
        Image img = Image.getInstance(imageStr);
        img.scaleAbsolute(200, 40);
        return  img;
    }

    public Font getFont(String fontName) throws IOException, DocumentException {
        URL url = new URL(bucketLink+"font/"+fontName);
        Font font = new Font(BaseFont.createFont(url.getPath(),
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
        return font;
    }

}
