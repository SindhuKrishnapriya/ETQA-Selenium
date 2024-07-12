package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RetirementCalculatorPage {
    WebDriver driver;

    public RetirementCalculatorPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //defining all the locators in the homepage
    @FindBy(id = "current-age")
    private WebElement currentAge;
    @FindBy(id = "retirement-age")
    private WebElement retirementAge;
    @FindBy(id = "current-income")
    private WebElement currentIncome;
    @FindBy(id = "spouse-income")
    private WebElement spouseIncome;
    @FindBy(id = "current-total-savings")
    private WebElement currentBalance;
    @FindBy(id = "current-annual-savings")
    private WebElement currentSavingsPercentage;
    @FindBy(id = "savings-increase-rate")
    private WebElement savingsIncreaseRate;
    @FindBy(xpath = "//label[@for='yes-social-benefits']")
    private WebElement yesForSocialBenefits;
    @FindBy(xpath = "//label[@for='no-social-benefits']")
    private WebElement noForSocialBenefits;
    @FindBy(xpath = "//legend[@id='marital-status-label']")
    private WebElement maritalStatusLabel;
    @FindBy(xpath = "//label[contains(text(),'Single')]")
    private WebElement single;
    @FindBy(xpath = "/label[contains(text(),'Married')]")
    private WebElement married;
    @FindBy(id = "social-security-override")
    private WebElement socialSecurityOverride;
    @FindBy(xpath = "//button[contains(text(),'Calculate')]")
    private WebElement calculateButton;
    @FindBy(xpath = "//button[contains(text(),'Clear form')]")
    private WebElement clearFormButton;

    //defining all the locators for the default values
    @FindBy(xpath = "//a[normalize-space()='Adjust default values']")
    private WebElement defaultValuesLink;
    @FindBy(id = "additional-income")
    private WebElement additionalIncome;
    @FindBy(id = "retirement-duration")
    private WebElement retirementDuration;
    @FindBy(xpath = "//div[@id='default-values-modal']//li[1]")
    private WebElement includeInflation;
    @FindBy(xpath = "//label[@for='exclude-inflation']")
    private WebElement excludeInflation;
    @FindBy(id = "retirement-annual-income")
    private WebElement finalAnnualIncome;
    @FindBy(id = "pre-retirement-roi")
    private WebElement preRetirementROI;
    @FindBy(id = "post-retirement-roi")
    private WebElement postRetirementROI;
    @FindBy(xpath = "//button[contains(text(),'Save changes')]")
    private WebElement saveChangesButton;
    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;
    @FindBy(id = "retirement-results")
    private WebElement retirementResults;


    public void enterCurrentAge(String age) {
        currentAge.sendKeys(age);
    }

    public void enterRetirementAge(String age) {
        retirementAge.sendKeys(age);
    }

    public void enterCurrentIncome(String income) {
        currentIncome.sendKeys("income");
    }

    public void enterSpouseIncome(String income) {
        spouseIncome.sendKeys(income);
    }

    public void enterCurrentSavings(String totalSavings) {
        currentBalance.sendKeys(totalSavings);
    }

    public void enterCurrentRetirementSavings(String savings) {
        currentSavingsPercentage.sendKeys(savings);
    }

    public void enterRetirementSavingsPercentage(String percentage) {
        savingsIncreaseRate.sendKeys(percentage);
    }

    public void enterSocialSecurityOverride(String override) {
        socialSecurityOverride.sendKeys(override);
    }

    public void clickCalculate() {
        calculateButton.click();
    }

    public void clickDefaultValues() {
        defaultValuesLink.click();
    }

    public void selectSocialBenefit() {
        yesForSocialBenefits.click();
    }

    public boolean verifyToogle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean socialBenefitOptions = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//legend[@id='marital-status-label']"))).isDisplayed();
        return socialBenefitOptions;
    }

    public String getRetirementResults() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result-message"))).getText();
        return result;
    }

    public void clickDefaultLink() {
        defaultValuesLink.click();
    }

    public void enterAdditionIncome(String income) {
        additionalIncome.sendKeys(income);
    }

    public void enterRetirementDuration(String duration) {
        retirementDuration.sendKeys(duration);
    }

    public void finalAnnualIncome(String annualIncome) {
        finalAnnualIncome.sendKeys(annualIncome);
    }

    public void enterPreROI(String preROI) {
        preRetirementROI.sendKeys(preROI);
    }

    public void enterPostROI(String postROI) {
        postRetirementROI.sendKeys(postROI);
    }

    public void saveDefaultValues() {
        saveChangesButton.click();
    }
}
