package com.example.andersontimetracking.services;

import com.example.andersontimetracking.models.TimeRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PdfGenerator {
    public void generatePdf(List<TimeRecord> records) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                float margin = 40;
                float yPosition = page.getMediaBox().getHeight() - margin;
                int rowsPerPage = 10;
                int numRows = 0;

                for (TimeRecord record : records) {
                    if (numRows == rowsPerPage) {
                        contentStream.close();
                        PDPage newPage = new PDPage(PDRectangle.A4);
                        document.addPage(newPage);
                        contentStream.moveTo(margin, newPage.getMediaBox().getHeight() - margin);
                        yPosition = newPage.getMediaBox().getHeight() - margin;
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        numRows = 0;
                    }

                    yPosition -= 20;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("Date: " + record.getDate());
                    contentStream.endText();
                    yPosition -= 20;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("Name: " + record.getName());
                    contentStream.endText();
                    yPosition -= 20;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("Description: " + record.getDescription());
                    contentStream.endText();
                    yPosition -= 20;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("Total Time: " + record.getTotalTime());
                    contentStream.endText();
                    yPosition -= 20;

                    numRows++;
                }
            }

            document.save("report.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}