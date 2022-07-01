public class Doctor {
  
    private Specalties specialty; 
    private final int MAX_PATIENT = 2;
    public String name; 
    private int currentNumPatients = 0;

    public Doctor(String name, Specalties special){
        this.name = name; 
        this.specialty = special;
    }

    public Specalties getSpecialty(){
        return this.specialty;
    }

    public void increamentPatient(){
        this.currentNumPatients += 1;
    }

    public boolean canTakeMorePatients(){
        return this.currentNumPatients < this.MAX_PATIENT;
    }
    @Override
    public String toString(){
        return name;
    }
}
