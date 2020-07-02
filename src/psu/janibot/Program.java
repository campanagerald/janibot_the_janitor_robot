package psu.janibot;

import psu.janibot.mode.*;
import psu.janibot.util.*;
import psu.janibot.util.Compiler;
import psu.janibot.view.GamePanel;
import psu.janibot.view.LoadingPanel;
import psu.janibot.view.SimulationPanel;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

public class Program extends JFrame {

    private static final String RobotDirectory= System.getProperty("user.home") + "/Documents/Janibot/lib";
    private static final String mapDirectory = System.getProperty("user.home") + "/Documents/Janibot/lib/map/";
    private psu.janibot.util.Compiler compiler;
    private ModePanel modePanel;
    private SimpleCommandsPanel simpleCommandsPanel;
    private CountingLoopPanel countingLoopPanel;
    private ConditionsPanel conditionsPanel;
    private ConditionalLoopPanel conditionalLoopPanel;
    private CustomCommandsPanel customCommandsPanel;
    private GamePanel gamePanel;
    private SimulationPanel simulationPanel;
    private LoadingPanel loadingPanel;
    private JMenuBar menuBar;
    private JMenu editMenu;
    private JMenu difficultyMenu;
    private JMenu helpMenu;
    private JMenuItem manualMenuItem;
    private JRadioButtonMenuItem beginnerRadioButtonMenuItem;
    private JRadioButtonMenuItem advanceRadioButtonMenuItem;
    ButtonGroup difficultyTypesGroup;

    private Block[][] blocks;
    private Janibot janibot;
    private Timer timer;

    private int maxStep;
    private int maxDirt;
    private int maxLine;
    private int maxTime;
    private int bagCapacity;

    private int millisec = 0;
    private int sec = 0;
    private int mins = 0;
    private int hrs = 0;
    private File mapFile;
    private boolean finished = false;
    private Mode gameMode;

    private Difficulty difficulty = Difficulty.BEGINNER;

    public Program(String title) throws HeadlessException {
        super(title);
        setIconImage(new ImageLoader().loadImage("/images/game/janibot.png"));
        SoundEffect.init();
        initializeComponents();
        designComponents();
    }

