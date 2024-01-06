
package utils.validadores;

public class ValidadorCEP {
    private static String regiao;
    public static boolean isCepValido(String cep){
        if(cep.length() != 9){
            return false;
        }
        String digitoRegiaoDigitoSubRegiao = cep.substring(0,2);
        int cepInt = Integer.parseInt(digitoRegiaoDigitoSubRegiao);

        if (cepInt >= 1 && cepInt <= 5){
            regiao = "SP - Capital";
            return true;
        }
        if (cepInt >= 6 && cepInt <= 9){
            regiao = "SP - Área Metropolitana";
            return true;
        }
        if (cepInt == 11){
            regiao = "SP - Litoral";
            return true;
        }
        if (cepInt >= 12 && cepInt <= 19){
            regiao = "SP - Interior";
            return true;
        }
        return false;
    }
    public static String getRegiao() {
        return regiao;
    }

}
