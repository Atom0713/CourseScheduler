package IO;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import root.DataManipulations;
import root.Timetable;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;



public class OutputEXCEL {
    static Timetable timetable;
    public OutputEXCEL(Timetable timetable)
    {
        this.timetable=timetable;
    }

    public OutputEXCEL() {
    }

    public static void professorsTimetableOutputExcel(ArrayList<String> profNames, ArrayList<String[][]> profTimetable, String path)
    {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        /*Creating XSSFStyle objects.*/
        XSSFCellStyle daysOfTheWeekStyle = workbook.createCellStyle();
        XSSFCellStyle timeIntervalsStyle = workbook.createCellStyle();
        XSSFCellStyle timeCellStyle = workbook.createCellStyle();
        XSSFCellStyle timeTableStyle = workbook.createCellStyle();

        /* Creating XSSFFont objects, set font settings */
        XSSFFont daysOfTheWeekFont = workbook.createFont();
        XSSFFont timeTableFont = workbook.createFont();
        XSSFFont timeIntervalFont = workbook.createFont();

        timeIntervalFont.setBold(true);
        daysOfTheWeekFont.setBold(true);
        daysOfTheWeekFont.setFontHeightInPoints((short)10);
        timeIntervalFont.setFontHeightInPoints((short)9);
        timeTableFont.setFontHeightInPoints((short)8);

        timeTableStyle.setFont(timeTableFont);
        timeTableStyle.setWrapText(true);
        timeTableStyle.setAlignment(HorizontalAlignment.CENTER);
        timeTableStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        /* Attaching the font to the daysOfTheWeekStyle created earlier
        * Columns B:G
        */
        daysOfTheWeekStyle.setFont(daysOfTheWeekFont);
        daysOfTheWeekStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        daysOfTheWeekStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        daysOfTheWeekStyle.setBorderBottom(BorderStyle.THIN);
        daysOfTheWeekStyle.setBorderRight(BorderStyle.THIN);
        daysOfTheWeekStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        daysOfTheWeekStyle.setAlignment(HorizontalAlignment.CENTER);


        //Time interval cells from A2:A10
        timeIntervalsStyle.setFont(timeIntervalFont);
        timeIntervalsStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        timeIntervalsStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        timeIntervalsStyle.setBorderRight(BorderStyle.THIN);
        timeIntervalsStyle.setBorderBottom(BorderStyle.THIN);
        timeIntervalsStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        timeIntervalsStyle.setAlignment(HorizontalAlignment.CENTER);
        timeIntervalsStyle.setShrinkToFit(true);


        // "Time" cell: A1
        timeCellStyle.setFont(daysOfTheWeekFont);
        timeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        timeCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        timeCellStyle.setBorderBottom(BorderStyle.THIN);
        timeCellStyle.setBorderRight(BorderStyle.THIN);
        timeCellStyle.setAlignment(HorizontalAlignment.CENTER);

        //Declare  blank sheet
        XSSFSheet sheet;

        //TreeMap will be holding timetable of each separate sheet, and used to fill the EXCEL file with that data.
        TreeMap<String, Object[]> data = new TreeMap<String, Object[]>();

        //Declare couple neccessary objects,
        Object[] objArr = null;
        Row row = null;
        Cell cell = null;
        int rowNum = 0;
        int cellNum = 0;


        /*
        * Creating sheets, each named after a professor.
        * Creating and filling their timetables.
        * */
        for (int pnames = 0; pnames < profNames.size(); pnames++)
        {
            sheet = workbook.createSheet(profNames.get(pnames));

            row = sheet.createRow(rowNum++);

            /*
             * Filling first row of each sheet with column names.
             */
            if(rowNum == 1)
            {
                objArr = new Object[] {"Time", "Professors Name", "Monday","Tuesday", "Wednesday", "Thursday", "Friday"};
                for (Object obj : objArr)
                {
                    cell = row.createCell(cellNum++);
                    cell.setCellStyle(daysOfTheWeekStyle);
                    cell.setCellValue((String)obj);

                }
            }

            String[][] professorsTimetable = profTimetable.get(pnames);
            /*
            * Collecting all data neccessary for the timetable creation of each professor.
             */
            for (int j = 1; j < 10; j++)
            {
                data.put(Integer.toString(j), new Object[] {Timetable.timeIndexToString(j-1)+"-"+(Timetable.timeIndexToString((j-1)+1)),profNames.get(pnames), professorsTimetable[0][j-1], professorsTimetable[1][j-1], professorsTimetable[2][j-1], professorsTimetable[3][j-1], professorsTimetable[4][j-1]});
            }

            Set<String> keyset = data.keySet();//keyset is unordered, mixes up output

            /*
            * Looping through all 9 time intervals and filling cells with column appropriate data.
             */
            for (int i = 1; i < 10; i++)
            {
                objArr=null;
                row = sheet.createRow(rowNum++);

                /*
                * Unfortunately, keyset of the data collected prior to the filling process is unordered.
                * Attempts ordering keyset in ascending order failed, so far!
                * For loop below helps to locate the key to the data that is required!
                 */
                //TODO: order keyset without looping(reduce tine!)
                for(String key: keyset)
                {
                    if(key.equals(Integer.toString(i)))
                    {
                        objArr = data.get(key);
                    }
                }

                cellNum = 0;//every new sheet requires cellNum to start from 0
                for (Object obj : objArr)
                {
                    cell = row.createCell(cellNum++);

                    if(cellNum == 1)//"Time" column
                    {
                        cell.setCellStyle(timeIntervalsStyle);

                    }else{
                            cell.setCellStyle(timeTableStyle);
                    }

                    if(obj instanceof String)
                    {
                        cell.setCellValue((String)obj);

                    } else if(obj instanceof Integer)
                    {
                        cell.setCellValue((Integer)obj);
                    }

                }
            }
            //sheet.autoSizeColumn(pnames);
            rowNum = 0;//row count should be set to zero prior to the new sheet creation.
            cellNum = 0;
            data = new TreeMap<String, Object[]>();
        }

            autisizeColumns(workbook, profNames.size(), 7);

        try
        {
            //Write workbook into file system
            FileOutputStream out = new FileOutputStream(new File(path+"\\Academic Staff Timetable.xlsx"));
            workbook.write(out);
            out.close();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public static void classroomsTimetableOutputExcel(ArrayList<String> allClassRoomNames, ArrayList<String[][]> allClassRoomsTimetables, String path)
    {
        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle daysOfTheWeekStyle = workbook.createCellStyle();
        XSSFCellStyle timeIntervalsStyle = workbook.createCellStyle();
        XSSFCellStyle timeCellStyle = workbook.createCellStyle();
        XSSFCellStyle timeTableStyle = workbook.createCellStyle();

        /* Create XSSFFont objects, set font settings */
        XSSFFont daysOfTheWeekFont=workbook.createFont();
        XSSFFont timeTableFont=workbook.createFont();
        XSSFFont timeIntervalFont=workbook.createFont();

        daysOfTheWeekFont.setBold(true);
        timeIntervalFont.setBold(true);
        daysOfTheWeekFont.setFontHeightInPoints((short)10);
        timeIntervalFont.setFontHeightInPoints((short)9);
        timeTableFont.setFontHeightInPoints((short)8);

        timeTableStyle.setFont(timeTableFont);
        timeTableStyle.setWrapText(true);
        timeTableStyle.setAlignment(HorizontalAlignment.CENTER);
        timeTableStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        /* attach the font to the daysOfTheWeekStyle created earlier
         * Columns B:G
         */
        daysOfTheWeekStyle.setFont(daysOfTheWeekFont);
        daysOfTheWeekStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        daysOfTheWeekStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        daysOfTheWeekStyle.setBorderBottom(BorderStyle.THIN);
        daysOfTheWeekStyle.setBorderRight(BorderStyle.THIN);
        daysOfTheWeekStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        daysOfTheWeekStyle.setAlignment(HorizontalAlignment.CENTER);


        //Time interval cells from A2:A10
        timeIntervalsStyle.setFont(timeIntervalFont);
        timeIntervalsStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        timeIntervalsStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        timeIntervalsStyle.setBorderRight(BorderStyle.THIN);
        timeIntervalsStyle.setBorderBottom(BorderStyle.THIN);
        timeIntervalsStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        timeIntervalsStyle.setAlignment(HorizontalAlignment.CENTER);
        timeIntervalsStyle.setShrinkToFit(true);


        // "Time" cell: A1
        timeCellStyle.setFont(daysOfTheWeekFont);
        timeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        timeCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        timeCellStyle.setBorderBottom(BorderStyle.THIN);
        timeCellStyle.setBorderRight(BorderStyle.THIN);
        timeCellStyle.setAlignment(HorizontalAlignment.CENTER);
        timeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


        //Declare a blank sheet
        XSSFSheet sheet;

        //Fill TreeMap with the data to be written to the excel file
        TreeMap<String, Object[]> data = new TreeMap<String, Object[]>();

        //Declare couple neccessary objects,
        Object[] objArr;
        Row row;
        Cell cell;
        int rowNum = 0;
        int cellNum = 0;


        /*
         * Creating sheets, each named after a professor.
         * Creating and filling their timetables.
         * */
        for (int classroom = 0; classroom < allClassRoomNames.size(); classroom++)
        {
            sheet = workbook.createSheet(allClassRoomNames.get(classroom));
            row = sheet.createRow(rowNum++);

            /*
             * Filling first row of each sheet with column names.
             */
            if(rowNum == 1)
            {
                objArr = new Object[] {"Time", "Professors Name", "Monday","Tuesday", "Wednesday", "Thursday", "Friday"};
                for (Object obj : objArr)
                {
                    cell = row.createCell(cellNum++);
                    cell.setCellStyle(daysOfTheWeekStyle);
                    cell.setCellValue((String)obj);

                }
            }

            String[][] classromTimetable = allClassRoomsTimetables.get(classroom);;
            /*
             * Collecting all data neccessary for the timetable creation of each professor.
             */
            for (int j = 1; j < 10; j++)
            {
                data.put(Integer.toString(j), new Object[] {Timetable.timeIndexToString(j-1)+"-"+(Timetable.timeIndexToString((j-1)+1)),allClassRoomNames.get(classroom), classromTimetable[0][j-1], classromTimetable[1][j-1], classromTimetable[2][j-1], classromTimetable[3][j-1], classromTimetable[4][j-1]});
            }

            Set<String> keyset = data.keySet();//keyset is unordered, mixes up output

            /*
             * Looping through all 9 time intervals and filling cells with column appropriate data.
             */
            for (int i = 1; i < 10; i++)
            {
                objArr=null;
                row = sheet.createRow(rowNum++);

                /*
                 * Unfortunately, keyset of the data collected prior to the filling process is unordered.
                 * Attempts ordering keyset in ascending order failed, so far!
                 * For loop below helps to locate the key to the data that is required!
                 */
                //TODO: order keyset without looping(reduce tine!)
                for(String key: keyset)
                {
                    if(key.equals(Integer.toString(i)))
                    {
                        objArr = data.get(key);
                    }
                }

                cellNum = 0;//every new sheet requires cellNum to start from 0
                for (Object obj : objArr)
                {
                    cell = row.createCell(cellNum++);

                    if(cellNum == 1)//"Time" column
                    {
                        cell.setCellStyle(timeIntervalsStyle);

                    }else{
                        cell.setCellStyle(timeTableStyle);
                    }

                    if(obj instanceof String)
                    {
                        cell.setCellValue((String)obj);

                    } else if(obj instanceof Integer)
                    {
                        cell.setCellValue((Integer)obj);
                    }

                }
            }
            rowNum = 0;//row count should be set to zero prior to the new sheet creation.
            cellNum = 0;
            data = new TreeMap<String, Object[]>();
        }
        autisizeColumns(workbook, allClassRoomNames.size(), 7);

        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(path+"\\Classrooms Timetable.xlsx"));
            workbook.write(out);
            out.close();
        }
        catch (Exception e) { e.printStackTrace();
        }
    }
    public static void weeklyTimetableOutputExcel(DataManipulations dm, String path)
    {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //create XSSFCellStyle objects from wrokbook
        XSSFCellStyle daysOfTheWeekStyle = workbook.createCellStyle();//general text&cell style
        XSSFCellStyle borderStyle = workbook.createCellStyle();//to set Border colors
        XSSFCellStyle timeTableStyle = workbook.createCellStyle();
        XSSFCellStyle timeCellStyle = workbook.createCellStyle();
        XSSFCellStyle timeIntervalsStyle = workbook.createCellStyle();


        /* Create XSSFFont objects, set font settings */
        XSSFFont daysOfTheWeekFont=workbook.createFont();
        XSSFFont timeTableFont=workbook.createFont();
        XSSFFont timeIntervalFont=workbook.createFont();
        XSSFFont borderStyleFont = workbook.createFont();

        timeIntervalFont.setBold(true);
        timeIntervalFont.setFontHeightInPoints((short)8);
        daysOfTheWeekFont.setBold(true);
        daysOfTheWeekFont.setFontHeightInPoints((short)10);
        timeTableFont.setFontHeightInPoints((short)8);
        borderStyleFont.setFontHeightInPoints((short)8);

        //set Border thickness and color
        borderStyle.setBorderRight(BorderStyle.THICK);
        borderStyle.setWrapText(true);
        borderStyle.setFont(borderStyleFont);

        timeTableStyle.setWrapText(true);
        timeTableStyle.setFont(timeTableFont);
        timeTableStyle.setAlignment(HorizontalAlignment.CENTER);
        timeTableStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        /* Applying the font and adding other attributes to the daysOfTheWeekStyle cell style*/
        daysOfTheWeekStyle.setFont(daysOfTheWeekFont);
        daysOfTheWeekStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        daysOfTheWeekStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        daysOfTheWeekStyle.setBorderRight(BorderStyle.THIN);
        daysOfTheWeekStyle.setBorderBottom(BorderStyle.THIN);
        daysOfTheWeekStyle.setBorderLeft(BorderStyle.THIN);
        daysOfTheWeekStyle.setAlignment(HorizontalAlignment.CENTER);
        daysOfTheWeekStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // "Time" cell: A1
        timeCellStyle.setFont(daysOfTheWeekFont);
        timeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        timeCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        timeCellStyle.setBorderBottom(BorderStyle.THIN);
        timeCellStyle.setBorderRight(BorderStyle.THIN);

        //Time interval cells from A2:A10
        timeIntervalsStyle.setFont(timeIntervalFont);
        timeIntervalsStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        timeIntervalsStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        timeIntervalsStyle.setBorderRight(BorderStyle.THIN);
        timeIntervalsStyle.setBorderBottom(BorderStyle.THIN);
        timeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        timeCellStyle.setAlignment(HorizontalAlignment.CENTER);


        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Weekly Timetable");

        //Fill TreeMap with the data to be written to the excel file
        TreeMap<String, Object[]> data = new TreeMap<String, Object[]>();

        Cell cell = null;
        Row row = null;
        int rownum = 0;
        int cellnum = 0;

                 /* This part is creating a complete schedule, that is ready to be printed
          and prints it in table view
        ====================================================================================================
        */
                 //print columnNames
        //DataManipulations dm = new DataManipulations(timetable);
        String[][] completeSchedule = dm.getCompleteScheduleAs2dStringArray();
        int dayTrigger = dm.getMaxClassesAtSameTime(), columnIndex = 0;
        String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        row = sheet.createRow(rownum++);

        for (int j = 0; j < completeSchedule.length; j++)
        {
            if(j == 0)
            {
                cell = row.createCell(cellnum++);
                cell.setCellValue(columnNames[columnIndex++]);
                cell.setCellStyle(daysOfTheWeekStyle);
            }else if(j == 1)
            {
                cell = row.createCell(cellnum++);
                cell.setCellStyle(daysOfTheWeekStyle);
                cell.setCellValue(columnNames[columnIndex++]);

            }
            else if((j-1)%dayTrigger==0)
            {
                cell = row.createCell(cellnum++);
                cell.setCellStyle(daysOfTheWeekStyle);
                cell.setCellValue(columnNames[columnIndex++]);
            }
            else{
                    cell = row.createCell(cellnum++);
            }

            if(columnIndex == 6)
                break;

        }
        //print time periods and timetable
        for (int i = 0; i < completeSchedule[0].length; i++)
        {
            row = sheet.createRow(rownum++);
            cell=null;
            cellnum = 0;
            for (int j = 0; j < completeSchedule.length; j++) {
                if(j==0)
                {
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle(timeIntervalsStyle);
                    cell.setCellValue(Timetable.timeIndexToString(i)+"-"+Timetable.timeIndexToString(i+1));

                }
                else if(j % dayTrigger==0){
                    cell.setCellStyle(borderStyle);
                }else{
                    cell.setCellStyle(timeTableStyle);
                }

                cell = row.createCell(cellnum++);
                cell.setCellValue(completeSchedule[j][i]);
            }
        }
        //autisizeColumns(workbook,1, completeSchedule.length );

        try
        {
            //Write the workbook into file system
            FileOutputStream out = new FileOutputStream(new File(path+"\\Eng. Fac. Weekly Timetable.xlsx"));
            workbook.write(out);
            out.close();
        }
        catch (Exception e) { e.printStackTrace(); }
    }
    public static String getDesctopPath() {
        try{
            String desktopPath = System.getProperty("user.home") + "/Desktop";
            return desktopPath;
        }catch (Exception e){
            System.out.println("Exception caught ="+e.getMessage());
        }
        return null;
    }

    public static void autisizeColumns(Workbook workbook, int sheetNum, int colNum)
    {
        for(int sheets = 0; sheets < sheetNum; sheets++)
        {
            for(int columns = 0; columns < colNum; columns++)
            {
                workbook.getSheetAt(sheets).autoSizeColumn(columns);
            }
        }
    }


}
