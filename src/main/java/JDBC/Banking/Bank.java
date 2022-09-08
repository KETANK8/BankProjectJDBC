/**
 * @author Ketan Kumar
 * Illustrating BANK MANAGEMENT SYSTEM TO CREATE BANK ACCOUNT ADD ACCOUNT DETAILS TO DATABASE AND PRINT DATA OF ONE OR ALL ACCOUNT USING DATA ACCESS OBJECT
 * PERFORMING MONEY DEPOSIT AND MONEY WITHDRAWING FROM ACCOUNT BALANCE
 *
 */

package JDBC.Banking;

import java.util.List;
import java.util.Scanner;

import BankingDAO.BankCustomer;
import BankingDAO.BankDao;


public class Bank {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int csmId,csmAge,csmAccBal,choice=0;
		String csmName,csmPhone,csmPswrd;
		
		// CREATING OBJECT OF BANK DAO
		BankDao BDAO = new BankDao();
		
		// CALLING CONNECT METHOD TO ACCESS DATABASE
		BDAO.connect();

		outer:
		while(choice<4) {
		System.out.println("\nPress 1 - Create a Bank Account \nPress 2 - User Login \nPress 3 - Print all Bank Account Data from DataBase \nPress 4 - Exit");
		System.out.print("ENTER CHOICE : ");
		choice = scan.nextInt();
		
		
		switch(choice) {
		
			// OUTER CASE 1
			// TO CREATE AN USER ACCOUNT
			case 1->{
				
				//CREATING BANK CUSTOMER OBJECT TO  STORE DETAILS
				BankCustomer bcsm = new BankCustomer();
				System.out.print("\nEnter User Name : ");
				csmName = scan.next();// ASKING USER TO INPUT ACCOUNT HOLDER NAME
				bcsm.csmName = csmName;
				System.out.print("Set Account Password : ");
				csmPswrd = scan.next();// ASKING USER TO SET ACCOUNT PASSWORD
				bcsm.csmPassword = csmPswrd;
				System.out.print("Enter Age : ");
				csmAge = scan.nextInt();// ASKING USER TO INPUT AGE
				bcsm.csmAge = csmAge;
				System.out.print("Enter Phone No : ");
				csmPhone = scan.next();// ASKING USER TO INPUT PHONE NO
				bcsm.csmPhone = csmPhone;
				System.out.print("Enter Account Balance : ");
				csmAccBal = scan.nextInt();// ASKING USER TO ACCOUNT BALANCE
				bcsm.csmAccBal = csmAccBal;
				int set = BDAO.setAccount(bcsm);// CALLING SET ACCOUNT METHOD TO CREATE BANK ACCOUNT AND ADD DATA IN DATABASE
				if (set==1)
					System.out.println("\nAccount Created Successfully");
			}

			// OUTER CASE 2
			// TO LOGIN INTO USER ACCOUNT
			case 2->{
				System.out.print("\nEnter Account Holder Name : ");
				csmName = scan.next();
				System.out.print("Enter Account Password : ");
				csmPswrd = scan.next();
				
				// CAKKING USER LOGIN METHOD TO VERIFY USER ACCOUNT
				int id = BDAO.userLogin(csmName,csmPswrd);
				if(id>0) { // IF ACCOUNT AND PASSWORD DETAIL CORRECT THEN USER CAN ACCESS BANK ACCOUNT
					System.out.println("\nLogin Successfully.");
					int cycle =0;
					
					inner:
					while(cycle<6) {
						System.out.println("\nPress 1 - Print Account Detail \nPress 2 - Deposit Amount \nPress 3 - Withdraw Amount \nPress 4 - Delete Your Account \nPress 5 - Change Your PassWord \nPress 6 - Log Out.");
						System.out.println("Enter Choice : ");
						cycle = scan.nextInt();
						
						// DIFFERENT TASK CAN BE PERFORM BY USER IN USER BANK ACCOUNT
						
						switch(cycle) {
						
						// INNER CASE 1
						// TO PRINT USER ACCOUNT DETAIL
						case 1 ->{
							// CALLING GET ACCOUNT METHOD TO ACCESS AN BANK ACCOUNT DETAILS
							BankCustomer bcsm= BDAO.getAccount(id);
							System.out.println("\nAccount No: "+bcsm.csmId+"  Account Holder Name: "+bcsm.csmName+"  Age: "+bcsm.csmAge+"  Phone No: "+bcsm.csmPhone+"  Account Balance: "+bcsm.csmAccBal);
						}
						
						
						//INNER CASE 2
						// TO DEPOSIT AMOUNT IN ACCOUNT 
						case 2->{
							int amt = 0;
							System.out.print("\nEnter Amount to Deposit : ");
							amt = scan.nextInt();
							
							// CALLING DEPOSIT METHOD TO ADD AMOUNT IN ACCOUNT BALANCE
							int ret = BDAO.Deposit(id,amt);
							System.out.println("\nUpdated Account Balance : "+ret);
						}
						
						// INNER CASE 3
						// TO WITHDRAW AMOUNT FROM BALANCE
						case 3->{
							int amt = 0;
							System.out.print("\nEnter Amount to WithDraw : ");
							amt = scan.nextInt();
							
							// CALLING WITHDRAW METHOD TO REMOVE AMOUNT FROM USER ACCOUNT BALANCE
							int ret = BDAO.withDraw(id,amt);
							if(ret>0)
								System.out.println("Updated Account Balance : "+ret);
							else
								System.out.println("Insufficient Account Balance.");
						}
						
						// INNER CASE 4
						// TO DELETE USER ACCOUNT
						case 4->{
							System.out.print("\nEnter Account No: ");
							csmId = scan.nextInt();
							System.out.print("Enter Account Holder Name : ");
							csmName = scan.next();
							System.out.print("Enter Account Password : ");
							csmPswrd = scan.next();
							
							// CALLING DELETE ACCOUNT METHOD
							// TO DELETE CURRENT ACCOUNT BY VERIFYING ACCOUNT NO , NAME AND PASSWORD
							int ret = BDAO.deleteAccount(id, csmId, csmName, csmPswrd);
							if(ret>0) {
								System.out.println("\nAccount Deleted Successfully.");
							}
							else if(ret==0) {
								System.out.println("\nIncorrect UserName or Password!!!");
							}
							else if(ret== -1) {
								System.out.println("\nInvalid Account Holder Name!!!");
							}
							else {
								System.out.println("\nInvalid Account No!!!");
							}
							continue outer;
						}
						
						// INNER CASE 5
						case 5->{
							System.out.print("Enter Account Holder Name : ");
							csmName = scan.next();
							System.out.print("Enter Current Password : ");
							csmPswrd = scan.next();
							System.out.print("Enter New Password : ");
							String Pswrd = scan.next();
							
							// CALLING CHANGE PASSWORD METHOD
							// TO CHANGE OLD PASSWORD WITH NEW PASSWORD
							int ret = BDAO.changePswrd(id, csmName, csmPswrd, Pswrd);
							if(ret>0) {
								System.out.println("\nPassword can not Change!!! \nOld Password and New Password can not be same");
							}
							else if(ret==0) {
								System.out.println("\nPassWord Successfully Changed. \nLogin Again with New Password!!");
							}
							else if(ret== -1) {
								System.out.println("\nIncorrect UserName or Password!!!");
							}
							else {
								System.out.println("\nInvalid Account!!!");
							}
							continue outer;
						}
						
						}
					}
					System.out.println("\nSuccessfully Logged Out\n");
				}
				else if(id==0)
					System.out.println("\nIncorrect UserName or Password!!!");
				else
					System.out.println("\nAccount not Exist!!! \nRegister Your Account!!!");
				
			}
			
			// OUTER CASE 3
			// TO PRINT ALL USER ACCOUNT DETAIL
			// CAN ONLY ACCESS BY DATABASE ADMIN
			case 3->{
				System.out.print("\nEnter Admin Name : ");
				csmName = scan.next();
				System.out.print("Enter Admin Password : ");
				csmPswrd = scan.next();
				
				if(csmName.equals("root")) {
					if(csmPswrd.equals("142307")) {
						
						List<BankCustomer> consumer;
						// CALLING GET ALL DATA
						// TO PRITNT DATA OF EVERY USER FROM DATABASE
						consumer = BDAO.getAllData(csmName,csmPswrd);
						for(BankCustomer csm : consumer) {
							System.out.println("\nAccount No: "+csm.csmId+"  Account Holder Name: "+csm.csmName+"  Age: "+csm.csmAge+"  Phone No: "+csm.csmPhone+"  Account Balance: "+csm.csmAccBal+"\n");
						}
				}
				// IF GIVEN PASSWORD DOES NOT MATCH WITH ADMIN PASSWORD
				else {System.out.println("\nWrong Password!!!");
				}
			}
			// IF GIVEN NAME DOES NOT MATCH WITH ADMIN NAME
			else {System.out.println("\nWrong User Name!!!");}	
			}
		}
		}
		scan.close();
	}

}


