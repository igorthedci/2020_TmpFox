package EmployeeTests;

import BasicTests.BasicTest;
import models.Employee;
import org.junit.Before;
import org.junit.Test;
import pages.EmployeesPage;
import pages.LoginPage;
import pages.NavigationPage;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class EmployeesTests extends BasicTest {

    private EmployeesPage employeesPage;
    private int employeesCounter = 0;

    @Before
    public void beforeTest() {
        LoginPage loginPage;
        NavigationPage navigationPage;

        // review Login page
        loginPage = new LoginPage(wd);
        assertTrue(loginPage.isLoginPageValid());

        // login as a valid admin
        assertTrue(loginPage.loginWithRole(validAdmin));

        // work with Navigation page
        navigationPage = new NavigationPage(wd);
//        assertTrue(navigationPage.isNavigationPageValid()); TODO

        // move to EmployeesPage
        navigationPage.openEmployeesPage();
        employeesPage = new EmployeesPage(wd);
        assertTrue(employeesPage.isEmployeesPageValid());
    }

    @Test
    public void testEmployeesPageSizes() {
        ArrayList<Integer> sizes = employeesPage.getEmployeesPageSizes();
        assertTrue(sizes.size() >= 1);
    }

    @Test
    public void testEmployeesPagePagination() {
        ArrayList<Integer> sizes = employeesPage.getEmployeesPageSizes();
        for(int size: sizes) {
            employeesPage.setEmployeesPageSize(size);
            assertTrue(employeesPage.isFirstPaginationValid());
        }
    }

    @Test
    public void testEmployeeList() {
        int pageSize = employeesPage.getEmployeesPageSize();
        ArrayList<Integer> pageSizes = employeesPage.getEmployeesPageSizes();

        // the number of employees
        employeesCounter = employeesPage.getEmployeeCounter();
        System.out.println("the number of employees: " + employeesCounter);

        // read page size and employee
        Employee[] employeesList = employeesPage.getEmployeesList();
        System.out.println("list of EMPLOYEES");
        for (Employee employeeOne : employeesList) {
            System.out.println(employeeOne.toString());
        }
        assertTrue(employeesCounter >= employeesList.length);
        //
        System.out.println("pageSize: " + pageSize);
        assertTrue(pageSize >= employeesList.length);
        //
    }
}
