package Company;

public class Management  extends Departament {

    public Management(String numeDepartament) {
        super(numeDepartament);
    }

    @Override
    public double getTotalSalaryBudget() {
        double totalSalaryBudget = 0;
        for(Employee employee : this.employees) {
            totalSalaryBudget += employee.salary - employee.salary * 0.16;
        }
    return totalSalaryBudget;
    }

    public String getTaxRule() {
        return "Taxe de 16% in orice situatie ";
    }
}
