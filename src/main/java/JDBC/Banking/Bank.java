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
		
		// CREATING OBJECT OF EMPLOYEEDOA
		BankDao BDOA = new BankDao();
		
		// CALLING COONECT METHOD TO ACCESS DATABASE
		BDOA.connect();
		
		
		while(choice<4) {
		System.out.println("Press 1 - Create a Bank Account \nPress 2 - User Login \nPress 3 - Print all Bank Account Data from DataBase \nPress 4 - Exit");
		System.out.print("ENTER CHOICE : ");
		choice = scan.nextInt();
		
		switch(choice) {
			case 1->{
				
				//CREATING EMPLOYEE OBJECT TO  STORE DETAILS
				BankCustomer bcsm = new BankCustomer();
				System.out.print("Enter User Name : ");
				csmName = scan.next();// ASKING USER TO INPUT NAME
				bcsm.csmName = csmName;
				System.out.print("Set Account Password : ");
				csmPswrd = scan.next();// ASKING USER TO INPUT DEPARTMENT
				bcsm.csmPassword = csmPswrd;
				System.out.print("Enter Age : ");
				csmAge = scan.nextInt();// ASKING USER TO INPUT SALARY
				bcsm.csmAge = csmAge;
				System.out.print("Enter Phone No : ");
				csmPhone = scan.next();// ASKING USER TO INPUT SALARY
				bcsm.csmPhone = csmPhone;
				System.out.print("Enter Account Balance : ");
				csmAccBal = scan.nextInt();// ASKING USER TO INPUT SALARY
				bcsm.csmAccBal = csmAccBal;
				BDOA.setAccount(bcsm);// CALLING SET EMPLOYEE METHOD TO ADD DATA IN DATABASE
			}

			case 2->{
				System.out.print("Enter Account Holder Name : ");
				csmName = scan.next();
				System.out.print("Enter Account Password : ");
				csmPswrd = scan.next();
				
				int id = BDOA.userLogin(csmName,csmPswrd);
				if(id>0) {
					int cycle =0;
					while(cycle<4) {
						System.out.println("Press 1 - Print Account Detail \nPress 2 - Deposite Amount \nPress 3 - Withdraw Amount \nPress 4 - Log Out.");
						System.out.println("Enter Choice : ");
						cycle = scan.nextInt();
						
						switch(cycle) {
						
						case 1 ->{
							// CALLING GET EMPLOYEE METHOD TO ACCESS AN EMPLOYEE DATA
							BankCustomer bcsm= BDOA.getAccount(id);
							System.out.println("Account No: "+bcsm.csmId+"  Account Holder Name: "+bcsm.csmName+"  Age: "+bcsm.csmAge+"  Phone No: "+bcsm.csmPhone+"  Account Balance: "+bcsm.csmAccBal);
						}
						
						case 2->{
							int amt = 0;
							System.out.print("Enter Amount to WithDraw : ");
							amt = scan.nextInt();
							BDOA.Deposit(id,amt);
							
						}
						
						case 3->{
							int amt = 0;
							System.out.print("Enter Amount to WithDraw : ");
							amt = scan.nextInt();
							BDOA.withDraw(id,amt);
						}
						
						}
					}
					System.out.println("Successfully Logged Out");
				}
				
			}
			
			case 3->{
				System.out.print("Enter Admin Name : ");
				csmName = scan.next();
				System.out.print("Enter Admin Password : ");
				csmPswrd = scan.next();
				// CALLING GET ALL DATA
				// TO PRITNT DATA OF EVERY EMPLOYEE FROM DATABASE
				BDOA.getAllData(csmName,csmPswrd);
			}
		}
		
		
		}
		scan.close();
	}

}


