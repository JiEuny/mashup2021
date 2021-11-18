package com.cityhub.semantics;

public class QueryGenerator {

	// x --> longitude
   //  y --> latitude
	
	
	   /////////////////////////////////////////////////////////////
	  //-->SPARQL Query to INSERT triples connecting Parking Lot 
	 //--->containing Parking Spots among them..                
	/////////////////////////////////////////////////////////////
	public String query_updateWithSameParkingID() {
		
		String query_string = "PREFIX common: <http://www.city-hub.kr/ontologies/2019/1/common#> \n"
							+ "PREFIX parking: <http://www.city-hub.kr/ontologies/2019/1/parking#> \n"
							+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
							
							+ "INSERT { "
							+ "        GRAPH ?g_lot{ \n"
							+ "                     ?s_lot common:hasSubSystem ?s_spot . \n"
							+ "        }  \n"
							+ "        GRAPH ?g_spot{ \n"
							+ "                      ?s_spot common:subSystemOf ?s_lot . \n"
							+ "        } \n"
							+ " }\n"
							
							//+ "SELECT ?g_lot ?g_spot (STR(?id_lot) AS ?id) (STR(?ref_lot) AS ?ref) \n"
							+ "WHERE { GRAPH ?g_lot { \n"
							+ "	                     ?s_lot rdf:type parking:ParkingLot . \n"
							+ "                      ?s_lot common:hasID ?id_lot . \n"
							+ "                      } . \n"
							+ "        GRAPH ?g_spot { \n"
							+ "                       ?s_spot rdf:type parking:ParkingSpot . \n"
							+ "                       ?s_spot parking:hasParkingSpotProfile ?profile . \n"
							+ "                       ?profile parking:hasParkingLotReference ?ref_lot . \n"
							+ "                       } .\n"
							+ "        FILTER( \n"
							+ "                STR(?id_lot) = STR(?ref_lot)"
							+ "               ) \n"
							+ "}";
		
		showQuery(query_string);
		
		return query_string;
	}
	
	

	
	    /////////////////////////////////////////////////////////////
	   //-->SPARQL Query to INSERT triples connecting Parking Lot 
	  //--->with Weather and Air-Quality Data having same
	 //---->location...
	/////////////////////////////////////////////////////////////
	public String query_updateGraphWithSameLocation(String graph) {
		
		String[] query_line = null;
		
		if( graph.equals("AirQualityObservation") ) {
			
			query_line = new String[]{ 
									  "                     ?plot rdfs:seeAlso ?aq_Observe . \n",
									  "        GRAPH ?g_aqo { \n"
									  + "                      ?aq_Observe rdf:type air-quality:AirQualityObservation . \n"
									  + "                      ?aq_Observe rdfs:seeAlso ?eval . \n"
										};
			
		}else if( graph.equals("AirQualityEstimation") ) {
			
			query_line = new String[]{ 
									  "                     ?plot rdfs:seeAlso ?aq_Estimate . \n",
									  "        GRAPH ?g_aqe { \n"
									  + "                      ?aq_Estimate rdf:type air-quality:AirQualityEstimation . \n"
									  + "                      ?aq_Estimate rdfs:seeAlso ?eval . \n"
						};
			
		}else if( graph.equals("WeatherObservation") ) {
			
			query_line = new String[]{ 
									  "                     ?plot rdfs:seeAlso ?w_Observe . \n",
									  "        GRAPH ?g_wo { \n"
									  + "                      ?w_Observe rdf:type weather:WeatherObservation . \n"
									  + "                      ?eval rdf:type weather:WeatherEvaluation . \n"
			};
		}else if( graph.equals("WeatherEstimation") ) {
			
			query_line = new String[]{ 
									  "                     ?plot rdfs:seeAlso ?w_Estimate . \n",
									  "        GRAPH ?g_we { \n"
									  + "                      ?w_Estimate rdf:type weather:WeatherEstimation . \n"
									  + "                      ?eval rdf:type weather:WeatherEvaluation . \n"
			};
		}
		
		String query_string = null;
		
		if(query_line!=null) {
			
			
			   query_string = "PREFIX common: <http://www.city-hub.kr/ontologies/2019/1/common#> \n"
							+ "PREFIX air-quality: <http://www.city-hub.kr/ontologies/2019/1/air-quality#> \n"
							+ "PREFIX weather: <http://www.city-hub.kr/ontologies/2019/1/weather#> \n"
							+ "PREFIX parking: <http://www.city-hub.kr/ontologies/2019/1/parking#> \n"
							+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
							+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
							+ "PREFIX bif: <bif:> \n"
							
							+ "INSERT { "
							+ "        GRAPH ?g_lot{ \n"
							+ 						query_line[0]
							+ "        }  \n"
							+ " }\n"
							
//							+ "SELECT ?distance \n"
							+ "WHERE { \n"
							+ 			query_line[1]
							+ "                      ?eval common:evaluatedForLocation ?geo . \n"
							+ "                      ?geo common:hasLatitute ?lat . \n"
							+ "                      ?geo common:hasLongitude ?long .\n"
							+ "                      } . \n"
							+ "        GRAPH ?g_lot { \n"
							+ "                       ?plot rdf:type parking:ParkingLot . \n"
							+ "                       ?plot common:isLocatedAt ?plot_geo . \n"
							+ "                       ?plot_geo common:hasLatitute ?plot_lat . \n"
							+ "                       ?plot_geo common:hasLongitude ?plot_long .\n"
							+ "                       } .\n"
							+ "        BIND(bif:haversine_deg_km(?lat, ?long, ?plot_lat, ?plot_long) AS ?distance ). \n"
							+ "        FILTER( \n"
							+ "                ?distance <= xsd:double(5) \n"
							+ "               ) \n"
							+ "}";
		
		}
		
		showQuery(query_string);
		
		return query_string;
	}
	
	
	
