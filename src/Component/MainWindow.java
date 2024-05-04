package Component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.*;

import Solver.GraphAdjacencyMap;
import Solver.GraphNode;
import Solver.SolutionData;
import Solver.UCSSolver;
import Solver.WordLadderSolver;;

public class MainWindow extends JFrame {
    Label titleLabel = new Label("Word Ladder Solver");
    SquaredInputField sourceInput = new SquaredInputField();
    SquaredInputField destinationInput = new SquaredInputField();
    NormalButton submitButton = new NormalButton("Submit");

    Label sourceLabel = new Label("Source:");
    Label destinationLabel = new Label("Destination:");
    Label resultLabel = new Label("Result:");
    Label executionTimeLabel = new Label("Execution Time: ");

    // Layout
    JPanel panel = new JPanel();
    JPanel titlePanel = new JPanel();
    JPanel sourceLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    // JPanel sourcePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JScrollPane sourcePanel = new JScrollPane();
    JPanel destinationLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    // JPanel destinationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JScrollPane destinationPanel = new JScrollPane();
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    
    ArrayList<SquaredLabel> resultList = new ArrayList<SquaredLabel>();
    Component spacingBox = Box.createRigidArea(new Dimension(0, 100));
    GridBagConstraints gbc = new GridBagConstraints();
    ArrayList<GraphAdjacencyMap> graphList;

