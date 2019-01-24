package net.prac.onlineshopping.scrappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class iPriceScrapper implements ScrapperInterface{

	@Override
	public List<Item> parse(String searchString) throws IOException {
		String theUrl="https://iprice.my/search/?term="+searchString; 	//URL of the iprice website 
		 Connection.Response response=null;
			 response=Jsoup.
			 connect(theUrl).
			 followRedirects(true).
			 userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").execute();
		 Document doc = null;
        doc =  response.parse();
        Elements itemS = doc.getElementById("shop").children();			//getting the element which contain all the product
        ArrayList<Item> result=new ArrayList<Item>(itemS.size());
        for (int i = 0; i < 45; i++) {
       	 try {
        Element img=itemS.get(0).child(1).child(0).child(i).child(0).child(0).child(0).child(1).child(0).child(0);
        Element nam=itemS.get(0).child(1).child(0).child(i).child(0).child(0).child(1).child(0);
        Element pric=nam.child(1).child(0);
        String title=nam.attr("data-vars-lb");
            String PriceInString=pric.text().substring(2).replaceAll(",", "");
            double price=Double.parseDouble(PriceInString);
        	String href=nam.attr("href");
        	String image=img.attr("src");
            result.add(new Item("Iprice", title, price, image, href));
       	 } catch (IndexOutOfBoundsException e) {
			}
       	 	catch(NullPointerException ex) {
       	 		
       	 	}
        }
		return result;
	}

}
