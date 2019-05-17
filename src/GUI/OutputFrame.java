package GUI;

import IO.OutputEXCEL;

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
    public OutputFrame(int generation, double fitness, int clashes, ArrayList<String> allProfNames, ArrayList<String[][]> allProfTimetables, ArrayList<String> allClassRoomsNames, ArrayList<String[][]> allClassRoomsTimetables)
    {
        this.allProfNames=allProfNames;
        this.allProfTimetables=allProfTimetables;
        this.clashes=clashes;
        this.fitness=fitness;
        this.generation=generation;
        this.allClassRoomsNames=allClassRoomsNames;
        this.allClassRoomsTimetables=allClassRoomsTimetables;
    }

    public static void outputFrame() throws Exception
    {
        JFrame showProcessFrame = new JFrame("Output");
        JLabel area = new JLabel();
        JLabel area1 = new JLabel();
        JLabel line;
        JButton b;
        JCheckBox cb1,cb2,cb3, cb5;
        area.setText("Solution found in " + generation + " generations");
        area1.setText("Final solution fitness: " + fitness+"    Clashes: " + clashes);
        line = new JLabel("Choose desired view in Excel:");
        line.setBounds(100,90,180,15);
        cb1=new JCheckBox("Academic Staff Timetable");
        cb1.setBounds(100,120,220,15);
        cb2=new JCheckBox("Classrooms Timetable");
        cb2.setBounds(100,150,220,15);
        cb3=new JCheckBox("Eng. Fuc. Weekly Schedule");
        cb3.setBounds(100,180,220,15);
        cb5=new JCheckBox("Print weekly schedule as a PDF");
        cb5.setBounds(100,220,220,15);
        b=new JButton("Print");
        b.setBounds(100,330,80,30);


        area.setBounds(105,10,200,30);
        area1.setBounds(45,40,400,30);
        b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                OutputEXCEL output = new OutputEXCEL();
                if(cb1.isSelected())
                {//professors view
                    output.professorsTimetableOutputExcel(allProfNames, allProfTimetables);
                }
                if(cb2.isSelected())
                {//classrooms view
                        output.classroomsTimetableOutputExcel(allClassRoomsNames,allClassRoomsTimetables);                }
                if(cb3.isSelected())
                {//weekly schedule, all courses
                    output.weeklyTimetableOutputExcel();
                }
                if(cb5.isSelected())
                {

                }
            }
        });

        showProcessFrame.add(area);
        showProcessFrame.add(area1);
        showProcessFrame.add(cb1);
        showProcessFrame.add(cb2);
        showProcessFrame.add(cb3);
        showProcessFrame.add(cb5);
        showProcessFrame.add(line);
        showProcessFrame.add(b, BorderLayout.AFTER_LAST_LINE);
        showProcessFrame.setSize(400,400);
        showProcessFrame.setLayout(null);
        showProcessFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        showProcessFrame.setLocationRelativeTo(null);
        showProcessFrame.setVisible(true);
    }





}
