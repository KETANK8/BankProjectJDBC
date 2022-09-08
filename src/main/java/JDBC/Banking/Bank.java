/**
 * @author Ketan Kumar
 * Illustrating BANK MANAGEMENT SYSTEM TO CREATE BANK ACCOUNT ADD ACCOUNT DETAILS TO DATABASE AND PRINT DATA OF ONE OR ALL ACCOUNT USING DATA ACCESS OBJECT
 * PERFORMING MONEY DEPOSIT AND MONEY WITHDRAWING FROM ACCOUNT BALANCE
 *
 */

package JDBC.Banking;

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
				BDAO.setAccount(bcsm);// CALLING SET ACCOUNT METHOD TO CREATE BANK ACCOUNT AND ADD DATA IN DATABASE
			}

			case 2->{
				System.out.print("\nEnter Account Holder Name : ");
				csmName = scan.next();
				System.out.print("Enter Account Password : ");
				csmPswrd = scan.next();
				
				// CAKKING USER LOGIN METHOD TO VERIFY USER ACCOUNT
				int id = BDAO.userLogin(csmName,csmPswrd);
				if(id>0) { // IF ACCOUNT AND PASSWORD DETAIL CORRECT THEN USER CAN ACCESS BANK ACCOUNT
					int cycle =0;
					
					inner:
					while(cycle<5) {
						System.out.println("\nPress 1 - Print Account Detail \nPress 2 - Deposit Amount \nPress 3 - Withdraw Amount \nPress 4 - Delete Your Account \nPress 5 - Log Out.");
						System.out.println("Enter Choice : ");
						cycle = scan.nextInt();
						
						// DIFFERENT TASK CAN BE PERFORM BY USER IN USER BANK ACCOUNT
						
						switch(cycle) {
						
						case 1 ->{
							// CALLING GET ACCOUNT METHOD TO ACCESS AN BANK ACCOUNT DETAILS
							BankCustomer bcsm= BDAO.getAccount(id);
							System.out.println("\nAccount No: "+bcsm.csmId+"  Account Holder Name: "+bcsm.csmName+"  Age: "+bcsm.csmAge+"  Phone No: "+bcsm.csmPhone+"  Account Balance: "+bcsm.csmAccBal);
						}
						
						case 2->{
							int amt = 0;
							System.out.print("\nEnter Amount to Deposit : ");
							amt = scan.nextInt();
							
							// CALLING DEPOSIT METHOD TO ADD AMOUNT IN ACCOUNT BALANCE
							BDAO.Deposit(id,amt);
							
						}
						
						case 3->{
							int amt = 0;
							System.out.print("\nEnter Amount to WithDraw : ");
							amt = scan.nextInt();
							
							// CALLING WITHDRAW METHOD TO REMOVE AMOUNT FROM USER ACCOUNT BALANCE
							BDAO.withDraw(id,amt);
						}
						
						case 4->{
							System.out.print("\nEnter Account No: ");
							csmId = scan.nextInt();
							System.out.print("Enter Account Holder Name : ");
							csmName = scan.next();
							System.out.print("Enter Account Password : ");
							csmPswrd = scan.next();
							
							// CALLING DELETE ACCOUNT METHOD
							// TO DELETE CURRENT ACCOUNT BY VERIFYING ACCOUNT NO , NAME AND PASSWORD
							BDAO.deleteAccount(id, csmId, csmName, csmPswrd);
							continue outer;
						}
						
						}
					}
					System.out.println("\nSuccessfully Logged Out\n");
				}
				
			}
			
			case 3->{
				System.out.print("\nEnter Admin Name : ");
				csmName = scan.next();
				System.out.print("Enter Admin Password : ");
				csmPswrd = scan.next();
				// CALLING GET ALL DATA
				// TO PRITNT DATA OF EVERY USER FROM DATABASE
				BDAO.getAllData(csmName,csmPswrd);
			}
		}
		
		
		}
		scan.close();
	}

}


