package co.edu.unicauca.gestordocumental.util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConvertirJson {

    public List<JSONObject> convertir(String[] campos, List<Object[]> valores){
        List<JSONObject> aux = new ArrayList<>();
        valores.forEach((item)->{
            JSONObject objeto = new JSONObject();
            int i = 0;
            for (String campo : campos) {
                objeto.put(campo, item[i]);
                i++;
            }
            aux.add(objeto);

        });
        return aux;
    }
}
