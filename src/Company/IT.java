package Company;

public class IT extends Departament{
    boolean scutireTaxe = true;

    public IT(String numeDepartament) {
        super(numeDepartament);
    }

    @Override
    public double getTotalSalaryBudget() {
        double totalSalary = Double.valueOf(0);
        for(Job job : this.jobs) {
            totalSalary += job.salariu;
        }

        return totalSalary;
    }

    public String getTaxRule() {
        return "Scutiti de taxe";
    }
}
