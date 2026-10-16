import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import static java.lang.Math.abs;


/**
 * RREFCalc is the main GUI class for the Automated Linear System Solver.
 *
 * It allows the user to:
 * - Choose the size of an augmented matrix (m x n, with m, n ≤ 10)
 * - Input matrix values
 * - Calculate its Reduced Row Echelon Form (RREF) using Gaussian Elimination
 * - Display the matrix step-by-step with Elementary Row Operations (EROs)
 *
 * The program handles unique solutions, no solution, or infinitely many solutions.
 *
 * Authors: Fajilan, James Carl F.; Fajardo, Ellimhykelle P.;
 *          Lapuz, Katrice Anne T.; Masacupan, Jana Sophia S.;
 *          Sarmiento, Nathaniel B.
 */


public class RREFCalc {
    // Frame Title
    JFrame frame = new JFrame("RREF Calculator");


    // Main Panel and Scroll Pane
    JPanel main = new JPanel();
    JScrollPane scroll = new JScrollPane(main);


    // Title and Description Panels
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();
    JPanel panel6 = new JPanel();
    JPanel pane4 = new JPanel();


    // Labels for Titles, Description, and Instructions
    JLabel label1 = new JLabel("Automated Linear System Solver");
    JLabel label2 = new JLabel("This is a program that calculates the Reduced Row Echelon Form (RREF) of Linear Systems in Augmented Matrix Form.");
    JLabel label3 = new JLabel("Choose the number of rows (m): ");
    JLabel label4 = new JLabel("Choose the number of columns (n): ");
    JLabel label5 = new JLabel("Augmented Matrix Reduced Row Echelon Form");


    // Combo Boxes for selecting row and column size
    JComboBox<Integer> cmb1 = new JComboBox<>();
    JComboBox<Integer> cmb2 = new JComboBox<>();


    // Buttons for user actions
    Button btn1 = new Button("Create Matrix");
    Button btn3 = new Button("Clear Matrix");
    Button btn2 = new Button("Calculate RREF");


    // List of TextFields for matrix input
    List<JTextField> txtList = new ArrayList<>();
    float[][] matArr;


