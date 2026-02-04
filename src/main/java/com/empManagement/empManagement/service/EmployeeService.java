package com.empManagement.empManagement.service;

import com.empManagement.empManagement.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empManagement.empManagement.repository.EmployeeRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
//核心操作层 通过调用repository来进行数据处理
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository bRepo;

    @Autowired
    private StringRedisTemplate redisTemplate; //redis工具

    public void save(Employee b){
        bRepo.save(b);
        redisTemplate.delete("total_employee_count");
        System.out.println(" [Cache Evict] 数据变动，已清空缓存！");
    }//保存员工



    public Employee getemployeeById(int id){
        return bRepo.findById(id).get();
    }

    public void deleteById(int id) {
        bRepo.deleteById(id);
        redisTemplate.delete("total_employee_count");
        System.out.println("[Cache Evict] 数据变动，已清空缓存！");
    }//通过id删除员工



    public List<Employee> getAllemployees(){
        return bRepo.findAll();
    }//显示所有员工

    public List<Employee> getByKeyword(String keyword){
        return bRepo.findByKeyword(keyword);
    }//通过身份证或者全名查找

    public long getEmployeeCount() {
        String cacheKey = "total_employee_count";

        // 先去Redis找
        String cachedValue = redisTemplate.opsForValue().get(cacheKey);

        if (cachedValue != null) {
            //找到直接转成long返回
            System.out.println(" [Redis Cache Hit] ");
            return Long.parseLong(cachedValue);
        }

        // 没找到去MySQL
        System.out.println("[Cache Miss] ");
        long dbCount = bRepo.count();


        redisTemplate.opsForValue().set(cacheKey, String.valueOf(dbCount));


        return dbCount;
    }




//算平均工资
    public double getAverageSalary() {
        List<Employee> employees = bRepo.findAll();
        double totalSalary = employees.stream()
                .filter(employee -> employee.getSalary() != null)//过滤掉空值的
                .mapToDouble(employee -> {
                    try {
                        return Double.parseDouble(employee.getSalary());//强转为double类型
                    } catch (NumberFormatException e) {

                        return 0.0;//非法字符就为0
                    }
                })
                .sum();
        return employees.isEmpty() ? 0.0 : totalSalary / employees.size();
    }



    //是否出勤筛选
    public long getCountOfEmployeesWithAttendance() {
        return bRepo.countByisAttedenceIgnoreCase("Yes");
    }
    public long getCountOfEmployeesWithOutAttendance() {
        return bRepo.countByisAttedenceIgnoreCase("No");
    }
//找出具体未出勤名单
    public List<Employee> getEmployeesWithNoAttendance() {
        return bRepo.findByisAttedenceIgnoreCase("No");

    }



    //总薪水
    public double getTotalSalary() {
        List<Employee> employees = bRepo.findAll();
        double totalSalary = employees.stream()
                .filter(employee -> employee.getSalary() != null)
                .mapToDouble(employee -> {
                    try {
                        return Double.parseDouble(employee.getSalary());
                    } catch (NumberFormatException e) {

                        return 0.0;
                    }
                })
                .sum();
        return totalSalary;
    }

    //统计各部门人数
    public long getCountOfEmployeesIT() {
        return bRepo.countBydepartmentIgnoreCase("IT");
    }
    public long getCountOfEmployeesHRM() {
        return bRepo.countBydepartmentIgnoreCase("HRM");
    }
    public long getCountOfEmployeesTechnical() {
        return bRepo.countBydepartmentIgnoreCase("Technical");
    }
    public long getCountOfEmployeesDesign() {
        return bRepo.countBydepartmentIgnoreCase("Design");
    }
    public long getCountOfEmployeesSocial() {
        return bRepo.countBydepartmentIgnoreCase("Social");
    }

    //计算各个部门占比
    public Map<String, Double> getSectionPercentages() {
        List<Employee> employees = bRepo.findAll();
        //各部门计数
        Map<String, Long> sectionCounts = employees.stream()
                .collect(Collectors.groupingBy(
                        employee -> getSection(employee.getDepartment()),
                        Collectors.counting()
                ));

        long totalEmployees = employees.size();
        Map<String, Double> sectionPercentages = new HashMap<>();

        //算百分比
        for (String section : sectionCounts.keySet()) {
            long sectionCount = sectionCounts.get(section);
            double sectionPercentage = (sectionCount * 100.0) / totalEmployees;
            sectionPercentages.put(section, sectionPercentage);
        }

        return sectionPercentages;
    }

    //字符纠错
    private String getSection(String department) {
        if (department == null) {
            return "Unknown";
        }

        department = department.toLowerCase();

        if (department.contains("it")) {
            return "IT";
        } else if (department.contains("hrm")) {
            return "HRM";
        } else if (department.contains("technical")) {
            return "Technical";
        } else if (department.contains("design")) {
            return "Design";
        } else if (department.contains("social")) {
            return "Social";
        } else {
            return "Unknown";
        }
    }


}
