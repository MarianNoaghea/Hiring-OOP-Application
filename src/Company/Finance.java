package Company;

public class Finance extends Departament{
    public Finance(String numeDepartament) {
        super(numeDepartament);
    }
    
    @Override
    public double getTotalSalaryBudget() {
        double totalSalaryBudget = 0;
        for(Employee employee : this.employees) {
            if(employee.getLuniVechime() <= 12)
                totalSalaryBudget = employee.salary - employee.salary * 0.1;
            else
                totalSalaryBudget = employee.salary - employee.salary * 0.16;
        }

        return totalSalaryBudget;
    }

    public String getTaxRule() {
        return "Pentru vechime <= 1 an : taxe de 10% \n Pentru vechime > 1 an : taxe de 16%";
    }
}