    // Colors and border used in GUI
    Color cl1 = new Color(251,239,239);
    Color cl2 = new Color(245,175,175);
    Color cl3 = new Color(255,242,198);
    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);


    // Static variables for matrix calculations
    static int row;
    static int col;
    static int mat;
    static int k;
    //static int btnclick = 0;
    static int pvsearch = 0;
    static int pvcount = 0;
    static int index = 0;
    static int rowcount = 0;
    static int counterI = 0;
    static float targetNum = 0;
    static float currNum = 0;
    static String numConvert;


    /**
     * Initializes the GUI and sets up all components, including:
     * - ComboBoxes for selecting matrix size
     * - Buttons for creating matrix, calculating RREF, and clearing matrix
     * - Panels and labels for displaying the matrix and results
     */


    public void Calc(){
        // ComboBox Properties
        for(int i = 1; i<= 10; i++){
            cmb1.addItem(i);
            cmb2.addItem(i);
        }


        // Configure buttons
        btn1.setPreferredSize(new Dimension(200,40));
        btn1.setFont(new Font("Arial",Font.BOLD,16));
        btn1.setBackground(cl2);
        btn2.setPreferredSize(new Dimension(200,40));
        btn2.setFont(new Font("Arial",Font.BOLD,16));
        btn2.setBackground(cl2);
        btn3.setPreferredSize(new Dimension(200,40));
        btn3.setFont(new Font("Arial",Font.BOLD,16));
        btn3.setBackground(cl2);


        // mainTitle Label and Description Properties
        label1.setFont(new Font("Georgia",Font.BOLD,24));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setFont(new Font("Arial",Font.PLAIN,16));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label3.setFont(new Font("Arial",Font.BOLD,16));
        label4.setFont(new Font("Arial",Font.BOLD,16));
        label5.setFont(new Font("Georgia",Font.BOLD,24));
        label5.setHorizontalAlignment(SwingConstants.CENTER);


        // Panel Properties
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER,0,20));
        panel1.setMaximumSize(new Dimension(1000,80));
        panel1.setBackground(cl1);
        panel1.add(label1);
        panel2.setLayout(new GridLayout(2,1));
        panel2.setMaximumSize(new Dimension(1000,100));
        panel2.setBackground(cl1);
        JPanel pane1 = new JPanel();
        JPanel pane2 = new JPanel();
        pane1.setBackground(cl1);
        pane2.setBackground(cl1);
        pane1.add(label3);
        pane1.add(cmb1);
        pane1.add(label4);
        pane1.add(cmb2);
        pane2.add(btn1);
        pane2.add(btn3);
        panel2.add(pane1);
        panel2.add(pane2);
        panel3.setBackground(cl1);
        panel4.setMaximumSize(new Dimension(1000,70));
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER,0,20));
        panel4.setBackground(cl1);
        panel5.setMaximumSize(new Dimension(1000,50));
        panel5.setBackground(cl1);
        panel6.setLayout(new BoxLayout(panel6,BoxLayout.Y_AXIS));
        panel6.setBackground(cl1);


        // Frame Properties
        frame.setVisible(true);
        frame.setSize(1000,1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(cl1);
        frame.getContentPane().add(scroll, BorderLayout.CENTER);
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        main.setBackground(cl1);
        main.add(panel1);
        main.add(Box.createRigidArea(new Dimension(0,10)));
        main.add(panel2);
        main.add(Box.createRigidArea(new Dimension(0,10)));
        main.add(panel3);
        main.add(panel4);


        // Action Listeners
        // Button 1 (Create Matrix) Action Listener
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creates input matrix dynamically based on selected rows and columns


                Integer rowobj = (Integer) cmb1.getSelectedItem();
                Integer colobj = (Integer) cmb2.getSelectedItem();
                if(rowobj != null && colobj != null){
                    row = rowobj;
                    col = colobj;
                }
                mat = row*col;
                JPanel pane3 = new JPanel();
                pane3.setBackground(cl1);
                pane3.setLayout(new GridLayout(row,col,5,5));
                panel3.removeAll();
                panel4.removeAll();
                panel5.removeAll();
                panel6.removeAll();
                pane4.removeAll();
                txtList.clear();
                panel3.revalidate();
                panel4.revalidate();
                panel5.revalidate();
                panel6.revalidate();
                panel3.repaint();
                panel4.repaint();
                panel5.repaint();
                panel6.repaint();


                k = 0;
                pvcount = 0;
                pvsearch = 0;
                index = 0;
                rowcount = 0;


                matArr = new float[row][col];


                for(int i = 1; i <= mat; i++){
                    JTextField textField = new JTextField(5);
                    textField.setHorizontalAlignment(JTextField.CENTER);
                    textField.setFont(new Font("Arial",Font.PLAIN,20));
                    textField.setText("0");


                    if(i % col == 0){
                        textField.setBackground(cl3);
                    } else {
                        textField.setBackground(cl2);
                    }


                    textField.setBorder(border);
                    txtList.add(textField);
                    pane3.add(textField);
                }
                panel3.setMaximumSize(new Dimension(1000,(26*row)+(5*row+1)));
                panel3.add(pane3);
                panel3.revalidate();
                panel3.repaint();
                panel4.add(btn2);
                panel4.revalidate();
                panel4.repaint();
            }
        });


        // Button 2 (Calculate RREF) Action Listener
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculates RREF of the matrix and displays each step
                // Recalculation
                k = 0;
                pvcount = 0;
                pvsearch = 0;
                index = 0;
                rowcount = 0;


                panel5.removeAll();
                panel6.removeAll();
                panel5.revalidate();
                panel6.revalidate();
                panel5.repaint();
                panel6.repaint();


                //Loop 1 for array creation
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        matArr[i][j] = Float.parseFloat(txtList.get(k).getText());
                        k++;
                    }
                }


                JLabel cur = new JLabel("The Augmented Matrix: ");
                cur.setFont(new Font("Georgia",Font.BOLD,20));
                cur.setPreferredSize(new Dimension(800,25));
                cur.setHorizontalAlignment(SwingConstants.CENTER);
                display(cur);


                for(int i = 0; i < col-1; i++){
                    if(pvcount < row){
                        for(int sen = pvcount; sen < row; sen++){
                            if(matArr[sen][i] != 0){
                                ER1(i);
                                ER3(i);
                                pvcount++;
                                index = 0;
                                break;
                            }
                        }
                        System.out.println(pvcount);
                    }
                }


                panel5.add(label5);
                main.add(panel6);
                rowcount = rowcount + (row+2);


                JLabel sol = new JLabel("The Reduced Form is: ");
                sol.setFont(new Font("Georgia",Font.BOLD,20));
                sol.setPreferredSize(new Dimension(800,25));
                sol.setHorizontalAlignment(SwingConstants.CENTER);
                display(sol);


                showSolutions();


                panel6.setMaximumSize(new Dimension(1000,Short.MAX_VALUE));
                panel6.setPreferredSize(new Dimension(950,(30*rowcount+1)+(5*rowcount+2)));
                panel6.revalidate();
                panel6.repaint();
            }
        });


        btn3.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                // Clears matrix inputs and resets panel display


                for(JTextField txt : txtList){
                    txt.setText("0");
                }


                panel5.removeAll();
                panel6.removeAll();
                panel5.revalidate();
                panel6.revalidate();
                panel5.repaint();
                panel6.repaint();


                k = 0;
                pvcount = 0;
                pvsearch = 0;
                index = 0;
                rowcount = 0;
            }
        });
    }


    /**
     * Applies Elementary Row Operation 1: Row swapping.
     * If needed, swaps rows to move pivot 1 to the correct position.
     *
     * @param j1 the column index where the pivot is being searched
     */


    // Elementary Row Operation 1 (Row Swapping)
    void ER1(int j1){
        // Method body unchanged


        float[][] temp = new float[1][matArr[0].length];
        for(int i = 0; i < row; i++) {
            if (matArr[i][j1] == 1) {
                pvsearch++;
            }
        }
        if(pvsearch > 0 && pvcount+1 != row){
            if(matArr[pvcount][j1] != 1){
                for(int i = pvcount; i < row; i++){
                    if(matArr[i][j1] == 1) {
                        System.arraycopy(matArr[i], 0, temp[0], 0, matArr[i].length);
                        System.arraycopy(matArr[pvcount], 0, matArr[i], 0, matArr[i].length);
                        System.arraycopy(temp[0], 0, matArr[pvcount], 0, matArr[i].length);
                        counterI = i;
                        break;
                    }
                }
                inDisplay("1", Integer.toString(pvcount+1), Integer.toString(counterI+1), " ", 1);
            }
        } else if (matArr[pvcount][j1] != 1){
            ER2(j1);
        }
        pvsearch = 0;
    }


    /**
     * Applies Elementary Row Operation 2: Multiply row by scalar
     *
     * @param j2 the column index to normalize
     */


    // Elementary Row Operation 2 (Row Scalar Multiplication)
    void ER2(int j2){
        // Method body unchanged


        targetNum = matArr[pvcount][j2];
        for(int i = 0; i < col; i++){
            matArr[pvcount][i] = matArr[pvcount][i] / targetNum;
        }
        inDisplay("2", Integer.toString(pvcount+1), " ", Integer.toString((int) targetNum), 2);
    }


    /**
     * Applies Elementary Row Operation 3: Row addition
     *
     * @param j3 the column index used for elimination
     */


    // Elementary Row Operation 3 (Row Addition)
    void ER3(int j3){
        // Method body unchanged


        for(int i = 0; i < row; i++){
            currNum = matArr[i][j3];
            if(index != pvcount && matArr[i][j3] != 0){
                for(int n = pvcount; n < col; n++){
                    matArr[i][n] = (-currNum * matArr[pvcount][n]) + matArr[i][n];
                }
                inDisplay("3", Integer.toString(i+1), Integer.toString(pvcount+1) , Integer.toString((int) -currNum), 3);
            }
            index++;
        }
    }


    /**
     * Checks if the linear system has no solution.
     *
     * @return true if the system has no solution; false otherwise
     */


    // Row check if linear system is no solution or not
    public boolean noSolutionCheck(){
        // Method body unchanged


        float rowSum = 0;
        boolean check = false;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col-1; j++){
                rowSum += abs(matArr[i][j]);
            }
            if(rowSum == 0 && matArr[i][col-1] > 0){
                check = true;
                break;
            }
            rowSum = 0;
        }
        return check;
    }


    /**
     * Helper method to create a formatted JLabel for displaying matrix entries.
     *
     * This method formats the label with:
     * - Font: Arial, size 20
     * - Centered alignment
     * - Border
     * - Background color based on whether it is the last column (b column) or not
     * - Preferred and maximum size
     *
     * @param j The column index of the matrix entry (used to decide background color)
     * @param border The border to apply to the label
     * @param cl3 The background color for the last column (b column)
     * @param cl2 The background color for other columns
     * @return A JLabel configured to display a matrix entry with formatting
     */


    // Static Method for Display Label
    private static JLabel getJLabel(int j, Border border, Color cl3, Color cl2) {
        JLabel label = new JLabel(numConvert);
        label.setFont(new Font("Arial",Font.PLAIN,20));
        label.setMaximumSize(new Dimension(100,50));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(80,25));
        label.setBorder(border);
        label.setOpaque(true);
        if(j == col-1){
            label.setBackground(cl3);
        } else {
            label.setBackground(cl2);
        }
        return label;
    }


    /**
     * Displays the augmented matrix or intermediate steps.
     *
     * @param l JLabel containing text for display
     */


    // Function for Display Label
    void display(JLabel l){
        // Method body unchanged


        JPanel pane = new JPanel();
        pane.setMaximumSize(new Dimension(950,Short.MAX_VALUE));
        pane.setLayout(new FlowLayout(FlowLayout.CENTER));
        pane.setBackground(cl1);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(row+1,col,5,5));
        panel.setBackground(cl1);
        for(int i = 1; i <= col-1; i++){
            JLabel xLabel = new JLabel("x" + Integer.toString(i));
            xLabel.setFont(new Font("Arial",Font.BOLD,20));
            xLabel.setHorizontalAlignment(SwingConstants.CENTER);
            xLabel.setPreferredSize(new Dimension(80,25));
            panel.add(xLabel);
        }
        JLabel bLabel = new JLabel("b");
        bLabel.setFont(new Font("Arial",Font.BOLD,20));
        bLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bLabel.setPreferredSize(new Dimension(80,25));
        panel.add(bLabel);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(matArr[i][j] % 1 == 0){
                    numConvert = Integer.toString((int) matArr[i][j]);
                    JLabel label = getJLabel(j, border, cl3, cl2);
                    panel.add(label);
                } else {
                    numConvert = String.format("%.3f",matArr[i][j]);
                    JLabel label = getJLabel(j, border, cl3, cl2);
                    panel.add(label);
                }
            }
        }
        pane.add(l);
        pane.add(panel);
        panel6.add(pane);
        rowcount = rowcount + (row+1);
    }


    /**
     * Displays a step of an elementary row operation.
     *
     * @param opnum The operation number (1, 2, 3)
     * @param op1 Row involved in operation
     * @param op2 Second row involved (if applicable)
     * @param sc Scalar multiplier (if applicable)
     * @param num Operation type
     */


    // Function for display between operations
    void inDisplay(String opnum, String op1, String op2, String sc, int num){
        JLabel notation = new JLabel();
        notation.setFont(new Font("Georgia",Font.BOLD,16));
        notation.setPreferredSize(new Dimension(800,25));
        notation.setHorizontalAlignment(SwingConstants.CENTER);
        notation.setBackground(cl1);
        notation.setOpaque(true);
        if(num == 1){
            notation.setText("Apply Elementary Row Operation " +  opnum + ": Swap Row " + op1 + " and Row " + op2);
            display(notation);
        } else if(num == 2){
            notation.setText("Apply Elementary Row Operation " +  opnum + ": Multiply Row " + op1 + " by 1/" + sc);
            display(notation);
        } else if(num == 3){
            notation.setText("Apply Elementary Row Operation " +  opnum + ": Replace Row " + op1 + " by the sum of Row " + op1 + " and Row " + op2 + " times " + sc);
            display(notation);
        }
    }


    /**
     * Displays the final solutions of the linear system.
     * Handles unique solutions, no solution, or infinitely many solutions.
     */


    void showSolutions(){
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(col+1,1,0,5));
        pane.setMaximumSize(new Dimension(950,Short.MAX_VALUE));
        pane.setBackground(cl1);
        JLabel label = new JLabel();
        label.setFont(new Font("Georgia",Font.BOLD,20));
        label.setPreferredSize(new Dimension(950,25));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        pane.add(label);
        if(pvcount == col-1){
            label.setText("The given linear system has a unique solution: ");
            for (int i = 0; i < row; i++) {
                JLabel xLabel = new JLabel();
                xLabel.setFont(new Font("Georgia",Font.BOLD,20));
                xLabel.setHorizontalAlignment(SwingConstants.CENTER);
                if(matArr[i][col-1] % 1 == 0){
                    numConvert = Integer.toString((int) matArr[i][col-1]);
                } else {
                    numConvert = String.format("%.3f",matArr[i][col-1]);
                }
                xLabel.setText("x" + (i+1) + " = " + numConvert);
                pane.add(xLabel);
            }
        } else if(pvcount < col-1){
            int count = 0;
            if (noSolutionCheck()){
                label.setText("The given linear system has no solutions.");
            } else {
                label.setText("The given linear system has an infinitely many solution: ");
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        if(i == j && matArr[count][j] == 1){
                            JLabel xLabel = new JLabel();
                            xLabel.setFont(new Font("Georgia",Font.BOLD,20));
                            xLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            if(matArr[i][col-1] % 1 == 0){
                                numConvert = Integer.toString((int) matArr[count][col-1]);
                            } else {
                                numConvert = String.format("%.3f",matArr[count][col-1]);
                            }
                            xLabel.setText("x" + (j+1) + " = " + numConvert);
                            count++;
                            pane.add(xLabel);
                        } else if(i == j && matArr[count][j] != 1) {
                            JLabel xLabel = new JLabel();
                            xLabel.setFont(new Font("Georgia",Font.BOLD,20));
                            xLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            xLabel.setText("x" + (j+1) + " is free");
                            pane.add(xLabel);
                        }
                    }
                }
            }
        }
        panel6.add(pane);
        rowcount = rowcount + (row+1);
    }


    /**
     * Main method to launch the GUI using SwingUtilities.
     *
     * @param args command-line arguments (not used)
     */


    // Main Method
    public static void main(String[] args)throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RREFCalc().Calc();
            }
        });
    }
}
