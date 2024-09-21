import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JTextArea foodListArea;
    private JLabel totalCaloriesLabel;
    private DatabaseUtil dbUtil;

    public Main() {
        // initialize the DatbaseUtil and UI components
        dbUtil = new DatabaseUtil();

        // create JFrame
        JFrame frame = new JFrame("Calorie Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // create UI components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField foodNameField = new JTextField(15);
        JTextField gramsField = new JTextField(15);

        JButton addFoodButton = new JButton("Add Food");

        foodListArea = new JTextArea(10, 30);
        foodListArea.setEditable(false);

        totalCaloriesLabel = new JLabel("Total Calories: 0");

        // add components to the panel
        panel.add(new JLabel("Food Name:"));
        panel.add(foodNameField);
        panel.add(new JLabel("Grams Eaten:"));
        panel.add(gramsField);
        panel.add(addFoodButton);
        panel.add(new JScrollPane(foodListArea));
        panel.add(totalCaloriesLabel);

        // add action listeners
        addFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String foodName = foodNameField.getText();
                String gramsText = gramsField.getText();

                if (!foodName.isEmpty() && !gramsText.isEmpty()) {
                    try {
                        int gramsEaten = Integer.parseInt(gramsText);

                        // get calories per 100 grams from the database
                        int caloriesPer100g = dbUtil.getCaloriesPer100g(foodName);

                        if(caloriesPer100g != -1) {
                            // calculate total calories for the given grams eaten
                            int totalCalories = (gramsEaten * caloriesPer100g) / 100;

                            // update the food list and clear the text fields
                            foodListArea.append(foodName + ": " + totalCalories + " cal (" + gramsEaten + "g)\n");
                            foodNameField.setText("");
                            gramsField.setText("");

                            updateTotalCalories(totalCalories);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Food not found in the database!");
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid number for grams.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter both food name and grams.");
                }
            }
        });

        // add panel to the frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void updateTotalCalories(int newCalories) {
        int currentTotal = Integer.parseInt(totalCaloriesLabel.getText().split(": ")[1]);
        currentTotal += newCalories;
        totalCaloriesLabel.setText("Total Calories: " + currentTotal);
    }

    public static void main(String[] args) {
        // start the application
        SwingUtilities.invokeLater(Main::new);
        DatabaseUtil databaseUtil = new DatabaseUtil();
        databaseUtil.listFoods();
    }
}