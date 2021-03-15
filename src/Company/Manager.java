package Company;

import Aplication.Aplication;
import User.User;
import User.Consumer;
import java.util.Comparator;
import java.util.TreeSet;


public class Manager extends Employee {
    TreeSet<Request> requests = new TreeSet<Request>(new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {

            return o2.getScore().compareTo(o1.getScore());
        }
    });

    public void receiveRequest(Request request) {
        requests.add(request);
    }

    public void process(Job job) {
        Aplication aplication = Aplication.getInstance();
        for(Request request : requests) {
            if (((Job) request.getKey()).meetsRequirments(((User) request.getValue1()))) {
                if(job.noPositions > 0) {

                    User user = (User) request.getValue1();

                    for (Consumer u : aplication.users) {
                        if (u.equals(user)) {

                            Employee newEmployee = user.convert();

                            this.getCompany().notify(user, "JOB PRIMIT cu scor: "
                                    + ((Double) request.getScore())
                                    + " de catre: "
                                    + ((User) request.getValue1()).cod);

                            System.out.println();

                            this.getCompany().getITDepartment().add(newEmployee);

                            aplication.users.remove(user);
                            //dezabonez userul de la notificarile de angajare
                            this.getCompany().unsubscribe(user);

                            job.noPositions--;

                            break;
                        }
                    }

                }
                else return;
            }
        }
    }

    public String getNumeCompanie() {
        return this.numeCompanie;
    }

    public TreeSet<Request> getRequests() { //metoda scrisa de mine pentru ManagerPage
        return requests;
    }

    public Request getRequest(int index) { //metoda pentru ManagerPage
        int i = 0;

        for(Request request : requests) {
            if(i == index)
                return request;
            i++;
        }
        return null;
    }

    public void deleteRequest(int index) { //metoda pentru ManagerPage
        int i = 0;

        Request toDeleteRequest = null;
        for(Request request : requests) {
            if(i == index)
                toDeleteRequest = request;
            i++;
        }
        if(toDeleteRequest != null)
            requests.remove(toDeleteRequest);
    }
}
