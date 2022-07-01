public class Patient {
  
    private Symptoms symptom; 
    public String name; 

    public Patient(String name, Symptoms symptom){
        this.name = name; 
        this.symptom = symptom;
    }

    public Symptoms getSymptoms(){
        return this.symptom;
    }

    @Override
    public String toString(){
        return name;
    }
}
