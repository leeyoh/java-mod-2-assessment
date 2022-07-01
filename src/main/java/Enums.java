enum Specalties {
    DERMATOLOGY (1),
    PEDIATRICS (2),
    RADIOLOGY (3),
    DENTIST (4);
    private final int index;
    Specalties(int i){
        index = i;
    }
    int getIndex(){
        return index;
    }
}

enum Symptoms {
    RASH (1),
    COLIC (2),
    CANCER (3),
    CAVITY (4);
    private final int index;
    Symptoms(int i){
        index = i;
    }
    int getIndex(){
        return index;
    }
}