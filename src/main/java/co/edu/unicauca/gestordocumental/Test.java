package co.edu.unicauca.gestordocumental;

import org.apache.logging.log4j.util.Strings;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Test {

    private double nuevo;
    private String pruebaTest;
    public int otro;

    public void getName() {
        String prueba = Arrays.stream(this.getClass().getDeclaredFields()).filter(field -> {
                    System.out.println(field.getName());
            return field.getName().equals("pruebaTest");
                })
                .findFirst()
                .map(Field::getName)
                .orElse(Strings.EMPTY);
        System.out.println("-> " + prueba);
        if (pruebaTest.isEmpty())
            System.out.println("--> " + pruebaTest.getClass().getName());
    }
}
