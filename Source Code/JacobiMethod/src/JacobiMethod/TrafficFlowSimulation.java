package JacobiMethod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TrafficFlowSimulation extends JFrame {

    private JTextField sizeTextField;
    private JTextArea resultTextArea, inputVariablesTextArea;
    private JTabbedPane tabbedPane;

    public TrafficFlowSimulation() {
        setTitle("Traffic Flow Simulation - Jacobi Method");
        setSize(800, 600); 			// SIZE
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TAAS
        JLabel sizeLabel = new JLabel("Enter number of traffic junctions:");
        sizeTextField = new JTextField(5);
        JButton simulateButton = new JButton("Simulate Traffic Flow");
        resultTextArea = new JTextArea(5, 20);
        resultTextArea.setEditable(false);
        tabbedPane = new JTabbedPane();
        inputVariablesTextArea = new JTextArea(10, 10);
        inputVariablesTextArea.setEditable(false);

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        // Default color ra sir para di sakit sa mata

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(sizeLabel)
                        .addComponent(simulateButton)
                        .addComponent(resultTextArea)
                        .addComponent(tabbedPane))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(sizeTextField))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(sizeLabel)
                        .addComponent(sizeTextField))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(simulateButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(resultTextArea))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabbedPane)
        );

        // BUTTON
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateTrafficFlow();
            }
        });

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panel);
        setVisible(true);
    }

    private void simulateTrafficFlow() {
        try {
            int n = Integer.parseInt(sizeTextField.getText());

            // MINIMUM IS SET TO 2
            if (n < 2) {
                JOptionPane.showMessageDialog(this, "Minimum number of junctions is 2. Setting size to 2.");
                n = 2;
                sizeTextField.setText("2");
            }

            // PROMPT USER TO INPUT THE CONNECTIVITY MATRIX (A)
            ArrayList<ArrayList<Double>> A = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                ArrayList<Double> row = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    double coefficient = Double.parseDouble(
                            JOptionPane.showInputDialog("Enter A[" + (i + 1) + "][" + (j + 1) + "]:"));
                    row.add(coefficient);
                }
                A.add(row);
            }

            // PROMPT USER TO INPUT THE EXTERNAL FORCES VECTOR (b)
            ArrayList<Double> b = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                double force = Double.parseDouble(
                        JOptionPane.showInputDialog("Enter b[" + (i + 1) + "]:"));
                b.add(force);
            }

            // PROMPT USER TO INPUT THE INITIAL GUESS VECTOR (x)
            ArrayList<Double> x = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                double guess = Double.parseDouble(
                        JOptionPane.showInputDialog("Enter x[" + (i + 1) + "]:"));
                x.add(guess);
            }

            // DISPLAYING THE GUIDE
            displayGuide(A, b, x);

            // DISPLAYING THE INPUTTED VARIABLES
            displayInputVariables(A, b, x);

            // ASSIGNING TO JACOBI
            jacobiMethod(n, A, b, x, 2);

            // DISPLAYING THE RESULT
            displayResult(x, n);

            // IF WAY INPUT
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.");
        }
    }

    private void displayGuide(ArrayList<ArrayList<Double>> A, ArrayList<Double> b, ArrayList<Double> x) {
    	// SHOW GUIDE
        JTextArea inputGuideTextArea = new JTextArea(10, 10);
        inputGuideTextArea.setEditable(false);
        inputGuideTextArea.setText("Input Variables Guide:\n");

        inputGuideTextArea.append("1. Connectivity Matrix (A):\n");
        inputGuideTextArea.append("   - Each element A[i][j] represents the traffic flow from junction i to junction j.\n");
        inputGuideTextArea.append("User Input: \n");
        for (ArrayList<Double> row : A) {
            for (Double coefficient : row) {
                inputGuideTextArea.append(String.format("%.2f", coefficient) + " ");
            }
            inputGuideTextArea.append("\n");
        }

        inputGuideTextArea.append("2. External Forces (b):\n");
        inputGuideTextArea.append("   - Represents the traffic entering each junction.\n");
        inputGuideTextArea.append("User Input: \n");
        for (Double force : b) {
            inputGuideTextArea.append(String.format("%.2f", force) + " vehicles\n");
        }

        inputGuideTextArea.append("3. Initial Guess (x):\n");
        inputGuideTextArea.append("   - Represents the initial traffic conditions at each junction.\n");
        inputGuideTextArea.append("User Input: \n");
        for (Double guess : x) {
            inputGuideTextArea.append(String.format("%.2f", guess) + " vehicles\n");
        }

        JScrollPane guideScrollPane = new JScrollPane(inputGuideTextArea);
        tabbedPane.addTab("Input Variables Guide", guideScrollPane);
        tabbedPane.setSelectedComponent(guideScrollPane);
    }

    private void jacobiMethod(int n, ArrayList<ArrayList<Double>> A, ArrayList<Double> b,
                              ArrayList<Double> x, int decimalPlaces) {
        int maxIterations = 100; // MAX ITERATIONS IS SET TO 100
        double tolerance = 1e-6; // DEFAULT TOLERANCE

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Junction");
        for (int i = 0; i < n; i++) {
            tableModel.addColumn("Iteration " + (i + 1));
        }

        JTable iterationsTable = new JTable(tableModel);
        JScrollPane iterationsScrollPane = new JScrollPane(iterationsTable);
        tabbedPane.addTab("Iteration Results", iterationsScrollPane);

        boolean converged = false;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            ArrayList<Double> tempX = new ArrayList<>(x);

            Object[] rowData = new Object[n + 1];
            rowData[0] = "Iteration " + (iteration + 1);

            for (int i = 0; i < n; i++) {
                double sum = b.get(i);
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        sum -= A.get(i).get(j) * tempX.get(j);
                    }
                }
                x.set(i, sum / A.get(i).get(i));
                rowData[i + 1] = String.format("%.2f", x.get(i));
            }

            tableModel.addRow(rowData);

            // CHECK IF NICONVERGE NA
            converged = hasConverged(x, tempX, tolerance);

            // IF CONVERGED KAY EXIT
            if (converged) {
                break;
            }
        }

        if (converged) {
            tabbedPane.setTitleAt(tabbedPane.indexOfComponent(iterationsScrollPane), "Solution Found");
        } else {
            JOptionPane.showMessageDialog(this, "Jacobi method did not converge within the maximum iterations.");
        }
    }

    private boolean hasConverged(ArrayList<Double> x, ArrayList<Double> tempX, double tolerance) {
        // CHECK IF NICONVERGE
        for (int i = 0; i < x.size(); i++) {
            if (Math.abs(x.get(i) - tempX.get(i)) >= tolerance) {
                return false;
            }
        }
        return true;
    }

    private void displayResult(ArrayList<Double> x, int n) {
        // DISPLAYING RESULT SA JTEXTAREA
        resultTextArea.setText("Traffic Flow at Junctions:\n");
        for (int i = 0; i < n; i++) {
            resultTextArea.append("Junction " + (i + 1) + ": " + String.format("%.2f", x.get(i)) + " vehicles\n");
        }
    }

    private void displayInputVariables(ArrayList<ArrayList<Double>> A, ArrayList<Double> b, ArrayList<Double> x) {
        // DISPLAYING INPUTTED VARIABLES SA TEXTAREA
        inputVariablesTextArea.setText("Input Variables:\n");
        inputVariablesTextArea.append("1. Connectivity Matrix (A):\n");
        for (ArrayList<Double> row : A) {
            for (Double coefficient : row) {
                inputVariablesTextArea.append(String.format("%.2f", coefficient) + " ");
            }
            inputVariablesTextArea.append("\n");
        }
        inputVariablesTextArea.append("2. External Forces (b):\n");
        for (Double force : b) {
            inputVariablesTextArea.append(String.format("%.2f", force) + " vehicles\n");
        }
        inputVariablesTextArea.append("3. Initial Guess (x):\n");
        for (Double guess : x) {
            inputVariablesTextArea.append(String.format("%.2f", guess) + " vehicles\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrafficFlowSimulation();
            }
        });
    }
}