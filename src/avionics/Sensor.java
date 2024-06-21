package avionics;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

/**
 * The Sensor class represents a sensor with a name, minimum and maximum values,
 * and a current value.
 * It simulates sensor behavior by generating random values within the specified
 * range.
 * The sensor value can be updated, and its status can be checked to determine
 * if it is within range.
 */
public class Sensor {
    private int max; // The maximum value
    private int min; // The minimum value
    private int objectValue; // The current value reported by the sensor
    private int incrementCounter; // Counter for simulating value changes
    private int variationAmount; // Variation amount to generate increment that affect value
    private String name; // The name of the sensor
    private final Random rand = new Random(); // Random number generator
    
    /**
     * Constructs a Sensor object with the given name, maximum, and minimum values.
     * 
     * @param name The name of the sensor.
     * @param max  The maximum value the sensor can report.
     * @param min  The minimum value the sensor can report.
     */
    public Sensor(String name, int max, int min) {
        assert name != null : "Name cannot be null";
        assert max >= min : "Max must be greater or equal to min";
        this.name = name;
        this.max = max;
        this.min = min;
        this.variationAmount = generateVariation();
        this.incrementCounter = generateIncrement();
        this.objectValue = generateValue();
    }

    /**
     * Sets the minimum and maximum values of the sensor.
     * Regenerates the sensor value and increment counter within the new range.
     * 
     * @param min The new minimum value.
     * @param max The new maximum value.
     */
    public void setMinMax(int min, int max) {
        this.max = max;
        this.min = min;
        this.variationAmount = generateVariation();
        this.objectValue = generateValue();
        this.incrementCounter = generateIncrement();
        assert incrementCounter >= -variationAmount && incrementCounter <= variationAmount : "incrementCounter must be within range";
        assertTrue("incrementCounter must be within range", incrementCounter >= -variationAmount && incrementCounter <= variationAmount);
    }

    /**
     * Sets the minimum and maximum values of the sensor without regenerating the
     * value.
     * This method is used for testing purposes.
     * 
     * @param min The new minimum value.
     * @param max The new maximum value.
     */
    public void setBadMinMax(int min, int max) {
        assert min <= max : "Min must be less or equal to max";
        this.max = max;
        this.min = min;
    }

    /**
     * Retrieves the maximum value of the sensor.
     * This method is only used for testing purposes.
     * 
     * @return The maximum value.
     */
    public Integer getMax() {
        assert max >= min : "Max must be greater or equal to min";
        return max;
    }

    /**
     * Retrieves the minimum value of the sensor.
     * This method is only used for testing purposes.
     * 
     * @return The minimum value.
     */
    public Integer getMin() {
        assert max >= min : "Max must be greater or equal to min";
        return min;
    }

    /**
     * Updates the value of the sensor based on the increment counter.
     * If the increment counter is zero, it regenerates a new increment value.
     */
    public void updateValue() {
        if (incrementCounter > 0) {
            objectValue -= 1;
            incrementCounter -= 1;
        } else if (incrementCounter < 0) {
            objectValue += 1;
            incrementCounter += 1;
        } else if (!isWithinRange()) {
            objectValue += 1;
            incrementCounter = generateIncrement();
        } else {
            incrementCounter = generateIncrement();
        }
        assertTrue("incrementCounter must be within range", incrementCounter >= -variationAmount && incrementCounter <= variationAmount);
        assert incrementCounter >= -variationAmount && incrementCounter <= variationAmount : "incrementCounter must be within range";
    }

    /**
     * Retrieves the name of the sensor.
     * 
     * @return The name of the sensor.
     */
    public String getName() {
        assert name != null : "Name cannot be null";
        return name;
    }

    /**
     * Retrieves the current value reported by the sensor.
     * 
     * @return The current value.
     */
    public Integer getValue() {
        assertNotNull("ObjectValue cannot be null", objectValue);
        return objectValue;
    }

    /**
     * Returns a string representation of the sensor, including its name and value.
     * 
     * @return A string representation of the sensor.
     */
    public String toString() {
        assert name != null : "Name and objectValue cannot be null";
        return "Name = " + name + ", Value = " + objectValue;
    }

    /**
     * Checks if the current sensor value is within the specified range.
     * 
     * @return True if the value is within the range, false otherwise.
     */
    public Boolean isWithinRange() {
        return objectValue >= min && objectValue <= max;
    }

    /**
     * Checks if the current sensor value is too high.
     * 
     * @return True if the value is too high, false otherwise.
     */
    public Boolean isTooHigh() {
        assert min < max : "Min must be less than max";
        return objectValue > max;
    }

    /**
     * Checks if the current sensor value is too low.
     * 
     * @return True if the value is too low, false otherwise.
     */
    public Boolean isTooLow() {
        assert min < max : "Min must be less than max";
        return objectValue < min;
    }

    /**
     * Generates a random increment value within the specified variation amount.
     *
     * @return The generated increment value.
     */
    private int generateIncrement() {
        assertTrue("Variation amount must be greater or equal to zero", variationAmount >= 0);
        int increment = rand.nextInt((variationAmount * 2) + 1) - variationAmount;
        assertTrue("Increment must be within variation amount", increment <= variationAmount && increment >= -variationAmount);
        return increment;
    }

    
    /**
     * Generates a random integer value within the specified range.
     *
     * @return the generated integer value
     */
    private Integer generateValue() {
        assert max >= min : "Max must be greater or equal to min";
        int value = rand.nextInt((max - min) + 1) + min;
        //check value is int
        assert value == (int)value : "Value must be an integer";
        return value;
    }

    /**
     * Generates a random variation amount based on the specified range.
     *
     * @return The generated variation amount.
     */
    private Integer generateVariation() {
        assert max >= min : "Max must be greater or equal to min";
        int variation = (int) ((max - min) * 0.10); // 10% of the range
        assert variation == (int)variation : "Value must be an integer";
        return variation;
    }
}
