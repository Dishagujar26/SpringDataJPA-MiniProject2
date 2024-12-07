package com.disha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.disha.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
