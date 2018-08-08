package edu.neu.cs5200.utitlities;

import java.sql.Date;
import java.util.Calendar;


public class Utility {
	/**
	 * Method: modifySQLDate
	 * @param: int day
	 * @param: int month 
	 * @param: int year 
	 * @return: java.sql.Date
	 */
	public Date modifySQLDate (int day, int month, int year) {

		java.util.Calendar cal = Calendar.getInstance();
		java.util.Date utilDate = new java.util.Date(); // util date
		cal.setTime(utilDate);
		cal.set(Calendar.YEAR, year); // set the year
		cal.set(Calendar.MONTH, month-1); // set the month (0 indexed)
		cal.set(Calendar.DAY_OF_MONTH, day); // set the day  
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); // sql date
		return sqlDate;
	}
	

}
