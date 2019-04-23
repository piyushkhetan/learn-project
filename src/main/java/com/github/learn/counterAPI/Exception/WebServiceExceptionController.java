package com.github.learn.counterAPI.Exception;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

 
@ControllerAdvice
public class WebServiceExceptionController {
	Gson gson = new Gson();
	private static final Logger LOG = LoggerFactory.getLogger(WebServiceExceptionController.class);

	/**
	 * Handle all exception.
	 *
	 * @param 
	 * @return the string
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ResponseBody
	public String handleAllException(Exception exception) {
		LOG.info("Error occurred in application\n"+exception);
		return gson.toJson("Something wrong has happened. Please check the log");
	}
}