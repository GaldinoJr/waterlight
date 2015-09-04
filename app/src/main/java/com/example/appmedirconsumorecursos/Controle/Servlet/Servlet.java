package com.example.appmedirconsumorecursos.Controle.Servlet;

import com.example.appmedirconsumorecursos.Controle.ViewHelper.impl.AguaViewHelper;
import com.example.appmedirconsumorecursos.Controle.ViewHelper.impl.ConfiguracaoSistemaViewHelper;
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
import com.example.appmedirconsumorecursos.Dominio.Luz;
import com.example.appmedirconsumorecursos.Dominio.Residencia;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Galdino on 19/08/2015.
 */
public class Servlet {
    private static final long serialVersionUID = 1L;

    private static Map<String, ICommand> commands;
    private static Map<String, IViewHelper> vhs;
    public Servlet()
    {
        commands = new HashMap<String, ICommand>();
        // CADASTRA OS COMANDOS

        commands.put("salvar",  new SalvarCommand());
        commands.put("excluir", new ExcluirCommand());
        commands.put("consultar", new ConsultarCommand());
        commands.put("alterar", new AlterarCommand());

        vhs = new HashMap<String, IViewHelper>();
        //CADASTRA AS CLASSES ************
        vhs.put(Luz.class.getName(), new AguaViewHelper());
        vhs.put(Agua.class.getName(), new LuzViewHelper());
        vhs.put(Residencia.class.getName(), new ResidenciaViewHelper());
        vhs.put((ConfiguracaoSistema.class.getName()), new ConfiguracaoSistemaViewHelper());

    }
    public Resultado doPost(Map request){
        // Pega qual é a operação desejada(EX: CRUD) e qual a classe que está execultando a operação
        String operacao = (String)request.get("operacao");
        String classe = (String)request.get("classe");
        // Através do nome da classe, diz qual é o viewHelper da correspondente da mesma
        IViewHelper vh = vhs.get(classe); // manda o nome da classe e recebe o new do viewHelper Correspondente
        EntidadeDominio entidade =  vh.getEntidade(request); // pega os dados da entidade correspondete
        // Verifica qual é a operação que está sendo feita
        // de acordo com o que está descrito ácima, tem um mapa a chave é o nome da operação
        // e o conteudo é o new da mesma
        ICommand command = commands.get(operacao);
        Resultado resultado = command.execute(entidade); // Execulta a operação e
        return resultado; // retorna o resultado da mesma
    }
}
