package net.prac.onlineshopping.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.prac.onlineshopping.bean.User;
public class DataProvider {
	//Register user method in database 
	public String writeData(User user) throws ClassNotFoundException, SQLException {
		boolean flag=false;
		MyDb db=new MyDb();
		Connection con=db.getCon();
		Statement st =con.createStatement();
        ResultSet results=st.executeQuery("select username from users");
        while(results.next()){
            String CheckUser=results.getString("username");
            if(CheckUser.equals(user.getUserName())){
                flag=true;
                return "Cannot Register: "+user.getUserName()+ " UserName is already taken";
            }
        }
        results.close();
        if(!flag) {
		st.executeUpdate("insert into users(username,password,FullName) values('"+user.getUserName()+"','"+user.getPassword()+"','"+user.getFullName()+"')");
        }
		return "";
       }
	
	//Login user method in database
	public String[] readData(User user) throws ClassNotFoundException, SQLException{
		MyDb db=new MyDb();
		ResultSet result;
		Connection con=db.getCon();
		Statement st =con.createStatement();
		result=st.executeQuery("Select username,password,FullName from users");
         while(result.next()){
             String DataId=result.getString("username");
             String DataPass=result.getString("password");
             String DataName=result.getString("FullName");
             if((DataId.equals(user.getUserName()))&&(DataPass.equals(user.getPassword()))){
            	 return new String[]{ "success", DataName };
             }
         }
		return new String[]{ "", ""};
	}
}
