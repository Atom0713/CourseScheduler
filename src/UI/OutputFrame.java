package UI;

import IO.OutputEXCEL;
import IO.OutputPDF;
import root.DataManipulations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OutputFrame
{
    static int generation;
    static double fitness;
    static int clashes;
    static ArrayList<String> allProfNames;
    static ArrayList<String[][]> allProfTimetables;
    static ArrayList<String> allClassRoomsNames;
    static ArrayList<String[][]> allClassRoomsTimetables;
    static DataManipulations dm;
    static String path=null;

    //Constructor. Initializes all variable.
    public OutputFrame(int generation, double fitness, int clashes, ArrayList<String> allProfNames, ArrayList<String[][]> allProfTimetables, ArrayList<String> allClassRoomsNames, ArrayList<String[][]> allClassRoomsTimetables, DataManipulations dm)
    {
        this.allProfNames=allProfNames;
        this.allProfTimetables=allProfTimetables;
        this.clashes=clashes;
        this.fitness=fitness;
        this.generation=generation;
        this.allClassRoomsNames=allClassRoomsNames;
        this.allClassRoomsTimetables=allClassRoomsTimetables;
        this.dm = dm;
    }

    public static void outputFrame() throws Exception
    {

        JFrame showProcessFrame = new JFrame("Output");
        JLabel area = new JLabel();
        JLabel area1 = new JLabel();
        JLabel desiredView, desiredClass;
        JButton print, savePath;
        JCheckBox cb1,cb2,cb3, cb4, cb5, cb6, cb7, cb8;
        JTextField pathField = new JTextField();

        area.setText("Solution found in " + generation + " generations");
        area1.setText("Final solution fitness: " + fitness+"    Clashes: " + clashes);
        area.setBounds(300,10,200,30);
        area1.setBounds(240,40,400,30);

        desiredView = new JLabel("Choose desired view in Excel:");
        desiredView.setBounds(100,90,180,15);
        cb1=new JCheckBox("Academic Staff Timetable");
        cb1.setBounds(100,120,220,15);
        cb2=new JCheckBox("Classrooms Timetable");
        cb2.setBounds(100,150,220,15);
        cb3=new JCheckBox("Eng. Fuc. Weekly Schedule");
        cb3.setBounds(100,180,220,15);
        cb4=new JCheckBox("Print weekly schedule as a PDF");
        cb4.setBounds(100,210,220,15);

        desiredClass = new JLabel("Choose desired Class:");
        desiredClass.setBounds(500,90,180,17);
        cb5=new JCheckBox("1st Class");
        cb5.setBounds(500,120,220,15);
        cb6=new JCheckBox("2nd Class");
        cb6.setBounds(500,150,220,15);
        cb7=new JCheckBox("3rd Class");
        cb7.setBounds(500,180,220,15);
        cb8=new JCheckBox("4th Class");
        cb8.setBounds(500,210,220,15);

        print=new JButton("Print");
        print.setBounds(600,330,80,30);
        savePath = new JButton("Save To:");
        savePath.setBounds(100,330,110,30);
        pathField.setBounds(210, 330, 300, 30);



        print.addActionListener((event) ->//event listener implementation using lambda expression
        {
                OutputPDF out = new OutputPDF();
                OutputEXCEL output = new OutputEXCEL();

                if(cb1.isSelected())
                {//professors view
                    OutputEXCEL.professorsTimetableOutputExcel(allProfNames, allProfTimetables, path);
                }
                if(cb2.isSelected())
                {//classrooms view
                       OutputEXCEL.classroomsTimetableOutputExcel(allClassRoomsNames,allClassRoomsTimetables, path);
                }
                if(cb3.isSelected())
                {//weekly schedule, all courses
                    OutputEXCEL.weeklyTimetableOutputExcel(dm, path);
                }
                if(cb4.isSelected())
                {//all courses pdf view
                    OutputPDF.writeAllToPdf(dm.getClassesAsCodeTimeRoom(), path);
                }
                if(cb5.isSelected())
                {//1st class view
                }
                if(cb6.isSelected())
                {//2st class view

                }
                if(cb7.isSelected())
                {//3st class view

                }
                if(cb8.isSelected())
                {//4st class view

                }
        });

        savePath.addActionListener((event) ->{ //event listener implementation using lambda expression
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                f.showSaveDialog(null);
                path = f.getSelectedFile().getPath();
                pathField.setText(path);
        });

        showProcessFrame.add(pathField);
        showProcessFrame.add(savePath);
        showProcessFrame.add(area);
        showProcessFrame.add(area1);
        showProcessFrame.add(cb1);
        showProcessFrame.add(cb2);
        showProcessFrame.add(cb3);
        showProcessFrame.add(cb4);
        showProcessFrame.add(cb5);
        showProcessFrame.add(cb6);
        showProcessFrame.add(cb7);
        showProcessFrame.add(cb8);
        showProcessFrame.add(desiredClass);
        showProcessFrame.add(desiredView);
        showProcessFrame.add(print, BorderLayout.AFTER_LAST_LINE);
        showProcessFrame.setSize(800,400);
        showProcessFrame.setLayout(null);
        showProcessFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        showProcessFrame.setLocationRelativeTo(null);
        showProcessFrame.setVisible(true);
    }


}
