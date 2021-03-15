package Company;

public class Marketing extends Departament {

    public Marketing(String numeDepartament) {
        super(numeDepartament);
    }

    @Override
    public double getTotalSalaryBudget() {
        double totalSalaryBudget = 0;
        for(Employee employee : employees) {
            if(employee.salary > 5000)
                totalSalaryBudget += employee.salary - employee.salary * 0.1;
            else
                if(employee.salary < 3000)
                    totalSalaryBudget += employee.salary;
                else
                    totalSalaryBudget += employee.salary + employee.salary * 0.16;
        }
        return totalSalaryBudget;
    }

    public String getTaxRule() {
        return "Pentru salarii > 5000 lei : taxe de 10% " +
                "\n Pentru salarii < 3000 lei : Scutiti de taxe " +
                "\n Pentru restul : taxe de 16%";
    }
}
