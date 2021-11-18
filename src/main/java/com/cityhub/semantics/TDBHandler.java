package com.cityhub.semantics;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;

public class TDBHandler {

String tdb_access_url, tdb_userName, tdb_userPass;
	
	TDBHandler(){
		
		tdb_access_url = ServerConfiguration.TDB_BASE_URL;
		tdb_userName = ServerConfiguration.TDB_USER;
		tdb_userPass = ServerConfiguration.TDB_PASS;
	}
	
	void execute_ParkingMashup() {
		
		QueryGenerator qGen = new QueryGenerator();
		
		execute_Update( qGen.query_updateWithSameParkingID() );
//		execute_Update(qGen.query_ParkingDebugDelete(
//													"http://www.city-hub.kr/ontologies/2019/1/parking#yatap_01", 
//													"http://www.city-hub.kr/ontologies/2019/1/parking#yatap_01_yatap_540")
//				);
	}
	
	void execute_ParkingAndAirQualityObservationMashup() {
		
		QueryGenerator qGen = new QueryGenerator();
		
		execute_Update( qGen.query_updateGraphWithSameLocation("AirQualityObservation") );
		//formatResultSet( execute_Query( qGen.query_updateGraphWithSameLocation("AirQualityObservation") ));
	}
	
	void execute_ParkingAndAirQualityEstimationMashup() {
		
		QueryGenerator qGen = new QueryGenerator();
		
		execute_Update( qGen.query_updateGraphWithSameLocation("AirQualityEstimation") );
		//formatResultSet( execute_Query( qGen.query_updateGraphWithSameLocation("AirQualityEstimation") ));
	}
	
	void execute_ParkingAndWeatherObservationMashup() {
		
		QueryGenerator qGen = new QueryGenerator();
		
		execute_Update( qGen.query_updateGraphWithSameLocation("WeatherObservation") );
		//formatResultSet( execute_Query( qGen.query_updateGraphWithSameLocation("WeatherObservation") ));
	}
	
	void execute_ParkingAndWeatherEstimationMashup() {
	
		QueryGenerator qGen = new QueryGenerator();
		
		execute_Update( qGen.query_updateGraphWithSameLocation("WeatherEstimation") );
	}
	
	
	
	void execute_ParkingSpotsForDisabledMashup() {
		
		QueryGenerator qGen = new QueryGenerator();
		
		execute_Update( qGen.query_updateWithSameParkingSpotType() );
		//formatResultSet( execute_Query( qGen.query_updateWithSameParkingSpotType() ) );
//		execute_Update( qGen.query_ParkingSpotDebugUpdateID(
//															new String[] {
//																	"http://localhost:8890/DAV/home/exUser/rdf_sink/instance#lot_and_spot_instance_only",
//																	"http://localhost:8890/DAV/home/exUser/rdf_sink/instanceExample.owl#LDITEMP",
//																	"http://localhost:8890/DAV/home/exUser/rdf_sink/instance#Example.owl"
//																	} , 
//															new String[] {
//																	"urn:epc:id:sgln:880002697101.0120000",
//																	"urn:epc:id:sgln:880002697101.0130000",
//																	"urn:epc:id:sgln:880002697101.0140000"
//															}
//															) );
	}
	
	
	void execute_WeatherAndAirQualityMashup() {
		
		QueryGenerator qGen = new QueryGenerator();
		
		execute_Update( qGen.query_update_W_And_AQ_WithSameTimeAndLocation() );
		
		//formatResultSet( execute_Query( qGen.query_update_W_And_AQ_WithSameTimeAndLocation() ) );
	}
	
	
	
	private ResultSet execute_Query(String query) {
		// TODO Auto-generated method stub
		
		VirtGraph set = new VirtGraph (tdb_access_url, tdb_userName, tdb_userPass);
		
		Query sparql = QueryFactory.create( query );
		
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, set);
		
		return vqe.execSelect();
	}
	
	void execute_Update(String update_query) {
		
		/*			STEP 1			url --> "jdbc:virtuoso://localhost:1111"*/
		VirtGraph set = new VirtGraph ( tdb_access_url, tdb_userName, tdb_userPass );
		
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create( update_query, set );
        vur.exec();
		
        System.out.println("Executed Update Query....");
	}
	
	void formatResultSet(ResultSet rs) {
		
		ResultSetFormatter.out(rs);
	}
}
