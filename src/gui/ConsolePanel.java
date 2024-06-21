package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import avionics.Sensor;
import avionics.SensorSimulator;

public class ConsolePanel extends JPanel {
    private Map<String, Sensor> sensors;
    private Map<Sensor, Boolean> isSensorPrinted = new HashMap<Sensor, Boolean>();
    ArrayList<String> sensorNames = new ArrayList<>();
    private JTextArea logTextArea = null;
    private Boolean sensorError = false;

    /*
     * Constructor for ConsolePanel.
     *
     * @param sensorSimulator used to access sensor data.
     */
    public ConsolePanel(SensorSimulator sensorSimulator) {

        assert sensorSimulator != null : "sensorSimulator is null";

        // Set layout of panel
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        setBorder(new LineBorder(Color.GRAY, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        sensors = sensorSimulator.getSensors();

        assert sensors != null : "sensors is null"; 

        // Put sensors in isSensorsPrinted with false value
        isSensorPrinted.put(sensors.get("airspeed"), false);
        isSensorPrinted.put(sensors.get("altitude"), false);
        isSensorPrinted.put(sensors.get("pitch"), false);
        isSensorPrinted.put(sensors.get("roll"), false);
        isSensorPrinted.put(sensors.get("yaw"), false);
        isSensorPrinted.put(sensors.get("thrust"), false);

        // Add sensor names into list
        sensorNames.add("airspeed");
        sensorNames.add("altitude");
        sensorNames.add("pitch");
        sensorNames.add("roll");
        sensorNames.add("yaw");
        sensorNames.add("thrust");

        // Create contents of ConsolePanel
        createHeader();
        logTextArea = createTextBox(gbc);
        createScrollBox(gbc, logTextArea);
    }

    /*
     * This method is responsible for adding a label to display the
     * console title at the top left corner of the ConsolePanel.
     * 
     * @return The JLabel for the console header.
     */
    private void createHeader() {
        JLabel consoleLabel = new JLabel("Console");
        consoleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        consoleLabel.setForeground(Color.BLACK);

        assert consoleLabel != null : "consoleLabel is null";

        add(consoleLabel);
    }

    /*
     * This method is responisble for creating the textbox where
     * the logs will be displayed.
     * 
     * @return The JTextArea for where the logs will be displayed
     */
    private JTextArea createTextBox(GridBagConstraints gbc) {
        JTextArea logTextArea = new JTextArea();
        logTextArea.setEditable(true);
        logTextArea.setBackground(Color.BLACK);
        logTextArea.setForeground(Color.GREEN);
        logTextArea.setFont(new Font("Arial", Font.PLAIN, 15));

        assert logTextArea != null : "logTextArea is null";

        return logTextArea;
    }

    /*
     * This method is responsible for adding a scroll box panel
     * where all the logs will be displayed.
     * 
     * @param gbc The styling of the scroll pane.
     * 
     * @param logTextArea The JTextArea for displayed in scroll pane.
     * 
     * @return The JScrollPane for scrolling through the text box.
     */
    private void createScrollBox(GridBagConstraints gbc, JTextArea logTextArea) {
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setPreferredSize(new Dimension(960, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        assert scrollPane != null : "scrollPane is null";
        assert logTextArea != null : "parameter logTextArea is null";

        add(scrollPane, gbc);
    }

    /*
     * This method is responsible for printing logs to the
     * logTextArea.
     *
     * @param log The log being printed to console.
     * @param logTextArea The JTextArea where you will print the log to.
     */
    private void printToConsole(String log, JTextArea logTextArea) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateTime = dateFormat.format(new Date());

        assert dateFormat != null : "dateFormat is null";
        assert logTextArea != null :  "parameter logTextArea is null";

        logTextArea.append(dateTime + " - " + log + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    /*
     * This method is responsible for continously checking
     * if there is a sensor faul and updating the console
     * if there is one detected.
     */
    public void updateConsole() {
        for (int i = 0; i < sensorNames.size(); i++) {
            Sensor currentSensor = sensors.get(sensorNames.get(i));
            String sensorFault = "Fault found in " + currentSensor.getName() + " sensor";
            String rangeFault = " --> Sensor is exceeding range";

            assert currentSensor != null: "null sensor";

            boolean isPrinted = isSensorPrinted.getOrDefault(currentSensor, false);

            if (!isPrinted) {
                if (currentSensor.isTooLow()) {
                    rangeFault = " --> Sensor detecting value lower than min";
                    isSensorPrinted.put(currentSensor, true);
                    printToConsole(sensorFault + rangeFault, logTextArea);
                } else if (currentSensor.isTooHigh()) {
                    rangeFault = " --> Sensor detecting value higher than max";
                    isSensorPrinted.put(currentSensor, true);
                    printToConsole(sensorFault + rangeFault, logTextArea);
                }
            } else if (currentSensor.isWithinRange() && isPrinted) {
                isSensorPrinted.put(currentSensor, false);
                String msg = currentSensor.getName() + " is now within range";
                printToConsole(msg, logTextArea);
            }
        }

        for (int i = 0; i < sensorNames.size(); i++) {
            Sensor currentSensor = sensors.get(sensorNames.get(i));

            assert currentSensor != null: "null sensor";

            if (!currentSensor.isWithinRange()) {
                sensorError = true;
                break;
            }

            sensorError = false;
        }
    }

    /*
     * Getter for sensor names.
     * 
     * @return ArrayList<String> list of sensor names.
     */
    public ArrayList<String> getSensorNames() {
        assert this.sensorNames instanceof ArrayList : "Incorrect data type";
        return this.sensorNames;
    }

    /*
     * Getter for sensor error. Used for debugging.
     * 
     * @return Boolean if error is detected
     */
    public Boolean getSensorError() {
        assert this.sensorError instanceof Boolean : "Incorrect data type";
        return this.sensorError;
    }
}