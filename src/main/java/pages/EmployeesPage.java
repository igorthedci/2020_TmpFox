package pages;

import models.Employee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


public class EmployeesPage extends WebPage {

    @FindBy(id = "fixed-table-toolbar--buttons")
    private WebElement btnCreateEmployee;

    @FindBy(xpath = "//tr/td[1]/a")
    private List <WebElement> employeeIds;
    @FindBy(xpath = "//tr/td[2]/a")
    private List <WebElement> employeeNames;
    @FindBy(xpath = "//tr/td[3]")
    private List <WebElement> employeeWorkloads;

    @FindBy(className = "pagination-info")
    private WebElement tableInfo;
    @FindBy(className = "page-size")
    private WebElement pageSize;

    public EmployeesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getEmployeesInfo() {
        return tableInfo.getText();
    }

    public int getEmployeesPageSize() {
        return Integer.parseInt(pageSize.getText());
    }

    public void setEmployeesPageSize(int size) {
        ArrayList<Integer> sizes = new ArrayList<Integer>();

        pageSize.click(); // open drop-list
        List <WebElement> sizeList = driver.findElements(By.xpath("//li[@role='menuitem']"));
        for(WebElement element: sizeList) {
            String item = element.getText();
            if (item.equals(String.valueOf(size))) {
                element.click(); // change page size
                PageFactory.initElements(driver, this); // refresh page locators
                System.out.println("set pageSize: " + getEmployeesPageSize());
                break;
            }
        }
    }

    public ArrayList<Integer> getEmployeesPageSizes() {
        ArrayList<Integer> sizes = new ArrayList<Integer>();
        String size;

        pageSize.click(); // open drop-list
        List <WebElement> sizeList = driver.findElements(By.xpath("//li[@role='menuitem']"));
        for(WebElement item: sizeList) {
            size = item.getText();
            System.out.println("pageSize item: " + size);
            sizes.add(Integer.parseInt(size));
        }
        pageSize.click(); // close drop-list

        return sizes;
    }

    public boolean isEmployeesPageValid() {
        boolean result = "Create Employee".equals(btnCreateEmployee.getText());
        return result;
    }

    // table size is less-equal pageSize
    // employee qty is bigger-equal pageSize
    public boolean isFirstPaginationValid() {
        int pageSize = getEmployeesPageSize();
        int qty = getEmployeeCounter();
        Employee[] eList = getEmployeesList();

        if (qty <= pageSize && qty != eList.length)
            return false;
        if (qty > pageSize && pageSize != eList.length)
            return false;
        return true;
    }

    public int getEmployeeCounter() {
        String info = tableInfo.getText();
        String[] sss = info.split(" ");
        int counter;
        counter = Integer.parseInt(sss[sss.length - 1]);
        System.out.println("page INFO: " + info);
        System.out.println("employees COUNTER: " + counter);
        return counter;
    }

    public Employee[] getEmployeesList() {
        Employee[] employees;
        Employee employeeOne;

        int limit = employeeIds.size();  // read the number of employees on page
        employees = new Employee[limit]; // create array
        for (int ndx = 0; ndx < limit; ndx++) {
            // read id-name-workload from NDXth line
            int idOne = Integer.parseInt(employeeIds.get(ndx).getText());
            String nameOne = employeeNames.get(ndx).getText();
            String workloadOne = employeeWorkloads.get(ndx).getText();
            // create Employee object
            employeeOne = new Employee(idOne, nameOne, workloadOne);
            // put new object to the array
            employees[ndx] = employeeOne;
        }
        return employees;
    }
}
