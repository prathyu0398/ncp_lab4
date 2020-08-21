import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import java.io.*;
public class register_and_signup {
	static Connection con;
	static Scanner sc;

	public static void main(String[] args)throws ClassNotFoundException, SQLException, FileNotFoundException {
		// TODO Auto-generated method stub
		sc=new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/regandlogin","root","root"); 
		while(true) {
			try {
				String query=sc.nextLine();
				String[] argus=query.split(" ");
				switch(argus[0]) {
				case("help"):{
					System.out.println("Commands possible:");
					System.out.println("1. login <username> <password>");
					System.out.println("2. Register");				
				}break;
				case("login"):{
					boolean succ= logintocinfo(argus[1],argus[2]);
					if(succ){
						System.out.println("Welcome to Cinfo");						
					}
					else {
						System.out.println("Wrong Credentials");
					}
				}break;
				case("Register"):{
					boolean succ=registertocinfo();
					if(succ) {
						System.out.println("Hello! Welcome to Cinfo");
					}
					else {
						System.out.println("User Already Exists!");
					}
				}
				}
				
			}
			catch (SQLException se) {
                se.printStackTrace();
                sc.close();
            }
			
			
		}
		
		

	}
	public static boolean logintocinfo(String uname, String pwd) throws SQLException {
		PreparedStatement pst=con.prepareStatement("select * from login where username=? and pwd=?");
		pst.setString(1, uname);
		pst.setString(2, pwd);
		ResultSet rs= pst.executeQuery();
		if(rs.next()==false) {
			return false;
		}
		return true;
	}
	public static boolean registertocinfo() throws SQLException, FileNotFoundException{
		System.out.println("Enter your username:");
		String uname=sc.next();
		if(userexits(uname)) {
			System.out.println("User already exits! Choose other username");
			uname=sc.next();						
		}
		System.out.println("Enter your first name:");
		String firstname=sc.next();
		System.out.println("Enter your last name:");
		String lastname=sc.next();
		System.out.println("Enter your password(Minimum of 8 character includes special characters):");
		String pwd=sc.next();
		if(!crctpwd(pwd)) {
			System.out.println("Please enter password as per mentioned:");	
			pwd=sc.next();
		}
		System.out.println("Enter your Email:");
		String email=sc.next();
		if(validemail(email)) {
			System.out.println("Email already exits! Please check your credentails");	
			pwd=sc.next();			
		}
		System.out.println("Enter date of birth in format (DD/MM/YYYY)");
		String dob=sc.next();
		System.out.println("Enter your blood group:");	
		String b_grp=sc.next();
		System.out.println("Enter your phone number:");			
		long phn_num=sc.nextLong();
		System.out.println("Enter your Gender(M/F):");	
		String gen=sc.next();
		String filename = "C:\\Users\\dell\\Desktop\\Prathyusha_elevator system.txt";
        File file = new File(filename);
        FileInputStream input = new FileInputStream(file);
        PreparedStatement pst = con.prepareStatement("insert into signup values (?,?,?,?,?,?,?,?,?,?)");
        pst.setString(1, uname);
        pst.setString(2, firstname);
        pst.setString(3, lastname);
        pst.setString(4,pwd);
        pst.setString(5, email);
        pst.setString(6, dob);
        pst.setString(7, b_grp);
        pst.setLong(8, phn_num);
        pst.setString(9, gen);
        pst.setBlob(10, input);
        
        int rows = pst.executeUpdate();

        if (rows == 0)
            return false;   
        return true;  
        
	}
	public static boolean userexits(String uname) throws SQLException  {
		PreparedStatement pst = con.prepareStatement("select * from signup where username = ?");
        pst.setString(1, uname);

        ResultSet rs = pst.executeQuery();
        
        if (rs.next() == false)
            return false;

        return true;
	}
	public static boolean crctpwd(String pwd) throws SQLException{
		if(pwd.length()==8) {
			return true;
		}
		return false;
	}
	public static boolean validemail(String email) throws SQLException{
		PreparedStatement pst = con.prepareStatement("select * from signup where email = ?");
        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();
        
        if (rs.next() == false)
            return false;

        return true;
	}
	
	

	

}
