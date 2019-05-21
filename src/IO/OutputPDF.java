package IO;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import javax.swing.border.Border;
import java.io.FileOutputStream;

public class OutputPDF {

    public static void writeAllToPdf(String[][] classes) {
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("CompleteSchedule.pdf"));
            document.open();
            PdfPTable table = new PdfPTable(3); // 3 columns.
            document.add(new Paragraph("Complete schedule:"));
            int i =0;
            for (String[] oneClass : classes) {
                //System.out.println(oneClass[0]);
                table.setWidthPercentage(100); //Width 100%
                table.setSpacingBefore(10f); //Space before table
                table.setSpacingAfter(10f); //Space after table

                //Set Column widths
                float[] columnWidths = {1f,1f,1f};
                table.setWidths(columnWidths);

                PdfPCell cell1 = new PdfPCell(new Paragraph(oneClass[0]));
                cell1.setBorder(0);
                //cell1.setBorderColor(BaseColor.BLUE);
                //cell1.setPaddingLeft(10);
                cell1.setPaddingTop(3);
                cell1.setPaddingBottom(3);
                cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell2 = new PdfPCell(new Paragraph(oneClass[1]));
                cell2.setBorder(0);
                //cell1.setBorderColor(BaseColor.BLUE);
                //cell2.setPaddingLeft(10);
                cell2.setPaddingTop(3);
                cell2.setPaddingBottom(3);
                cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell3 = new PdfPCell(new Paragraph(oneClass[2]));
                cell3.setBorder(0);
                //cell1.setBorderColor(BaseColor.BLUE);
                //cell2.setPaddingLeft(10);
                cell3.setPaddingTop(3);
                cell3.setPaddingBottom(3);
                cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);




                //To avoid having the cell border and the content overlap, if you are having thick cell borders
                //cell1.setUserBorderPadding(true);
                //cell2.setUserBorderPadding(true);
                //cell3.setUserBorderPadding(true);

                if(i%2==0){
                    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                }

                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                i++;
            }

            document.add(table);

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeToPdfByFaculty(){

    }

    public static void writeToPdfByYear(){

    }
    public static void writeToPdfByFacultyAndYear(){

    }
}
