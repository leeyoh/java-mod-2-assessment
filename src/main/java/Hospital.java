import java.util.ArrayList;
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
        if(patientChart.containsKey(doc)){
            patientChart.get(doc).add(pat);
            return 0;
        }

        List<Patient> patList =  new ArrayList<Patient>();
        patList.add(pat);
        patientChart.put(doc,patList);
        System.out.println(ConsoleColors.BLUE_BACKGROUND + "Assigned to "+ doc + ConsoleColors.RESET);
        return 0;
    }

    public void printHospital(){
        System.out.println("Hospital: " + this.name + " # Docs : " + this.numDoctors + " # Patients : " + this.numPatients);
        System.out.println("Doctors: " + this.doctors);
        System.out.println("Patients: " + this.patients);

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
