package test;

import base.Base;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.RetirementCalculatorPage;
import utill.ExcelUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;


public class CalculateRetirementTest extends Base {

    RetirementCalculatorPage retirementCalculatorPage = null;
    ExcelUtils excelUtils;

    Properties prop = loadPropertiesFile();
    WebDriver driver;

    InputStream inputStream = new FileInputStream("src/test/resources/defaultRetirementCalculator.json");
    JSONTokener tokener = new JSONTokener(inputStream);
    JSONObject jsonObject = new JSONObject(tokener);

    String excelPath = "src/test/resources/retirement_calculator_test_data.xlsx";

    public CalculateRetirementTest() throws FileNotFoundException {
    }

    @DataProvider(name = "retirementData")
    public Object[][] getTestData() {
        excelUtils = new ExcelUtils(excelPath, "Sheet1");
        int rowCount = excelUtils.getRowCount();
        Object[][] data = new Object[rowCount][7];

        for (int i = 1; i <= rowCount; i++) {
            data[i - 1][0] = excelUtils.getCellData(i, 0); // currentAge
            data[i - 1][1] = excelUtils.getCellData(i, 1); // retirementAge
            data[i - 1][2] = excelUtils.getCellData(i, 2); // currentIncome
            data[i - 1][3] = excelUtils.getCellData(i, 3); // spouseIncome
            data[i - 1][4] = excelUtils.getCellData(i, 4); // currentRetirementSavings
            data[i - 1][5] = excelUtils.getCellData(i, 5); // retirementSavingsPercentage
            data[i - 1][6] = excelUtils.getCellData(i, 6); // socialSecurityOverride
        }
        return data;
    }

    @Test(priority = 1, dataProvider = "retirementData")
    public void submitFormWithALllFields(String currentAge, String retirementAge, String currentIncome,
                                         String spouseIncome, String totalSavings, String currentRetirementSavings,
                                         String retirementSavingsPercentage) {
        driver = openBrowserAndApplication(prop.getProperty("browser"));
        retirementCalculatorPage = new RetirementCalculatorPage(driver);
        retirementCalculatorPage.enterCurrentAge(currentAge);
        retirementCalculatorPage.enterRetirementAge(retirementAge);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('current-income').value='100000'");
        js.executeScript("document.getElementById('spouse-income').value='70000'");
        js.executeScript("document.getElementById('current-total-savings').value='50000'");
        retirementCalculatorPage.enterCurrentRetirementSavings(currentRetirementSavings);
        retirementCalculatorPage.enterRetirementSavingsPercentage(retirementSavingsPercentage);
        retirementCalculatorPage.clickCalculate();
        retirementCalculatorPage.getRetirementResults();
        System.out.println("Result: " + retirementCalculatorPage.getRetirementResults());
    }

    @Test(priority = 2, dataProvider = "retirementData")
    public void submitFormWithMandatoryFields(String currentAge, String retirementAge, String currentIncome,
                                              String spouseIncome, String totalSavings, String currentRetirementSavings,
                                              String retirementSavingsPercentage) {
        driver = openBrowserAndApplication(prop.getProperty("browser"));
        retirementCalculatorPage = new RetirementCalculatorPage(driver);
        retirementCalculatorPage.enterCurrentAge(currentAge);
        retirementCalculatorPage.enterRetirementAge(retirementAge);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('current-income').value='100000'");
        js.executeScript("document.getElementById('current-total-savings').value='50000'");
        retirementCalculatorPage.enterCurrentRetirementSavings(currentRetirementSavings);
        retirementCalculatorPage.enterRetirementSavingsPercentage(retirementSavingsPercentage);
        retirementCalculatorPage.clickCalculate();
        retirementCalculatorPage.getRetirementResults();
        System.out.println("Result: " + retirementCalculatorPage.getRetirementResults());
    }

    @Test(priority = 3, dataProvider = "retirementData")
    public void validateSocialToogleFeature(String currentAge, String retirementAge, String currentIncome,
                                            String spouseIncome, String totalSavings, String currentRetirementSavings,
                                            String retirementSavingsPercentage) {
        driver = openBrowserAndApplication(prop.getProperty("browser"));
        retirementCalculatorPage = new RetirementCalculatorPage(driver);
        retirementCalculatorPage.enterCurrentAge(currentAge);
        retirementCalculatorPage.enterRetirementAge(retirementAge);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("current-income"))).sendKeys("10000");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('current-income').value='100000'");
        js.executeScript("document.getElementById('spouse-income').value='70000'");
        js.executeScript("document.getElementById('current-total-savings').value='50000'");
        retirementCalculatorPage.enterCurrentRetirementSavings(currentRetirementSavings);
        retirementCalculatorPage.enterRetirementSavingsPercentage(retirementSavingsPercentage);
        retirementCalculatorPage.selectSocialBenefit();
        Assert.assertTrue(retirementCalculatorPage.verifyToogle());
        retirementCalculatorPage.clickCalculate();
    }

    @Test(priority = 4)
    public void updateDefaultCalculator() {
        driver = openBrowserAndApplication(prop.getProperty("browser"));
        retirementCalculatorPage = new RetirementCalculatorPage(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000)");
        retirementCalculatorPage.clickDefaultLink();
        retirementCalculatorPage.enterAdditionIncome(jsonObject.getString("income"));
        retirementCalculatorPage.enterRetirementDuration(jsonObject.getString("duration"));
        retirementCalculatorPage.finalAnnualIncome(jsonObject.getString("annualIncome"));
        retirementCalculatorPage.enterPreROI(jsonObject.getString("preROI"));
        retirementCalculatorPage.enterPostROI(jsonObject.getString("postROI"));
        retirementCalculatorPage.saveDefaultValues();


    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
