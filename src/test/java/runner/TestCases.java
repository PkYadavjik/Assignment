package runner;

import org.testng.annotations.Test;

import com.SenWellsys.homepage.HomePage;
import com.SenWellsysassignment.login.LoginPage;
import com.SenWellsysassignment.utility.BaseClass;
import com.SenWellsysassignment.utility.Utility;
import com.senWells.checkout.CheckOutPage;
import com.sendsWellsys.addtocard.AddToCardPage;

public class TestCases extends BaseClass {

	@Test(priority = 1)
	public void VerifyUserisLandedOnTheHomePage001() {
		validLogin();
		HomePage hm = new HomePage(webUtil);
		hm.verifyhomepagePage();
	}

	@Test(priority = 2)
	public void verifyloginWithInvalidData002() {
		LoginPage lp = new LoginPage(webUtil);
		lp.invalidLogin();
	}

	@Test(priority = 3)

	public void VerifyUsercanAddItemInAddToCart003() {
		validLogin();
		AddToCardPage ap = new AddToCardPage(webUtil);
		ap.userCanAddItemToAddCart();

	}

	@Test(priority = 4)
	public void verifyUserCanOrderTheProduct004() {
		validLogin();
		CheckOutPage cp = new CheckOutPage(webUtil);
		cp.checkOutProcess();

	}

	public void validLogin() {
		LoginPage lp = new LoginPage(webUtil);
		lp.validLogin();

	}
}
