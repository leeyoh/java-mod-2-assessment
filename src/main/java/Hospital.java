import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hospital {
    private int numPatients, numDoctors; 
    private String name; 

    private List<Doctor> doctors;
    private List<Patient> patients;

    private Map<Doctor, List<Patient>> patientChart; 
    private Map<Specalties, List<Doctor>> docChart; 
    private Map<Symptoms,Specalties> symToSpecial; 
    
    private List<Patient> waitList; 

    public Hospital(int numPatients,int  numDoctors, String name){
        this.numDoctors = numDoctors;
        this.numPatients = numPatients;
        this.name = name; 

        this.doctors = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.waitList = new ArrayList<>();

        patientChart = new HashMap<>(); 
        docChart = new HashMap<>();
        symToSpecial = new HashMap<>(); 

        symToSpecial.put(Symptoms.CANCER, Specalties.RADIOLOGY);
        symToSpecial.put(Symptoms.CAVITY, Specalties.DERMATOLOGY);
        symToSpecial.put(Symptoms.COLIC, Specalties.PEDIATRICS);
        symToSpecial.put(Symptoms.RASH, Specalties.DERMATOLOGY);
    }


    @Override
    public String toString(){
        return "Hospital: " + this.name;
    }

    public String[] getDoctors(){
        String[] docs = new String[doctors.size()];
        int index = 0;

        for(Doctor doc: doctors){
            docs[index] = doc.toString();
            index ++;
        }
        return docs;
    }
    public void printDocChart(){
        System.out.println(this.docChart);
    }

    public void printPatientChart(){
        System.out.println(this.patientChart);
    }
    public int addDoctor(Doctor doc){
        Specalties special = doc.getSpecialty();
       
        if(doctors.size() >= numDoctors){
            return 1; 
        }
        this.doctors.add(doc);
        if(docChart.containsKey(special)){
            docChart.get(special).add(doc);
            return 0;
        }

        List<Doctor> doclist =  new ArrayList<Doctor>();
        doclist.add(doc);
        docChart.put(special, doclist);
        return 0;
    }

    public int addPatient(Patient pat){
        if(patients.size() >= numPatients){
            return 1; 
        }
        this.patients.add(pat);

        Doctor doc = findDoctor(findSpecalties(pat.getSymptoms()));
        if(doc == null){
            System.out.println(ConsoleColors.BLUE_BACKGROUND + "No Doctor found, added to waitlist" + ConsoleColors.RESET);
            waitList.add(pat);
            return 0; 
        }

        System.out.println(ConsoleColors.BLUE_BACKGROUND + "Assigned to "+ doc + ConsoleColors.RESET);
        if(patientChart.containsKey(doc)){
            patientChart.get(doc).add(pat);
            return 0;
        }

        List<Patient> patList =  new ArrayList<Patient>();
        patList.add(pat);
        patientChart.put(doc,patList);
        return 0;
    }

    public void printHospital(){
        System.out.print(ConsoleColors.GREEN_BACKGROUND);
        System.out.println("" + ConsoleColors.RESET);
        System.out.println("Hospital: " + 
            ConsoleColors.CYAN_BACKGROUND + this.name + ConsoleColors.RESET + " Max Patient per Doc : " + 
            ConsoleColors.CYAN_BACKGROUND + "2" + ConsoleColors.RESET + " # Docs : " + 
            ConsoleColors.CYAN_BACKGROUND +  this.numDoctors + ConsoleColors.RESET + " # Patients : " + 
            ConsoleColors.CYAN_BACKGROUND + this.numPatients + ConsoleColors.RESET);

        System.out.println("---------------------------");
        System.out.println("Waitlist: " + ConsoleColors.CYAN_BACKGROUND + this.waitList + ConsoleColors.RESET);

        System.out.println("Specialties: " + ConsoleColors.CYAN_BACKGROUND + Arrays.asList(Specalties.values())+ ConsoleColors.RESET);
        System.out.println("Symptoms: " + ConsoleColors.CYAN_BACKGROUND + Arrays.asList(Symptoms.values()) + ConsoleColors.RESET);
        System.out.println("---------------------------");

        System.out.println("Map < Doctor , Patient > ");
        System.out.println(ConsoleColors.CYAN_BACKGROUND + patientChart + ConsoleColors.RESET);


        System.out.println("Map < Specialties , Doctors > ");
        System.out.println(ConsoleColors.CYAN_BACKGROUND + docChart + ConsoleColors.RESET);


        System.out.println("Map < Symptoms , Specialties > ");
        System.out.println(ConsoleColors.CYAN_BACKGROUND + symToSpecial + ConsoleColors.RESET);
    }
    public int numDoctors(){
        return this.numDoctors;
    }

    public int numPatients(){
        return this.numPatients;
    }

    private Specalties findSpecalties(Symptoms symptom){
        return symToSpecial.get(symptom);
    }

    private Doctor findDoctor(Specalties specalty){
        List<Doctor> doclist =  docChart.get(specalty);
        if(doclist == null){
            return null;
        }
        for(Doctor doc: doclist){
            if(doc.canTakeMorePatients()){
                doc.increamentPatient();
                return doc;
            }
        }
        return null;
    }
}