    public MainWindow(ArrayList<GraphAdjacencyMap> graphList) {
        this.graphList = graphList;

        setTitle("Word Ladder Solver");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setBackground(Colors.slate950);
        
        // Panels
        panel.setBackground(Colors.slate950);
        panel.setLayout(new GridBagLayout());
        gbc.weighty = 0;
        gbc.weightx = 1;
        
        add(panel);

        // Title Panel
        titlePanel.setBackground(Colors.slate950);
        titleLabel.setFont(Fonts.OutfitExtraBold.deriveFont(30f));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        gbc.gridy++;
        panel.add(titlePanel, gbc);
        
        // Source Panel
        sourceLabelPanel.setBackground(Colors.slate950);
        sourcePanel.setBackground(Colors.slate950);
        sourcePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sourcePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        sourcePanel.setPreferredSize(new Dimension(1000, 70));
        sourcePanel.setBorder(null);

        gbc.gridy++;
        panel.add(sourceLabelPanel, gbc);
        gbc.gridy++;
        panel.add(sourcePanel, gbc);


        // Destination Panel
        destinationLabelPanel.setBackground(Colors.slate950);
        destinationPanel.setBackground(Colors.slate950);
        destinationPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        destinationPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        destinationPanel.setPreferredSize(new Dimension(1000, 70));
        destinationPanel.setBorder(null);
        gbc.gridy++;
        panel.add(destinationLabelPanel, gbc);
        gbc.gridy++;
        panel.add(destinationPanel, gbc);

        // Button Panel
        buttonPanel.setBackground(Colors.slate950);
        buttonPanel.add(submitButton);
        gbc.gridy++;
        panel.add(buttonPanel, gbc);

        // Result Panel
        resultLabel.setFont(Fonts.OutfitExtraBold.deriveFont(20f));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 20));

        // Execution Time Label
        executionTimeLabel.setFont(Fonts.OutfitBold.deriveFont(14f));
        executionTimeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 20));

        // Components
        titlePanel.add(titleLabel);
        sourceLabelPanel.add(sourceLabel);
        // sourcePanel.add(sourceInput);
        sourcePanel.setViewportView(sourceInput);
        destinationLabelPanel.add(destinationLabel);
        // destinationPanel.add(destinationInput);
        destinationPanel.setViewportView(destinationInput);
        buttonPanel.add(submitButton);

        
        // Event Listeners
        submitButton.addActionListener(e -> {
            try {
                populateSolution();
            } catch (Exception ex) {
                popUpError("Error", ex.getMessage());
            }
        });

        sourceInput.onEnter = () -> {
            destinationInput.setFocusToLeftestField();
        };
        sourceInput.onRightInRightestField = () -> {
            destinationInput.setFocusToLeftestField();
        };
        sourceInput.onDown = () -> {
            destinationInput.setFocusToLeftestField();
        };

        destinationInput.onEnter = () -> {
            submitButton.doClick();
        };
        destinationInput.onLeftInLeftestField = () -> {
            sourceInput.setFocusToLeftestField();
        };
        destinationInput.onUp = () -> {
            sourceInput.setFocusToLeftestField();
        };
        destinationInput.onDown = () -> {
            submitButton.requestFocus();
        };
    }

    // Return the choosen algorithm populated with the source, destination and graph
    WordLadderSolver getSolver() {
        int algorithmType = 0;
        switch(algorithmType){
            case 0:
                return new UCSSolver(sourceInput.getText(), destinationInput.getText(), graphList);
            case 1:
                // return new GBFSSolver(sourceInput.getText(), destinationInput.getText(), graphList);
            case 2:
                // return new AStarSolver(sourceInput.getText(), destinationInput.getText(), graphList);
        }
        return new UCSSolver(sourceInput.getText(), destinationInput.getText(), graphList);
    }

    void populateSolution() throws Exception {
        WordLadderSolver solver = getSolver();
        SolutionData solution = solver.Solve();
        
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultLabel.setAlignmentY(Component.TOP_ALIGNMENT);

        resultPanel.setBackground(Colors.slate950);
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        JDialog dialog = new JDialog(this);
        dialog.setTitle("Solution");
        dialog.setBackground(Colors.slate950);

        dialog.setSize(400, 400);
        dialog.setVisible(true);
        resultPanel.setVisible(true);
        JScrollPane resultScrollPane = new JScrollPane(resultPanel);
        resultScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        dialog.add(resultScrollPane);
        

        JPanel resultPanelTitle = new JPanel();
        resultPanelTitle.setBackground(Colors.slate950);
        resultPanelTitle.setAlignmentY(Component.TOP_ALIGNMENT);

        JPanel resultPanelSolution = new JPanel();
        resultPanelSolution.setBackground(Colors.slate950);
        resultPanelSolution.setAlignmentY(Component.TOP_ALIGNMENT);
        resultPanelSolution.setLayout(new BoxLayout(resultPanelSolution, BoxLayout.Y_AXIS));

        resultPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;

        // row 1
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        resultPanel.add(resultPanelTitle, gbc);

        // row 2. col 1(take as small space), col 2(take as much space)
        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        resultPanel.add(executionTimeLabel, gbc);
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        resultPanel.add(Box.createRigidArea(new Dimension(5, 5)), gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        resultPanel.add(resultPanelSolution, gbc);
        gbc.weighty = 100;
        resultPanel.add(Box.createRigidArea(new Dimension(5, 5)), gbc);
        

        resultPanelTitle.add(resultLabel);
        resultList.clear();

        

        LinkedList<String> result = solution.getSolution();
        double duration = solution.getDuration();
        executionTimeLabel.setText("Execution Time: " + duration + " ms");
        
        for (String node : result) {
            SquaredLabel newResult = new SquaredLabel(node);
            resultPanelSolution.add(newResult);
            resultList.add(newResult);
        }

        // move to the center
        dialog.setLocation(this.getX() + this.getWidth() / 2 - dialog.getWidth() / 2, this.getY() + this.getHeight() / 2 - dialog.getHeight() / 2);
        

    }

    void popUpError(String title, String message) {
        JDialog dialog = new JDialog(this);
        dialog.setTitle(title);
        dialog.setSize(480, 170);
        dialog.setBackground(Colors.slate950);
        dialog.setVisible(true);

        NormalButton okButton = new NormalButton("Ok");
        okButton.addActionListener(e -> {
            dialog.dispose();
        });
        okButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    dialog.dispose();
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
            }
        
        });

        JPanel panel = new JPanel();
        panel.setBackground(Colors.slate950);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weighty = 0;
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Label messageLabel = new Label(message);
        messageLabel.setFont(Fonts.OutfitBold.deriveFont(18f));
        panel.add(messageLabel, gbc);
        gbc.gridy++;
        panel.add(Box.createRigidArea(new Dimension(0, 20)), gbc);
        gbc.gridy++;
        // align to the right
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(okButton, gbc);

        dialog.add(panel);
        dialog.setLocation(this.getX() + this.getWidth() / 2 - dialog.getWidth() / 2, this.getY() + this.getHeight() / 2 - dialog.getHeight() / 2);



    }
}