    private void initializeComponents() {

        timer = new Timer(100, null);
        timer.addActionListener(new TimerActionListener());

        compiler = new Compiler();

        loadingPanel = new LoadingPanel();

        menuBar = new JMenuBar();
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");
        manualMenuItem = new JMenuItem("Manual");
        manualMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    File pdfFile = new File(System.getProperty("user.home") + "\\Documents\\Janibot\\lib\\Manual.pdf");

                    if (pdfFile.exists()) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(pdfFile);
                        } else {
                            System.out.println("Awt Desktop is not supported!");
                        }
                    } else {
                        System.out.println("File is not exists!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        difficultyMenu = new JMenu("Difficulty");
        difficultyTypesGroup = new ButtonGroup();
        beginnerRadioButtonMenuItem = new JRadioButtonMenuItem("Beginner", true);
        beginnerRadioButtonMenuItem.addActionListener(new BeginnerRadioButtonActionListener());

        advanceRadioButtonMenuItem = new JRadioButtonMenuItem("Advance", false);
        advanceRadioButtonMenuItem.addActionListener(new AdvanceRadioButtonActionListener());

        simulationPanel = new SimulationPanel();
        modePanel = new ModePanel();
        modePanel.setSimpleCommandsButtonActionListener(new SimpleCommandsButtonActionListener());
        modePanel.setCountingLoopButtonActionListener(new CountingLoopButtonActionListener());
        modePanel.setConditionsButtonActionListener(new ConditionsButtonActionListener());
        modePanel.setConditionalLoopButtonActionListener(new ConditionalLoopButtonActionListener());
        modePanel.setCustomCommandsButtonActionListener(new CustomCommandsButtonActionListener());

        simpleCommandsPanel = new SimpleCommandsPanel();
        simpleCommandsPanel.setSimpleCommands1ButtonActionListener(new SimpleCommands1ButtonActionListener());
        simpleCommandsPanel.setSimpleCommands2ButtonActionListener(new SimpleCommands2ButtonActionListener());
        simpleCommandsPanel.setSimpleCommands3ButtonActionListener(new SimpleCommands3ButtonActionListener());
        simpleCommandsPanel.setBackButtonActionListener(new BackButtonActionListener());

        countingLoopPanel = new CountingLoopPanel();
        countingLoopPanel.setCountingLoop1ButtonActionListener(new CountingLoop1ActionListener());
        countingLoopPanel.setCountingLoop2ButtonActionListener(new CountingLoop2ActionListener());
        countingLoopPanel.setCountingLoop3ButtonActionListener(new CountingLoop3ActionListener());
        countingLoopPanel.setCountingLoop4ButtonActionListener(new CountingLoop4ActionListener());
        countingLoopPanel.setBackButtonActionListener(new BackButtonActionListener());

        conditionsPanel = new ConditionsPanel();
        conditionsPanel.setConditions1ButtonActionListener(new ConditionsButton1ActionListener());
        conditionsPanel.setConditions2ButtonActionListener(new ConditionsButton2ActionListener());
        conditionsPanel.setConditions3ButtonActionListener(new ConditionsButton3ActionListener());
        conditionsPanel.setConditions4ButtonActionListener(new ConditionsButton4ActionListener());
        conditionsPanel.setBackButtonActionListener(new BackButtonActionListener());

        conditionalLoopPanel = new ConditionalLoopPanel();
        conditionalLoopPanel.setConditionalLoop1ButtonActionListener(new ConditionalLoopButton1ActionListener());
        conditionalLoopPanel.setConditionalLoop2ButtonActionListener(new ConditionalLoopButton2ActionListener());
        conditionalLoopPanel.setConditionalLoop3ButtonActionListener(new ConditionalLoopButton3ActionListener());
        conditionalLoopPanel.setConditionalLoop4ButtonActionListener(new ConditionalLoopButton4ActionListener());
        conditionalLoopPanel.setBackButtonActionListener(new BackButtonActionListener());

        customCommandsPanel = new CustomCommandsPanel();
        customCommandsPanel.setCustomCommands1ButtonActionListener(new CustomCommands1ButtonActionListener());
        customCommandsPanel.setCustomCommands2ButtonActionListener(new CustomCommands2ButtonActionListener());
        customCommandsPanel.setCustomCommands3ButtonActionListener(new CustomCommands3ButtonActionListener());
        customCommandsPanel.setCustomCommands4ButtonActionListener(new CustomCommands4ButtonActionListener());
        customCommandsPanel.setCustomCommands5ButtonActionListener(new CustomCommands5ButtonActionListener());
        customCommandsPanel.setCustomCommands6ButtonActionListener(new CustomCommands6ButtonActionListener());
        customCommandsPanel.setCustomCommands7ButtonActionListener(new CustomCommands7ButtonActionListener());
        customCommandsPanel.setCustomCommands8ButtonActionListener(new CustomCommands8ButtonActionListener());
        customCommandsPanel.setCustomCommands9ButtonActionListener(new CustomCommands9ButtonActionListener());
        customCommandsPanel.setCustomCommands10ButtonActionListener(new CustomCommands10ButtonActionListener());
        customCommandsPanel.setBackButtonActionListener(new BackButtonActionListener());

        gamePanel = new GamePanel(simulationPanel, difficulty);
        gamePanel.setCompileButtonActionListener(new CompileButtonActionListener());
        gamePanel.setRunButtonActionListener(new RunButtonActionListener());
        gamePanel.setStartButtonActionListener(new StartButtonActionListener());
        gamePanel.setRestoreMazeButtonActionListener(new RestoreMazeButtonActionListener());
        gamePanel.setAbortGameButtonActionListener(new AbortGameButtonActionListener());
        gamePanel.setNextButtonActionListener(new NextButtonActionListener());
    }

    private void designComponents() {

        setJMenuBar(menuBar);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        editMenu.add(difficultyMenu);
        helpMenu.add(manualMenuItem);
        difficultyMenu.add(beginnerRadioButtonMenuItem);
        difficultyMenu.add(advanceRadioButtonMenuItem);
        difficultyTypesGroup.add(beginnerRadioButtonMenuItem);
        difficultyTypesGroup.add(advanceRadioButtonMenuItem);

        getContentPane().setLayout(new CardLayout());
        getContentPane().add(modePanel, "modePanel");
        getContentPane().add(simpleCommandsPanel, "simpleCommandsPanel");
        getContentPane().add(countingLoopPanel, "countingLoopPanel");
        getContentPane().add(conditionsPanel, "conditionsPanel");
        getContentPane().add(conditionalLoopPanel, "conditionalLoopPanel");
        getContentPane().add(customCommandsPanel, "customCommandsPanel");
        getContentPane().add(gamePanel, "gamePanel");
        getContentPane().add(loadingPanel, "loadingPanel");
    }

    public void go() {

        if(janibot.getStep() < maxStep) {
            if (!checkWall()) {
                updateLocation();
                janibot.setStep(1);
                gamePanel.setStepLabel(janibot.getStep());
            } else {
                SoundEffect.MOVE.stop();
                janibot.stop();
                janibot.setExplode(true);
                simulationPanel.setGameState(SimulationPanel.GameState.GAME_OVER);
                gamePanel.setConsolePane("Error line " + getErrorLineNumber() + ": Ouch you crashed me!!!");
                SoundEffect.EXPLODE.play();
            }
        }else{
            SoundEffect.MOVE.stop();
            janibot.stop();
            simulationPanel.setGameState(SimulationPanel.GameState.GAME_OVER);
            gamePanel.setConsolePane("Too much steps!!!");
            SoundEffect.FAIL.play();
        }
    }

    public void pick() {
        switch (difficulty){
            case BEGINNER:{

                for (Block[] block : blocks) {
                    for (int j = 0; j < blocks[0].length; j++) {
                        if (janibot.getLocation().equals(block[j].getLocation())) {
                            if (block[j].hasDirt()) {
                                janibot.setSweep(true);
                                SoundEffect.SWEEP.play();
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                block[j].removeDirt();
                                janibot.setBag(1);
                                gamePanel.setBagLabel(janibot.getBag());
                                janibot.setSweep(false);
                            } else {
                                SoundEffect.MOVE.stop();
                                janibot.stop();
                                gamePanel.setConsolePane("Error line " + getErrorLineNumber() + ": No dirt to pick!!!");
                                SoundEffect.FAIL.play();
                            }
                        }
                    }
                }
                break;
            }
            case ADVANCE:{
                for (Block[] block : blocks) {
                    for (int j = 0; j < blocks[0].length; j++) {
                        if (janibot.getLocation().equals(block[j].getLocation())) {
                            if (block[j].hasDirt()) {
                                if(janibot.getBag() == bagCapacity){
                                    gamePanel.setConsolePane("Error line " + getErrorLineNumber() + ": bag is full");
                                    janibot.stop();
                                }else {
                                    janibot.setSweep(true);
                                    SoundEffect.SWEEP.play();
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    block[j].removeDirt();
                                    janibot.setBag(1);
                                    gamePanel.setBagLabel(janibot.getBag());
                                    janibot.setSweep(false);
                                }
                            } else {
                                SoundEffect.MOVE.stop();
                                janibot.stop();
                                gamePanel.setConsolePane("Error line " + getErrorLineNumber() + ": No dirt to pick!!!");
                                SoundEffect.FAIL.play();
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    public boolean dirt() {

        for (Block[] block : blocks) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (janibot.getLocation().equals(block[j].getLocation())) {
                    if (block[j].hasDirt()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean trashcan() {
        for (Block[] block : blocks) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (janibot.getLocation().equals(block[j].getLocation()) && block[j].hasTrashcan()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void put() {
        switch (difficulty){
            case BEGINNER:{
                for (int i = 0; i < blocks.length; i++) {
                    for (int j = 0; j < blocks[0].length; j++) {
                        if(janibot.getLocation().equals(blocks[i][j].getLocation())){
                            if(blocks[i][j].hasTrashcan()){
                                if(janibot.getBag() == maxDirt){
                                    SoundEffect.MOVE.stop();
                                    janibot.stop();
                                    gamePanel.revealResultPanel("Good Job!!!!", Integer.toString(janibot.getStep()), Integer.toString(janibot.getBag()), Integer.toString(countLine()), hrs + ":" + mins + ":" + sec + "." + millisec);
                                    SoundEffect.VICTORY.play();
                                    finished = true;
                                }else {
                                    SoundEffect.MOVE.stop();
                                    gamePanel.setConsolePane("You have forgotten some dirts!!!");
                                    SoundEffect.FAIL.play();
                                }
                            }else{
                                SoundEffect.MOVE.stop();
                                janibot.stop();
                                gamePanel.setConsolePane("Error line " + getErrorLineNumber() + ": No trashcan on the ground!!!");
                                SoundEffect.FAIL.play();
                            }
                        }
                    }
                }
                break;
            }
            case ADVANCE:{
                if(!janibot.empty()) {
                    for (int i = 0; i < blocks.length; i++) {
                        for (int j = 0; j < blocks[0].length; j++) {
                            if (janibot.getLocation().equals(blocks[i][j].getLocation())) {
                                if (blocks[i][j].hasTrashcan()) {
                                    if (janibot.getTotalDirtCollected() == maxDirt) {
                                        SoundEffect.MOVE.stop();
                                        janibot.stop();
                                        gamePanel.revealResultPanel("Good Job!!!!", Integer.toString(janibot.getStep()), Integer.toString(janibot.getTotalDirtCollected()), Integer.toString(countLine()), hrs + ":" + mins + ":" + sec + "." + millisec);
                                        SoundEffect.VICTORY.play();
                                        finished = true;
                                    } else {
                                        gamePanel.setConsolePane("Janibot throw " + janibot.getBag() + "of dirt(s).");
                                        gamePanel.setBagLabel(0);
                                        janibot.emptyBag();
                                    }
                                } else {
                                    SoundEffect.MOVE.stop();
                                    janibot.stop();
                                    gamePanel.setConsolePane("Error line " + getErrorLineNumber() + ": No trashcan on the ground!!!");
                                    SoundEffect.FAIL.play();
                                }
                            }
                        }
                    }
                }else {
                    SoundEffect.MOVE.stop();
                    janibot.stop();
                    gamePanel.setConsolePane("Error line " + getErrorLineNumber() + ": Bag is empty!!!");
                    SoundEffect.FAIL.play();
                }
                break;
            }
        }

    }

    public boolean checkWall() {

        Point janibotLocation = janibot.getLocation();
        Direction direction = janibot.getDirection();

        switch(direction){
            case NORTH:{
                if(janibotLocation.y > 0){
                    for (int i = 0; i < blocks.length; i++ ) {
                        for (int j = 0; j < blocks[0].length; j++) {
                            if((blocks[i][j].getLocation().equals(janibotLocation)) && (!blocks[i - 1][j].isSouthWall())){
                                return false;
                            }
                        }
                    }
                }
                break;
            }
            case EAST:{
                if(janibotLocation.x + 40 < SimulationPanel.CANVAS_WIDTH){
                    for (Block[] block : blocks) {
                        for (int j = 0; j < blocks[0].length; j++) {
                            if ((block[j].getLocation().equals(janibotLocation)) && (!block[j].isEastWall())) {
                                return false;
                            }
                        }
                    }
                }
                break;
            }
            case SOUTH:{
                if(janibotLocation.y + 40 < SimulationPanel.CANVAS_HEIGHT){
                    for (Block[] block : blocks) {
                        for (int j = 0; j < blocks[0].length; j++) {
                            if ((block[j].getLocation().equals(janibotLocation)) && (!block[j].isSouthWall())) {
                                return false;
                            }
                        }
                    }
                }
                break;
            }
            case WEST:{
                if(janibotLocation.x > 0){
                    for (Block[] block : blocks) {
                        for (int j = 0; j < blocks[0].length; j++) {
                            if ((block[j].getLocation().equals(janibotLocation)) && (!block[j - 1].isEastWall())) {
                                return false;
                            }
                        }
                    }
                }
                break;
            }
        }

        return true;
    }

    public void updateLocation() {

        Direction direction = janibot.getDirection();
        int speed = janibot.getSpeed();

        switch (direction) {

            case EAST: {

                int nextLocation = janibot.getLocation().x + 40;

                while (janibot.getLocation().x < nextLocation) {
                    janibot.getLocation().x++;
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            case WEST: {

                int nextLocation = janibot.getLocation().x - 40;

                while (janibot.getLocation().x > nextLocation) {
                    janibot.getLocation().x--;
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            case SOUTH: {
                int nextLocation = janibot.getLocation().y + 40;

                while (janibot.getLocation().y < nextLocation) {
                    janibot.getLocation().y++;

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                break;
            }
            case NORTH: {

                int nextLocation = janibot.getLocation().y - 40;

                while (janibot.getLocation().y > nextLocation) {
                    janibot.getLocation().y--;

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    private void changePanel(String name) {
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), name);
    }

    public Janibot getJanibot() {
        return janibot;
    }

    private Block[][] setupBlocks() {

        Block[][] blocks = new Block[12][15];
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks[0].length; col++) {
                blocks[row][col] = new Block(new Point(col, row));
            }
        }

        try {

            Scanner scanner = new Scanner(mapFile);

            while (scanner.hasNext()){
                String stringLine = scanner.nextLine();

                if(stringLine.contains("block end")){
                    break;
                }else{

                    stringLine = stringLine.replaceAll("\\s", "");

                    if(stringLine.contains("eastWall:")){

                        int row = Integer.parseInt(stringLine.substring(stringLine.indexOf("(") + 1, stringLine.indexOf(",")));
                        int col = Integer.parseInt(stringLine.substring(stringLine.indexOf(",") + 1, stringLine.indexOf(")")));

                        blocks[row][col].setEastWall(true);
                    }

                    else if(stringLine.contains("southWall:")){

                        int row = Integer.parseInt(stringLine.substring(stringLine.indexOf("(") + 1, stringLine.indexOf(",")));
                        int col = Integer.parseInt(stringLine.substring(stringLine.indexOf(",") + 1, stringLine.indexOf(")")));

                        blocks[row][col].setSouthWall(true);
                    }

                    else if(stringLine.contains("dirt:")){
                        int row = Integer.parseInt(stringLine.substring(stringLine.indexOf("(") + 1, stringLine.indexOf(",")));
                        int col = Integer.parseInt(stringLine.substring(stringLine.indexOf(",") + 1, stringLine.indexOf(")")));

                        blocks[row][col].setDirt(new Dirt(blocks[row][col].getLocation()));
                    }

                    else if(stringLine.contains("trashcan:")){
                        int row = Integer.parseInt(stringLine.substring(stringLine.indexOf("(") + 1, stringLine.indexOf(",")));
                        int col = Integer.parseInt(stringLine.substring(stringLine.indexOf(",") + 1, stringLine.indexOf(")")));
                        blocks[row][col].setTrashcan(new Trashcan(blocks[row][col].getLocation()));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return blocks;
    }

    private Janibot setUpJanibot() {

        janibot = null;

        try{

            URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(RobotDirectory).toURI().toURL()});
            janibot = (Janibot) classLoader.loadClass("Robot").newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {

            Scanner scanner = new Scanner(mapFile);
            while (scanner.hasNext()) {
                String stringLine = scanner.nextLine();

                if(stringLine.contains("janibot end")){
                    break;
                } else {
                    stringLine = stringLine.replaceAll("\\s", "");

                    if(stringLine.contains("location:")){

                        int x = Integer.parseInt(stringLine.substring(stringLine.indexOf("(") + 1, stringLine.indexOf(",")));
                        int y = Integer.parseInt(stringLine.substring(stringLine.indexOf(",") + 1, stringLine.indexOf(")")));

                        janibot.setLocation(new Point(x, y));
                        janibot.setProgram(this);

                    }else if(stringLine.contains("direction:")){

                        String direction = stringLine.substring(stringLine.indexOf(":") + 1);

                        if (direction.equals("north")) {
                            janibot.setDirection(Direction.NORTH);
                        } else if (direction.equals("east")) {
                            janibot.setDirection(Direction.EAST);
                        } else if (direction.equals("south")) {
                            janibot.setDirection(Direction.SOUTH);
                        } else if (direction.equals("west")) {
                            janibot.setDirection(Direction.WEST);
                        }

                    }
                }
            }

            return janibot;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return janibot;
     }

    public boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    private void resetTimer() {
        millisec = 0;
        sec = 0;
        mins = 0;
        hrs = 0;
    }

    public void programFinished() {
        if (!finished){
            String string = gamePanel.getConsolePane();
            gamePanel.setConsolePane(string + "\nUnfinished Job!!!");
        }

        gamePanel.setCompileButton(false);
    }

    public int countLine() {

        int count = 0;

        String[] strings = gamePanel.getCodeEditorPane().trim().split("\n");

        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replaceAll("\\s+", "");
        }

        for (int i = 0; i < strings.length; i++) {
            if(isWhitespace(strings[i])){
                continue;
            }else {
                //if (strings[i].contains("def")){
                //    break;
                //}else {
                    count++;
                //}
            }
        }

        return count;
    }

    public String getErrorLineNumber() {
        int line = 0;
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTraceElement1: stackTraceElement){
            if (stackTraceElement1.getClassName() == "Robot"){
                line = stackTraceElement1.getLineNumber();
                break;
            }
        }

        return Integer.toString((line - 5));
    }

    private void startLevelThread(final int maxStep, int maxDirt, int maxLine, int maxTime, int bagCapacity){

        this.maxStep = maxStep;
        this.maxDirt = maxDirt;
        this.maxLine = maxLine;
        this.maxTime = maxTime;
        this.bagCapacity = bagCapacity;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                loadingPanel.setProgressBar(30);

                blocks = setupBlocks();

                loadingPanel.setProgressBar(40);

                janibot = setUpJanibot();
                simulationPanel.setBlocks(blocks);
                simulationPanel.setJanibot(janibot);

                loadingPanel.setProgressBar(50);

                changePanel("gamePanel");
                simulationPanel.start();
            }
        });

        loadingPanel.setProgressBar(0);
        thread.start();
        gamePanel.revealUserPanel();
        changePanel("loadingPanel");
    }

    private class SimpleCommandsButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel("simpleCommandsPanel");
            gameMode = Mode.SIMPLE_COMMANDS;
        }
    }

    private class SimpleCommands1ButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "sc1");

            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Collect (3) dirts and quickly put the dirts into the trashcan.");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/garden/garden1.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/woodthinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/woodthinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("4", "3", "8", "2", "3");
                    startLevelThread(4, 3, 8, 2, 3);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 3, 9999, 3, 1);
                    gamePanel.setObjectivePanel("none", "3", "none", "3", "1");
                    break;
                }
            }
        }
    }

    private class SimpleCommands2ButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            mapFile = new File(mapDirectory + "sc2");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Janibot is practicing ballet moves by collecting a dirt that is positioned diagonally between him and the trashcan.");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/garden/garden2.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/woodthinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/woodthinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("4", "1", "10", "2", "1");
                    startLevelThread(9999, 3, 9999, 4, 1);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 1, 9999, 2, 1);
                    gamePanel.setObjectivePanel("none", "1", "none", "2", "1");
                    break;
                }
            }
            startLevelThread(4, 1, 10, 2, 1);
        }
    }

    private class SimpleCommands3ButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            mapFile = new File(mapDirectory + "sc3");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Janibot is training advanced kung-fu techniques. He needs to neutralize four enemy " +
                    "fighters (four dirts).");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/garden/garden3.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/woodthinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/woodthinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("14", "4", "25", "5", "4");
                    startLevelThread(14, 4, 25, 3, 4);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 4, 9999, 5, 2);
                    gamePanel.setObjectivePanel("none", "4", "none", "5", "2");
                    break;
                }
            }

        }
    }

    private class CountingLoopButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel("countingLoopPanel");
            gameMode = Mode.COUNTING_LOOP;
        }
    }

    private class CountingLoop1ActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cl1");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("The trashcan is ten steps away and there are no obstacles on the way. Below Janibot is a dirt, pick the dirt " +
                    "then Use the \"repeat\" command to throw the dirt into the trashcan.");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/desert/desert1.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/stonethinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/stonethinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("10", "1", "7", "3", "1");
                    startLevelThread(10, 1, 7, 3, 1);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 1, 9999, 3, 1);
                    gamePanel.setObjectivePanel("none", "1", "none", "3", "1");
                    break;
                }
            }


        }
    }

    private class CountingLoop2ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cl2");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Collect all 11 dirts!");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/desert/desert2.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/stonethinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/stonethinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("22", "11", "10", "5", "11");
                    startLevelThread(22, 11, 10, 5, 11);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 11, 9999, 10, 3);
                    gamePanel.setObjectivePanel("none", "11", "none", "10", "3");
                    break;
                }
            }


        }
    }

    private class CountingLoop3ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cl3");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Write a program that will help Janibot to collect all dirts.");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/desert/desert3.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/stonethinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/stonethinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("none", "60", "25", "15", "60");
                    startLevelThread(9999, 60, 25, 15, 60);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 60, 9999, 30, 10);
                    gamePanel.setObjectivePanel("none", "60", "none", "30", "10");
                    break;
                }
            }


        }
    }

    private class CountingLoop4ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cl4");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Janibot needs to collect all 46 dirts.");
            gamePanel.setIntroPane("Write a program that will help Janibot to collect all dirts.");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/desert/desert4.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/stonethinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/stonethinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("59", "46", "40", "15", "46");
                    startLevelThread(59, 46, 40, 15, 46);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 46, 9999, 30, 23);
                    gamePanel.setObjectivePanel("none", "46", "none", "30", "23");
                    break;
                }
            }


        }
    }

    private class ConditionsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel("conditionsPanel");
            gameMode = Mode.CONDITIONS;
        }
    }

    private class ConditionsButton1ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "c1");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Because of a hole in his pocket, Janibot just lost some dirts. They lie " +
                    "between him and the trashcan which is 10 steps away. Go back and collect all of them quickly!");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/mexico/mexico1.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/bonethinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/bonethinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("10", "5", "10", "3","5");
                    startLevelThread(10, 5, 10, 3, 5);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 5, 9999, 6, 1);
                    gamePanel.setObjectivePanel("none", "5", "none", "6", "1");
                    break;
                }
            }
        }
    }

    private class ConditionsButton2ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            mapFile = new File(mapDirectory + "c2");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Janibot is inside a library. His task is to collect all dirts inside the bookshelfs.");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/mexico/mexico2.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/bonethinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/bonethinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("32", "5", "20", "5", "5");
                    startLevelThread(32, 5, 20, 5, 5);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 5, 9999, 5, 1);
                    gamePanel.setObjectivePanel("none", "5", "none", "5", "1");
                    break;
                }
            }
        }
    }

    private class ConditionsButton3ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "c3");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("The trashcan is 11 steps straight ahead but he cannot just walk over there because of three " +
                    "impassable excavations which are in his way. Write a program for Janibot reach the trashcan " +
                    "collecting (7) dirts that he finds on the way!");

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("17", "7", "40", "15", "7");
                    startLevelThread(17, 7, 40, 5, 7);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 7, 9999, 30, 2);
                    gamePanel.setObjectivePanel("none", "7", "none", "30", "2");
                    break;
                }
            }


        }
    }

    private class ConditionsButton4ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "c4");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("Janibot is hunting for the legendary Treasure of the Pharaoh’s. He is almost there - one last " +
                    "deadly trap awaits him! He stands in complete darkness, at the cross-section of four tunnels. " +
                    "He is facing in the North direction. He knows that the West " +
                    "destroy him. Using the map of the pyramid that you can see, write a program for Janibot to collect " +
                    "all dirts and throw it all in the trashcan!");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/mexico/mexico3.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/bonethinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/bonethinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("30", "15", "11", "5", "15");
                    startLevelThread(30, 15, 11, 5, 15);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 15, 9999, 10, 4);
                    gamePanel.setObjectivePanel("none", "15", "none", "10", "4");
                    break;
                }
            }


        }
    }

    private class ConditionalLoopButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel("conditionalLoopPanel");
            gameMode = Mode.CONDITIONAL_LOOP;
        }
    }

    private class ConditionalLoopButton1ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "conl1");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Janibot is playing hide-and-seek with his friends. They left on the ground a chain of dirts that will " +
                    "help the robot find them. They gave Janibot the following instructions: He should walk straight " +
                    "ahead, and whenever he finds a dirt, he should turn left and walk straight ahead again. Write " +
                    "a program for Janibot to do this, and do not forget to collect all dirts on the way!");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/jungle/jungle1.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/logthinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/logthinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("none", "11", "7", "3", "11");
                    startLevelThread(9999, 11, 8, 3, 11);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 11, 9999, 6, 2);
                    gamePanel.setObjectivePanel("none", "11", "none", "6", "2");
                    break;
                }
            }


        }
    }

    private class ConditionalLoopButton2ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "conl2");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("Janibot stands next to a straight wall and faces the east direction. The wall is " +
                    "not touching the border of the maze. The trashcan is adjacent to the wall but on the " +
                    "other side. Write a program for the robot to get the dirt and put it in the trashcan!");

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("8", "1", "25", "15", "1");
                    startLevelThread(8, 1, 25, 15, 1);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 1, 9999, 15, 1);
                    gamePanel.setObjectivePanel("none", "1", "none", "15", "1");
                    break;
                }
            }


        }
    }

    private class ConditionalLoopButton3ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "conl3");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Janibot stands at the beginning of a spiral maze, facing South. The spiral is clock-wise " +
                    "oriented. Write a program for Janibot to get to his trashcan and collecting " +
                    "all dirts on the way!");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/jungle/jungle1.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/logthinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/logthinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("none", "46", "15", "3", "46");
                    startLevelThread(9999, 46, 15, 3, 46);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 46, 9999, 6, 15);
                    gamePanel.setObjectivePanel("none", "46", "none", "6", "15");
                    break;
                }
            }


        }
    }

    private class ConditionalLoopButton4ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "conl4");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("Janibot stands in the south-west corner of the maze, facing east. In front of him is a " +
                    "poles of different heights. Between some of them, dirt lie on the ground. Write a program for " +
                    "the robot to collect all dirt!");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/jungle/jungle1.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/logthinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/logthinwall_Horizontal.png"));


            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("none", "7", "25", "30", "7");
                    startLevelThread(9999, 7, 25, 30, 7);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 7, 9999, 60, 1);
                    gamePanel.setObjectivePanel("none", "7", "none", "60", "1");
                    break;
                }
            }


        }
    }

    private class CustomCommandsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel("customCommandsPanel");
            gameMode = Mode.CUSTOM_COMMANDS;
        }
    }

    private class CustomCommands1ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cc1");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("In this maze, dirts are distributed along the walls. " +
                    "The Trashcan is in the south-west corner, and the robot stands on the right of it, facing east. " +
                    "Define a your own method to collect all the dirts!");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/ship/roboshop2.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/chainthinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/chainthinwall_Horizontal.png"));


            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("49", "25", "20", "10", "25");
                    startLevelThread(49, 25, 20, 10, 25);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 25, 9999, 8, 7);
                    gamePanel.setObjectivePanel("none", "25", "none", "8", "7");
                    break;
                }
            }
        }
    }

    private class CustomCommands2ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cc2");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Write a program for Janibot to climb the staircase, Define a method for the " +
                    "robot to climb and collect all dirts then put it in the trashcan.");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/ship/ship1.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/chainthinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/chainthinwall_Horizontal.png"));

            switch (difficulty){
                case BEGINNER:{
                    gamePanel.setObjectivePanel("20", "9", "20", "5", "9");
                    startLevelThread(20, 9, 20, 5, 9);
                    break;
                }
                case ADVANCE:{
                    // step, dirt, line, time
                    startLevelThread(9999, 9, 9999, 10, 4);
                    gamePanel.setObjectivePanel("none", "9", "none", "10", "4");
                    break;
                }
            }


        }
    }

    private class CustomCommands3ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            mapFile = new File(mapDirectory + "cc3");
            gamePanel.initializeComponentState(mapFile);
            gamePanel.setIntroPane("Janibot is on a pirate ship! Write a program for the robot " +
                    "to collect all dirts and run to the trashcan before the pirates are back. " +
                    "Janibot needs to be extremely efficient to survive.");
            simulationPanel.setBackgroundImage(new ImageLoader().loadImage("/images/themes/ship/ship1.jpg"));
            simulationPanel.setEastWallImage(new ImageLoader().loadImage("/images/themes/walls/chainthinwall_Vertical.png"));
            simulationPanel.setSouthWallImage(new ImageLoader().loadImage("/images/themes/walls/chainthinwall_Horizontal.png"));

            switch (difficulty) {
                case BEGINNER: {
                    gamePanel.setObjectivePanel("none", "105", "60", "30", "105");
                    startLevelThread(9999, 105, 60, 30, 105);
                    break;
                }
                case ADVANCE: {
                    // step, dirt, line, time
                    startLevelThread(9999, 105, 9999, 60, 50);
                    gamePanel.setObjectivePanel("none", "105", "none", "60", "50");
                    break;
                }
            }

        }
    }

    private class CustomCommands4ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cc4");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("Define a custom command to collect one star of dirts, and use it four times!");

            //gamePanel.setObjectivePanel("none", "none", "none", "none");

            switch (difficulty){
                case BEGINNER:{
                    break;
                }
                case ADVANCE:{
                    break;
                }
            }

            startLevelThread(9999, 0, 9999, 5, 0);
        }
    }

    private class CustomCommands5ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cc5");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("Janibot needs to get some dirts from his friends who live close by. Write a program for the robot " +
                    "to get the dirt on the ground in the middle of each friend’s home, and then get back to his own " +
                    "house, and put the dirts at the trashcan! Define a command to get to the next house and get the dirt there. Repeat it four times.");

            //gamePanel.setObjectivePanel("none", "none", "none", "none");

            switch (difficulty){
                case BEGINNER:{
                    break;
                }
                case ADVANCE:{
                    break;
                }
            }

            startLevelThread(9999, 0, 9999, 5, 0);
        }
    }

    private class CustomCommands6ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cc6");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("Write a program for the robot to move a 6x6 square of dirts from the north-west corner to the " +
                    "south-east corner of the maze.");

            //gamePanel.setObjectivePanel("none", "none", "none", "none");

            switch (difficulty){
                case BEGINNER:{
                    break;
                }
                case ADVANCE:{
                    break;
                }
            }

            startLevelThread(9999, 0, 9999, 5, 0);
        }
    }

    private class CustomCommands7ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cc7");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("There are six cells and each contains several randomly distributed dirts. Write a program " +
                    "for Janibot to collect all of them!");

            //gamePanel.setObjectivePanel("none", "none", "none", "none");

            switch (difficulty){
                case BEGINNER:{
                    break;
                }
                case ADVANCE:{
                    break;
                }
            }

            startLevelThread(9999, 0, 9999, 5, 0);
        }
    }

    private class CustomCommands8ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cc8");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("Janibot stands below the south-west corner of a rectangular pool, facing east. The trashcan is " +
                    "right behind him. Write a program for the robot to get all dirts.");

            //gamePanel.setObjectivePanel("none", "none", "none", "none");

            switch (difficulty){
                case BEGINNER:{
                    break;
                }
                case ADVANCE:{
                    break;
                }
            }

            startLevelThread(9999, 0, 9999, 5, 0);
        }
    }

    private class CustomCommands9ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cc9");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("Write a program for Janibot to collect all dirts! Break the task into " +
                    "smaller ones, and define custom commands for these.");

            //gamePanel.setObjectivePanel("none", "none", "none", "none");

            switch (difficulty){
                case BEGINNER:{
                    break;
                }
                case ADVANCE:{
                    break;
                }
            }

            startLevelThread(9999, 0, 9999, 5, 0);
        }
    }

    private class CustomCommands10ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapFile = new File(mapDirectory + "cc10");
            gamePanel.initializeComponentState(mapFile);

            gamePanel.setIntroPane("Janibot is escaping from the Alcatraz prison! At the moment he is in an underground labyrinth " +
                    "with many tunnels but only one of them leads to his freedom. Start by picking the dirt below Janibot then go " +
                    "straight ahead to the nearest wall. Then use the right-hand rule to search the tunnels.");

            //gamePanel.setObjectivePanel("none", "none", "none", "none");

            switch (difficulty){
                case BEGINNER:{
                    break;
                }
                case ADVANCE:{
                    break;
                }
            }

            startLevelThread(9999, 0, 9999, 5, 0);
        }
    }

    private class CompileButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(!isWhitespace(gamePanel.getCodeEditorPane()) && compiler.syntaxAnalyzer(gamePanel.getCodeEditorPane())) {
                Thread compilingThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        compiler.codeGenerator(gamePanel.getCodeEditorPane());
                        timer.stop();
                        if (compiler.compile()) {
                            blocks = setupBlocks();
                            janibot = setUpJanibot();
                            simulationPanel.setBlocks(blocks);
                            simulationPanel.setJanibot(janibot);
                            gamePanel.setConsolePane(compiler.getMessage());
                            gamePanel.setRunButton(true);
                            timer.start();
                        } else {
                            gamePanel.setConsolePane(compiler.getMessage());
                            gamePanel.setRunButton(false);
                            timer.start();
                        }
                    }
                });
                compilingThread.start();
                gamePanel.setConsolePane("Compiling, please wait");
            }else {
                gamePanel.setConsolePane(compiler.getMessage());
                gamePanel.setRunButton(false);
            }
        }
    }

    private class RunButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(countLine() <= maxLine) {
                timer.stop();
                janibot.start();

                /*Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int line;
                        while (janibot.isRunning()) {
                            Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                            Thread[] threads = threadSet.toArray(new Thread[threadSet.size()]);

                            for (Thread thread: threads) {
                                StackTraceElement[] stackTraceElements = thread.getStackTrace();
                                for (StackTraceElement stackTraceElement: stackTraceElements){
                                    if (stackTraceElement.getClassName().equals("Robot")) {
                                        line = (stackTraceElement.getLineNumber() - 5);
                                        gamePanel.setConsolePane(Integer.toString(line));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
                thread.start();*/

                gamePanel.getRunButton().setEnabled(false);
                gamePanel.getCompileButton().setEnabled(true);

            }else
            {
                gamePanel.setConsolePane("Your Program is too long!!!!");
            }
        }
    }

    private class StartButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            SoundEffect.MOVE.play(Clip.LOOP_CONTINUOUSLY);
            gamePanel.setConsolePane("");
            gamePanel.revealCodeEditor();
            gamePanel.setCompileButton(true);
            simulationPanel.setGameState(SimulationPanel.GameState.RUNNING);
            timer.start();
            finished = false;
        }
    }

    private class RestoreMazeButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            janibot.stop();
            blocks = setupBlocks();
            janibot = setUpJanibot();
            simulationPanel.setBlocks(blocks);
            simulationPanel.setJanibot(janibot);
            gamePanel.setCodeEditorPane(true);
            gamePanel.initializeComponentState(gamePanel.getMapFile());
            gamePanel.revealUserPanel();
        }
    }

    private class AbortGameButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            SoundEffect.MOVE.stop();

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    timer.stop();
                    resetTimer();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    loadingPanel.setProgressBar(50);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    janibot.stop();

                    loadingPanel.setProgressBar(50);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    gamePanel.setCodeEditorPane("");
                    gamePanel.setCodeEditorPane(true);
                    changePanel("modePanel");
                }
            });

            loadingPanel.setProgressBar(0);
            thread.start();
            changePanel("loadingPanel");
        }
    }

    private class TimerActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(mins < maxTime) {
                if (simulationPanel.getGameState() == SimulationPanel.GameState.RUNNING) {
                    millisec++;

                    if (millisec > 9) {
                        millisec = 0;
                        sec++;
                    }

                    if (sec > 59) {
                        sec = 0;
                        mins++;
                    }

                    if (mins > 59) {
                        mins = 0;
                        hrs++;
                    }
                    gamePanel.setTimerLabel(hrs + ":" + mins + ":" + sec + "." + millisec);
                }
            }else{
                janibot.stop();
                simulationPanel.setGameState(SimulationPanel.GameState.GAME_OVER);
                gamePanel.setConsolePane("Times up!!!!!");
                gamePanel.setCodeEditorPane(false);
                resetTimer();
                SoundEffect.TIMESUP.play();
            }
        }
    }

    private class BackButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel("modePanel");
        }
    }

    private class NextButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (gameMode){
                case SIMPLE_COMMANDS: {
                    changePanel("simpleCommandsPanel");
                    break;
                }
                case COUNTING_LOOP: {
                    changePanel("countingLoopPanel");
                    break;
                }
                case CONDITIONS: {
                    changePanel("conditionsPanel");
                    break;
                }
                case CONDITIONAL_LOOP: {
                    changePanel("conditionalLoopPanel");
                    break;
                }
                case CUSTOM_COMMANDS: {
                    changePanel("customCommandsPanel");
                    break;
                }
            }
        }
    }

    private class AdvanceRadioButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            final JFrame frame = new JFrame();
            JLabel label = new JLabel("Applying Settings...");
            label.setFont(new Font("Arial", Font.BOLD, 22));
            frame.setUndecorated(true);
            frame.setAlwaysOnTop(true);
            frame.setBackground(Color.WHITE);
            frame.getContentPane().add(label);
            frame.getContentPane().setBackground(Color.white);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setVisible(true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    setVisible(false);
                    difficulty = Difficulty.ADVANCE;
                    getContentPane().remove(gamePanel);
                    gamePanel.setDifficulty(difficulty);
                    gamePanel.designComponents();
                    getContentPane().add(gamePanel, "gamePanel");
                    changePanel("modePanel");
                    setVisible(true);
                    frame.dispose();
                }
            }).start();
        }
    }

    private class BeginnerRadioButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            final JFrame frame = new JFrame();
            JLabel label = new JLabel("Applying Settings...");
            label.setFont(new Font("Arial", Font.BOLD, 22));
            frame.setUndecorated(true);
            frame.setAlwaysOnTop(true);
            frame.setBackground(Color.WHITE);
            frame.getContentPane().add(label);
            frame.getContentPane().setBackground(Color.white);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setVisible(true);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    setVisible(false);
                    difficulty = Difficulty.BEGINNER;
                    getContentPane().remove(gamePanel);
                    gamePanel.setDifficulty(difficulty);
                    gamePanel.designComponents();
                    getContentPane().add(gamePanel, "gamePanel");
                    changePanel("modePanel");
                    setVisible(true);
                    frame.dispose();
                }
            }).start();
        }
    }

    public static void main(String[] args) {

        System.setProperty("java.home", System.getProperty("java.home"));

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Program program = new Program("Janibot: The Janitor Robot");
                program.setResizable(false);
                program.pack();
                program.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                program.setLocationRelativeTo(null);
                program.setVisible(true);
            }
        });
    }
}