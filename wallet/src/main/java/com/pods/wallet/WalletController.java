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

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
	private static ConcurrentHashMap<Long, Wallet> walletMap = new ConcurrentHashMap<Long, Wallet>();
	private static String INITIAL_FILE_PATH="/initialData.txt";
	static {
        initialize();
	}
    
    @PostMapping("/addBalance")
    public ResponseEntity<String> addBalance(@RequestBody Wallet data, HttpServletResponse response) {
    	Wallet currCust = walletMap.get(data.getCustId());
    	if(currCust != null && data.getAmount()>=0)
    		currCust.setAmount(currCust.getAmount()+data.getAmount());
    	return new ResponseEntity<String>(HttpStatus.CREATED);
    }
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
    
    @GetMapping("/balance/{num}")
    public Wallet balanceNum(@PathVariable long num) {
    	Wallet currCust = walletMap.get(num);
    	System.out.println(currCust);
    	if(currCust == null)
    		currCust = new Wallet(-1, -1);
    	return currCust;
    }  
        
    
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

    
    @PostMapping("/reInitialize")
    public ResponseEntity<String> reInitialize(HttpServletResponse response) {
    	initialize();
    	return new ResponseEntity<String>(HttpStatus.CREATED);
    }
}
