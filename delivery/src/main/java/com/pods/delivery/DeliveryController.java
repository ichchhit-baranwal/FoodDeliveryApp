package com.pods.delivery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

/**
 * The class serves as entry point to any 
 * request made to the service. It is auto-detected  
 * as the controller through classpath scanning 
 */
@RestController
public class DeliveryController {
	
	/**
	 * restaurantMap is ConcurrentHashMap is the in memory data structure that has
	 * been used storing all the restaurant so that id based look up
	 * can be made easily and faster. 
	 */
	private static ConcurrentHashMap<Long, Restaurant> restaurantMap = new ConcurrentHashMap<Long, Restaurant>();
	/**
	 * deliverAgentMap to store in memory
	 * agents information which supports quick and 
	 * easy lookups based on agentId
	 */
	private static ConcurrentHashMap<Long, DeliveryAgent> deliveryAgentMap = new ConcurrentHashMap<Long, DeliveryAgent>();
	/**
	 * orderMap to orders information in memory 
	 * and supports easy and fast lookup based on orderid.
	 */
	private static ConcurrentHashMap<Long, Order> orderMap = new ConcurrentHashMap<Long, Order>();
	private static final String INITIAL_FILE_PATH="/initialData.txt";
//	private static final String INITIAL_FILE_PATH="F:\\MTechCourse\\Principal Of Distribution Software\\PoDS_Project_1\\initialData.txt";
//	private static final String RESTAURANT_URL="http://localhost:8080/";
//	private static final String WALLET_URL="http://localhost:8082/";
	private static final String RESTAURANT_URL="http://host.docker.internal:8080/";
	private static final String WALLET_URL="http://host.docker.internal:8082/";
	private static final int START_ORDER_ID=1000;
	private static long currentOrderId = 0;
	static {
        initialize();
	}
    
	/**
	 * Returns the available delivery agent
	 * with lowest agentId
	 * @return agent
	 */
	private static DeliveryAgent getAvailableDeliveryAgent() {
		DeliveryAgent agent = null;
		long min_id = Integer.MAX_VALUE;
		for(long id : deliveryAgentMap.keySet()) {
			DeliveryAgent curr = deliveryAgentMap.get(id);
			if(curr.getStatus().equals(DeliveryAgent.AVAILABLE)) {
				if(min_id > curr.getAgentId()) {
					agent = curr;	
					min_id = curr.getAgentId();
				}
			}
		}
		return agent;
	}
	
	/**
	 * Returns the unassigned order with
	 * lowest order id
	 * @return order
	 */
	private static Order getUnassignedOrders() {
		Order order = null;
		long min_id = Integer.MAX_VALUE;
		for(long id : orderMap.keySet()) {
			Order curr = orderMap.get(id);
			if(curr.getStatus().equals(Order.UNASSIGNED)) {
				if(min_id > curr.getOrderId()) {
					order = curr;	
					min_id = curr.getOrderId();
				}
			}
		}
		return order;
	}
	
	
	/**
	 * This method serves the request to the postfix
	 * url /requestOrder and creates the order based
	 * on series of conditions and returns order 
	 * as a part of response or error 410 if order
	 * could not be created 
	 * @param data
	 * @return ResponseEntity as response to client
	 */
    @PostMapping("/requestOrder")
    public ResponseEntity<ReqOrder> requestOrder(@RequestBody ReqOrderDTO data) {
    	System.out.println(data);
    	ReqOrder returnObj = null;
    	HttpStatus status_code = HttpStatus.GONE;
    	Restaurant currRes = restaurantMap.get(data.getRestId());
    	System.out.println("Restaurant returned from backend"+currRes);
    	double billAmount = 0;
    	if(currRes != null) {
    		Item currItem = currRes.getItem(data.getItemId());
    		if(currItem != null && data.getQty() >= 0) {
    			billAmount = currItem.getPrice() * (double)data.getQty();
    		}
    	}
    	if(billAmount > 0) {
    		ConcurrentHashMap<String, String> params = new ConcurrentHashMap<>();
    		ConcurrentHashMap<String, String> resParams = new ConcurrentHashMap<>();
    		params.put("custId", String.valueOf(data.getCustId()));
    		params.put("amount", String.valueOf(billAmount));
    		System.out.println("Before deduct");
    		HttpStatus out = generatePostRequest(WALLET_URL, "deductBalance", params);
    		if(out.compareTo(HttpStatus.CREATED) == 0) {
    			resParams.clear();
    			resParams.put("restId", ""+data.getRestId());
    			resParams.put("itemId", ""+data.getItemId());
    			resParams.put("qty", ""+data.getQty());
    			HttpStatus outResServ = generatePostRequest(RESTAURANT_URL, "acceptOrder", resParams);
    			System.out.println("Out restaurant service "+outResServ);
    			if(outResServ.equals(HttpStatus.CREATED)){
    				Order obj = new Order(currentOrderId);
    				DeliveryAgent avlAgent = getAvailableDeliveryAgent();
    				if(avlAgent != null) {
    					obj.setStatus(Order.ASSIGNED);
    					obj.setDeliveryAgent(avlAgent);
    					avlAgent.setStatus(DeliveryAgent.UNAVAILABLE);
    				}
    				orderMap.put(currentOrderId, obj);
    				currentOrderId++;
    				status_code = HttpStatus.CREATED;
    				returnObj = new ReqOrder();
    				returnObj.setOrderId((int)obj.getOrderId());
    			}
    			else {
    				System.out.println("Else of restaurant service");
    				generatePostRequest(WALLET_URL, "addBalance", params);
    				status_code = HttpStatus.GONE;
    			}
    		}
    		else {
    			System.out.println("Balance cannot be deducted");
    			status_code = HttpStatus.GONE;
    		}  		
    	}

    	return new ResponseEntity<>(returnObj,status_code);
    }
    
