package tuc.ro.ds2022.senderapplication.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MeasurementsCsvReader {

    private Scanner scanner;

    public MeasurementsCsvReader(String fileName) {
        try {
            this.scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Fisierul nu a fost gasit!");
        }
    }

    public double readMeasurementTupleFromFile(){
        //am deschis fisierul, deci acum trebuie sa citim o linie din csv daca mai exista linii, altfel, sa il inchidem.
        if(scanner != null){
            if(scanner.hasNextLine()){
                try{
                    return Double.parseDouble(scanner.next());
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                    System.err.println("Formatul valorii citite din fisier nu este acceptat de clasa!");
                }
            }
        }
        return -1; //daca nu s-a citit valoare corecta sau nu mai exista valori de citit, se returneaza -1 (nu putem avea valori negative).
    }
}