	    /////////////////////////////////////////////////////////////
	   //-->SPARQL Query to INSERT triples connecting Parking Lot 
	  //--->with Weather and Air-Quality Data having same
	 //---->location...
	/////////////////////////////////////////////////////////////
	public String query_updateParkingAndAirQualityWithSameLocation() {
	
		String query_string = "PREFIX common: <http://www.city-hub.kr/ontologies/2019/1/common#> \n"
						+ "PREFIX air-quality: <http://www.city-hub.kr/ontologies/2019/1/air-quality#> \n"
						+ "PREFIX parking: <http://www.city-hub.kr/ontologies/2019/1/parking#> \n"
						+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
						+ "PREFIX bif: <bif:>"
						
						+ "INSERT { "
						+ "        GRAPH ?g_lot{ \n"
						+ "                     ?plot rdfs:seeAlso ?aq_Forecast . \n"
						+ "        }  \n"
						+ " }\n"
						
						//+ "SELECT DISTINCT ?plot (rdfs:seeAlso AS ?seeAlso) ?aq_Observe \n"
						+ "WHERE { GRAPH ?g_aqf { \n"
						+ "                      ?aq_Forecast rdf:type air-quality:AirQualityForecast . \n"
						+ "	                     ?aq_Forecast rdfs:seeAlso ?aq_Eval . \n"
						+ "                      ?aq_Eval common:evaluatedForLocation ?aq_geo . \n"
						+ "                      ?aq_geo common:hasLatitute ?aqf_lat . \n"
						+ "                      ?aq_geo common:hasLongitude ?aqf_long .\n"
						+ "                      } . \n"
						+ "        GRAPH ?g_lot { \n"
						+ "                       ?plot rdf:type parking:ParkingLot . \n"
						+ "                       ?plot common:isLocatedAt ?plot_geo . \n"
						+ "                       ?plot_geo common:hasLatitute ?plot_lat . \n"
						+ "                       ?plot_geo common:hasLongitude ?plot_long .\n"
						+ "                       } .\n"
						+ "        BIND(bif:haversine_deg_km(?aqf_lat, ?aqf_long, ?plot_lat, ?plot_long) AS ?distance ). \n"
						+ "        FILTER( \n"
						+ "                ?distance <= xsd:double(10)"
						+ "               ) \n"
						+ "}";
	
		showQuery(query_string);
	
		return query_string;
	}
	
	
	
