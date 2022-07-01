import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Hospital hospital; 

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        try{
            hospital = startHospital(scanner);
            System.out.println(hospital);
            for(int i = 1; i <= hospital.numDoctors(); i++){
                System.out.println("----- Doctor " + i + "-----");
                hospital.addDoctor(createDoctor(scanner));
            }
            for(int i = 1; i <= hospital.numPatients(); i++){
                System.out.println("----- Patient " + i + "-----");
                hospital.addPatient(createPatient(scanner));
            }
            hospital.printHospital();
            //hospital.printPatientChart();
        }catch(Exception e){
            printError(e.toString());
        }
        scanner.close();
    }

    private static Hospital startHospital(Scanner sc){
        String name;
        int patients, doctors; 

        Main.printQuestion("Hospital Name? : ");
        name = Main.getInputString(sc, new String[0]);

        Main.printQuestion("# Doctors? : ");
        doctors = Main.getInputInt(sc,0, 1000);

        Main.printQuestion("# Patients? : ");
        patients = Main.getInputInt(sc,0, 1000);

        return new Hospital(patients, doctors, name);
    }

    private static Patient createPatient(Scanner sc){
        String name;
        int symptomIndex;
        Symptoms symptom;
        Main.printQuestion("Patient Name? :");
        name = Main.getInputString(sc, new String[0]);

        for(Symptoms s : Symptoms.values()){
            System.out.println(s.getIndex() + ": " + s);
        }

        Main.printQuestion("Type Index (#): ");
        symptomIndex = Main.getInputInt(sc, 1, Symptoms.values().length);
        symptom = Symptoms.values()[symptomIndex-1];
        return new Patient(name, symptom);
    }

    private static Doctor createDoctor(Scanner sc){
        String name;
        int specialIndex;
        Specalties specialty;

        Main.printQuestion("Doctor Name? :");
        name = Main.getInputString(sc,hospital.getDoctors());

        for(Specalties s : Specalties.values()){
            System.out.println(s.getIndex() + ": " + s);
        }
        Main.printQuestion("Type Index (#): ");
        specialIndex = Main.getInputInt(sc, 1, Specalties.values().length);
        specialty = Specalties.values()[specialIndex-1];
        return new Doctor(name, specialty);
    }

    public static void printError(String str){
        System.out.println(ConsoleColors.RED_BOLD +str + ConsoleColors.RESET);
    }
    public static void printQuestion(String str){
        System.out.print(ConsoleColors.GREEN_BOLD +str + ConsoleColors.RESET);
    }

    public static String getInputString(Scanner sc, String[] repeatList){
        String value;
        while(true){
            try{
                value = sc.nextLine();
                if(Arrays.asList(repeatList).contains(value)){
                    printError(value + " already in list");
                } else if(value.matches("[a-zA-Z]+")){
                    return value;
                }
                printError("Only a-z A-Z");
            }catch (Exception e){
                printError(e.toString());
            }finally{
               // sc.nextLine();
            }
            System.out.print(": ");
        }
    }

    public static int getInputInt(Scanner sc, int min, int max){
        int value;
        while(true){
            try{
                value = sc.nextInt();
                if(value >= min && value <= max){
                    return value;
                }
                printError("value not in range " + min + " - " + max);
            } catch (Exception e){
               printError("only nums allowed");
            } finally{
                sc.nextLine();
            }
            System.out.print(": ");
        }
    }
}