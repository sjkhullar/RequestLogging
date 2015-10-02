package org.learn.log;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/hotel")
public class BookingController {
	
	private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "hotel";
	}

	@RequestMapping(value="/book", method=RequestMethod.POST)
    public String bookHotel(
    		@RequestParam(value = "name",required=false) String name,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "purpose",required=false) String purpose,
            @RequestParam(value = "idproof", required = false) String idproof,
            Model model,  HttpServletRequest request){
		
		//Save the required information in data base
		//......
		//......
		//Send the response back
		model.addAttribute("name", name );
		model.addAttribute("city", city );
		model.addAttribute("purpose", purpose );
		model.addAttribute("idproof", idproof );
		
		return "customerDetails";
	}
	
	@RequestMapping(value="/book", method=RequestMethod.GET)
    public String bookHotel(
    		@RequestParam(value = "id",required=false) String id,
    		Model model,  HttpServletRequest request){
		
		//get the required information in data base for customer Id
		//......
		//......
		
		//suppose we get these value from database
		String randomName = UUID.randomUUID().toString().substring(0,4);
		String randomCity = UUID.randomUUID().toString().substring(0,4);
		String randomPurpose = UUID.randomUUID().toString().substring(0,4);
		String randomIdProof = UUID.randomUUID().toString().substring(0,4);
			
		//Send the response back
		model.addAttribute("name", "Name "+randomName );
		model.addAttribute("city", "City "+randomCity );
		model.addAttribute("purpose", "Purpose "+randomPurpose);
		model.addAttribute("idproof", "IdProof "+randomIdProof );
		
		return "customerDetails";
	}
}
