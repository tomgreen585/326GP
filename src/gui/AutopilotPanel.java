package gui;

import flightSystem.Autopilot;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;

import static org.junit.Assert.assertFalse;

import java.awt.Color;

import avionics.SensorSimulator;

public class AutopilotPanel extends JPanel {

    private SensorSimulator sensorSimulator; //sensor simulator object

    private Autopilot autopilot; //autopilot object
    private JButton engagedButton; //engage button
    private JButton disengageButton; //disengage button
    private JButton overrideButton; //override button
    private JButton submitButton; //submit button
    private JButton badDataButton; //bad data button
    private JFormattedTextField minSpeedInput; //min speed input field
    private JFormattedTextField maxSpeedInput; //max speed input field
    private JFormattedTextField minAltitudeInput; //min altitude input field
    private JFormattedTextField maxAltitudeInput; //max altitude input field
    
    /**
     * Creates a new AutopilotPanel object.
     *
     * @param s the SensorSimulator object used for communication with the sensors.
     */
    public AutopilotPanel(SensorSimulator s) {
        assert s != null : "SensorSimulator object must not be null";

        this.sensorSimulator = s; //set sensor simulator object
        setupAutopilot(); //setup autopilot
        setup(); //setup autopilot panel
        s.getSensors(); //get sensors
    }

    /**
     * Sets up the autopilot by creating a new Autopilot object.
     */
    private void setupAutopilot() { 
        try {
            this.autopilot = new Autopilot(); // Potentially complex initialization
            assert this.autopilot != null : "Autopilot should not be null after initialization";
        } catch (Exception e) {
            throw new RuntimeException("Initialization of Autopilot failed", e);
        }
    }
    
    /**
     * Sets up the autopilot panel by creating the autopilot heading, buttons, indicator names, and inputs.
     */
    private void setup() {
        try {
            setupHeading(); // Setup autopilot heading
            setupButtons(); // Setup autopilot buttons
            setupIndicatorNames(); // Setup indicator names
            setupInputs(); // Setup inputs for autopilot
        } catch (RuntimeException e) {
            throw new RuntimeException("Setup failed", e);
        }
    }
    

    /**
     * Sets up the heading of the AutopilotPanel.
     * This method sets the layout for positioning, creates an autopilot label,
     * sets the position and font of the label, and adds the label to the panel.
     */
    private void setupHeading(){
        setLayout(null); //set layout for positioning
        JLabel autoPilot = new JLabel("AutoPilot"); //create autopilot label
        assert autoPilot != null : "AutoPilot label should not be null";
        
        autoPilot.setBounds(80, 0, 70, 20); //set position of label
        autoPilot.setFont(new Font("Calibri", Font.BOLD, 13)); //set font of label
        add(autoPilot); //add label to panel
    }

    /**
     * Sets up the buttons in the AutopilotPanel.
     * This method creates and configures the engage, disengage, and override buttons,
     * sets their positions and fonts, and adds them to the panel.
     * It also sets the initial enabled/disabled state of the buttons and attaches
     * action listeners to handle button clicks.
     */
    private void setupButtons() {
        setLayout(null); //set layout for positioning
        
        engagedButton = new JButton("Engage"); //create engage button
        disengageButton = new JButton("Disengage"); //create disengage button
        overrideButton = new JButton("Override"); //create override button

        assert engagedButton != null : "Engage button should not be null";
        assert disengageButton != null : "Disengage button should not be null";
        assert overrideButton != null : "Override button should not be null";

        engagedButton.setFont(new Font("Calibri", Font.PLAIN, 10)); //set font of engage button
        disengageButton.setFont(new Font("Calibri", Font.PLAIN, 10)); //set font of disengage button
        overrideButton.setFont(new Font("Calibri", Font.PLAIN, 10)); //set font of override button
    
        engagedButton.setBounds(130, 150, 80, 20); //set position of engage button
        disengageButton.setBounds(130, 170, 80, 20); //set position of disengage button
        overrideButton.setBounds(130, 190, 80, 20); //set position of override button
    
        engagedButton.setEnabled(false); //disable engage button
        disengageButton.setEnabled(false); //disable disengage button
        overrideButton.setEnabled(false); //disable override button

        engagedButton.addActionListener(e -> {
            assert !autopilot.getEngaged() : "Autopilot should not already be engaged";
            autopilot.engage(); // engage autopilot
            assert autopilot.getEngaged() : "Autopilot should be engaged after pressing the engage button";
            repaint();
        });
        
        disengageButton.addActionListener(e -> {
            assert autopilot.getEngaged() : "Autopilot should be engaged before it can be disengaged";
            autopilot.disengage(); // disengage autopilot
            assert !autopilot.getEngaged() : "Autopilot should be disengaged after pressing the disengage button";
            repaint();
        });
        
        overrideButton.addActionListener(e -> {
            assert autopilot.getEngaged() || autopilot.getDisabled() : "Autopilot should be in a known state before override";
            // Disable buttons to ensure user cannot engage/disengage during override
            engagedButton.setEnabled(false);
            disengageButton.setEnabled(false);
            overrideButton.setEnabled(false);
            submitButton.setEnabled(true);
            badDataButton.setEnabled(true);
        
            minSpeedInput.setEnabled(true);
            maxSpeedInput.setEnabled(true);
            minAltitudeInput.setEnabled(true);
            maxAltitudeInput.setEnabled(true);
        
            autopilot.override(); // override autopilot
            assert autopilot.getOverridden() : "Autopilot should be overridden after pressing the override button";
            repaint();
        });
    
        add(engagedButton); //add engage button to panel
        add(disengageButton); //add disengage button to panel
        add(overrideButton); //add override button to panel
    }
    
