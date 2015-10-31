package com.example.appmedirconsumorecursos.Controle.Controler;

import com.example.appmedirconsumorecursos.Controle.ViewHelper.impl.AguaViewHelper;
import com.example.appmedirconsumorecursos.Controle.ViewHelper.impl.ConfiguracaoSistemaViewHelper;
import com.example.appmedirconsumorecursos.Controle.ViewHelper.impl.GastoAtualViewHelper;
import com.example.appmedirconsumorecursos.Controle.ViewHelper.impl.GastoHojeViewHelper;
import com.example.appmedirconsumorecursos.Controle.ViewHelper.impl.GastoMesViewHelper;
import com.example.appmedirconsumorecursos.Controle.ViewHelper.impl.LuzViewHelper;
import com.example.appmedirconsumorecursos.Controle.ViewHelper.impl.ResidenciaViewHelper;
import com.example.appmedirconsumorecursos.Controle.ViewHelper2.IViewHelper;
import com.example.appmedirconsumorecursos.Core.Aplicacao.Resultado;
import com.example.appmedirconsumorecursos.Core.command.ICommand;
import com.example.appmedirconsumorecursos.Core.command.Impl.AlterarCommand;
import com.example.appmedirconsumorecursos.Core.command.Impl.ConsultarCommand;
import com.example.appmedirconsumorecursos.Core.command.Impl.ExcluirCommand;
import com.example.appmedirconsumorecursos.Core.command.Impl.SalvarCommand;
import com.example.appmedirconsumorecursos.Dominio.Agua;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Dominio.ConfiguracaoSistema;
import com.example.appmedirconsumorecursos.Dominio.GastoAtual;
import com.example.appmedirconsumorecursos.Dominio.GastoHoje;
import com.example.appmedirconsumorecursos.Dominio.GastoMes;
import com.example.appmedirconsumorecursos.Dominio.Luz;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Galdino on 19/08/2015.
 */
public class Controler {
    private static final long serialVersionUID = 1L;
    public static final String DF_SALVAR = "salvar";
    public static final String DF_CONSULTAR = "consultar";
    public static final String DF_ALTERAR = "alterar";
    public static final String DF_EXCLUIR = "excluir";


    private static Map<String, ICommand> commands;
    private static Map<String, IViewHelper> vhs;
    public Controler()
    {
        commands = new HashMap<String, ICommand>();
        // CADASTRA OS COMANDOS

        commands.put(DF_SALVAR,  new SalvarCommand());
        commands.put(DF_EXCLUIR, new ExcluirCommand());
        commands.put(DF_CONSULTAR, new ConsultarCommand());
        commands.put(DF_ALTERAR, new AlterarCommand());

        vhs = new HashMap<String, IViewHelper>();
        //CADASTRA AS CLASSES ************
        vhs.put(Luz.class.getName(), new AguaViewHelper());
        vhs.put(Agua.class.getName(), new LuzViewHelper());
        vhs.put(Residencia.class.getName(), new ResidenciaViewHelper());
        vhs.put(ConfiguracaoSistema.class.getName(), new ConfiguracaoSistemaViewHelper());
        vhs.put(GastoAtual.class.getName(), new GastoAtualViewHelper());
        vhs.put(GastoHoje.class.getName(), new GastoHojeViewHelper());
        vhs.put(GastoMes.class.getName(), new GastoMesViewHelper());

    }
    public Resultado doPost(Map request){
        // Pega qual a a operação desejada(EX: CRUD) e qual a classe que está execultando a operação
        String operacao = (String)request.get("operacao");
        String classe = (String)request.get("classe");
        // Através do nome da classe, diz qual é o viewHelper da correspondente da mesma
        IViewHelper vh = vhs.get(classe); // manda o nome da classe e recebe o new do viewHelper Correspondente
        EntidadeDominio entidade =  vh.getEntidade(request); // pega os dados da entidade correspondete
        // Verifica qual a a operação que está sendo feita
        // de acordo com o que está descrito acima, tem um mapa a chave é o nome da operação
        // e o conteudo é o new da mesma
        ICommand command = commands.get(operacao);
        Resultado resultado = command.execute(entidade); // Execulta a operação e
        return resultado; // retorna o resultado da mesma
    }
}
