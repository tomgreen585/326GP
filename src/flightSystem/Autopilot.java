package flightSystem;

public class Autopilot {

    private boolean engaged = false; //engaged indicator
    private boolean disabled = true; //disengaged indicator
    private boolean overridden = false; //overridden button
    private boolean fault = false; //fault indicator
    private boolean submit = true; //submit button
    private boolean badData = false; //bad data indicator
    private boolean input = true; //input field true or false

    private int minSpeed = 0; //minimum speed
    private int maxSpeed = 0; //maximum speed
    private int minAltitude = 0; //minimum altitude
    private int maxAltitude = 0; //maximum altitude

    /**
     * The Autopilot class represents an autopilot system for controlling a flight system.
     * It provides methods for controlling the autopilot functionality.
     */
    public Autopilot() {}

    /**
     * Submits the autopilot settings.
     * This method sets various indicators and buttons to their initial values.
     */
    public void submit() {
        assert submit && !engaged : "Submit should be set to true and engaged false";
        
        engaged = true; // engaged indicator
        disabled = false; // disengaged indicator
        overridden = false; // overridden button
        fault = false; // fault indicator
        submit = false; // submit button
        badData = false; // bad data indicator
        input = false; // input field true or false

        assert !submit && engaged : "Submit should set submit false and engaged true";
    }
    
    /**
     * Sets the autopilot to bad data state.
     * This method sets the bad data indicator to true and other indicators to false.
     */
    public void badData() {
        assert !badData && !engaged : "Bad data should not coexist with engaged state";

        engaged = false; //engaged indicator
        disabled = true; //disengaged indicator
        overridden = false; //overridden button
        fault = true; //fault indicator
        submit = false; //submit button
        badData = true; //bad data indicator
        input = false; //input field true or false

        assert badData && fault : "Bad data should set badData true and submit false";
    }

    /**
     * Engages the autopilot.
     * This method sets the engaged indicator to true and other indicators to false.
     */
    public void engage() {
        assert !engaged && disabled : "Engage should not coexist with disabled state";
        
        engaged = true; 
        disabled = false;
        overridden = false;
        fault = false;
        submit = true;
        badData = false;
        input = false;
    
        assert engaged && !disabled : "Engage should set engaged true and disabled false";
    }

    /**
     * Disengages the autopilot.
     * This method sets the engaged indicator to false and other indicators to false.
     */
    public void disengage() {
        assert engaged && !disabled : "Disengage should not coexist with engaged state";
        
        engaged = false;
        disabled = true;
        overridden = false;
        fault = false;
        submit = true;
        badData = false;
        input = false;
    
        assert !engaged && disabled : "Disengage should set engaged false and disabled true";
    }

    /**
     * Overrides the autopilot.
     * This method sets the overridden button to true and other indicators to false.
     */
    public void override() {
        engaged = false; //engaged indicator
        disabled = true; //disengaged indicator
        overridden = true; //overridden button
        fault = false; //fault indicator
        submit = true; //submit button
        badData = false; //bad data indicator
        input = true; //input field true or false

        assert overridden && !engaged : "Override should set overridden true and engaged false";
    }

    /**
     * Faults the autopilot.
     * This method sets the fault indicator to true and other indicators to false.
     */
    public void fault() {
        assert !fault && !engaged : "Fault state should not coexist with engaged state";
        engaged = false;
        disabled = false;
        overridden = false;
        fault = true;
        submit = false;
        badData = true;
        input = false;
    
        assert fault && !engaged : "Fault state should not coexist with engaged state";
    }

    /**
     * Sets the minimum speed for the autopilot.
     *
     * @param value the new minimum speed value
     * @return the updated minimum speed value
     */
    public int setMinSpeed(int value) {
        assert value >= 40 : "Minimum speed must be above 40";  // Example condition
        minSpeed = value;
        return minSpeed;
    }

    /**
     * Sets the maximum speed for the autopilot.
     *
     * @param value the new maximum speed value
     * @return the updated maximum speed value
     */
    public int setMaxSpeed(int value) {
        assert value <= 453 : "Maximum speed must be less than 453";
        maxSpeed = value;
        return maxSpeed;
    }

    /**
     * Sets the minimum altitude for the autopilot.
     *
     * @param value the new minimum altitude value
     * @return the updated minimum altitude value
     */
    public int setMinAltitude(int value) {
        assert value >= 1000 : "Minimum altitude must be greater than 1000";
        minAltitude = value;
        return minAltitude;
    }

    /**
     * Sets the maximum altitude for the autopilot.
     *
     * @param value the new maximum altitude value
     * @return the updated maximum altitude value
     */
    public int setMaxAltitude(int value) {
        assert value <= 37000 : "Maximum altitude must be greater than 37000";
        maxAltitude = value;
        return maxAltitude;
    }

    /**
     * Returns the minimum speed of the autopilot.
     *
     * @return the minimum speed of the autopilot
     */
    public int getMinSpeed() {
        assert minSpeed >= 40 : "Returned minimum speed should never be negative";
        return minSpeed;
    }
    

    /**
     * Returns the maximum speed of the autopilot.
     *
     * @return the maximum speed of the autopilot
     */
    public int getMaxSpeed() {
        assert maxSpeed <= 453: "Returned maximum speed should never be negative";
        return maxSpeed;
    }

    /**
     * Returns the minimum altitude of the autopilot.
     *
     * @return the minimum altitude of the autopilot
     */
    public int getMinAltitude() {
        assert minAltitude >= 1000 : "Returned minimum altitude should never be negative";
        return minAltitude;
    }

    /**
     * Returns the maximum altitude of the autopilot.
     *
     * @return the maximum altitude of the autopilot
     */
    public int getMaxAltitude() {
        assert maxAltitude <= 37000 : "Returned maximum altitude should never be negative";
        return maxAltitude;
    }

    /**
     * Sets the autopilot to input state.
     * This method sets the input field to true and other indicators to false.
     */
    public boolean getSubmit() {
        return submit;
    }

    /**
     * Returns the value indicating whether the data is bad or not.
     *
     * @return true if the data is bad, false otherwise
     */
    public boolean getBadData() {
        assert !badData || fault : "Bad data should imply a fault condition";
        return badData;
    }

    /**
     * Returns the current engagement status of the autopilot.
     *
     * @return true if the autopilot is engaged, false otherwise.
     */
    public boolean getEngaged() {
        assert !(engaged && disabled) : "Autopilot cannot be engaged and disabled at the same time";
        return engaged;
    }

    /**
     * Returns the current disabled state of the autopilot.
     *
     * @return true if the autopilot is disabled, false otherwise.
     */
    public boolean getDisabled() {
        assert !(disabled && engaged) : "Autopilot cannot be disabled and engaged at the same time";
        return disabled;
    }

    /**
     * Returns the overridden status of the autopilot.
     *
     * @return true if the autopilot is overridden, false otherwise.
     */
    public boolean getOverridden() {
        return overridden;
    }

    /**
     * Returns the current fault status of the autopilot.
     *
     * @return true if there is a fault, false otherwise
     */
    public boolean getFault() {
        assert !fault || (!engaged && !submit && badData) : "Fault must restrict engagement and submission, typically indicating bad data";
        return fault;
    }

    /**
     * Returns the current input value.
     *
     * @return the current input value
     */
    public boolean getInput() {
        assert !input || !fault : "Valid input should not exist with a fault";
        return input;
    }
}