    /*
     * Sets up the indicator names for the AutopilotPanel.
     * This method creates labels for the engaged, disengaged, and fault indicators,
     * sets their positions, and adds them to the panel.
     */
    private void setupIndicatorNames() {
        setLayout(null);

        JLabel engage = new JLabel("Engaged"); //create engaged label
        JLabel disengage = new JLabel("Disengaged"); //create disengaged label
        JLabel fault = new JLabel("Fault"); //create fault label

        assert engage != null : "Engaged label should not be null";
        assert disengage != null : "Disengaged label should not be null";
        assert fault != null : "Fault label should not be null";

        engage.setBounds(5,150,53,20); //set position of engaged label
        disengage.setBounds(5, 170, 75, 20); //set position of disengaged label
        fault.setBounds(5, 190, 53, 20); //set position of fault label
        
        add(engage); //add engaged label to panel
        add(disengage); //add disengaged label to panel
        add(fault); //add fault label to panel
    }

    /*
     * Sets up the inputs for the AutopilotPanel.
     * This method creates labels for the speed, altitude calls method to setup.
     * Create inputs for the speed and altitude, and calls methods to setup.
     * Create labels for the min/max values, and calls method to setup.
     * Create submit and bad data buttons, and calls method to setup.
     */
    private void setupInputs(){
        //setup labels for inputs
        JLabel speedLabel = new JLabel("Speed"); //create speed label
        JLabel altitudeLabel = new JLabel("Altitude"); //create altitude label
        assert speedLabel != null && altitudeLabel != null : "Speed and altitude labels should not be null";

        setupHeaders(speedLabel, altitudeLabel); //setup headers for inputs
        
        //make text field to input speeds
        minSpeedInput = new JFormattedTextField(); //create min speed input field
        maxSpeedInput = new JFormattedTextField(); //create max speed input field
        minAltitudeInput = new JFormattedTextField(); //create min altitude input field
        maxAltitudeInput = new JFormattedTextField(); //create max altitude input field
        setupSpeedInputs(minSpeedInput, maxSpeedInput); //setup speed inputs
        setupAltitudeInputs(minAltitudeInput, maxAltitudeInput); //setup altitude inputs

        //setup min/max labels for inputs
        JLabel speedMin = new JLabel("Min"); //create min speed label
        JLabel speedMax = new JLabel("Max"); //create max speed label
        JLabel altMin = new JLabel("Min"); //create min altitude label
        JLabel altMax = new JLabel("Max"); //create max altitude label
        assert speedMin != null : "Min speed label should not be null";
        assert speedMax != null : "Max speed label should not be null";
        assert altMin != null : "Min altitude label should not be null";
        assert altMax != null : "Max altitude label should not be null";

        setupMinMax(speedMin, speedMax, altMin, altMax); //setup min/max labels

        submitButton = new JButton("Submit"); //create submit button
        badDataButton = new JButton("Start Fault"); //create start fault button
        setupSubmit(submitButton); //setup submit buttons
        setupBadData(badDataButton); //setup bad data button
    }

    /**
     * Sets up the headers for the AutopilotPanel.
     * 
     * @param speedLabel    the label for displaying the speed
     * @param altitudeLabel the label for displaying the altitude
     */
    private void setupHeaders(JLabel speedLabel, JLabel altitudeLabel){
        assert speedLabel != null : "Speed label should not be null";
        assert altitudeLabel != null : "Altitude label should not be null";
        
        speedLabel.setBounds(10, 25, 60, 20); //set position of speed label
        altitudeLabel.setBounds(10, 85, 60, 20); //set position of altitude label

        speedLabel.setFont(new Font("Calibri", Font.BOLD, 13)); //set font of speed label
        altitudeLabel.setFont(new Font("Calibri", Font.BOLD, 13)); //set font of altitude label

        add(speedLabel); //add speed label to panel
        add(altitudeLabel); //add altitude label to panel
    }

