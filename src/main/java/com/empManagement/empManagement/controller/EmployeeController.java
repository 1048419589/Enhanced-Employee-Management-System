package com.empManagement.empManagement.controller;

import com.empManagement.empManagement.entity.Employee;
import com.empManagement.empManagement.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    //基础页面跳转
    @GetMapping("/home")
    public String showHomePage() {
        return "pages/landing_page";
    }

    @GetMapping("/employee_register")
    public String showRegisterPage() {
        return "pages/employee_register";
    }

    @GetMapping("/logout")
    public String handleLogout() {
        return "pages/auth-login";
    }

    //列表与搜索逻辑
    @GetMapping("/employee_details")
    public String listDetails(@RequestParam(required = false) String keyword, Model model) {
        populateEmployeeList(model, keyword);
        return "pages/employee_details";
    }

    @GetMapping("/attendance")
    public String listAttendance(@RequestParam(required = false) String keyword, Model model) {
        populateEmployeeList(model, keyword);
        return "pages/attendance";
    }

//    @GetMapping("/show_attendance")
//    public String listShowAttendance(@RequestParam(required = false) String keyword, Model model) {
//        populateEmployeeList(model, keyword);
//        return "pages/show_attendance";
//    }

    @GetMapping("/payroll")
    public String listPayroll(@RequestParam(required = false) String keyword, Model model) {
        populateEmployeeList(model, keyword);
        return "pages/payroll";
    }

    //辅助方法：抽取重复的搜索逻辑
    private void populateEmployeeList(Model model, String keyword) {
        List<Employee> list = (keyword != null) ? service.getByKeyword(keyword) : service.getAllemployees();
        model.addAttribute("employee", list);
    }

    //员工操作
    @PostMapping("/save")
    public String saveOrUpdate(@ModelAttribute Employee formEmployee,
                               @RequestParam(required = false, defaultValue = "employee_details") String redirectUrl) {
        if (formEmployee.getId() == 0) {
            service.save(formEmployee);
        } else {
            Employee dbEmployee = service.getemployeeById(formEmployee.getId());
            BeanUtils.copyProperties(formEmployee, dbEmployee, getNullPropertyNames(formEmployee));
            service.save(dbEmployee);
        }

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/employee/edit/{id}")
    public String editEmployee(@PathVariable("id") int id, Model model) {
        model.addAttribute("employee", service.getemployeeById(id));
        return "pages/employee_edit";
    }

    @GetMapping("/payroll/edit/{id}")
    public String editPayroll(@PathVariable("id") int id, Model model) {
        model.addAttribute("employee", service.getemployeeById(id));
        return "pages/edit_payroll";
    }

    @GetMapping("/attendance/edit/{id}")
    public String editAttendance(@PathVariable("id") int id, Model model) {
        model.addAttribute("employee", service.getemployeeById(id));
        return "pages/edit_attendance";
    }

    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {
        service.deleteById(id);
        return "redirect:/employee_details";
    }

    //看板数据展示
    @GetMapping({"/", "/landing_page"})
    public String showDashboard(Model model) {
        // 统计数据
        model.addAttribute("employeeCount", service.getEmployeeCount());
        model.addAttribute("attendanceCount", service.getCountOfEmployeesWithAttendance());
        model.addAttribute("LeaveCount", service.getCountOfEmployeesWithOutAttendance());
        model.addAttribute("averageSalary", service.getAverageSalary());
        model.addAttribute("totalSalary", service.getTotalSalary());

        // 部门分布
        model.addAttribute("it", service.getCountOfEmployeesIT());
        model.addAttribute("hrm", service.getCountOfEmployeesHRM());
        model.addAttribute("technical", service.getCountOfEmployeesTechnical());
        model.addAttribute("design", service.getCountOfEmployeesDesign());
        model.addAttribute("social", service.getCountOfEmployeesSocial());
        model.addAttribute("sectionPercentages", service.getSectionPercentages());

        // 缺勤列表
        model.addAttribute("employee", service.getEmployeesWithNoAttendance());

        return "pages/landing_page";
    }

    //内部工具方法
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames.toArray(new String[0]);
    }
}