package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

import avionics.SensorSimulator;

/**
 * The main application class that initializes and manages the GUI components.
 */
public class MainApplication extends JFrame {

    private static final int FRAME_WIDTH = 16 * 70;
    private static final int FRAME_HEIGHT = 9 * 70;

    private static final int FRAME_WIDTH_RATIO = FRAME_WIDTH / 100;
    private static final int FRAME_HEIGHT_RATIO = FRAME_HEIGHT / 100;

    private static final int UPDATE_PERIOD = 40; // Milliseconds between updates

    private PlaneOnMapPanel planeOnMapPanel;
    private SensorDataPanel sensorDataPanel;
    private ManagementPanel managementPanel;
    private AutopilotPanel autopilotPanel;
    private ConsolePanel consolePanel;
    private HazardPanel hazardPanel;
    private static SensorSimulator sensorSimulator = new SensorSimulator();

    /**
     * Constructs the main application frame.
     */
    public MainApplication() {
        initGUI();
        startTimer();
    }

    /**
     * Initializes the GUI components.
     */
    private void initGUI() {        
        planeOnMapPanel = new PlaneOnMapPanel();
        sensorDataPanel = new SensorDataPanel();
        managementPanel = new ManagementPanel(sensorSimulator, planeOnMapPanel);
        autopilotPanel = new AutopilotPanel(sensorSimulator);
        consolePanel = new ConsolePanel(sensorSimulator);
        hazardPanel = new HazardPanel(sensorSimulator);

        assert planeOnMapPanel != null && sensorDataPanel != null && managementPanel != null && autopilotPanel != null
                && consolePanel != null && hazardPanel != null : "GUI components are not initialized";

        setTitle("Main Application with Controls");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = addAllPanels();
        assert mainPanel != null : "Main panel is not initialized";
        add(mainPanel);
    }

    /**
     * Starts the timer for updating the data panels at regular intervals.
     */
    private void startTimer() {
        Timer timer = new Timer(UPDATE_PERIOD, this::update);
        assert timer != null : "Timer is not initialized";
        timer.start();
    }

    /**
     * Updates the data panels.
     * 
     * @param e the action event
     */
    private void update(ActionEvent e) {
        planeOnMapPanel.update(e);
        assert planeOnMapPanel != null : "Plane on map panel is not initialized";
        sensorDataPanel.updateData();
        assert sensorDataPanel != null : "Sensor data panel is not initialized";
        consolePanel.updateConsole();
        assert consolePanel != null : "Console panel is not initialized";
        hazardPanel.update();
        assert hazardPanel != null : "Hazard panel is not initialized";
    }

    /**
     * Adds all panels to the main panel.
     * 
     * @return the main panel containing all subpanels
     */
    private JPanel addAllPanels() {
        JPanel mainPanel = new JPanel(null);

        Dimension sensorDataPanelSize = new Dimension(FRAME_WIDTH_RATIO * 20, FRAME_HEIGHT_RATIO * 65);
        Dimension planeOnMapPanelSize = new Dimension(FRAME_WIDTH_RATIO * 60, FRAME_HEIGHT_RATIO * 65);
        Dimension managementPanelSize = new Dimension(FRAME_WIDTH_RATIO * 20, FRAME_HEIGHT_RATIO * 65);
        Dimension autopilotPanelSize = new Dimension(FRAME_WIDTH_RATIO * 20, FRAME_HEIGHT_RATIO * 35);
        Dimension consolePanelSize = new Dimension(FRAME_WIDTH_RATIO * 60, FRAME_HEIGHT_RATIO * 35);
        Dimension hazardPanelSize = new Dimension(FRAME_WIDTH_RATIO * 20, FRAME_HEIGHT_RATIO * 35);
        Color panelColor = Color.WHITE;
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        assert sensorDataPanelSize != null && planeOnMapPanelSize != null && managementPanelSize != null
                && autopilotPanelSize != null && consolePanelSize != null && hazardPanelSize != null
                && panelColor != null && lineBorder != null : "Panel dimensions, color, or border is not initialized";
        assert panelColor != null : "Panel color is not initialized";
        assert lineBorder != null : "Line border is not initialized";

        sensorDataPanel.setBounds(0, 0, sensorDataPanelSize.width, sensorDataPanelSize.height);
        sensorDataPanel.setBackground(panelColor);
        sensorDataPanel.setBorder(lineBorder);

        planeOnMapPanel.setBounds(sensorDataPanelSize.width, 0, planeOnMapPanelSize.width, planeOnMapPanelSize.height);
        planeOnMapPanel.setBackground(panelColor);
        planeOnMapPanel.setBorder(lineBorder);

        managementPanel.setBounds(sensorDataPanelSize.width + planeOnMapPanelSize.width, 0, managementPanelSize.width,
                managementPanelSize.height);
        managementPanel.setBackground(panelColor);
        managementPanel.setBorder(lineBorder);

        autopilotPanel.setBounds(0, sensorDataPanelSize.height, autopilotPanelSize.width, autopilotPanelSize.height);
        autopilotPanel.setBackground(panelColor);
        autopilotPanel.setBorder(lineBorder);

        consolePanel.setBounds(autopilotPanelSize.width, planeOnMapPanelSize.height, consolePanelSize.width,
                consolePanelSize.height);
        consolePanel.setBackground(panelColor);
        consolePanel.setBorder(lineBorder);

        hazardPanel.setBounds(sensorDataPanelSize.width + consolePanelSize.width, managementPanelSize.height,
                hazardPanelSize.width, hazardPanelSize.height);
        hazardPanel.setBackground(panelColor);
        hazardPanel.setBorder(lineBorder);

        mainPanel.add(sensorDataPanel);
        mainPanel.add(planeOnMapPanel);
        mainPanel.add(managementPanel);
        mainPanel.add(autopilotPanel);
        mainPanel.add(consolePanel);
        mainPanel.add(hazardPanel);

        return mainPanel;
    }

    /**
     * The entry point of the application.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        
        Thread sensorSimulatorThread = new Thread(sensorSimulator);
        sensorSimulatorThread.start();

        SwingUtilities.invokeLater(() -> {
            MainApplication frame = new MainApplication();
            frame.setVisible(true);
        });

        try {
            sensorSimulatorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}