    /**
     * Sets up the speed inputs in the AutopilotPanel.
     * 
     * @param minSpeedInput the JFormattedTextField for the minimum speed input
     * @param maxSpeedInput the JFormattedTextField for the maximum speed input
     */
    private void setupSpeedInputs(JFormattedTextField minSpeedInput, JFormattedTextField maxSpeedInput){
        assert minSpeedInput != null : "Min speed input should not be null";
        assert maxSpeedInput != null : "Max speed input should not be null";

        minSpeedInput.setValue(200); //set value of min speed input
        maxSpeedInput.setValue(400); //set value of max speed input
        assert minSpeedInput.getValue() != null : "Min speed input value should not be null";
        assert maxSpeedInput.getValue() != null : "Max speed input value should not be null";

        minSpeedInput.setBounds(60, 45, 50, 20); //set position of min speed input
        maxSpeedInput.setBounds(60, 65, 50, 20); //set position of max speed input
        minSpeedInput.setBorder(new LineBorder(Color.BLACK)); //set border of min speed input
        maxSpeedInput.setBorder(new LineBorder(Color.BLACK)); //set border of max speed input
        
        minSpeedInput.setEnabled(true); //enable min speed input
        maxSpeedInput.setEnabled(true); //enable max speed input

        add(minSpeedInput); //add min speed input to panel
        add(maxSpeedInput); //add max speed input to panel
    }

    /**
     * Sets up the altitude inputs in the AutopilotPanel.
     * 
     * @param minAltitudeInput the JFormattedTextField for the minimum altitude input
     * @param maxAltitudeInput the JFormattedTextField for the maximum altitude input
     */
    private void setupAltitudeInputs(JFormattedTextField minAltitudeInput, JFormattedTextField maxAltitudeInput){
        assert minAltitudeInput != null : "Min altitude input should not be null";
        assert maxAltitudeInput != null : "Max altitude input should not be null";
       
        minAltitudeInput.setValue(2000); //set value of min altitude input
        maxAltitudeInput.setValue(23000); //set value of max altitude input
        assert minAltitudeInput.getValue() != null : "Min altitude input value should not be null";
        assert maxAltitudeInput.getValue() != null : "Max altitude input value should not be null";

        minAltitudeInput.setBounds(60, 105, 50, 20); //set position of min altitude input
        maxAltitudeInput.setBounds(60, 125, 50, 20); //set position of max altitude input
        minAltitudeInput.setBorder(new LineBorder(Color.BLACK)); //set border of min altitude input
        maxAltitudeInput.setBorder(new LineBorder(Color.BLACK)); //set border of max altitude input
        
        minAltitudeInput.setEnabled(true); //enable min altitude input
        maxAltitudeInput.setEnabled(true); //enable max altitude input

        add(minAltitudeInput); //add min altitude input to panel
        add(maxAltitudeInput); //add max altitude input to panel
    }

    /**
     * Sets up the min/max labels for the inputs in the AutopilotPanel.
     * 
     * @param speedMin the JLabel for the minimum speed
     * @param speedMax the JLabel for the maximum speed
     * @param altMin the JLabel for the minimum altitude
     * @param altMax the JLabel for the maximum altitude
     */
    private void setupMinMax(JLabel speedMin, JLabel speedMax, JLabel altMin, JLabel altMax){
        assert speedMin != null : "Min speed label should not be null";
        assert speedMax != null : "Max speed label should not be null";
        assert altMin != null : "Min altitude label should not be null";
        assert altMax != null : "Max altitude label should not be null";

        speedMin.setBounds(10, 40, 30, 30); //set position of min speed label
        speedMax.setBounds(10, 60, 30, 30); //set position of max speed label
        altMin.setBounds(10, 100, 30, 30); //set position of min altitude label
        altMax.setBounds(10, 120, 30, 30); //set position of max altitude label
        add(speedMin); //add min speed label
        add(speedMax); //add max speed label
        add(altMin); //add min altitude label
        add(altMax); //add max altitude label
    }

