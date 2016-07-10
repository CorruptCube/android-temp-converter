package wetsch.simpletemperatureconverter;
/**
 * This class holds methods to allow you to convert across the three temperature measurements.
 * Each method can be accessed statically so no object is necessary.
 * Each method take a double for it's parameter. 
 * In addition, each method returns the converted value as a double.
 * @author kevin
 *
 */
public class TemperatureConversionFormulas {
	//Convert from Fahrenheit to Celsius/kelvin.
	
	/**
	 * Convert from Fahrenheit to celsius.
	 * @param initialValue initial Value in Faherenheit.
	 * @return double
	 */
	public static double fahrenheitToCelsius(double initialValue){
		return (initialValue - 32)* 5 / 9;
	}
	
	/**
	 * Convert from Fahrenheit to kelvin.
	 * @param initialValue initial Value in Faherenheit.
	 * @return double
	 */
	public static double fahrenheitToKelvin(double initialValue){
		return (0.555555556 * (initialValue - 32) + 273.15);
	}
	
	//Convert from Celsius to Fahrenheit/Kelvin.

	/**
	 * Convert from celsius to Fahrenheit.
	 * @param initialValue initial Value in Celsius.
	 * @return double
	 */

	public static double celsiusToFahrenheit(double initialValue){
		return 1.8 * initialValue + 32;
	}
	
	/**
	 * Convert from celsius to kelvin.
	 * @param initialValue initial Value in Celsius.
	 * @return double
	 */
	public static double celsiusToKelvin(double initialValue){
		return initialValue+273.15;
	}
	
	//Convert from Kelvin To Fahrenheit/Celsius

	/**
	 * Convert from kelvin to Fahrenheit.
	 * @param initialValue initial Value in Kelvin.
	 * @return double
	 */
	public static double kelvinToFahrenheit(double initialValue){
		return ((initialValue-273.15)*1.8)+32;
	}
	

	/**
	 * Convert from kelvin to celsius.
	 * @param initialValue initial Value in Kelvin.
	 * @return double
	 */
	public static double kelvinToCelsius(double initialValue){
		return initialValue-273.15;
	}

	

}


