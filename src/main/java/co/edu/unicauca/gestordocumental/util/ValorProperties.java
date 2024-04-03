package co.edu.unicauca.gestordocumental.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValorProperties {

    private static String HOST;

    private ValorProperties() {
    }

    private void setHOST(String HOST) {
        ValorProperties.HOST = HOST;
    }

    public static String getHost() {
        return HOST;
    }

    public static final class ValorPropertiesBuilder {
        private String HOST;

        private ValorPropertiesBuilder() {
        }

        public static ValorPropertiesBuilder aValorProperties() {
            return new ValorPropertiesBuilder();
        }

        public ValorPropertiesBuilder withHOST(String HOST) {
            this.HOST = HOST;
            return this;
        }

        public ValorProperties build() {
            ValorProperties valorProperties = new ValorProperties();
            valorProperties.setHOST(HOST);
            return valorProperties;
        }
    }
}