    /**
     * Sets up the submit button and bad data button in the AutopilotPanel.
     * 
     * @param submitButton The submit button to be set up.
     * @param badDataButton The bad data button to be set up.
     */
    private void setupSubmit(JButton submitButton) {
        assert submitButton != null : "Submit button should not be null";

        submitButton.setFont(new Font("Calibri", Font.PLAIN, 10)); // Set font of submit button
        submitButton.setBounds(130, 105, 80, 20); // Set position of submit button

        submitButton.addActionListener(e -> {
            autopilot.setMinSpeed(((Number) minSpeedInput.getValue()).intValue()); //set min speed
            autopilot.setMaxSpeed(((Number) maxSpeedInput.getValue()).intValue()); //set max speed
            autopilot.setMinAltitude(((Number) minAltitudeInput.getValue()).intValue()); //set min altitude
            autopilot.setMaxAltitude(((Number) maxAltitudeInput.getValue()).intValue()); //set max altitude

            if (autopilot.getMinSpeed() < 40 || autopilot.getMaxSpeed() > 453 
            || autopilot.getMinAltitude() < 1000 || autopilot.getMaxAltitude() > 37000) { //check if min/max values are out of range
                assertFalse("Min and Max Out of Range", autopilot.getMinSpeed() < 40 
                || autopilot.getMaxSpeed() > 453 || autopilot.getMinAltitude() < 1000 || autopilot.getMaxAltitude() > 37000); //assert if min/max values are out of range
            } else {
                System.out.println("HERE");
                sensorSimulator.getSensors().get("altitude").setMinMax(autopilot.getMinAltitude(), autopilot.getMaxAltitude()); //set min/max altitude
                sensorSimulator.getSensors().get("airspeed").setMinMax(autopilot.getMinSpeed(), autopilot.getMaxSpeed()); //set min/max speed

                engagedButton.setEnabled(true); // Enable engage button
                disengageButton.setEnabled(true); // Enable disengage button
                overrideButton.setEnabled(true); // Enable override button
                submitButton.setEnabled(false); // Disable submit button
                badDataButton.setEnabled(false); // Disable bad data button

                minSpeedInput.setEnabled(false); // Disable min speed input
                maxSpeedInput.setEnabled(false); // Disable max speed input
                minAltitudeInput.setEnabled(false); // Disable min altitude input
                maxAltitudeInput.setEnabled(false); // Disable max altitude input

                autopilot.submit(); // Call autopilot submit method
                repaint(); // Repaint panel
            }
        });

        add(submitButton); // Add submit button
    }

    /**
     * Sets up the bad data button in the AutopilotPanel.
     * 
     * @param badDataButton The bad data button to be set up.
     */
    public void setupBadData(JButton badDataButton) {
        assert badDataButton != null : "Bad data button should not be null";

        badDataButton.setFont(new Font("Calibri", Font.PLAIN, 10)); //set font of bad data button
        badDataButton.setBounds(130, 125, 80, 20); //set position of bad data button

        badDataButton.addActionListener(e -> {
            engagedButton.setEnabled(true); //enable engage button
            disengageButton.setEnabled(true); //enable disengage button
            overrideButton.setEnabled(true); //enable override button
            submitButton.setEnabled(false); //disable submit button
            badDataButton.setEnabled(false);

            minSpeedInput.setEnabled(false); //disable min speed input
            maxSpeedInput.setEnabled(false); //disable max speed input
            minAltitudeInput.setEnabled(false); //disable min altitude input
            maxAltitudeInput.setEnabled(false); //disable max altitude input

            autopilot.setMinAltitude((int) minAltitudeInput.getValue()); //set min altitude
            autopilot.setMaxAltitude((int) maxAltitudeInput.getValue()); //set max altitude
            autopilot.setMinSpeed((int) minSpeedInput.getValue()); //set min speed
            autopilot.setMaxSpeed((int) maxSpeedInput.getValue()); //set max speed

            //assert if null
            assert minSpeedInput.getValue() != null : "Min speed input value should not be null";
            assert maxSpeedInput.getValue() != null : "Max speed input value should not be null";
            assert minAltitudeInput.getValue() != null : "Min altitude input value should not be null";
            assert maxAltitudeInput.getValue() != null : "Max altitude input value should not be null";

            sensorSimulator.getSensors().get("altitude").setBadMinMax(autopilot.getMinAltitude(), autopilot.getMaxAltitude()); //set min/max altitude
            sensorSimulator.getSensors().get("airspeed").setBadMinMax(autopilot.getMinSpeed(), autopilot.getMaxSpeed()); //set min/max speed

            autopilot.badData(); //start bad data
            repaint(); //repaint panel
        });

        add(badDataButton); //add bad data button
    }
    
