package Company;



public class DepartmentFactory {
    //factory pattern
    public Departament factory(String numeDepartament) {
        if(numeDepartament.equals("IT"))
            return new IT(numeDepartament);
        if(numeDepartament.equals("Finance"))
            return new Finance(numeDepartament);
        if(numeDepartament.equals("Management"))
            return new Management(numeDepartament);
        if(numeDepartament.equals("Marketing"))
            return  new Marketing(numeDepartament);

        return null;
    }
}
