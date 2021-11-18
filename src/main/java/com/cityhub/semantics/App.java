package com.cityhub.semantics;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
    	
    	TDBHandler tdb_handler = new TDBHandler();
    	
    	System.out.println( "Executing Mashup Between Parking Lot and Parking Spot: \n" );
    	tdb_handler.execute_ParkingMashup();
    	
    	System.out.println( "Executing Mashup Between Parking Lot and Air-Quality Observation: \n" );
    	tdb_handler.execute_ParkingAndAirQualityObservationMashup();
    	
    	System.out.println( "Executing Mashup Between Parking Lot and Air-Quality Estimation: \n" );
    	tdb_handler.execute_ParkingAndAirQualityEstimationMashup();
    	
    	System.out.println( "Executing Mashup Between Parking Lot and Weather Observation: \n" );
    	tdb_handler.execute_ParkingAndWeatherObservationMashup();
    	
    	System.out.println( "Executing Mashup Between Parking Lot and Weather Estimation: \n" );
    	tdb_handler.execute_ParkingAndWeatherEstimationMashup();
    	
    	System.out.println( "Executing Mashup Between Parking Spots Having same type and Close to each other: \n" );
    	tdb_handler.execute_ParkingSpotsForDisabledMashup();
    	
    	System.out.println( "Executing Mashup Between Weather and Air-Quality Graph Having similar time and location values: \n" );
    	tdb_handler.execute_WeatherAndAirQualityMashup();
    	
    	System.out.println( "Mashup Formation Completed Succesfully! .. \n" );
    }
}
