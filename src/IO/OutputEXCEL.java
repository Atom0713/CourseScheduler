package IO;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import root.DataManipulations;
import root.Timetable;

import java.io.File;
import java.io.FileOutputStream;
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

    public static void professorsTimetableOutputExcel(ArrayList<String> profNames, ArrayList<String[][]> profTimetable)
    {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //set border thickness and color

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFCellStyle style_1 = workbook.createCellStyle();
        XSSFCellStyle style_2 = workbook.createCellStyle();
        //XSSFCellStyle styleBackgroundColor = workbook.createCellStyle();

        /* Create HSSFFont object from the workbook */
        XSSFFont my_font=workbook.createFont();
        /* set the weight of the font */
        my_font.setBold(true);
        /* attach the font to the style created earlier */
        style.setFont(my_font);
        style.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
        style.setFillPattern(FillPatternType.LESS_DOTS);
        style.setAlignment(HorizontalAlignment.FILL);
        style.setBorderBottom(BorderStyle.THICK);
        style.setRightBorderColor(IndexedColors.BLACK.index);
        style_1.setFont(my_font);
        style_1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
        style_1.setFillPattern(FillPatternType.LESS_DOTS);
        style_1.setAlignment(HorizontalAlignment.FILL);
        style_1.setBorderRight(BorderStyle.THICK);
        style_1.setRightBorderColor(IndexedColors.BLACK.index);

        style_2.setFont(my_font);
        style_2.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
        style_2.setFillPattern(FillPatternType.LESS_DOTS);
        style_2.setAlignment(HorizontalAlignment.FILL);


        //Create a blank sheet
        XSSFSheet sheet;
        //XSSFSheet sheet1 = workbook.createSheet("Second Sheet");

        //Fill TreeMap with the data to be written to the excel file
        TreeMap<String, Object[]> data = new TreeMap<String, Object[]>();

        int count=1;
        Object [] objArr;
        for (int pnames = 0; pnames < profNames.size(); pnames++)
        {
            data.put(Integer.toString(count), new Object[] {"Time", "ProfessorsName", "Monday","Tuesday", "Wednesday", "Thursday", "Friday"});
            count++;
            sheet = workbook.createSheet(profNames.get(pnames));
            String[][] temp=profTimetable.get(pnames);
            for (int j = 0; j < 9; j++)
            {
                data.put(Integer.toString(count), new Object[] {Timetable.timeIndexToString(j)+"-"+(Timetable.timeIndexToString(j+1)),profNames.get(pnames), temp[0][j], temp[1][j], temp[2][j], temp[3][j], temp[4][j]});
                count++;
            }
            Set<String> keyset = data.keySet();//keyset is unordered, mixes up output

            int rownum = 0;
            for (int i = 1; i< count; i++)//String key:keyset
            {
                objArr=null;
                Row row = sheet.createRow(rownum++);
                for(String key: keyset)
                {
                    if(key.equals(Integer.toString(i)))//extra help to get objects in ascending order
                    {
                        objArr = data.get(key);
                    }
                }
                int cellnum = 0;
                for (Object obj : objArr)
                {
                    Cell cell = row.createCell(cellnum++);
                    if(cellnum<9&&cellnum>1&&rownum==1)
                    {
                        cell.setCellStyle(style);

                    }else if(rownum>1&&cellnum==1)
                    {
                        cell.setCellStyle(style_1);

                    }else if(cellnum==1&&rownum==1){
                        cell.setCellStyle(style_2);
                    }

                    if(obj instanceof String)
                        cell.setCellValue((String)obj);
                    else if(obj instanceof Integer)
                        cell.setCellValue((Integer)obj);
                }
            }
            count = 1;
            data = new TreeMap<String, Object[]>();
        }
        //Iterate over data and write to sheet

        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(getDesctopPath()+"\\Academic Staff Timetable.xlsx"));
            workbook.write(out);
            out.close();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public static void classroomsTimetableOutputExcel(ArrayList<String> allClassRoomNames, ArrayList<String[][]> allClassRoomsTimetables)
    {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //XSSFCellStyle styleBorderBottom = workbook.createCellStyle();
        //set border thickness and color


        XSSFCellStyle style = workbook.createCellStyle();
        XSSFCellStyle style_1 = workbook.createCellStyle();
        XSSFCellStyle style_2 = workbook.createCellStyle();
        //XSSFCellStyle styleBackgroundColor = workbook.createCellStyle();

        /* Create HSSFFont object from the workbook */
        XSSFFont my_font=workbook.createFont();
        /* set the weight of the font */
        my_font.setBold(true);
        /* attach the font to the style created earlier */
        style.setFont(my_font);
        style.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
        style.setFillPattern(FillPatternType.LESS_DOTS);
        style.setAlignment(HorizontalAlignment.FILL);
        style.setBorderBottom(BorderStyle.THICK);
        style.setRightBorderColor(IndexedColors.BLACK.index);
        style_1.setFont(my_font);
        style_1.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
        style_1.setFillPattern(FillPatternType.LESS_DOTS);
        style_1.setAlignment(HorizontalAlignment.FILL);
        style_1.setBorderRight(BorderStyle.THICK);
        style_1.setRightBorderColor(IndexedColors.BLACK.index);

        style_2.setFont(my_font);
        style_2.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
        style_2.setFillPattern(FillPatternType.LESS_DOTS);
        style_2.setAlignment(HorizontalAlignment.FILL);


        //Create a blank sheet
        XSSFSheet sheet;

        //Fill TreeMap with the data to be written to the excel file
        TreeMap<String, Object[]> data = new TreeMap<String, Object[]>();
        int count=1;

        Object [] objArr;
        for (int classroom = 0; classroom < allClassRoomNames.size(); classroom++)
        {
            data.put(Integer.toString(count), new Object[] {"Time", "Classroom", "Monday","Tuesday", "Wednesday", "Thursday", "Friday"});
            count++;
            sheet = workbook.createSheet(allClassRoomNames.get(classroom));


            String[][] temp=allClassRoomsTimetables.get(classroom);
            for (int j = 0; j < 9; j++)
            {
                data.put(Integer.toString(count), new Object[] {Timetable.timeIndexToString(j)+"-"+(Timetable.timeIndexToString(j+1)),allClassRoomNames.get(classroom), temp[0][j], temp[1][j], temp[2][j], temp[3][j], temp[4][j]});
                count++;
            }
            Set<String> keyset = data.keySet();//keyset is unordered, mixes up output

            int rownum = 0;
            for (int i = 1; i< count; i++)//String key:keyset
            {
                objArr=null;
                Row row = sheet.createRow(rownum++);
                for(String key: keyset)
                {
                    if(key.equals(Integer.toString(i)))//extra help o get objects in ascending order
                    {
                        objArr = data.get(key);
                    }
                }
                int cellnum = 0;
                for (Object obj : objArr)
                {
                    Cell cell = row.createCell(cellnum++);
                    if(cellnum<9&&cellnum>1&&rownum==1)
                    {
                        cell.setCellStyle(style);

                    }else if(rownum>1&&cellnum==1)
                    {
                        cell.setCellStyle(style_1);

                    }else if(cellnum==1&&rownum==1){
                        cell.setCellStyle(style_2);
                    }

                    if(obj instanceof String)
                        cell.setCellValue((String)obj);
                    else if(obj instanceof Integer)
                        cell.setCellValue((Integer)obj);
                }
            }
            count = 1;
            data = new TreeMap<String, Object[]>();
        }
        //Iterate over data and write to sheet

        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(getDesctopPath()+"\\Classrooms Timetable.xlsx"));
            workbook.write(out);
            out.close();
        }
        catch (Exception e) { e.printStackTrace();
        }
    }
    public static void weeklyTimetableOutputExcel()
    {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //create XSSFCellStyle object from wrokbook to set bold text
        XSSFCellStyle style = workbook.createCellStyle();
        //create XSSFCellStyle object from wrokbook to set border color
        XSSFCellStyle styleBorder = workbook.createCellStyle();
        //set border thickness and color
        styleBorder.setBorderRight(BorderStyle.THICK);
        styleBorder.setRightBorderColor(IndexedColors.BLACK.index);
        /* Create XSSFFont object from the workbook */
        XSSFFont my_font=workbook.createFont();
        /* set the weight of the font */
        my_font.setBold(true);
        /* attach the font to the style created earlier */
        style.setFont(my_font);
        style.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
        style.setFillPattern(FillPatternType.LESS_DOTS);
        style.setAlignment(HorizontalAlignment.FILL);

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Weekly Timetable");

        //Fill TreeMap with the data to be written to the excel file
        TreeMap<String, Object[]> data = new TreeMap<String, Object[]>();
        int rownum = 0;
        int cellnum=0;

                 /* This part is creating a complete schedule, that is ready to be printed
          and prints it in table view
        ====================================================================================================
        */
                 //print days
        DataManipulations dm = new DataManipulations(timetable);
        String[][] completeSchedule = dm.getCompleteScheduleAs2dStringArray();
        int dayTrigger = dm.getMaxClassesAtSameTime(), dayIndex=0;
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        Row row1 = sheet.createRow(rownum++);

        for (int j = 0; j < completeSchedule.length; j++)
        {
            if(j==0)
            {
                Cell cell = row1.createCell(cellnum);
                cell.setCellValue("Time");
                cell.setCellStyle(style);
            }else if(j==1)
            {
                Cell cell = row1.createCell(cellnum);
                cell.setCellStyle(style);
                cell.setCellValue(days[dayIndex]);
                dayIndex++;
            }
            else if((j-1)%dayTrigger==0)
            {
                Cell cell = row1.createCell(cellnum);
                cell.setCellStyle(style);
                cell.setCellValue(days[dayIndex]);
                dayIndex++;
            }else
                {
                    Cell cell = row1.createCell(cellnum);
                }


            if(dayIndex==5)
                break;
            cellnum++;

        }
        //print time periods and schedule
        for (int i = 0; i < completeSchedule[0].length; i++)
        {
            Row row = sheet.createRow(rownum++);
            Cell cell=null;
            cellnum = 0;
            for (int j = 0; j < completeSchedule.length; j++) {
                if(j==0)
                {
                    cell = row.createCell(cellnum++);
                    cell.setCellValue(Timetable.timeIndexToString(i)+"-"+Timetable.timeIndexToString(i) );
                    cell.setCellStyle(style);
                }
                else if(j%dayTrigger==0){
                    cell.setCellStyle(styleBorder);
                }
                cell = row.createCell(cellnum++);
                cell.setCellValue(completeSchedule[j][i]);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(getDesctopPath()+"\\Eng. Fac. Weekly Timetable.xlsx"));
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


}
