package co.edu.unicauca.gestordocumental.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValorProperties {

    private static String HOST;
    private static String USUARIO;
    private static String CONTRASENA;


    private ValorProperties() {
    }

    public static String getUSUARIO() {
        return USUARIO;
    }

    private void setUSUARIO(String USUARIO) {
        ValorProperties.USUARIO = USUARIO;
    }

    public static String getCONTRASENA() {
        return CONTRASENA;
    }

    private void setCONTRASENA(String CONTRASENA) {
        ValorProperties.CONTRASENA = CONTRASENA;
    }

    private void setHOST(String HOST) {
        ValorProperties.HOST = HOST;
    }

    public static String getHost() {
        return HOST;
    }

    public static final class ValorPropertiesBuilder {
        private String HOST;
        private String USUARIO;
        private String CONTRASENA;

        private ValorPropertiesBuilder() {
        }

        public static ValorPropertiesBuilder aValorProperties() {
            return new ValorPropertiesBuilder();
        }

        public ValorPropertiesBuilder withHOST(String HOST) {
            this.HOST = HOST;
            return this;
        }
        public ValorPropertiesBuilder withUSUARIO(String USUARIO) {
            this.USUARIO = USUARIO;
            return this;
        }
        public ValorPropertiesBuilder withCONTRASENA(String CONTRASENA) {
            this.CONTRASENA = CONTRASENA;
            return this;
        }

        public ValorProperties build() {
            ValorProperties valorProperties = new ValorProperties();
            valorProperties.setHOST(HOST);
            valorProperties.setUSUARIO(USUARIO);
            valorProperties.setCONTRASENA(CONTRASENA);
            return valorProperties;
        }
    }
}
