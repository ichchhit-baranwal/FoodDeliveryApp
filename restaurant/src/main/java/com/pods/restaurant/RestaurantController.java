package com.pods.restaurant;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * The class serves as entry point to any 
 * request made to the service. It is auto-detected  
 * as the controller through classpath scanning 
 */
@RestController
public class RestaurantController {
	/**
	 * restaurantMap is ConcurrentHashMap is the in memory data structure that has
	 * been used storing all the restaurant so that id based look up
	 * can be made easily and faster
	 */
	private static ConcurrentHashMap<Long, Restaurant> restaurantMap = new ConcurrentHashMap<Long, Restaurant>();
//	private static String INITIAL_FILE_PATH="F:\\MTechCourse\\Principal Of Distribution Software\\PoDS_Project_1\\initialData.txt";
	private static String INITIAL_FILE_PATH="/initialData.txt";
	static {
        initialize();
	}
    /**
     * This method serves the request made to the postfix 
     * url /acceptOrder and accept the order based on the condition
     * that if item is available that is qunatity of the item is greater than asked 
     * given in the request body
     * @param data
     * @return ResponseEntity as a response to the client
     */
    @PostMapping("/acceptOrder")
    public ResponseEntity<String> acceptOrder(@RequestBody ResReq data) {
    	System.out.println(data);
    	HttpStatus status_code = HttpStatus.GONE;
    	Restaurant currRes = restaurantMap.get(data.getRestId());
    	Item currItem  = null;
    	if(currRes != null)
    		currItem = currRes.getItem(data.getItemId());
    	if(currItem !=null  && currItem.getQuantity() >= data.getQty() && data.getQty() >= 0) {
    		currItem.setQuantity(currItem.getQuantity() - data.getQty());
    		status_code = HttpStatus.CREATED;
    	}

    	return new ResponseEntity<String>(status_code);
    }
    
    /**
     * This method serves the request made to the postfix 
     * url /refillItem and increase the
     *  quantity of item of the restaurant
     * by amount given in request
     * @param data
     * @return ResponseEntity as a response to the client
     */    
    @PostMapping("/refillItem")
    public ResponseEntity<String> refillItem(@RequestBody ResReq data) {
    	Restaurant currRes = restaurantMap.get(data.getRestId());
    	Item currItem  = null;
    	if(currRes != null)
    		currItem = currRes.getItem(data.getItemId());
    	if(currItem !=null && data.getQty() >= 0) {
    		currItem.setQuantity(currItem.getQuantity() + data.getQty());

    	}
    	return new ResponseEntity<String>(HttpStatus.CREATED);
    }
    
    /**
     * initialize the data in the restauranttMap
     * after reading data from the file INITIAL_FILE_PATH    
     */
    public static void initialize() {
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
            	 * Extracting Restaurant Information
            	 */
            	long resId = Long.parseLong(restaurantEntries[0]);
            	int nItems = Integer.parseInt(restaurantEntries[1]);
            	
            	Restaurant newRes  =  new Restaurant();
            	newRes.setRestId(resId);
            	
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
    
    /**
     * reInitialize the walletMap to original condition
     * @return ResponseEntity as the response to client
     */
    @PostMapping("/reInitialize")
    public ResponseEntity<String> reInitialize() {
    	initialize();
    	return new ResponseEntity<String>(HttpStatus.CREATED);

    }
}
