package orangehrm.testcases;

import java.io.IOExce

import org.testng.annotations.Test;

import orangehrm.library.Employee;
import orangehrm.library.LoginPage;
import orangehrm.library.User;
import utils.AppUtils;
import utils.XLUtils;

public class OrangeHRMTestSuite extends AppUtils 
{

	
	String tcfile = "C:\\Users\\91982\\Downloads\\OrangeHRM_Hybrid\\keywordfiles\\OrangeHRMKeywords.xlsx";
	String tcsheet = "TestCases";
	String tssheet = "TestSteps";
	
	@Test
	public void checkOrangeHRM() throws IOException, InterruptedException
	{
		
		int tccount = XLUtils.getRowCount(tcfile, tcsheet);
		int tscount = XLUtils.getRowCount(tcfile, tssheet);
		
		String tcexeflag,tcid,tstcid,keyword,stepres,tcres;
		
		String adminuid,adminpwd;
		String fname,lname;
		String role,empname,empuid,emppwd;
		
		LoginPage lp = new LoginPage();
		Employee emp = new Employee();
		User us = new User();
		
		boolean res=false;
		for(int i=1;i<=tccount;i++)
		{
			tcexeflag = XLUtils.getStringCellData(tcfile, tcsheet, i, 2);
			if(tcexeflag.equalsIgnoreCase("y"))
			{
				tcid = XLUtils.getStringCellData(tcfile, tcsheet, i, 0);
				for(int j=1;j<=tscount;j++)
				{
					tstcid = XLUtils.getStringCellData(tcfile, tssheet, j, 0);
					if(tcid.equalsIgnoreCase(tstcid))
					{
						keyword = XLUtils.getStringCellData(tcfile, tssheet, j, 4);
						
						switch (keyword.toLowerCase()) 
						{
							case "adminlogin":
								adminuid = XLUtils.getStringCellData(tcfile, tssheet, j, 5);
								adminpwd = XLUtils.getStringCellData(tcfile, tssheet, j, 6);
								lp.login(adminuid, adminpwd);
								res =lp.isAdminModuleDisplayed();
								break;
							case "logout":
								res = lp.logout();
								break;
							case "newempreg":
								fname = XLUtils.getStringCellData(tcfile, tssheet, j, 5);
								lname = XLUtils.getStringCellData(tcfile, tssheet, j, 6);
								res = emp.addEmployee(fname, lname);
								break;	
							case "newuserreg":
								role = XLUtils.getStringCellData(tcfile, tssheet, j, 5);
								empname = XLUtils.getStringCellData(tcfile, tssheet, j, 6);
								empuid = XLUtils.getStringCellData(tcfile, tssheet, j, 7);
								emppwd = XLUtils.getStringCellData(tcfile, tssheet, j, 8);
								res = us.addUser(role, empname, empuid, emppwd);
								break;
							case "emplogin":
								empuid = XLUtils.getStringCellData(tcfile, tssheet, j, 5);
								emppwd = XLUtils.getStringCellData(tcfile, tssheet, j, 6);
								lp.login(empuid, emppwd);
								res = lp.isEmpModuleDisplayed();
								break;
							case "invalidlogin":
								empuid = XLUtils.getStringCellData(tcfile, tssheet, j, 5);
								emppwd = XLUtils.getStringCellData(tcfile, tssheet, j, 6);
								lp.login(empuid, emppwd);
								res = lp.isErrMsgDisplayed();
								break;								
						}
						
						// code to update Step Result
						if(res)
						{
							stepres = "Pass";
							XLUtils.setCellData(tcfile, tssheet, j, 3, stepres);
							XLUtils.fillGreenColor(tcfile, tssheet, j, 3);
						}else
						{
							stepres = "Fail";
							XLUtils.setCellData(tcfile, tssheet, j, 3, stepres);
							XLUtils.fillRedColor(tcfile, tssheet, j, 3);
						}
						
						// code to update TestCase Result
						tcres = XLUtils.getStringCellData(tcfile, tcsheet, i, 3);
						if(!tcres.equalsIgnoreCase("fail"))
						{
							XLUtils.setCellData(tcfile, tcsheet, i, 3, stepres);
						}
						tcres = XLUtils.getStringCellData(tcfile, tcsheet, i, 3);
						if(tcres.equalsIgnoreCase("pass"))
						{
							XLUtils.fillGreenColor(tcfile, tcsheet, i, 3);
						}else
						{
							XLUtils.fillRedColor(tcfile, tcsheet, i, 3);
						}
						
					}
				}
			}else
			{
				XLUtils.setCellData(tcfile, tcsheet, i, 3, "Blocked");
				XLUtils.fillRedColor(tcfile, tcsheet, i, 3);
			}
		}
		
		
	}	
	
	
}
