package com.example.appmedirconsumorecursos.Core.impl.Negocio;

import com.example.appmedirconsumorecursos.Core.IStrategy;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

/**
 * Created by Galdino on 03/09/2015.
 */
public class ResidenciaValidarLogin  implements IStrategy {
    private Residencia residencia;
    @Override
    public String processar(EntidadeDominio entidade) {
        // Ver como vai fazer, pois nesse ponto não tem os dados do banco ainda, ou seja, não da pra validar a  login
        // teria que fazer um select no DAO de residencias daqui, mas entraria na regra novamente,
        // se for fazer mesmo, colocar uma variável na entidade residencia para verificar se a consulta vem da regra,
        // para não passar nela novamente e entrar em looping
        if(entidade !=null) // tem dados cadastrados na entidade?
        { // sim
            residencia = (Residencia) entidade;
            return null;
        }
        return null;
    }
}
