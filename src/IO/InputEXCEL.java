package IO;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class InputEXCEL {
    public static String[][] readExcelFile(String path, String  sheetName)
    {
        String data[][];
        String shName;
        try(Workbook wb = WorkbookFactory.create(new File(path))) {
            int sheetID = -1;
            for (int k = 0;k < wb.getNumberOfSheets(); k++){//find index of the sheet with the given "sheetName"
                 shName = wb.getSheetName(k);
                if (shName.equals(sheetName)){
                    sheetID = k;
                    //access sheet at useID index
                    Sheet sheet = wb.getSheetAt(sheetID);
                    //get first row
                    int rowStart = sheet.getFirstRowNum();
                    //get last row
                    int rowEnd = sheet.getLastRowNum()+1;
                    int colCount = 0;
                    Iterator rowIterator = sheet.rowIterator();
                    if (rowIterator.hasNext())
                    {
                        Row headerRow = (Row) rowIterator.next();
                        //get the number of cells in the header row
                        colCount = headerRow.getPhysicalNumberOfCells();
                    }

                    //declare multi-dimensional array where first element is the number of rows and seconf=d is
                    // the number of columns
                    data = new String[rowEnd-rowStart][colCount];

                    CellType t=null;
                    //iterate through all columns of each row and copy the values into the 'data' array
                    for(int i = rowStart; i < rowEnd; i++){
                        Row row = sheet.getRow(i);//gets a row
                        for(int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++){//iterates through cells of the row
                            Cell cell = row.getCell(j);//gets the cell of the row
                            if(cell == null){//if cell is empty puts null
                                //System.out.printf("%-31s" ,"null");
                                continue;
                            }
                            CellType type = cell.getCellType();
                            if(type== CellType.NUMERIC || type== CellType.FORMULA  ){//if a cell is a number or a formula make it a string and copy its value
                                data[i][j]= Integer.toString((int)cell.getNumericCellValue());

                            }else if(type== CellType.STRING) {//in case cell is a string copie its value directly
                                data[i][j]=cell.getStringCellValue();
                            }
                        }

                    }return data;

                }

            }
        }
        catch (IOException ex){
            System.out.println(ex);
        }
        return null;
    }

    /*getAllTimePeriodsOfCourses-processes data array gotten from the readExcelFile method above.
    any course with the time period similar to "2+1" is being separated into two courses with time periods 2 and 1
    courses treated as separate courses
    */
    public static ArrayList<ArrayList<String>> getAllTimePeriodsOfCourses(String path, String sheetID)
    {
        String dataLocal[][]= readExcelFile(path,sheetID);
        ArrayList<ArrayList<String>> getAllCourses = new ArrayList<ArrayList<String>>();
        for (int i = 1; i < dataLocal.length; i++){
            String timePeriod = dataLocal[i][3];
            /*
            if time period is of a similar type to '2+1', time periods (2, 1) are put into the hours array.
            The information about the course is copied as many times as the number of separate time periods there exists
            resulting in the same course being copied same number of times as the number of the time periods
            */
            if (timePeriod.length()>1&&timePeriod.substring(1,2).equals("+")){
                String hours[]={timePeriod.substring(0,1),timePeriod.substring(2,3)};
                int count = 0;
                while(count<2){
                    ArrayList<String> buff = new ArrayList<String>();
                    buff.add(dataLocal[i][0]);
                    buff.add(dataLocal[i][1]);
                    buff.add(dataLocal[i][2]);
                    buff.add(hours[count]);
                    buff.add(dataLocal[i][4]);
                    buff.add(dataLocal[i][5]);
                    count++;
                    getAllCourses.add(buff);
                }
                /*if there is only one time period course information is copied to the arraylist with the corresponding
                time period
                */
            }else if (timePeriod.length()==1){
                ArrayList<String> buff = new ArrayList<String>();
                buff.add(dataLocal[i][0]);
                buff.add(dataLocal[i][1]);
                buff.add(dataLocal[i][2]);
                buff.add(timePeriod);
                buff.add(dataLocal[i][4]);
                buff.add(dataLocal[i][5]);
                getAllCourses.add(buff);
            }
        }
        return getAllCourses;


    }
}
