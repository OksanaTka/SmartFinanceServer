package finance.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedExecption extends RuntimeException{

	private static final long serialVersionUID = 9033520560548507397L;

	public UnsupportedExecption() {
		
	}
	
	public UnsupportedExecption(String message) {
		super(message);
	}
	
	public UnsupportedExecption(String message, Throwable cause) {
		super(message, cause);
	}

}
