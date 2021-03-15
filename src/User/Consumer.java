package User;

import Aplication.Aplication;
import Exceptii.ResumeIncompleteException;

import java.util.*;

public abstract class Consumer {
    public String cod = "";
    public Resume resume; // = new Resume.ResumeBuilder();
    ArrayList<Consumer> cunoscuti = new ArrayList<>();

    public void add(Education education) {
        resume.educations.add(education);
    }

    public void add(Experience experience) {
        resume.experiences.add(experience);
    }

    public void add(Consumer consumer) {
        cunoscuti.add(consumer);
    }


    public int getDegreeInFriendship(Consumer consumer) {
        //metoda aceasta de bfs este prezentata in curusl
        // de sttructuri de date
        Aplication aplication = Aplication.getInstance();
        int indexPornire = aplication.getIndexOfUser(this);
        int numberOfUsers = aplication.getNumberOfUsers();
        Integer []visitVector = new Integer[numberOfUsers];
        Integer []distanceVector = new Integer[numberOfUsers];
        for(int i = 0; i < numberOfUsers; i++) {
            visitVector[i] = 0;
            distanceVector[i] = 0;
        }
        int x, y;

        Queue<Integer> queue = new LinkedList();

        visitVector[indexPornire] = 1;
        distanceVector[indexPornire] = 0;
        //distVector[y] = distanta de la nodul x la nodul y
        //visitVector ne ajuta pentru a nu intra in loop infinit
        queue.add(indexPornire);

        while(!queue.isEmpty()) {
            x = queue.remove();

            for(y = 0; y < numberOfUsers; y++)
                if(Aplication.getInstance().graph[x][y] != 0 && visitVector[y].equals(0)) {
                    visitVector[y] = 1;
                    queue.add(y);
                    distanceVector[y] = distanceVector[x] + 1;
                }
        }

        return distanceVector[aplication.getIndexOfUser(consumer)];
    }

    public void remove(Consumer consumer) {
        for(Consumer cunoscut : cunoscuti) {
            if(cunoscut.equals(consumer))
                cunoscuti.remove(cunoscut);
        }
    }

    public Integer getGraduationYear() {
        Integer gradYear = null;
        for(Education education : resume.educations)
            if(education.nivelEducatie.equals("college"))
                gradYear = education.dataSfarsit.getYear();
        return gradYear;
    }

    public Double meanGPA() {
        Double medieStudii = 0.0;

        for(Education education : resume.educations) {
            medieStudii += education.medieFinala;
        }
        return medieStudii / resume.educations.size();
    }

    public String getName() {
        return resume.information.getNume();
    }

    public static class Resume {
        private Information information;
        private TreeSet<Education> educations = new TreeSet<>();
        private TreeSet<Experience> experiences = new TreeSet<>();

        public Resume(ResumeBuilder builder) {
            this.information = builder.information;
            this.experiences = builder.experiences;
            this.educations = builder.educations;
        }

        public Information getInformation() {
            return information;
        }

        public TreeSet<Education> getEducations() {
            return educations;
        }

        public TreeSet<Experience> getExperiences() {
            return experiences;
        }

        //builder pattern
        public static class ResumeBuilder {

            private Information information;
            public TreeSet<Education> educations = new TreeSet<>();
            public TreeSet<Experience> experiences = new TreeSet<>();

            public ResumeBuilder information(Information information) throws ResumeIncompleteException {
                if(information == null)
                    throw new ResumeIncompleteException("information is null");
                this.information = information;
                return this;
            }

            public ResumeBuilder educations(TreeSet<Education> educations) throws ResumeIncompleteException{
                if(educations == null)
                    throw new ResumeIncompleteException("education is null");
                this.educations = educations;
                return this;
            }

            public ResumeBuilder experiences(TreeSet<Experience> experiences) throws ResumeIncompleteException{
                if(experiences == null)
                    throw new ResumeIncompleteException("experience is null");
                this.experiences = experiences;
                return this;
            }

            public Resume build() {
                return new Resume(this);
            }
        }
    }
}
