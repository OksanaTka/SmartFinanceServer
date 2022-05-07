package finance.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class Utils {

	/**
	 * checks if Object is not null
	 * 
	 * @param object
	 * @throws BadRequestException
	 */
	public void assertNull(Object obj) {
		if (obj == null)
			throw new BadRequestException("null object");
	}

	/**
	 * Creates a unique ID
	 * 
	 * @param springApplicationName - application name
	 * @return uniqueID + delimiter + springApplicationName
	 */
	public String createUniqueID(String springApplicationName) {
		return UUID.randomUUID().toString() + springApplicationName;
	}

	/**
	 * checks if the email is valid
	 * 
	 * @param email
	 * @throws BadRequestException
	 */
	public void assertValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (!email.matches(regex))
			throw new BadRequestException("Invalid email");
	}
	
	/**
	 * checks if the email is valid
	 * 
	 * @param email
	 * @return boolean if mail is valid
	 */
	public boolean checkValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (!email.matches(regex))
			return false;
		return true;
	}

	public String toLowerCaseEmail(String email) {
		return email.toLowerCase();
	}
	
	/**
	 * Gets the month from a string date
	 * 
	 * @param date
	 * @return String: month
	 */
	public String getMonthFromDate(String date) {
		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			return calendar.get(Calendar.MONTH) + "";
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Gets the year from a string date
	 * 
	 * @param date
	 * @return String: year
	 */
	public String getYearFromDate(String date) {
		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			return calendar.get(Calendar.YEAR) + "";
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
}
