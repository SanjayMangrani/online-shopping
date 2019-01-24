package net.prac.onlineshopping.scrappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class EbayScrapper implements ScrapperInterface {
	@Override
	public List<Item> parse(String searchString) throws IOException {
		String theUrl="https://www.ebay.com.my/sch/i.html?_odkw=apple&_osacat=0&_from=R40&_trksid=m570.l1313&_nkw=" + searchString + "&_sacat=0";
		System.out.println(theUrl);
		 Connection.Response response=null;
			 response=Jsoup.
			 connect(theUrl).
    followRedirects(true).
    userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").execute();

		 Document doc = null;
             doc =  response.parse();
        System.out.println("response succes");
         Elements itemS = doc.getElementById("ListViewInner").children();
         ArrayList<Item> result=new ArrayList<Item>(itemS.size());
//         System.out.println(linl.attr("href"));
         for (int i = 0; i < itemS.size(); i++) {
        	 try {
        	Element img = itemS.get(i).child(0).child(0).child(0).child(0);
        	 Element lin = itemS.get(i).child(0).child(0).child(0);
             Element tit = itemS.get(i).child(1).child(0);
             Element pri = itemS.get(i).child(2).child(0).child(0);
             String title=tit.text().toString();
             String PriceInString=pri.text();
             PriceInString=PriceInString.substring(PriceInString.lastIndexOf(' '));
             double Price=Double.parseDouble(PriceInString);
             String url=lin.attr("href").toString(); 
             String image=img.attr("src");
             result.add(new Item("EBay", title, Price, image, url));
             
        	 } catch (IndexOutOfBoundsException e) {
 			}
        	 catch(NumberFormatException nf) {
        		 
        	 }
         }
		return result;
	}
}
