package net.prac.onlineshopping.controller;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.prac.onlineshopping.bean.User;
import net.prac.onlineshopping.model.DataProvider;
import net.prac.onlineshopping.scrappers.EbayScrapper;
import net.prac.onlineshopping.scrappers.ExpansysScrapper;
import net.prac.onlineshopping.scrappers.Item;
import net.prac.onlineshopping.scrappers.iPriceScrapper;

@Controller
public class pageController {
	
	
	@RequestMapping(value= {"/","/home","/index"})
	public ModelAndView index() {
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("title","Home");
		mv.addObject("UserClickHome",true);
		return mv;	
	}
	
	@RequestMapping(value= "/About")
	public ModelAndView About() {
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("title","About Us");
		mv.addObject("UserClickAbout",true);
		return mv;	
	}
	
	@RequestMapping(value= "/Login")
	public ModelAndView Login() {
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("title","Login");
		mv.addObject("UserClickLogin",true);
		return mv;	
	}
	
	@RequestMapping(value= "/Register", method=RequestMethod.GET)
	public ModelAndView Register(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("title","Register");
		mv.addObject("UserClickRegister",true);
		return mv;	
	}
	

	@RequestMapping(value= "/registerProcess")
	public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		ModelAndView mv= new ModelAndView("page");
		String UserName=request.getParameter("UserName");
		String FullName=request.getParameter("FullName");
		String Password=request.getParameter("Password");
		User user=new User();
		DataProvider dProvider =new DataProvider();
		user.setUserName(UserName);
		user.setFullName(FullName);
		user.setPassword(Password);
		String s2=dProvider.writeData(user);
		if(s2.equalsIgnoreCase("")) {
			mv.addObject("UserClickLogin",true);
			mv.addObject("title","Login");
		}
		else {
			mv.addObject("error",s2);
			mv.addObject("UserClickRegister",true);
			mv.addObject("title","Register");
		}
		
		return mv;
		}

	@RequestMapping(value= "/loginProcess")
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		ModelAndView mv= new ModelAndView("page");
		mv.addObject("title","Login");
		String UserName=request.getParameter("UserName");
		String FullName=request.getParameter("FullName");
		String Password=request.getParameter("Password");
		User user=new User();
		DataProvider dProvider =new DataProvider();
		user.setUserName(UserName);
		user.setFullName(FullName);
		user.setPassword(Password);
		String[] s1=dProvider.readData(user);
		if(s1[0].equalsIgnoreCase("success")) {
			mv.addObject("Name",s1[1]);
			mv.addObject("UserClickHome",true);
		}else {
			mv.addObject("Wrong","Wrong UserName or Password");
			mv.addObject("UserClickLogin",true);
		}
		return mv;
		}
	@RequestMapping(value= "/Search")
	public ModelAndView Search(@RequestParam(value="query")String queryString) throws IOException {
		ModelAndView mv= new ModelAndView("page");
		ExpansysScrapper expansys=new ExpansysScrapper();
		EbayScrapper ebay = new EbayScrapper();
		iPriceScrapper iPrice =new iPriceScrapper();
		List<Item> expansysProducts = new ArrayList<Item>();
		List<Item> iPriceProducts = new ArrayList<Item>();
		List<Item> AlList = new ArrayList<Item>();
		expansysProducts = expansys.parse(queryString);
		iPriceProducts=iPrice.parse(queryString);
		AlList.addAll(iPriceProducts);
		AlList.addAll(expansysProducts);
		mv.addObject("Items",AlList);
		mv.addObject("searchString",queryString);
		mv.addObject("title",queryString);
		mv.addObject("UserClickHome",true);
		return mv;	
	}
	


	
	//@RequestMapping(value= "/QWER", produces = "application/json")
//	@RequestMapping("/search")
//	public HashMap<String, ArrayList<Item>> Search(@RequestParam(value="query", required=false)String quertString) {
//		if(quertString==null) {
//			quertString="Hello There";
//		}
//		ArrayList<Item> result = new ArrayList<Item>(5);
//        result.add(new Item("EBay", "iPhone X 64GB", 3300, "", ""));
//        result.add(new Item("EBay", "iPhone XS MAX 64GB", 3300, "", ""));
//        result.add(new Item("EBay", "iPhone XS MAX 254GB", 3300, "", ""));
//		
//        HashMap<String, ArrayList<Item>> rtn = new LinkedHashMap<String, ArrayList<Item>>();
//        rtn.put("results", result);
//        
//        return rtn;	
//	}
//	
//	@RequestMapping(value= {"/test"})
//	public ModelAndView test(@RequestParam(value="greeting", required=false)String greeting) {
//		if(greeting==null) {
//			greeting="Hello There";
//		}
//		ModelAndView mv= new ModelAndView("page");
//		mv.addObject("greetings",greeting);
//		return mv;
//	}
//	
//	
	
//	@RequestMapping(value= {"/test"})
//	public ModelAndView test(@PathVariable("greeting")String greeting) {
//		if(greeting==null) {
//			greeting="Hello There";
//		}
//		ModelAndView mv= new ModelAndView("page");
//		mv.addObject("greeting",greeting);
//		return mv;
//	}
//	
}