    /**
     * Use to make post request to the url with urlPostfix
     * with parameters as json object 
     * @param url
     * @param urlPostfix
     * @param parameters
     * @return
     */
    public HttpStatus generatePostRequest(String url, String urlPostfix, ConcurrentHashMap<String, String> parameters) {
    	URL urlObj;
    	HttpStatus statusCode = HttpStatus.GONE;
		try {
			urlObj = new URL(url+urlPostfix);
			HttpURLConnection con = (HttpURLConnection)urlObj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			String jsonInputString = "{";
            for(String res:Collections.list(parameters.keys()))
            	jsonInputString = jsonInputString + "\""+res+"\":"+parameters.get(res)+",";
            jsonInputString = jsonInputString.substring(0, jsonInputString.length() - 1);
            jsonInputString = jsonInputString +"}";
            System.out.println(jsonInputString);
            try(OutputStream os=con.getOutputStream()){
            	byte[] input = jsonInputString.getBytes("utf-8");
            	os.write(input);
            }
            statusCode = HttpStatus.valueOf(con.getResponseCode());
            System.out.println("Request Made "+url+urlPostfix);
            System.out.println("Json sent "+jsonInputString);
            System.out.println("Status code redturned "+statusCode);
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))){
            	String response = "";
            	String responseLine=null;
            	while((responseLine = br.readLine())!=null)
            		response = response + responseLine;
            	
            }

		} catch (IOException e) {
			System.out.println(e.getMessage()+" "+statusCode);
		}
    
    	return statusCode;
    }

    /**
     * Assigns agent to unassigned order
     * with minimum order id if available
     * else makes sets agents status as
     * available
     * @param agent
     */
    private void assignAgent(DeliveryAgent agent) {
    	Order unAss = getUnassignedOrders();
    	if(unAss != null) {
    		System.out.println(unAss);
    		unAss.setDeliveryAgent(agent);
			unAss.setStatus(Order.ASSIGNED);
			agent.setStatus(DeliveryAgent.UNAVAILABLE);
    	}else {
    		agent.setStatus(DeliveryAgent.AVAILABLE);
    	}
    }
    
    /**
     * Change status of the agent
     * to available if no unassigned order available
     * else assign order and change status to available
     * on request made to /agentSignIn endpoint
     * @param agentId
     * @return ResponseEntity as response to client
     */
    @PostMapping("/agentSignIn")
    public ResponseEntity<String> agentSignIn(@RequestBody ReqAgent agentId){
    	DeliveryAgent currAgent = deliveryAgentMap.get((long)agentId.getAgentId());
    	if(currAgent!=null && currAgent.getStatus().equals(DeliveryAgent.SIGNEDOUT)) {
    		assignAgent(currAgent);
    	}
    	System.out.println(currAgent);
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    /**
     * Signs out the agent if available else do nothing
     * @param agentId
     * @return RepsonseEntity to the requesting client
     */
    @PostMapping("/agentSignOut")
    public ResponseEntity<String> agentSignOut(@RequestBody ReqAgent agentId){
    	DeliveryAgent currAgent = deliveryAgentMap.get((long)agentId.getAgentId());
    	if(currAgent!=null && currAgent.getStatus().equals(DeliveryAgent.AVAILABLE)) {
    		currAgent.setStatus(DeliveryAgent.SIGNEDOUT);
    	}
    	System.out.println(currAgent);
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    /**
     * Change status of the order id passed as parameter
     * to delivered when request is made to /orderDelivered 
     * end point and assigns the agent to new order
     * if any unassigned order is present
     * @param order
     * @return ResponseEntity as response to requesting client
     */
    @PostMapping("/orderDelivered")
    public ResponseEntity<String> orderDelivered(@RequestBody ReqOrder order){
    	Order orderObj = orderMap.get((long)order.getOrderId());
    	if(orderObj!=null && orderObj.getStatus().equals(Order.ASSIGNED)) {
    		orderObj.setStatus(Order.DELIVERED);
    		assignAgent(orderObj.getDeliveryAgent());
    	}
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    
    /**
     * fetches the order details for the order id
     * returns it in the response body else 404 is returned
     * @param num
     * @return ResponseEntity wrapping order object
     */
    @GetMapping("/order/{num}")
    public ResponseEntity<ResOrder> order(@PathVariable long num){
    	Order currOrder = orderMap.get(num);
    	ResOrder responseOrder=null;
    	HttpStatus status_code = HttpStatus.NOT_FOUND;
    	if(currOrder != null) {
    		status_code = HttpStatus.OK;
    		responseOrder = new ResOrder(currOrder.getOrderId(), currOrder.getStatus(),
    				(currOrder.getDeliveryAgent() != null)?currOrder.getDeliveryAgent().getAgentId():-1);
    	}
    	return new ResponseEntity<ResOrder>(responseOrder, status_code);
    }
    
    
    /**
     * fetches the agent details for the agent id
     * returns it in the response body
     * @param num
     * @return ResponseEntity wrapping order object
     */
    @GetMapping("/agent/{num}")
    public DeliveryAgent agent(@PathVariable long num) {
    	DeliveryAgent agent = deliveryAgentMap.get(num) ;
    	if(agent == null)
    		agent = new DeliveryAgent(-1);
    	return agent;
    }
    
    
    /**
     * initialize the data in the restauranttMap and deliveryAgentMap
     * after reading data from the file INITIAL_FILE_PATH    
     */
    public static void initialize() {
    	Path filePath = Paths.get(INITIAL_FILE_PATH);
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            List<String> restaurantPortion = new ArrayList<String>();
            int countStar=0;
            for(String line: lines) {
            	if( line.charAt(0) != '*' && countStar==0) 
            		restaurantPortion.add(line);
            	else if(line.charAt(0) != '*' && countStar==1) {
            		long currId = Long.parseLong(line.strip());
            		DeliveryAgent currAgent = new DeliveryAgent(currId);
            		deliveryAgentMap.put(currId, currAgent);
            	}
            	else if(line.charAt(0) != '*' && countStar==2) 
            		break;
            	else
            		countStar++;
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
            
            orderMap.clear();
            
            for(Long res:Collections.list(restaurantMap.keys()))
            	System.out.println(restaurantMap.get(res));
            
            System.out.println(deliveryAgentMap);
            
            
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        currentOrderId = START_ORDER_ID;
//        System.out.println("Expected output");
//        getAvailableDeliveryAgent();
    }

    /**
     * reInitialize the walletMap to original condition
     * @return ResponseEntity as the response to client
     */
    @PostMapping("/reInitialize")
    public ResponseEntity<String> reInitialize(HttpServletResponse response) {
    	initialize();
    	return new ResponseEntity<String>(HttpStatus.CREATED);

    }
}