	   ///////////////////////////////////////////////////////////////////
	  //-->SPARQL Query to INSERT triples connecting Two Parking 
	 //--->Spots close to each other and having type: "forDisabled".                
	///////////////////////////////////////////////////////////////////
	public String query_updateWithSameParkingSpotType() {
		
		String query_string = "PREFIX common: <http://www.city-hub.kr/ontologies/2019/1/common#>\r\n" + 
				"PREFIX air-quality: <http://www.city-hub.kr/ontologies/2019/1/air-quality#> \r\n" + 
				"PREFIX weather: <http://www.city-hub.kr/ontologies/2019/1/weather#> \r\n" + 
				"PREFIX parking: <http://www.city-hub.kr/ontologies/2019/1/parking#> \r\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \r\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \r\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \r\n" + 
				"PREFIX bif: <bif:> \r\n" + 
				"\r\n" + 
				//"SELECT ?distance ?g_spot1 ?g_spot2 \r\n" + 
				"INSERT { \r\n" +
				"        GRAPH ?g_spot1 { \r\n" +
				"                        ?pspot1 rdfs:seeAlso ?pspot2 \r\n" +
				"        } \r\n" +
				"}"+
				"\r\n" + 
				"WHERE {\r\n" + 
				"        GRAPH ?g_spot1{\r\n" + 
				"	                    ?pspot1 rdf:type parking:ParkingSpot .\r\n" +
				"	                    ?pspot1 common:hasID ?pspot_id1 . \r\n" + 
				"                       ?pspot1 parking:hasParkingSpotProfile ?profile1 .\r\n" + 
				"                       ?profile1 parking:hasUserTypeLimit ?usrType1 .\r\n" +
				"                       ?pspot1 common:isLocatedAt ?pspot_geo1 .\r\n" + 
				"                       ?pspot_geo1 common:hasLatitute ?pspot_lat1 .\r\n" + 
				"                       ?pspot_geo1 common:hasLongitude ?pspot_long1\r\n" + 
				"                      } .\r\n" + 
				"        GRAPH ?g_spot2{\r\n" + 
				"	                    ?pspot2 rdf:type parking:ParkingSpot .\r\n" +
				"	                    ?pspot2 common:hasID ?pspot_id2 . \r\n" +
				"                       ?pspot2 parking:hasParkingSpotProfile ?profile2 .\r\n" + 
				"                       ?profile2 parking:hasUserTypeLimit ?usrType2 .\r\n" +
				"                       ?pspot2 common:isLocatedAt ?pspot_geo2 .\r\n" + 
				"                       ?pspot_geo2 common:hasLatitute ?pspot_lat2 .\r\n" + 
				"                       ?pspot_geo2 common:hasLongitude ?pspot_long2\r\n" + 
				"                      } . \r\n" + 
				"        BIND(bif:haversine_deg_km(?pspot_lat1, ?pspot_long1, ?pspot_lat2, ?pspot_long2)/1000 AS ?distance ).\r\n" + 
				"        FILTER(\r\n" + 
				"                ( CONTAINS( LCASE( STR( ?usrType1 ) ), \"disabled\" ) ) && \r\n" + 
				"                ( CONTAINS( LCASE( STR( ?usrType2 ) ), \"disabled\" ) ) && \r\n" + 
				"                ( ?distance <= xsd:double(500) ) && \r\n" + 
				"                ( ?pspot_id1 != ?pspot_id2 ) \r\n" + 
				"               ) \r\n" + 
				"}";
		
		showQuery(query_string);
		
		return query_string;
	}
	
	
	
	
	
