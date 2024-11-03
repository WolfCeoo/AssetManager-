import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MapGui extends JFrame {
    private JPanel headingPanel, namePanel, surnamePanel, studNumPanel, buttonPanel;

    private JLabel headingLabel, nameLabel, surnameLabel, studNumLabel;
    private JTextField nameTextField, surnameTextField, studNumTextField;
    private JButton option1, option2;

    private HashMap<String, Student> map;

    public MapGui() {
        setTitle("HashMap GUI");
        setSize(600, 400);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        map = new HashMap<>(); // Initialize the HashMap

        // Heading Panel
        headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingLabel = new JLabel("Asset Manager");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 19));
        headingLabel.setForeground(Color.BLUE);
        headingPanel.add(headingLabel);
        add(headingPanel, BorderLayout.NORTH);

        // Center Panel with student information fields
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        // Name Field
        namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameLabel = new JLabel("Name: ");
        nameTextField = new JTextField(15);
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);
        centerPanel.add(namePanel);

        // Surname Field
        surnamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        surnameLabel = new JLabel("Surname: ");
        surnameTextField = new JTextField(15);
        surnamePanel.add(surnameLabel);
        surnamePanel.add(surnameTextField);
        centerPanel.add(surnamePanel);

        // Student Number Field with button panel
        studNumPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        studNumLabel = new JLabel("Student Number: ");
        studNumTextField = new JTextField(10);
        studNumPanel.add(studNumLabel);
        studNumPanel.add(studNumTextField);

        // Button Panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        option1 = new JButton("Add to Map");
        option1.addActionListener(new Option1ActionListener());
        option2 = new JButton("Save to File");
        option2.addActionListener(new Option2ActionListener());
        buttonPanel.add(option1);
        buttonPanel.add(option2);

        studNumPanel.add(buttonPanel);
        centerPanel.add(studNumPanel);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // ActionListener for "Add to Map" button
    private class Option1ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameTextField.getText().trim();
            String surname = surnameTextField.getText().trim();
            String studNum = studNumTextField.getText().trim();

            if (name.isEmpty() || surname.isEmpty() || studNum.isEmpty()) {
                JOptionPane.showMessageDialog(MapGui.this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add student to map
            Student student = new Student(name, surname, studNum);
            map.put(studNum, student);
            JOptionPane.showMessageDialog(MapGui.this, "Student added to map successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            for(Map.Entry<String,Student> data: map.entrySet()){
                JOptionPane.showMessageDialog(null, "Key: "+data.getKey()+"\nValue: \n"+data.getValue().toString());
            }
            // Clear fields after adding
            nameTextField.setText("");
            surnameTextField.setText("");
            studNumTextField.setText("");
        }
    }

    // ActionListener for "Save to File" button
    private class Option2ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (map.isEmpty()) {
                JOptionPane.showMessageDialog(MapGui.this, "No students to save. Add a student first.", "No Data", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("map.txt"))) {
                for (Student student : map.values()) {
                    writer.write("Name: " + student.getName() + "\n");
                    writer.write("Surname: " + student.getSurname() + "\n");
                    writer.write("Student Number: " + student.getStudNum() + "\n\n");
                }
                
            JOptionPane.showMessageDialog(MapGui.this, "Data saved to file successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (IOException ex) {
                JOptionPane.showMessageDialog(MapGui.this, "Error saving to file.", "File Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MapGui();
    }
}

// Student class to represent a student with name, surname, and student number
class Student {
    private String name;
    private String surname;
    private String studNum;

    public Student(String name, String surname, String studNum) {
        this.name = name;
        this.surname = surname;
        this.studNum = studNum;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getStudNum() {
        return studNum;
    }
    public String toString(){
        String state = "name:"+this.name+"\n surname: "+surname+"\nStudentNumber: "+studNum;
        return state;
    }
}
