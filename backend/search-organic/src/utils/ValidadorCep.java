
package utils;

public class ValidadorCep {
    public static boolean isCepValido(String cep){
        if(cep.length() != 9){
            return false;
        }
        String digitoRegiaoDigitoSubRegiao = cep.substring(0,2);
        int cepInt = Integer.parseInt(digitoRegiaoDigitoSubRegiao);

        if (cepInt >= 1 && cepInt <= 5){
            String regiao = "SP - Capital";
            System.out.println(regiao);
            return true;
        }
        if (cepInt >= 6 && cepInt <= 9){
            String regiao = "SP - Área Metropolitana";
            System.out.println(regiao);
            return true;
        }
        if (cepInt == 11){
            String regiao = "SP - Litoral";
            System.out.println(regiao);
            return true;
        }
        if (cepInt >= 12 && cepInt <= 19){
            String regiao = "SP - Interior";
            System.out.println(regiao);
            return true;
        }
        return false;
    }
}