	   ///////////////////////////////////////////////////////////////////
	  //-->SPARQL Query to INSERT rdf:seeAlso connecting Weather and  
	 //--->Air-Quality Info havine similar time and locaiton values.                
	///////////////////////////////////////////////////////////////////
	public String query_update_W_And_AQ_WithSameTimeAndLocation() {
		
		String query_string = "PREFIX common: <http://www.city-hub.kr/ontologies/2019/1/common#> \r\n" + 
							  "PREFIX air-quality: <http://www.city-hub.kr/ontologies/2019/1/air-quality#> \r\n" + 
							  "PREFIX weather: <http://www.city-hub.kr/ontologies/2019/1/weather#> \r\n" + 
							  "PREFIX time: <http://www.w3.org/2006/time#> \r\n" + 
							  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \r\n" + 
							  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \r\n" +
							  "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \r\n" +
							  "PREFIX bif: <bif:> \r\n" +
							  " \r\n" + 
							  //"SELECT ?distance ?hour_diff ?obs_aq ?obs_w \r\n" +
							  "INSERT { \r\n" +
							  "        GRAPH ?g_aq { \r\n" +
							  "                        ?obs_aq rdfs:seeAlso ?obs_w \r\n" +
							  "        } \r\n" +
							  "        GRAPH ?g_w { \r\n" +
							  "                        ?obs_w rdfs:seeAlso ?obs_aq \r\n" +
							  "        } \r\n" +
							  "}"+
							  "WHERE { \r\n" + 
							  "  GRAPH ?g_aq { \r\n" + 
							  "       ?obs_aq rdfs:seeAlso ?eval_aq. \r\n" + 
							  "       ?eval_aq rdf:type air-quality:AirQualityEvaluation. \r\n" + 
							  "       ?eval_aq common:evaluatedForLocation ?geo_aq. \r\n" + 
							  "       ?geo_aq common:hasLatitute ?lat_aq. \r\n" + 
							  "       ?geo_aq common:hasLongitude ?long_aq. \r\n" + 
							  "       ?eval_aq common:evaluatedOn ?time_aq. \r\n" + 
							  "       ?time_aq time:inXSDDateTime ?XSDTime_aq. \r\n" + 
							  "  } \r\n" + 
							  "  GRAPH ?g_w { \r\n" + 
							  "       ?obs_w rdfs:seeAlso ?eval_w. \r\n" + 
							  "       ?eval_w rdf:type weather:WeatherEvaluation. \r\n" + 
							  "       ?eval_w common:evaluatedForLocation ?geo_w. \r\n" + 
							  "       ?geo_w common:hasLatitute ?lat_w. \r\n" + 
							  "       ?geo_w common:hasLongitude ?long_w. \r\n" + 
							  "       ?eval_w common:evaluatedOn ?time_w. \r\n" + 
							  "       ?time_w time:inXSDDateTime ?XSDTime_w. \r\n" + 
							  "  }. \r\n" + 
							  "  BIND(bif:haversine_deg_km(?lat_aq, ?long_aq, ?lat_w, ?long_w) AS ?distance ). \r\n" + 
							  "  BIND( ( HOURS(?XSDTime_aq) + ( MINUTES(?XSDTime_aq) / xsd:double(60) ) ) AS ?hour_aq ). \r\n" + 
							  "  BIND( ( HOURS(?XSDTime_w) + ( MINUTES(?XSDTime_w) / xsd:double(60) ) ) AS ?hour_w ). \r\n" + 
							  "  BIND( \r\n" + 
							  "            IF( \r\n" + 
							  "                (?hour_aq - ?hour_w) < xsd:double(0), \r\n" + 
							  "                (?hour_aq - ?hour_w) * xsd:double(-1), \r\n" + 
							  "                ?hour_aq - ?hour_w \r\n" + 
							  "            )            \r\n" + 
							  "            AS ?hour_diff \r\n" + 
							  "  ). \r\n" + 
							  "  FILTER( \r\n" + 
							  "               ( YEAR(?XSDTime_aq) = YEAR(?XSDTime_w) ) && \r\n" + 
							  "               ( MONTH(?XSDTime_aq) = MONTH(?XSDTime_w) ) && \r\n" + 
							  "               ( DAY(?XSDTime_aq) = DAY(?XSDTime_w) ) && \r\n" + 
							  "               ( ?distance <= xsd:double(1) ) && \r\n" + 
							  "               ( ?hour_diff <= xsd:double(1) ) \r\n" +
							  "               ) \r\n" + 
							  "}";
		
		showQuery(query_string);
		
		return query_string;
	}
	
	
	
	
	String query_ParkingDebugDelete(String graph_lot, String graph_spot) {
		
		String query_string = "PREFIX common: <http://www.city-hub.kr/ontologies/2019/1/common#> \n"
							+ "PREFIX parking: <http://www.city-hub.kr/ontologies/2019/1/parking#> \n"
							+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
							//+ "WITH <" + graph_lot + "> \n"
							//+ "WITH <" + graph_spot + "> \n"
							+ "DELETE { \n"
							+ "       GRAPH <" + graph_lot + "> { \n"
							+ "                                  ?s1 common:hasSubSystem ?o1 \n"
							+ "        } \n"
							+ "       GRAPH <" + graph_spot + "> { \n"
							+ "                                  ?s2 common:subSystemOf ?o2 \n"
							+ "        } \n"
							+ " } \n"
							+ "WHERE { \n"
							+ "       GRAPH <" + graph_lot + "> { \n"
							+ "                                  ?s1 common:hasSubSystem ?o1  \n"
							+ "        } . \n"
							+ "       GRAPH <" + graph_spot + "> { \n"
							+ "                                  ?s2 common:subSystemOf ?o2  \n"
							+ "        } \n"
							+ "}";
		
		
		return query_string;
	}
	
