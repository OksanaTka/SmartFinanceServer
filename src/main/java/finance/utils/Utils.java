package finance.utils;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class Utils {

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

	public void assertValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (!email.matches(regex))
			throw new BadRequestException("Invalid email");
	}

	public boolean checkValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (!email.matches(regex))
			return false;
		return true;
	}

	public String toLowerCaseEmail(String email) {
		return email.toLowerCase();
	}
}
