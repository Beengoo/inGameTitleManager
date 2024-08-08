package ua.beengoo.ingametitlemanager.utils.config;

public class ConfigurationChecker {
    public static void makeProbe(Object probeValue, Object defaultValue) throws ConfigCheckException{
        if (defaultValue != null) {
            if (probeValue == null) {
                throw new ConfigCheckException("Probe value is null.");
            } else {
                String value = probeValue.toString();

                try {
                    if (defaultValue instanceof Integer) {
                        Integer.parseInt(value);
                    } else if (defaultValue instanceof Boolean) {
                        Boolean.parseBoolean(value);
                    } else if (defaultValue instanceof Double) {
                        Double.parseDouble(value);
                    } else if (defaultValue instanceof Long) {
                        Long.parseLong(value);
                    } else if (defaultValue instanceof Enum) {
                        @SuppressWarnings("unchecked")
                        Class<Enum> enumClass = (Class<Enum>) defaultValue.getClass();
                        Enum.valueOf(enumClass, value);
                    }
                } catch (Exception e) {
                    if (e instanceof ConfigCheckException) {
                        throw (ConfigCheckException) e;
                    } else {
                        throw new ConfigCheckException("Cannot parse input value!", e);
                    }
                }
            }
        }
    }
}
