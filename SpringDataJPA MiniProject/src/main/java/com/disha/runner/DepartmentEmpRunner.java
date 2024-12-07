package com.disha.runner;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.disha.service.DepartmentEmpService;

@Component
public class DepartmentEmpRunner implements CommandLineRunner{
	private DepartmentEmpService service ;

	@Autowired
	public DepartmentEmpRunner(DepartmentEmpService service) {
		this.service = service;
	}
	
	@Override
	public void run(String... args) throws Exception {
	    Scanner scanner = new Scanner(System.in);

	    // Create Department
	    System.out.println("Enter the name of the Department to be created: ");
	    String deptNameString = scanner.nextLine(); // Use nextLine to capture the entire input
	    service.createDepartment(deptNameString);

	    // Add Employee to Department
	    System.out.println("Enter the department id and employee name: ");
	    int deptId = scanner.nextInt(); 
	    scanner.nextLine();  // Consume the newline character left by nextInt
	    String empName = scanner.nextLine(); // Use nextLine to capture full name input
	    service.addEmpToDepartment(deptId, empName);

	    service.listDepartment();

	    // Update Employee Name
	    System.out.println("Enter the employee id in order to update the employee name: ");
	    int empId = scanner.nextInt();
	    scanner.nextLine();  // Consume newline
	    String empString = scanner.nextLine();
	    service.updateEmployeeName(empId, empString);

	    // Update Department Name
	    System.out.println("Enter the department id in order to update the department name: ");
	    int deptID = scanner.nextInt();
	    scanner.nextLine();  // Consume newline
	    String deptString = scanner.nextLine();
	    service.updateDepartmentName(deptID, deptString);

	    // Remove Employee
	    System.out.println("Enter the ID of the employee to be removed from the company!");
	    int empID = scanner.nextInt();
	    service.removeEmployee(empID);

	    // Remove Employees associated with Department
	    System.out.println("Enter the ID of the department and the employees that are associated with the department to be removed from company!");
	    int deptID2 = scanner.nextInt();
	    service.deleteDepartment(deptID2);

	    

	    // Update Employee Department
	    System.out.println("Enter the department id and the employee id to update employee dept : ");
	    System.out.println("Dept id");
	    int deptID4 = scanner.nextInt();
	    System.out.println("emp id ");
	    int empID2 = scanner.nextInt();
	    service.updateEmployeeDept(deptID4, empID2);

	    // Search Employee by Name
	    System.out.println("Enter the employee name for searching: ");
	    scanner.nextLine();  // Consume newline from previous input
	    String empName2 = scanner.nextLine();
	    service.searchEmpByName(empName2);
	}

	

}
