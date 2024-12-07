package com.disha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disha.models.Department;
import com.disha.models.Employee;
import com.disha.repository.DepartmentRepository;
import com.disha.repository.EmpRepository;

@Service
public class DepartmentEmpService {
       private DepartmentRepository drepo;
       private EmpRepository Erepo;
       
    @Autowired   
	public DepartmentEmpService(DepartmentRepository drepo, EmpRepository erepo) {
		
		this.drepo = drepo;
		Erepo = erepo;
	}
    
    // method to just create a department 
    public void createDepartment(String deptName) {
    	Department deptDepartment = new Department(deptName);
    	drepo.save(deptDepartment);
    	System.out.println("Department created with name "+ deptName);
    }
    
    //method to add an employee to the department , the below code is same as the one in unidirectional if you don't set the back reference in the department class 
    
    public void addEmpToDepartment(Integer deptId , String empName) {
    	Department deptDepartment = drepo.findById(deptId).orElse(null); // shortcut way rather then getting and Optional, checking if it's not null then through get() getting the required object
    	if(deptDepartment != null) {
    		deptDepartment.addEmp(new Employee(empName));
        	drepo.save(deptDepartment); // save department cascade to employee
        	System.out.println("Employee added to the department !");
    	}
    	else {
    		System.out.println("Can not find the department of the given ID !");
    	}	
    }
    
    //retrieve all department  along with employees
    //if we had not mentioned the fetch type as Eager we had to use the annotation @Transactional to get the employees details 
    public void listDepartment() {
    	System.out.println("Showing the dpartment and it's employee ");
    	for(Department dept : drepo.findAll()) {
    		System.out.println("The department name "+ dept.getName() );
    		for(Employee employee : dept.getEmpList()) {
    			System.out.println(" -Emp : "+ employee.getName());
    		}
    	}
    }
    
    //method to update the employee name 
    // updating the owning side directly not through department (inverse) because we are using the bidirectional relationship 
    public void updateEmployeeName(Integer empId,String empName){
    	Employee employee = Erepo.findById(empId).orElse(null);
    	if(employee != null) {
    		employee.setName(empName);
    		Erepo.save(employee); // update the employee table
    		System.out.println("Employee name changes to "+ empName);
    	}
    	else {
			System.out.println("Employee with the given id not found !");
		}
		
	}
    
   //method to update the department name 
    public void updateDepartmentName(Integer deptId,String deptName){
    	Department deptDepartment = drepo.findById(deptId).orElse(null);
    	if(deptDepartment != null) {
    		deptDepartment.setName(deptName);
    		drepo.save(deptDepartment);
    		System.out.println("department name updated to " + deptName);
    	}
    	else {
    		System.out.println("Department with the given ID not found !");
    	}
		
	}
    
    //remove an employee from the company that is remove it from both the table
    public void removeEmployee(Integer empId){
    	Employee employee = Erepo.findById(empId).orElse(null);
    	if(employee != null){
    		Department deptDepartment = employee.getDept();
    		// its is possible that the particular employee didn't got any department then null will be return 
    	    if(deptDepartment != null ) {
    		     // get the department of the employee with the help of back reference we set before 
    	    	 // below two lines are written only for JPA 
    		     deptDepartment.removeEmp(employee); // remove the employee from department 
    	         drepo.save(deptDepartment); // optional, only for the consistency between JPA and SQL , state same rakhne ke liye 
    	    }
    	    Erepo.delete(employee); // remove employee from employee table 
    	    System.out.println("Employee removed !");
    	}
    	else {
			System.out.println("Employee with the given id not present !");
		}
    }
    
    //delete department from the table  -- that is if we delete the department then all it's employee will also be deleted wala case 
    public void deleteDepartment(Integer deptId) {
    	Department deptDepartment = drepo.findById(deptId).orElse(null); // shortcut way rather then getting and Optional, checking if it's not null then through get() getting the required object
    	if(deptDepartment != null) {
           drepo.delete(deptDepartment); // cascading is on so while we delete the department, all employees that are associated with it will also be removed 
           System.out.println("Department removed ! ");
    	}
    	else {
    		System.out.println("Department not found !");
    	}
    }
    
    //delete department from the table  -- that is if we delete the department then all it's employee will  be retained wala case 
//    public void deleteDepartmentWithoutEmployees(Integer deptId) {
//        // Fetch the department
//        Department department = drepo.findById(deptId).orElse(null);
//
//        if (department != null) {
//            // Fetch all employees associated with this department
//            List<Employee> employees = Erepo.findByDepartmentId(deptId);
//
//            // Dis-associate employees from this department
//            for (Employee employee : employees) {
//                employee.setDept(department); // Dis-associate department
//                Erepo.save(employee); // Save the updated employee
//            }
//
//            // Delete the department
//            drepo.delete(department);
//            System.out.println("Department deleted, but employees retained.");
//        } else {
//            System.out.println("Department not found!");
//        }
//    }

    
    //method to update employees department 
    public void updateEmployeeDept(Integer empId, Integer deptId) {
        // Fetch the employee and department
        Employee employee = Erepo.findById(empId).orElse(null);
        Department dept = drepo.findById(deptId).orElse(null);

        // Check if both employee and department exist
        if (employee != null && dept != null) {
            // Get the current department of the employee (if any)
            Department currentDept = employee.getDept();

            // Remove the employee from the current department's employee list
            if (currentDept != null) {
                currentDept.getEmpList().remove(employee);
            }

            // Update the employee's department
            employee.setDept(dept);

            // Add the employee to the new department's employee list
            dept.getEmpList().add(employee);

            // Save the updated employee and department
            Erepo.save(employee); // Saves changes for the employee
            drepo.save(dept);     // Updates the department's employee list

            System.out.println("Employee department updated successfully!");

        } else {
            // Handle cases where employee or department is not found
            if (employee == null) {
                System.out.println("Employee with ID " + empId + " not found!");
            }
            if (dept == null) {
                System.out.println("Department with ID " + deptId + " not found!");
            }
        }
    }
    
    
    // method to search emp By name and print its department  
    public void searchEmpByName(String empName){
           		Employee employee = Erepo.findByName(empName);
           		if(employee != null) {
           			System.out.println("The employee with name "+ empName +" is present in the deptartment "+ employee.getDept().getName());
           		}
           		else {
					System.out.println("Employee with given name not found !");
				}
	}

       
}
