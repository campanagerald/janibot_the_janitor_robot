package psu.janibot.view;

import psu.janibot.*;
import psu.janibot.util.*;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GamePanel extends JPanel{

    public static final Color consolePaneForeGroundColor = new Color(196, 70, 61);
    public static final Color consolePaneBackGroundColor = new Color(255, 253, 204);
    public static final Color etchedBorderColorHighLightColor = new Color(207, 207, 207);
    public static final Color etchedBorderShadowColor = new Color(224, 224, 224);
    public static final Color backgroundColor = new Color(218, 229, 243);
    public static final Image image = new ImageLoader().loadImage("/images/game/janibot_good_job.png");

    private SimulationPanel simulationPanel;
    private JScrollPane introPaneScrollPane;
    private JTextPane introPane;
    private JPanel resultPanelAndUserPanel;
    private JPanel userPanel;
    private JPanel resultPanel;
    private JPanel startAndCodeEditorPanel;
    private JPanel startPanel;
    private JButton startButton;
    private JScrollPane codeEditorPaneScrollPane;
    private JTextPane codeEditorPane;
    private JScrollPane consolePaneScrollPane;
    private JTextPane consolePane;
    private JButton compileButton;
    private JButton runButton;
    private JPanel toolbarPanel;
    private JButton abortGameButton;
    private JButton restoreMazeButton;
    private JLabel bagLabel;
    private JLabel stepLabel;
    private JLabel timerLabel;
    private JPanel objectivePanel;
    private JLabel maxStepLabel;
    private JLabel maxDirtLabel;
    private JLabel maxLineLabel;
    private JLabel bagCapacityLabel;
    private JLabel timeLimitLabel;
    private JPanel janibotPicturePanel;
    private JLabel resultLabel;
    private JLabel stepsMadeLabel;
    private JLabel dirtCollectedLabel;
    private JLabel lineMadeLabel;
    private JLabel elapsedTimeLabel;
    private File mapFile;
    private JButton nextButton;
    private Difficulty difficulty;

    public GamePanel(SimulationPanel simulationPanel, Difficulty difficulty) {
        this.simulationPanel = simulationPanel;
        this.difficulty = difficulty;
        setBackground(backgroundColor);
        initializeComponents();
        designComponents();
    }

    private void initializeComponents(){

        resultPanelAndUserPanel = new JPanel(new CardLayout());
        userPanel = new JPanel(new GridBagLayout());
        userPanel.setBackground(getBackground());
        resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(getBackground());

        nextButton = new JButton(new ImageIcon(Program.class.getResource("/images/game/next_button.png")));
        nextButton.setPreferredSize(new Dimension(52, 52));
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextButton.setContentAreaFilled(false);

        // abortGameButton
        abortGameButton = new JButton("Abort Game", new ImageIcon(Program.class.getResource("/images/game/stop.png")));
        abortGameButton.setBackground(getBackground());
        abortGameButton.setFont(new Font("Arial", Font.BOLD, 11));
        abortGameButton.setFocusPainted(false);

        // restoreMazeButton
        restoreMazeButton = new JButton("Restore Maze", new ImageIcon(Program.class.getResource("/images/game/restart.png")));
        restoreMazeButton.setBackground(getBackground());
        restoreMazeButton.setFont(new Font("Arial", Font.BOLD, 11));
        restoreMazeButton.setFocusPainted(false);
        toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        toolbarPanel.setOpaque(false);
        //toolbarPanel.setBorder(BorderFactory.createLineBorder(new Color(188, 188, 188)));
        //toolbarPanel.setBackground(getBackground());
        toolbarPanel.add(abortGameButton);
        toolbarPanel.add(restoreMazeButton);

        // introPane
        introPane = new JTextPane() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g);
            }
        };

        //http://java-sl.com/tip_html_letter_wrap.html
        /*introPane.setEditorKit(new HTMLEditorKit(){
            @Override
            public ViewFactory getViewFactory(){
                return new HTMLFactory(){
                    public View create(Element e){
                        View v = super.create(e);
                        if(v instanceof InlineView){
                            return new InlineView(e){
                                public int getBreakWeight(int axis, float pos, float len) {
                                    return GoodBreakWeight;
                                }
                                public View breakView(int axis, int p0, float pos, float len) {
                                    if(axis == View.X_AXIS) {
                                        checkPainter();
                                        int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len);
                                        if(p0 == getStartOffset() && p1 == getEndOffset()) {
                                            return this;
                                        }
                                        return createFragment(p0, p1);
                                    }
                                    return this;
                                }
                            };
                        }
                        else if (v instanceof ParagraphView) {
                            return new ParagraphView(e) {
                                protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) {
                                    if (r == null) {
                                        r = new SizeRequirements();
                                    }
                                    float pref = layoutPool.getPreferredSpan(axis);
                                    float min = layoutPool.getMinimumSpan(axis);
                                    // Don't include insets, Box.getXXXSpan will include them.
                                    r.minimum = (int)min;
                                    r.preferred = Math.max(r.minimum, (int) pref);
                                    r.maximum = Integer.MAX_VALUE;
                                    r.alignment = 0.5f;
                                    return r;
                                }

                            };
                        }
                        return v;
                    }
                };
            }
        });*/

        introPane = new JTextPane();
        introPane.setContentType("text/html");
        introPane.setEditable(false);
        introPaneScrollPane = new JScrollPane();
        introPaneScrollPane.setViewportView(introPane);
        introPaneScrollPane.getHorizontalScrollBar().setUI(new ScrollBarUI());
        introPaneScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 8));
        introPaneScrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        introPaneScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        introPaneScrollPane.setPreferredSize(new Dimension(400, 110));
        introPaneScrollPane.setBorder(BorderFactory.createEtchedBorder(new Color(207, 207, 207), new Color(224, 224, 224)));

        // bagCapacity
        bagCapacityLabel = new JLabel("Bag Capacity: ");
        bagCapacityLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 12));

        //maxStepLabel
        maxStepLabel = new JLabel("Max steps: ");
        maxStepLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 12));

        //maxDirtLabel
        maxDirtLabel = new JLabel("Max dirt : ");
        maxDirtLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 12));

        //maxLineLabel
        maxLineLabel = new JLabel("Max line        : ");
        maxLineLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 12));

        //timeLimitLabel
        timeLimitLabel = new JLabel("Time limit (min): ");
        timeLimitLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 12));
        objectivePanel = new JPanel(new GridBagLayout());
        objectivePanel.setOpaque(false);

        // startPanel
        startPanel = new JPanel(new GridBagLayout());
        startPanel.setBackground(backgroundColor);
        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 11));
        startButton.setFocusPainted(false);
        startButton.setBackground(backgroundColor);
        startPanel.add(startButton, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));

        //startAndCodeEditorPanel
        startAndCodeEditorPanel = new JPanel(new CardLayout());

        codeEditorPane = new JTextPane() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g);
            }
        };

        TabStop[] tabs = new TabStop[99];

        for (int i = 0; i < tabs.length; i++) {
            tabs[i] = new TabStop((25 * (i + 1)), TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
        }

        TabSet tabset = new TabSet(tabs);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet asset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabset);

        //http://stackoverflow.com/questions/14400946/how-to-change-the-color-of-specific-words-in-a-jtextpane
        final UndoManager undoManager = new UndoManager();
        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 128, 0));
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        final AttributeSet attrMethod = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 0, 255));
        final AttributeSet attrBooleanMethod = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(52, 156, 75));

        DefaultStyledDocument doc = new DefaultStyledDocument() {

            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("(\\W)*(repeat|if|else|while|def|MAIN|end)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        } else if (text.substring(wordL, wordR).matches("(\\W)*(go|left|right|pick|put)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrMethod, false);
                        } else if (text.substring(wordL, wordR).matches("(\\W)*(wall|north|dirt|trashcan|empty|not)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrBooleanMethod, false);
                        } else
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*(repeat|if|else|while|def|MAIN|end)")) {
                    setCharacterAttributes(before, after - before, attr, false);
                } else if (text.substring(before, after).matches("(\\W)*(go|left|right|pick|put)")) {
                    setCharacterAttributes(before, after - before, attrMethod, false);
                } else if (text.substring(before, after).matches("(\\W)*(wall|north|dirt|trashcan|empty|not)")) {
                    setCharacterAttributes(before, after - before, attrMethod, false);
                } else {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            }
        };

        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });

        codeEditorPane.getActionMap().put("Undo", new AbstractAction("Undo") {
            public void actionPerformed(ActionEvent evt) throws CannotUndoException {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });
        codeEditorPane.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        codeEditorPane.setDocument(doc);
        codeEditorPane.setParagraphAttributes(asset, false);
        codeEditorPane.setBackground(new Color(225, 243, 255));
        codeEditorPane.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 11));
        JPanel editorPanel = new JPanel(new BorderLayout());
        editorPanel.add(codeEditorPane, BorderLayout.CENTER);
        codeEditorPaneScrollPane = new JScrollPane();
        codeEditorPaneScrollPane.setViewportView(editorPanel);
        codeEditorPaneScrollPane.getHorizontalScrollBar().setUI(new ScrollBarUI());
        codeEditorPaneScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 8));
        codeEditorPaneScrollPane.getHorizontalScrollBar().setBackground(editorPanel.getBackground());
        codeEditorPaneScrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        codeEditorPaneScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        codeEditorPaneScrollPane.setBorder(BorderFactory.createEtchedBorder(etchedBorderColorHighLightColor, etchedBorderShadowColor));
        codeEditorPaneScrollPane.setRowHeaderView(new LineNumber(codeEditorPane));
        codeEditorPaneScrollPane.setPreferredSize(new Dimension(400, 225));
        startAndCodeEditorPanel.add(startPanel, "startPanel");
        startAndCodeEditorPanel.add(codeEditorPaneScrollPane, "codeEditorPaneScrollPane");

        // consolePane
        consolePane = new JTextPane() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g);
            }
        };

        consolePane.setBackground(consolePaneBackGroundColor);
        consolePane.setForeground(consolePaneForeGroundColor);
        consolePane.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 10));
        consolePane.setEditable(false);
        consolePaneScrollPane = new JScrollPane(consolePane);
        consolePaneScrollPane.getHorizontalScrollBar().setUI(new ScrollBarUI());
        consolePaneScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 8));
        consolePaneScrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        consolePaneScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        consolePaneScrollPane.setPreferredSize(new Dimension(400, 50));
        consolePaneScrollPane.setBorder(BorderFactory.createEtchedBorder(etchedBorderColorHighLightColor, etchedBorderShadowColor));

        // compileButton
        compileButton = new JButton(new ImageIcon(Program.class.getResource("/images/game/compile.png")));
        compileButton.setBackground(backgroundColor);
        compileButton.setPreferredSize(new Dimension(30, 30));
        compileButton.setFocusPainted(false);

        //runButton
        runButton = new JButton(new ImageIcon(Program.class.getResource("/images/game/run.png")));
        runButton.setBackground(backgroundColor);
        runButton.setPreferredSize(new Dimension(30, 30));
        runButton.setFocusPainted(false);
        runButton.setEnabled(false);


        //bagLabel
        bagLabel = new JLabel("00", new ImageIcon(Program.class.getResource("/images/game/dirt_icon.png")), SwingConstants.LEFT);
        bagLabel.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));

        //stepLabel
        stepLabel = new JLabel("00", new ImageIcon(Program.class.getResource("/images/game/step.png")), SwingConstants.LEFT);
        stepLabel.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));

        //timerLabel
        timerLabel = new JLabel("0:0:0.0", new ImageIcon(Program.class.getResource("/images/game/timer.png")), SwingConstants.LEFT);
        timerLabel.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));


        janibotPicturePanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        janibotPicturePanel.setPreferredSize(new Dimension(60, 300));

        resultLabel = new JLabel("Good Job!");
        resultLabel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 18));

        stepsMadeLabel = new JLabel("Steps made: ");
        stepsMadeLabel.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));

        dirtCollectedLabel = new JLabel("Collected Dirt: ");
        dirtCollectedLabel.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));

        lineMadeLabel = new JLabel("Line made: ");
        lineMadeLabel.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));

        elapsedTimeLabel = new JLabel("Elapsed time: ");
        elapsedTimeLabel.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));
    }

    public void designComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        GridBagConstraints userPanelConstraints = new GridBagConstraints();
        GridBagConstraints resultPanelConstraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        add(toolbarPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(resultPanelAndUserPanel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(simulationPanel, constraints);

        userPanelConstraints.gridx = 0;
        userPanelConstraints.gridy = 0;
        userPanelConstraints.weightx = 1;
        userPanelConstraints.weighty = 1;
        userPanelConstraints.gridwidth = 5;
        userPanelConstraints.fill = 0;
        userPanelConstraints.insets = new Insets(3, 3, 3, 3);
        userPanel.add(introPaneScrollPane, userPanelConstraints);

        userPanelConstraints.gridx = 0;
        userPanelConstraints.gridy = 1;
        userPanelConstraints.gridwidth = 5;
        userPanelConstraints.fill = GridBagConstraints.BOTH;
        userPanel.add(objectivePanel, userPanelConstraints);

        userPanelConstraints.gridx = 0;
        userPanelConstraints.gridy = 2;
        userPanelConstraints.gridwidth = 5;
        userPanelConstraints.fill = 0;
        userPanel.add(startAndCodeEditorPanel, userPanelConstraints);

        userPanelConstraints.gridx = 0;
        userPanelConstraints.gridy = 3;
        userPanel.add(consolePaneScrollPane, userPanelConstraints);

        userPanelConstraints.gridx = 0;
        userPanelConstraints.gridy = 4;
        userPanelConstraints.gridwidth = 1;
        userPanelConstraints.weightx = 0;
        userPanelConstraints.insets = new Insets(0, 5, 5, 5);
        userPanelConstraints.anchor = GridBagConstraints.LINE_START;
        userPanel.add(compileButton, userPanelConstraints);

        userPanelConstraints.gridx = 1;
        userPanelConstraints.gridy = 4;
        userPanelConstraints.weightx = 0;
        userPanelConstraints.insets = new Insets(0, 0, 5, 5);
        userPanel.add(runButton, userPanelConstraints);

        userPanelConstraints.gridx = 2;
        userPanelConstraints.gridy = 4;
        userPanelConstraints.anchor = GridBagConstraints.LINE_END;
        userPanelConstraints.weightx = 1;
        userPanel.add(bagLabel, userPanelConstraints);

        userPanelConstraints.gridx = 3;
        userPanelConstraints.gridy = 4;
        userPanelConstraints.weightx = 0.1;
        userPanel.add(stepLabel, userPanelConstraints);

        userPanelConstraints.gridx = 4;
        userPanelConstraints.gridy = 4;
        userPanelConstraints.weightx = 0.1;
        userPanel.add(timerLabel, userPanelConstraints);

        resultPanelConstraints.gridx = 0;
        resultPanelConstraints.gridy = 0;
        resultPanelConstraints.gridwidth = 3;
        resultPanelConstraints.fill = GridBagConstraints.BOTH;
        resultPanelConstraints.anchor = GridBagConstraints.CENTER;
        resultPanelConstraints.insets = new Insets(5, 5, 5, 5);
        resultPanel.add(resultLabel, resultPanelConstraints);

        resultPanelConstraints.gridx = 0;
        resultPanelConstraints.gridy = 1;
        resultPanelConstraints.gridwidth = 1;
        resultPanelConstraints.weightx = 0.1;
        //resultPanelConstraints.anchor = GridBagConstraints.LINE_START;
        resultPanelConstraints.insets = new Insets(5, 5, 5, 10);
        resultPanel.add(stepsMadeLabel, resultPanelConstraints);

        resultPanelConstraints.gridx = 1;
        resultPanelConstraints.gridy = 1;
        resultPanel.add(dirtCollectedLabel, resultPanelConstraints);

        resultPanelConstraints.gridx = 0;
        resultPanelConstraints.gridy = 2;
        resultPanelConstraints.weightx = 0;
        resultPanel.add(lineMadeLabel, resultPanelConstraints);

        resultPanelConstraints.gridx = 1;
        resultPanelConstraints.gridy = 2;
        resultPanel.add(elapsedTimeLabel, resultPanelConstraints);

        resultPanelConstraints.gridx = 0;
        resultPanelConstraints.gridy = 3;
        resultPanelConstraints.gridwidth = 3;
        resultPanel.add(janibotPicturePanel, resultPanelConstraints);

        resultPanelConstraints.gridx = 0;
        resultPanelConstraints.gridy = 4;
        resultPanelConstraints.gridwidth = 3;
        resultPanel.add(nextButton, resultPanelConstraints);

        resultPanelAndUserPanel.add(resultPanel, "resultPanel");
        resultPanelAndUserPanel.add(userPanel, "userPanel");
        setSize(getPreferredSize());

        switch (difficulty){
            case BEGINNER:{
                objectivePanel.removeAll();
                objectivePanel.add(maxStepLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, 0, new Insets(0, 0, 0, 35), 0, 0));
                objectivePanel.add(maxLineLabel, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, 0, new Insets(0, 0, 0, 0), 0, 0));
                objectivePanel.add(maxDirtLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, 0, new Insets(0, 0, 0, 35), 0, 0));
                objectivePanel.add(timeLimitLabel, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, 0, new Insets(0, 0, 0, 0), 0, 0));
                break;
            }
            case ADVANCE:{
                objectivePanel.removeAll();
                GridBagConstraints gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.anchor = GridBagConstraints.LINE_START;
                gridBagConstraints.insets = new Insets(0, 0, 0, 35);
                objectivePanel.add(bagCapacityLabel, gridBagConstraints);

                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.insets = new Insets(0, 0, 0, 35);
                objectivePanel.add(maxStepLabel, gridBagConstraints);

                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.insets = new Insets(0, 0, 0, 0);
                objectivePanel.add(maxLineLabel, gridBagConstraints);

                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.insets = new Insets(0, 0, 0, 35);
                objectivePanel.add(maxDirtLabel, gridBagConstraints);

                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.insets = new Insets(0, 0, 0, 0);
                objectivePanel.add(timeLimitLabel, gridBagConstraints);
                break;
            }
        }
    }

    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    public void setIntroPane(String s) {
        String intro = "<html><p><b><font size=\"3\" face=\"arial\">" + s + "</font><b></p></html>";
        introPane.setText(intro);
    }

    public void setConsolePane(String s) {
        consolePane.setText(s);
    }

    public JButton getRunButton() {
        return runButton;
    }

    public void setRunButton(boolean b){
        runButton.setEnabled(b);
    }

    public File getMapFile() {
        return mapFile;
    }

    public JButton getCompileButton() {
        return compileButton;
    }

    public String getCodeEditorPane() {
        return codeEditorPane.getText();
    }

    public String getConsolePane() {
        return consolePane.getText();
    }

    public void setCodeEditorPane(String s) {
        this.codeEditorPane.setText(s);
    }

    public void setCodeEditorPane(boolean b) {
        this.codeEditorPane.setEnabled(b);
    }

    public void setBagLabel(int bag) {
        bagLabel.setText(Integer.toString(bag));
    }

    public void setStepLabel(int step) {
        stepLabel.setText(Integer.toString(step));
    }

    public void setTimerLabel(String s) {
        this.timerLabel.setText(s);
    }

    public void setCompileButton(boolean b) {
        this.compileButton.setEnabled(b);
    }

    public void revealCodeEditor() {
        CardLayout layout = (CardLayout) startAndCodeEditorPanel.getLayout();
        layout.show(startAndCodeEditorPanel, "codeEditorPaneScrollPane");
        compileButton.setEnabled(true);
    }

    public void revealResultPanel(String result, String steps, String dirt, String line, String time) {
        setResultLabel(result);
        setStepsMadeLabel(steps);
        setDirtCollectedLabel(dirt);
        setLineMadeLabel(line);
        setElapsedTimeLabel(time);
        CardLayout layout = (CardLayout) resultPanelAndUserPanel.getLayout();
        layout.show(resultPanelAndUserPanel, "resultPanel");
    }

    public void revealUserPanel(){
        CardLayout layout = (CardLayout) resultPanelAndUserPanel.getLayout();
        layout.show(resultPanelAndUserPanel, "userPanel");
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setResultLabel(String result) {
        this.resultLabel.setText(result);
    }

    public void setStepsMadeLabel(String steps) {
        this.stepsMadeLabel.setText("Steps made: " + steps);
    }

    public void setDirtCollectedLabel(String dirtCollected) {
        this.dirtCollectedLabel.setText("Dirts Collected: " + dirtCollected);
    }

    public void setLineMadeLabel(String line) {
        this.lineMadeLabel.setText("Line made: " + line);
    }

    public void setElapsedTimeLabel(String time) {
        this.elapsedTimeLabel.setText("Elapsed time: " + time);
    }

    public void initializeComponentState(File mapFile) {

        this.mapFile = mapFile;

        CardLayout layout = (CardLayout) startAndCodeEditorPanel.getLayout();
        layout.show(startAndCodeEditorPanel, "startPanel");

        simulationPanel.setGameState(SimulationPanel.GameState.INITIALIZE);

        stepLabel.setText("00");
        bagLabel.setText("00");
        timerLabel.setText("0:0:0.0");
        consolePane.setText("");
        runButton.setEnabled(false);
        compileButton.setEnabled(false);
    }

    public void setObjectivePanel(String step, String dirt, String line, String time, String bagCapacity){
        maxStepLabel.setText("Max steps: " + step);
        maxDirtLabel.setText("Max dirt : " + dirt);
        maxLineLabel.setText("Max line : " + line);
        bagCapacityLabel.setText("Bag capacity: " + bagCapacity);
        timeLimitLabel.setText("Time limit (min): " + time);
    }

    public void setCompileButtonActionListener(ActionListener compileButtonActionListener){
        compileButton.addActionListener(compileButtonActionListener);
    }

    public void setRunButtonActionListener(ActionListener runButtonActionListener){
        runButton.addActionListener(runButtonActionListener);
    }

    public void setStartButtonActionListener(ActionListener startButtonActionListener){
        startButton.addActionListener(startButtonActionListener);
    }

    public void setRestoreMazeButtonActionListener(ActionListener restoreMazeButtonActionListener) {
        restoreMazeButton.addActionListener(restoreMazeButtonActionListener);
    }

    public void setAbortGameButtonActionListener(ActionListener abortGameButtonActionListener) {
        abortGameButton.addActionListener(abortGameButtonActionListener);
    }

    public void setNextButtonActionListener(ActionListener nextButtonActionListener){
        nextButton.addActionListener(nextButtonActionListener);
    }
}