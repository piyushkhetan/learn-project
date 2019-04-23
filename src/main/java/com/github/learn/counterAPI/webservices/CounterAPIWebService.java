package com.github.learn.counterAPI.webservices;

import com.github.learn.counterAPI.Util.APIUtil;
import com.github.learn.counterAPI.Util.Constants;
import com.github.learn.counterAPI.webservices.models.CounterAPIModel;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

@Controller
public class CounterAPIWebService {
	private static final Logger LOG = LoggerFactory.getLogger(CounterAPIWebService.class);
	/**
	 * Searches for the required text and returns with total occurrences of word.
	 * @param counterAPIModel
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8", consumes = "application/json")
	public @ResponseBody String searchText( @RequestBody CounterAPIModel counterAPIModel){
		Gson gson = new Gson();
		try {
			Map<String, Integer> countResultMap = APIUtil.getTextCounts(counterAPIModel.getSearchText());
			counterAPIModel.setSearchText(null);
			counterAPIModel.setCounts(countResultMap);			
		} catch (Exception exception) {
			LOG.info("Something wrong has happened",exception);
		}
		return gson.toJson(counterAPIModel);
	} 

	/**
	 * Returns the top words with total number of occurrences. Use input as id.
	 * @param count
	 * @param response
	 */
	@RequestMapping(value = "/top/{count}", method = RequestMethod.POST, headers = "Accept=application/json", produces = "text/csv", consumes = "application/json")
	public @ResponseBody void topText(@PathVariable int count, HttpServletResponse response){
		try {
			List<String[]> result = APIUtil.getMaxOccurrence(count);	
			response.setHeader("Content-Disposition", "attachment; filename=counter_result.csv");
            response.setContentType("text/csv");
            OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            CSVWriter csvWriter = new CSVWriter(osw, Constants.CSV_DELIMETER);
            csvWriter.writeAll(result);
            csvWriter.flush();
            csvWriter.close();
		} catch (Exception exception) {
			LOG.info("Something wrong has happened",exception);
		}
	} 
	
	/**
	 * Returns the paragraph text.
	 * @return
	 */
	@RequestMapping(value = "/text", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=utf-8", consumes = "application/json")
	public @ResponseBody String text(){
		Gson gson = new Gson();
		CounterAPIModel counterAPIModel = new CounterAPIModel();
		try {			
			counterAPIModel.setParagraphText(Constants.PARAGRAPH_TEXT);
		} catch (Exception exception) {
			LOG.info("Something wrong has happened",exception);
		}
		return gson.toJson(counterAPIModel);
	} 
}
