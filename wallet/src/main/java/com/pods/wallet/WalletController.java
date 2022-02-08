package com.pods.wallet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * The class serves as entry point to any 
 * request made to the service. It is auto-detected  
 * as the controller through classpath scanning 
 */
@RestController
public class WalletController {
	/**
	 * walletMap is ConcurrentHashMap is the in memory data structure that has
	 * been used storing all the wallets so that id based look up
	 * can be made easily and faster
	 */
	private static ConcurrentHashMap<Long, Wallet> walletMap = new ConcurrentHashMap<Long, Wallet>();
//	private static String INITIAL_FILE_PATH="F:\\MTechCourse\\Principal Of Distribution Software\\PoDS_Project_1\\initialData.txt";
	private static String INITIAL_FILE_PATH="/initialData.txt";
	static {
        initialize();
	}
    /**
     * This method serves the request made to the postfix 
     * url /addBalance and increments the balance by the 
     * amount specified in the request body for the custId 
     * given in the request body
     * @param data
     * @return ReponseEntity as response to the client
     */
    @PostMapping("/addBalance")
    public ResponseEntity<String> addBalance(@RequestBody Wallet data) {
    	System.out.println("Data "+data);
    	Wallet currCust = walletMap.get(data.getCustId());
    	if(currCust != null && data.getAmount()>=0)
    		currCust.setAmount(currCust.getAmount()+data.getAmount());
    	System.out.println("Current Customer "+currCust);
    	return new ResponseEntity<String>(HttpStatus.CREATED);
    }
    /**
     * This method serves the request made to the postfix 
     * url /deductBalance and decrements the balance by the 
     * amount specified in the request body for the custId 
     * given in the request body
     * @param data
     * @return ReponseEntity as response to the client
     */
    @PostMapping("/deductBalance")
    public ResponseEntity<String> deductBalance(@RequestBody Wallet data) {
    	Wallet currCust = walletMap.get(data.getCustId());
    	HttpStatus status_code=HttpStatus.GONE;
    	if(currCust!=null && currCust.getAmount()>=data.getAmount() && data.getAmount() >=0)
    	{
    		currCust.setAmount(currCust.getAmount()-data.getAmount());
    		status_code=HttpStatus.CREATED;
    	}
    	System.out.println(status_code);
    	return new ResponseEntity<String>(status_code);
    }
    /**
     * This method serves the request made to the postfix 
     * url /balance/{num} and returns the object of wallet with
     * custId = num
     * @param num
     * @return Wallet as response to the client
     */
    @GetMapping("/balance/{num}")
    public ResWallet balanceNum(@PathVariable long num) {
    	Wallet currCust = walletMap.get(num);
    	System.out.println(currCust);
    	if(currCust == null)
    		currCust = new Wallet(-1, -1);
    		
    	return new ResWallet(currCust.getCustId(), currCust.getAmount());
    }  
    
    /**
     * initialize the data in the walletMap
     * after reading data from the file INITIAL_FILE_PATH    
     */
    public static void initialize() {
    	Path filePath = Paths.get(INITIAL_FILE_PATH);
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            List<String> customerPortion = new ArrayList<String>();
            int startNumCountered=0;
            int balance = 0;
            for(String line: lines) {
            	System.out.println(line+" "+startNumCountered);
            	if( line.charAt(0) == '*') 
            		startNumCountered++;
            	else if(startNumCountered == 2)
            		customerPortion.add(line);
            	else if(startNumCountered == 3) {
            		balance = Integer.parseInt(line);
            		break;
            	}
            }
            
            for(String line: customerPortion) {
            	System.out.println(line);
            }
            System.out.println("Balance::"+balance);
            for(String custId:customerPortion) {
            	long currCustId = Long.parseLong(custId.strip());
            	Wallet currCust =  new Wallet(currCustId, balance);
            	walletMap.put(currCustId, currCust);
            }
            System.out.println(walletMap);
            
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