	String query_ParkingSpotDebugUpdateID(String[] graph_spot,String[] id) {
		
		String query_string = "PREFIX common: <http://www.city-hub.kr/ontologies/2019/1/common#> \n"
							+ "PREFIX parking: <http://www.city-hub.kr/ontologies/2019/1/parking#> \n"
							+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
							//+ "WITH <" + graph_lot + "> \n"
							//+ "WITH <" + graph_spot + "> \n"
							+ "DELETE { \n";
		
		for(int i=0; i<graph_spot.length; i++) {
			
			  query_string += "       GRAPH <" + graph_spot[i] + "> { \n"
					   		+ "                                  ?pspot common:hasID ?spot_id \n"
					   		+ "        } \n";
		}
							
			  query_string += " } \n"
					  		+ "INSERT { ";
			  
		for(int j=0; j<graph_spot.length; j++) {
			
			  query_string += "        GRAPH <" + graph_spot[j] + "> { \n"
					  		+ "                     ?pspot common:hasID \"" + id[j] + "\"^^<http://www.w3.org/2001/XMLSchema#string> \n"
					  		+ "        }  \n";
		}
							
			  query_string += " }\n"
							+ "WHERE { \n";
			  
		for(int k=0; k<graph_spot.length; k++) {
			
			  query_string += "       GRAPH <" + graph_spot[k] + "> { \n"
					  		+ "									 ?pspot rdf:type parking:ParkingSpot ."
					  		+ "                                  ?pspot common:hasID ?spot_id  \n"
					  		+ "        }  \n";
			
		}
							
			  query_string += "}";
			  
			  showQuery(query_string);
		
		
		return query_string;
	}
	
	
	void showQuery(String query) {
		
		System.out.println( "Generated Query: \n" );
		System.out.println( query );
	}
}
