package com.empManagement.empManagement.repository;

import com.empManagement.empManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//执行一些增删改查的任务 最底层的操作

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {//引入增删改查

    @Query(value = "select * from employee b where b.full_name like %:keyword% or b.NIC like %:keyword%", nativeQuery = true)
    List<Employee> findByKeyword(@Param("keyword") String keyword);//通过全名或者身份证查询

    long countByisAttedenceIgnoreCase(String isAttendance);//统计出勤

    long countBydepartmentIgnoreCase(String department);//统计该部门人数

    List<Employee> findByisAttedenceIgnoreCase(String isAttendance);//查询出勤情况
}
