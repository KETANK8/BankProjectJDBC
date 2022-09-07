/**
 * @author Ketan Kumar
 * Illustrating BANK MANAGEMENT SYSTEM TO CREATE BANK ACCOUNT ADD ACCOUNT DETAILS TO DATABASE AND PRINT DATA OF ONE OR ALL ACCOUNT USING DATA ACCESS OBJECT
 * PERFORMING MONEY DEPOSIT AND MONEY WITHDRAWING FROM ACCOUNT BALANCE
 *
 */
package BankingDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BankDao {
	
Connection con = null;
	
	//Method 1
	// METHOD TO SET CONNECTION WITH DATABASE
	public void connect()throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","142307");
	}
	
	//Method 2
	// METHOD TO ADD NEW BANK ACCOUNT IN DATABASE
	public void setAccount(BankCustomer csm) throws Exception {
		String query = ("insert into customerDetail(csmName,csmPassword,csmAge,csmPhone,csmAccBal) values(?,?,?,?,?)");
		PreparedStatement prepstm = con.prepareStatement(query);
		
		//Putting value on the index of question mark
		prepstm.setString(1,csm.csmName); // PUTTING NAME ON FIRST MARK
		prepstm.setString(2,csm.csmPassword);// PUTTING PASSWORD ON SECOND MARK
		prepstm.setInt(3,csm.csmAge); // PUTTING AGE ON THIRD MARK
		prepstm.setString(4,csm.csmPhone); // PUTTING PHONE NO ON FOURTH MARK
		prepstm.setInt(5,csm.csmAccBal); // PUTTING ACCOUNT BALANCE ON FIFTH MARKS
		
		int count = prepstm.executeUpdate();// UPDATING/ADDING THE BANK ACCOUNT DATA IN DATABASE
		System.out.println(count+" rows affected.");
	}
	
	//Method 3
	// METHOD TO PRINT AN ACCOUNT DETAIL BASED ON ACCOUNT NO
	public BankCustomer getAccount(int csmId) throws Exception {
		BankCustomer csm = new BankCustomer();
		csm.csmId = csmId;
		String query = "select * from customerDetail where csmId = "+csmId;
		Statement stm = con.createStatement();
		ResultSet set = stm.executeQuery(query);set.next();
		
		// ACCESSING DATA OF BANK ACCOUNT
		csm.csmName = set.getString(2);// GIVING COLOUMN NO TO ACCESS NAME
		csm.csmPassword=set.getString(3);// GIVING COLOUMN NO TO ACCESS PASSWORD
		csm.csmAge = set.getInt(4);// GIVING COLOUMN NO TO ACCESS AGE
		csm.csmPhone = set.getString(5);// GIVING COLOUMN NO TO ACCESS PHONE NO
		csm.csmAccBal = set.getInt(6);// GIVING COLOUMN NO TO ACCESS ACCOUNT BALANCE
		return csm;// RETURNING ACCCOUNT OBJECT
	}
	
	// METHOD 4
	// METHOD TO CHECK USER LOGIN CREDENTIALS
	// IF PASSWORD AND USER NAME CORRECT GIVE ACCESS OF BANK ACCOUNT TO USER
	public int userLogin(String csmName,String csmPswrd) throws Exception{
		
		Statement stm = con.createStatement();
		ResultSet set = stm.executeQuery("select * from customerDetail where csmName = '"+csmName+"'");
		
		if(set.next()) {
			
			String csmPassword = set.getString(3);
			if(csmPassword.equals(csmPswrd)) {
				int csmId = set.getInt(1);
				System.out.println("Login Successfully.");
				return csmId;
			}
			else {
				System.out.println("Incorret UserName or Password!!!");
				return 0;
			}
		}
		else {
			System.out.println("Invalid Account!!!");
			return -1;
		}
	}
	
	// METHOD 5
	// METHOD TO WITHDRAW MONEY FROM USER ACCOUNT BALANCE
	public void withDraw (int csmId,int csmAmount) throws Exception {
		Statement stm = con.createStatement();
		
		// GETTING USER DETAIL THROUGH ACCOUNT NO
		ResultSet set = stm.executeQuery("select * from customerDetail where csmId= "+csmId);
		set.next();
		int balance = set.getInt(6);// ACCESSING USER ACCOUNT BALANCE
		if(balance>csmAmount) {
			balance -= csmAmount;// WITHDRAWING MONEY FROM ACCOUNT BALANCE
			Statement wdrawStm = con.createStatement();
			
			// UPDATING USER ACCOUNT BALANCE
			int amount = wdrawStm.executeUpdate("update customerDetail set csmAccBal ="+balance+" where csmId ="+csmId);
			System.out.println("Updated Account Balance : "+balance);
		}
		else {
			System.out.println("Insufficient Account Balance.");
		}
	}
	
	// METHOD 6
	// METHOD TO ADD OR DEPOSIT MONEY IN USER ACCOUNT BALANCE
	public void Deposit (int csmId,int csmAmount) throws Exception {
		Statement stm = con.createStatement();
		
		// GETTING USER DETAIL THROUGH ACCOUNT NO
		ResultSet set = stm.executeQuery("select * from customerDetail where csmId= "+csmId);
		set.next();
		int balance = set.getInt(6);// ACCESSING USER ACCOUNT BALANCE
			balance += csmAmount;// DEPOSITING MONEY IN ACCOUNT BALANCE 
			Statement wdrawStm = con.createStatement();
			
			// UPDATING USER ACCOUNT BALANCE
			int amount = wdrawStm.executeUpdate("update customerDetail set csmAccBal ="+balance+" where csmId ="+csmId);
			System.out.println("Updated Account Balance : "+balance);
	}
	
	
	//Method 7
	// METHOD TO PRINT ALL EMPLOYEES DETAILS
	public void getAllData(String csmName,String csmPswrd) throws SQLException {
		
		if(csmName.equals("root")) {
			if(csmPswrd.equals("142307")) {
				Statement stm = con.createStatement();
		
				ResultSet set = stm.executeQuery("select * from customerDetail");
		
				System.out.println("\n\n----------Account List----------");
		
				// ACCESSING DATABASE TO THE LAST ROW
				while(set.next()) {
					String std= "Account No: "+set.getInt("csmId")+"  Account Holder Name: "+set.getString("csmName")+"  Age: "+set.getInt("csmAge")+"  Phone No: "+set.getString("csmPhone")+"  Account Balance: "+set.getInt("csmAccBal")+"\n";
					System.out.println(std);// PRINTING THE ACCOUNT DATA
				}
		
			}
			// IF GIVEN PASSWORD DOES NOT MATCH WITH ADMIN PASSWORD
			else {System.out.println("Wrong Password!!!");}
		}
		// IF GIVEN NAME DOES NOT MATCH WITH ADMIN NAME
		else {System.out.println("Wrong User Name!!!");}
	}


}
