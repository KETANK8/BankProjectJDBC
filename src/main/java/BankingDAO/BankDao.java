/**
 * @author Ketan Kumar
 * Illustrating BANK MANAGEMENT SYSTEM TO CREATE BANK ACCOUNT ADD ACCOUNT DETAILS TO DATABASE AND PRINT DATA OF ONE OR ALL ACCOUNT USING DATA ACCESS OBJECT
 * PERFORMING MONEY DEPOSIT AND MONEY WITHDRAWING FROM ACCOUNT BALANCE
 *
 */
package BankingDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
	public int setAccount(BankCustomer csm) throws Exception {
		 
		String query = "select * from customerDetail where csmName = '"+csm.csmName+"'";
		Statement stm = con.createStatement();
		ResultSet set = stm.executeQuery(query);
		if(set.next()) {
			return -1;
		}
		else {
		String query2 = ("insert into customerDetail(csmName,csmPassword,csmAge,csmPhone,csmAccBal) values(?,?,?,?,?)");
		PreparedStatement prepstm = con.prepareStatement(query2);

		//Putting value on the index of question mark
		prepstm.setString(1,csm.csmName); // PUTTING NAME ON FIRST MARK
		prepstm.setString(2,csm.csmPassword);// PUTTING PASSWORD ON SECOND MARK
		prepstm.setInt(3,csm.csmAge); // PUTTING AGE ON THIRD MARK
		prepstm.setString(4,csm.csmPhone); // PUTTING PHONE NO ON FOURTH MARK
		prepstm.setInt(5,csm.csmAccBal); // PUTTING ACCOUNT BALANCE ON FIFTH MARKS
		
		int count = prepstm.executeUpdate();// UPDATING/ADDING THE BANK ACCOUNT DATA IN DATABASE
		
		return count;
		}
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
				return csmId;
			}
			else {
				return 0;
			}
		}
		else {
			return -1;
		}
	}
	
	// METHOD 5
	// METHOD TO WITHDRAW MONEY FROM USER ACCOUNT BALANCE
	public int withDraw (int csmId,int csmAmount) throws Exception {
		Statement stm = con.createStatement();
		
		// GETTING USER DETAIL THROUGH ACCOUNT NO
		ResultSet set = stm.executeQuery("select * from customerDetail where csmId= "+csmId);
		set.next();
		int balance = set.getInt(6);// ACCESSING USER ACCOUNT BALANCE	
		set.close();
		if(balance>csmAmount) {
			balance -= csmAmount;// WITHDRAWING MONEY FROM ACCOUNT BALANCE
			Statement wdrawStm = con.createStatement();
			
			// UPDATING USER ACCOUNT BALANCE
			wdrawStm.executeUpdate("update customerDetail set csmAccBal ="+balance+" where csmId ="+csmId);
			return balance;
		}
		else {
			return 0;
		}
	}
	
	// METHOD 6
	// METHOD TO ADD OR DEPOSIT MONEY IN USER ACCOUNT BALANCE
	public int Deposit (int csmId,int csmAmount) throws Exception {
		Statement stm = con.createStatement();
		
		// GETTING USER DETAIL THROUGH ACCOUNT NO
		ResultSet set = stm.executeQuery("select * from customerDetail where csmId= "+csmId);
		set.next();
		int balance = set.getInt(6);// ACCESSING USER ACCOUNT BALANCE
			balance += csmAmount;// DEPOSITING MONEY IN ACCOUNT BALANCE 
			Statement wdrawStm = con.createStatement();
			
			// UPDATING USER ACCOUNT BALANCE
			wdrawStm.executeUpdate("update customerDetail set csmAccBal ="+balance+" where csmId ="+csmId);
			return balance;
	}
	
	//Method 7
	// METHOD TO PRINT ALL EMPLOYEES DETAILS
	public List<BankCustomer> getAllData(String csmName,String csmPswrd) throws SQLException {
		List<BankCustomer> list = new ArrayList<BankCustomer>();
        
		
				Statement stm = con.createStatement();
		
				ResultSet set = stm.executeQuery("select * from customerDetail");
		
				System.out.println("\n\n----------Account List----------");
		
				// ACCESSING DATABASE TO THE LAST ROW
				while(set.next()) {
					BankCustomer csm = new BankCustomer();
					csm.csmId = set.getInt(1);// GIVING COLOUMN NO TO ACCESS ACCOUNT NO
					csm.csmName = set.getString(2);// GIVING COLOUMN NO TO ACCESS NAME
					csm.csmPassword=set.getString(3);// GIVING COLOUMN NO TO ACCESS PASSWORD
					csm.csmAge = set.getInt(4);// GIVING COLOUMN NO TO ACCESS AGE
					csm.csmPhone = set.getString(5);// GIVING COLOUMN NO TO ACCESS PHONE NO
					csm.csmAccBal = set.getInt(6);// GIVING COLOUMN NO TO ACCESS ACCOUNT BALANCE
					list.add(csm);
				}
				return list;
	}	
			
	// METHOD 8
	// PERFORM ACCOUNT DELETION 
	// TAKE ACCOUNT NO, ACCOUNT HOLDER NAME AND ACCOUNT PASSWOORD AS INPUT
	// CHECK LOGIN CREDENTIALS BEFORE DELETING ACCOUNT
	public int deleteAccount(int id,int accNo,String csmName,String csmPswrd) throws Exception {
		
		// CHECKING ACCOUNT NO WITH THE INPUT ACCOUNT NO
		if(id==accNo) {
		Statement stm = con.createStatement();
		ResultSet set = stm.executeQuery("select * from customerDetail where csmId="+id+" and csmName = '"+csmName+"'");
		
			// CHECKING USERNAME
			if(set.next()) {
			
				String csmPassword = set.getString(3);
				set.close();
				// CHECKING PASSWORD
				if(csmPassword.equals(csmPswrd)) {
					Statement Deletestm = con.createStatement();
					Deletestm.executeUpdate("DELETE FROM customerDetail WHERE csmId="+accNo);
					return 1;
				}
				else {
					return 0;
				}
			}
			else {
				return -1;
			}
		}
		else {
			return -2;
		}
	}

	// Method 9
	// METHOD TO CHANGE PASSWORD  WITH NEW PASSWORD
	// LOGIN AGAIN TO CHECK PASSWORD
	public int changePswrd(int id,String csmName,String csmPswrd,String pswrd)throws Exception{
		
		Statement stm = con.createStatement();
		ResultSet set = stm.executeQuery("select * from customerDetail where csmName = '"+csmName+"'");
		
			// CHECKING USERNAME
			if(set.next()) {
			
				String csmPassword = set.getString(3);
				
				// CHECKING PASSWORD
				if(csmPassword.equals(csmPswrd)) {
					if(csmPassword.equals(pswrd)) {
						return 1;
					}
					else{
					Statement Changestm = con.createStatement();
					Changestm.executeUpdate("update customerDetail set csmPassword ="+pswrd+" where csmId ="+id);
					return 0;
					}
				}
				else {
					return -1;
				}
			}
			else {
				return -2;
			}
	}

}
