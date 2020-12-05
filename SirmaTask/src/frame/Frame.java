package frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import logic.ApplicationService;
import logic.ProjectResult;

public class Frame {

    private JFrame frame;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Frame window = new Frame();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Frame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[][] data = new String[4][4];
        String[] columnNames = { "Employee ID #1", "Employee ID #2", "Project ID", "Days worked" };
        table = new JTable(data, columnNames);
        DefaultTableModel tblModel = new DefaultTableModel(columnNames, 0);
        table.setModel(tblModel);
        tblModel.addRow(new Object[] { "Employee ID #1", "Employee ID #2", "Project ID", "Days worked" });
        frame.getContentPane().add(table, BorderLayout.CENTER);

        JButton btnNewButton = new JButton("Select File");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    ArrayList<ProjectResult> projectResults = ApplicationService.getEmployeesWhoWorkedMost(selectedFile.getAbsolutePath());
                    DefaultTableModel tblModel = new DefaultTableModel(columnNames, projectResults.size());
                    table.setModel(tblModel);
                    tblModel.addRow(new Object[] { "Employee ID #1", "Employee ID #2", "Project ID", "Days worked" });
                    for (ProjectResult projectResult : projectResults) {
                        tblModel.addRow(new Object[] { projectResult.getEmployee1ID(), projectResult.getEmployee2ID(),
                                projectResult.getProjectID(), projectResult.getDaysWorked() });
                    }
                }
            }
        });
        frame.getContentPane().add(btnNewButton, BorderLayout.SOUTH);
    }

}
