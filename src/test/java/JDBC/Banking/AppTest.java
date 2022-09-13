/**
 * @author Ketan Kumar
 * Illustrating TESTING OF BANK MANAGEMENT SYSTEM TEST CASE TO CREATE BANK ACCOUNT ADD ACCOUNT DETAILS TO DATABASE USING JUNIT TESTING
 * PERFORMING TESTING OF MONEY DEPOSIT AND MONEY WITHDRAWING FROM ACCOUNT BALANCE
 *
 */
package JDBC.Banking;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import BankingDAO.BankCustomer;
import BankingDAO.BankDao;



class AppTest{
	
	@Test
	@Order(1)
	void testSetAccount() throws Exception {		
		// CALLING CONNECT METHOD TO ACCESS DATABASE
		BankDao BDAO = new BankDao();
		BankCustomer bcsm = new BankCustomer();
		BDAO.connect();
		
		bcsm.csmName = "Ketan";
		bcsm.csmPassword = "80808";
		bcsm.csmAge = 22;
		bcsm.csmPhone = "46123789";
		bcsm.csmAccBal = 40000;
		
		//Testing for existed account in data base
		assertEquals(-1,BDAO.setAccount(bcsm));	
		bcsm.csmName = "Vikas";
		bcsm.csmPassword = "80808";
		bcsm.csmAge = 22;
		bcsm.csmPhone = "46123789";
		bcsm.csmAccBal = 40000;
		
		//Testing for new account in data base
		assertEquals(1,BDAO.setAccount(bcsm));
	}
	
	@Test
	@Order(2)
	void testUserLogin() throws Exception {		
		BankDao BDAO = new BankDao();
		BankCustomer bcsm = new BankCustomer();
		// CALLING CONNECT METHOD TO ACCESS DATABASE
		BDAO.connect();
		
		//Test Case for wrong account user Name in data base
		assertEquals(-1,BDAO.userLogin("Prem", "80808"));
		//			expected result		Name , Password

		
		//Test Case for wrong account password in data base
		assertEquals(0,BDAO.userLogin("Ketan", "080808"));
		//			expected result		Name , Password
		
		//Test Case for Correct account Name and PassWord in data base
		assertEquals(100,BDAO.userLogin("Ketan", "80808"));
		//			expected Id			  Name , Password

	}
	
	@Test
	@Order(4)
	void testWithDraw() throws Exception {		
		BankDao BDAO = new BankDao();
		BankCustomer bcsm = new BankCustomer();
		// CALLING CONNECT METHOD TO ACCESS DATABASE
		BDAO.connect();

		assertEquals(0,BDAO.withDraw(106, 555000));
		
		//Test Case where withdraw amount more than balance
		assertEquals(70000,BDAO.withDraw(106, 5000));
		//			expected Balance	id , amount to deposit
		
	}
	

	@Test
	@Order(5)
	void testDeposit() throws Exception {		
		BankDao BDAO = new BankDao();
		BankCustomer bcsm = new BankCustomer();
		// CALLING CONNECT METHOD TO ACCESS DATABASE
		BDAO.connect();
		
		//Test Case where withdraw amount more than balance
		assertEquals(50000,BDAO.Deposit(105, 5000));
		//			expected Balance	id , amount to deposit

	}

	@Test
	@Order(3)
	void testChangePassword() throws Exception {
		BankDao BDAO = new BankDao();
		BankCustomer bcsm = new BankCustomer();
		// CALLING CONNECT METHOD TO ACCESS DATABASE
		BDAO.connect();
		
		Assertions.assertAll(
			
		//Test Case for wrong account Password in data base
		() -> assertEquals(-1,BDAO.changePswrd(106,"amit", "000000", "000000")),
		//			expected result		Name , Password
	
		//Test Case for same account password in data base
		() -> assertEquals(1,BDAO.changePswrd(106, "amit", "80808", "80808")),
		//			expected result		Name , Password
		
		//Test Case for wrong account user Name in data base
		() -> assertEquals(-2,BDAO.changePswrd(106, "Prem", "80808", "80808")),
		//			expected Id			  Name , Password
	
		//Test Case for Correct account Name and PassWord in data base
		() -> assertEquals(0,BDAO.changePswrd(106, "amit", "80808","123456"))
		//			expected result		Name , Password

		);
	}
	
	@Test
	@Order(6)
	void testDeleteAccount() throws Exception {	
		BankDao BDAO = new BankDao();
		BankCustomer bcsm = new BankCustomer();
		// CALLING CONNECT METHOD TO ACCESS DATABASE
		BDAO.connect();
		
		//Test Case for wrong account name in data base
		assertEquals(-1,BDAO.deleteAccount(103, 103,"Prem", "080808"));
		//			expected result		Name , Password

		//Test Case for wrong account no in data base
		assertEquals(-2,BDAO.deleteAccount(104, 103,"Ankit", "080808"));
		//			expected Id			  Name , Password
	
		//Test Case for correct account name and password in data base
		assertEquals(0,BDAO.deleteAccount(103, 103,"Ankit", "80808"));
		//			expected result		Name , Password
		
		//Test Case for correct account name and password in data base
		assertEquals(1,BDAO.deleteAccount(103, 103,"Ankit", "1234"));
		//			expected result		Name , Password
		
	}

	
}