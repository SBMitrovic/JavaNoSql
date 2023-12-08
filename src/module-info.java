/**
 * 
 */
/**
 * @author Dajana
 *
 */
module NoSqlProjectCsvToJson {
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.annotation;
	requires org.mongodb.driver.core;
	requires org.mongodb.driver.sync.client;
	requires org.mongodb.bson;
	exports model;
}