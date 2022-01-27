package com.pods.restaurant;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {
	private static ConcurrentHashMap<Long, Restaurant> restaurantMap = new ConcurrentHashMap<Long, Restaurant>();
	private static String INITIAL_FILE_PATH="/initialData.txt";
	static {
        Path filePath = Paths.get(INITIAL_FILE_PATH);
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            List<String> restaurantPortion = new ArrayList<String>();
            for(String line: lines) {
            	if( line.charAt(0) != '*') 
            		restaurantPortion.add(line);
            	else 
            		break;
            }
            
            for(String line: restaurantPortion) {
            	System.out.println(line);
            }
            
            int lineIndex = 0;
            String[] restaurantEntries = restaurantPortion.get(lineIndex++).split(" ");
            
            while(lineIndex<restaurantPortion.size()) {
            	/**
            	 * Extracting REstaurant Informaiotn
            	 */
            	long resId = Long.parseLong(restaurantEntries[0]);
            	int nItems = Integer.parseInt(restaurantEntries[1]);
            	
            	Restaurant newRes  =  new Restaurant();
            	newRes.setId(resId);
            	
            	for(int i=0;i<nItems;i++) {
            		/**
            		 * Extracting items for current restaurant
            		 */
            		String[] itemEntries = restaurantPortion.get(lineIndex++).split(" ");
            		long itemId = Long.parseLong(itemEntries[0]);
            		double price = Double.parseDouble(itemEntries[1]);
            		int quantity = Integer.parseInt(itemEntries[2]);
            		newRes.addItem(itemId, price, quantity);
            	}
            	restaurantMap.put(resId, newRes);
            	if(lineIndex < restaurantPortion.size())
            		restaurantEntries = restaurantPortion.get(lineIndex++).split(" ");
            }
            
            
            
            for(Long res:Collections.list(restaurantMap.keys()))
            	System.out.println(restaurantMap.get(res));
            
            
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
	}
	
	
    @RequestMapping("/")
    public String index() {
    InetAddress ip = null;
            String retString = "";
            try {
                     ip = InetAddress.getLocalHost();

            }
             catch (Exception e) {
                   return "Internal error encountered in server " + ip + " !";
            }
            return retString + "Greetings from Spring Boot running at " + ip + " !";
    }
    
    @PostMapping("/acceptOrder")
    public String acceptOrder(@RequestBody String data) {
    	System.out.println(data);
    	return "Success";
    }

}