    /**
     * Sets up the first separator line in the AutopilotPanel.
     * This method draws a horizontal line at the top of the panel.
     *
     * @param g the Graphics object used for drawing
     */
    private void setupFirstSeperator(Graphics g){
        assert g != null : "Graphics context must not be null";

        g.setColor(Color.BLACK); //set color of line
        g.drawLine(0, 20, 280, 20); //draw line
    }
    
    /**
     * Draws a third separator line on the AutopilotPanel.
     * The line is drawn horizontally at the y-coordinate 150.
     *
     * @param g the Graphics object used for drawing
     */
    private void drawThirdSeperator(Graphics g){
        assert g != null : "Graphics context must not be null";

        g.setColor(Color.BLACK); //set color of line
        g.drawLine(0, 150, 280, 150); //draw line
    }

    /**
     * Sets up and displays the engage indicator on the AutopilotPanel.
     * The engage indicator is a rectangle that represents the current engagement state of the autopilot.
     * If the autopilot is engaged, the rectangle is filled with green color, otherwise it is filled with red color.
     *
     * @param g the Graphics object used for drawing the engage indicator
     */
    private void setupEngageInd(Graphics g) {
        assert autopilot != null : "Autopilot object must not be null";
        assert g != null : "Graphics context must not be null";
        
        g.setColor(Color.BLACK); //set border color of engaged indicator
        g.drawRect(90, 150, 20, 20); //draw rectangle
        boolean isEngaged = autopilot.getEngaged(); //get engagement status
        g.setColor(isEngaged ? Color.GREEN : Color.RED); //set color of engaged indicator
        g.fillRect(90, 150, 20, 20); //fill rectangle
        Color indicator = isEngaged ? Color.GREEN : Color.RED; //set color of indicator
        assert g.getColor().equals(indicator) : "Indicator color does not match the engagement status";
    }

    /**
     * Sets up and displays the disable indicator on the AutopilotPanel.
     * The disable indicator is a rectangle that changes color based on the state of the autopilot.
     *
     * @param g the Graphics object used for drawing
     */
    private void setupDisableInd(Graphics g) {
        assert autopilot != null : "Autopilot object must not be null";
        assert g != null : "Graphics context must not be null";

        g.setColor(Color.BLACK); //set border color of disabled indicator
        g.drawRect(90, 170, 20, 20); //draw rectangle
        boolean isDisabled = autopilot.getDisabled(); //get disabled status
        boolean isOverridden = autopilot.getOverridden(); //get overridden status
        g.setColor(isDisabled || isOverridden ? Color.GREEN : Color.RED); //set color of disabled indicator
        g.fillRect(90, 170, 20, 20); //fill rectangle
        Color indicator = isDisabled || isOverridden ? Color.GREEN : Color.RED; //set color of indicator
        assert g.getColor().equals(indicator) : "Indicator color does not match the disengagement status";
    }    

    /**
     * Sets up and displays the override indicator on the AutopilotPanel.
     * The override indicator is a rectangle that represents the status of the autopilot's fault.
     * If the autopilot has a fault, the rectangle is filled with green color, otherwise it is filled with red color.
     *
     * @param g the Graphics object used for drawing the override indicator
     */
    private void setupOverrideInd(Graphics g) {
        assert autopilot != null : "Autopilot object must not be null";
        assert g != null : "Graphics context must not be null";

        g.setColor(Color.BLACK); //set border color of indicator
        g.drawRect(90, 190, 20, 20); //draw rectangle
        boolean isFault = autopilot.getBadData(); //get disabled status
        g.setColor(isFault ? Color.GREEN : Color.RED); //set color of fault indicator
        g.fillRect(90, 190, 20, 20); //fill rectangle
        Color indicator = isFault ? Color.GREEN : Color.RED; //set color of indicator
        assert g.getColor().equals(indicator) : "Indicator color does not match the fault status";
    } 

    /**
     * Overrides the paintComponent method of the JPanel class to customize the appearance of the AutopilotPanel.
     * This method is responsible for painting the engage indicator, disable indicator, override indicator, and separators.
     *
     * @param g the Graphics object used for painting
     */
    protected void paintComponent(Graphics g) {
        assert g != null : "Graphics context must not be null";

        super.paintComponent(g);
        setupEngageInd(g);
        setupDisableInd(g);
        setupOverrideInd(g);
        setupFirstSeperator(g);
        drawThirdSeperator(g);
    }

    /**
     * Updates the AutopilotPanel by repainting it.
     *
     * @param e the ActionEvent that triggered the update
     */
    public void update(ActionEvent e) {
        assert e != null : "ActionEvent must not be null";

        repaint();
    }
}
