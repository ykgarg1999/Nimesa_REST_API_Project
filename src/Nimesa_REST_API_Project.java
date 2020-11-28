import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.json.simple.*;
public class Nimesa_REST_API_Project {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	     try {
	    	 while(true) {
	    		 System.out.println("1. Get Weather");
	    		 System.out.println("2. Get Wind Speed");
	    		 System.out.println("3. Get Pressure");
	    		 System.out.println("0. Exit");
	    		 System.out.print("Enter the index value according to the data needed : ");
	    		 int num = sc.nextInt();
	    		 sc.nextLine();
	    		 if(num==0) {
	    			 break;
	    		 }
	    		 else {
	    			 System.out.print("Enter the date in format[YYYY-MM-DD HR:MN:SC] : ");
		    		 String date = sc.nextLine();
		    		 get_json(num,date);
	    		 }		 
	    	 }
	        } catch (Exception e) {
	         e.printStackTrace();
	       }
	     }
		   
	public static void get_json(int num, String date) throws Exception {
	     String url = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
	     URL obj = new URL(url);
	     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	     
	     BufferedReader in = new BufferedReader(
	             new InputStreamReader(con.getInputStream()));
	     String inputLine;
	     StringBuffer response = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	response.append(inputLine);
	     }
	     in.close();

	     Object ob=JSONValue.parse(response.toString());  
	     JSONObject jsonObject = (JSONObject) ob; 
	     JSONArray ja = (JSONArray) jsonObject.get("list");
	     
	     Iterator itr2 = ja.iterator();
	     Iterator<Map.Entry> itr1;
	     while (itr2.hasNext())  
	        {    boolean val = false;
	             boolean val2=false;
	            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
	            while (itr1.hasNext()) { 
	                Map.Entry pair = itr1.next(); 
	                if(pair.getValue().equals(date)) {
	                	System.out.println();
	                	val = true;
	                }
	                if(val){
	                	val2=true;
	                	if(pair.getKey().equals("main")){
	                		if(num==1){
	                			temperature(pair.getValue());
		                		return;
	                			
	                		}
	                		if(num==3) {
	                			pressure(pair.getValue());
		                		return;
	                		}
	                	}
	                }
	                if(val2 && num==2){
	                	if(pair.getKey().equals("wind")){
	                		String windVar = pair.getValue().toString();
	                		wind_Speed(windVar);
	                		return;	
	                	}    	
	                }
	            } 
	        }
	   }

	private static void wind_Speed(String windVar) {
		String arr[] = String.valueOf(windVar).split(",");
		String windSpeed="";
		int itr = arr[1].indexOf(":");
		String windspeed = arr[1].substring(itr+1);
		System.out.println("Wind Speed : "+windspeed.replace("}", "")+"\n");
		
		
	}

	private static void pressure(Object value) {
		String press="";
		String arr[] = String.valueOf(value).split(",");
		for(int i=0;i<arr.length;i++){
			if(arr[i].contains("pressure")){
				String val = arr[i];
				int index = val.indexOf(":");
				
				press = val.substring(index+1);
			}
		}
		System.out.println("Pressure : "+press+"\n");
	}
	
	private static void temperature(Object value) {
		String arr[] = String.valueOf(value).split(",");
		int itr = arr[0].indexOf(":");
		String temp = arr[0].substring(itr+1);
		System.out.println("Temperature : "+temp+"\n");
		
	}
}