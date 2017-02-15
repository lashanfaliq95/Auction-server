import java.io.*;
import java.util.*;


public class StockItemsDB{

class Item{//to be able to put two values to teh hashmap
 String name;
 String price;

	public Item(String name,String price){
    this.name=name;
    this.price=price;
    }
} 

    public Map<String,Item> stock; 
    private String [] fields; 

    public StockItemsDB(String cvsFile, String symbol, String name, String price)  { 
	FileReader fileRd=null; 
	BufferedReader reader=null; 

	try { 
	    fileRd = new FileReader(cvsFile); 
	    reader = new BufferedReader(fileRd); 

	    
	    String header = reader.readLine(); 
	    fields = header.split(",");

	 
	     int keyIndex = findIndexOf(symbol);  
	     int nameIndex = findIndexOf(name); 
	     int priceIndex = findIndexOf(price);  

	    if(keyIndex == -1 || nameIndex == -1 || priceIndex == -1) 
		throw new IOException("CVS file does not have data"); 
	    
	    stock = new HashMap<String, Item>(); 

	    
	     
	    String [] tokens; 
	    for(String line = reader.readLine(); 
		line != null; 
		line = reader.readLine()) { 
		tokens = line.split(","); 
		stock.put(tokens[keyIndex],new Item(tokens[nameIndex],tokens[priceIndex])); 
	    }
	    
	    if(fileRd != null) fileRd.close();
	    if(reader != null) reader.close();
	    
	    // I can catch more than one exceptions 
	} catch (IOException e) { 
	    System.out.println(e);
	    System.exit(-1); 
	} catch (ArrayIndexOutOfBoundsException e) { 
	    System.out.println("Malformed CSV file");
	    System.out.println(e);
	}
    }

    private int findIndexOf(String key) { 
	for(int i=0; i < fields.length; i++) 
	    if(fields[i].equals(key)) return i; 
	return -1; 
    }
	
    // public interface 
    public String findName(String key) { 
	return stock.get(key).name; 
    }

    public String findPrice(String key) {   
	return stock.get(key).price; 
    }

    public void setPrice(String key,String price){
    	Item obj = new Item(findName(key),price);
    	stock.put(key,obj);
    }

}
	    