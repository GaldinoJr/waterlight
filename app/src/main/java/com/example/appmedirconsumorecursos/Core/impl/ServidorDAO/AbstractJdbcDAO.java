package com.example.appmedirconsumorecursos.Core.impl.ServidorDAO;

import com.example.appmedirconsumorecursos.Core.IDAO;
import com.example.appmedirconsumorecursos.Dominio.EntidadeDominio;
import com.example.appmedirconsumorecursos.Util.ConexaoServidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Galdino on 20/08/2015.
 */
public abstract class AbstractJdbcDAO implements IDAO{
    protected Connection connection;
    protected String table;
    protected String idTable;
    protected String conexao;
    protected String arqPHP;
    private String answer = "";
    protected boolean ctrlTransaction=true;

    public AbstractJdbcDAO( String conexao, String table, String idTable, String dif){
        this.table = table;
        this.idTable = idTable;
        this.conexao = conexao;
    }

    protected AbstractJdbcDAO(String table, String idTable, String arqPHP){
        this.table = table;
        this.idTable = idTable;
        this.arqPHP = arqPHP;
    }

    public void excluir(EntidadeDominio entidade) {
        //openConnection();
        PreparedStatement pst=null;
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(table);
        sb.append(" Set ");
        sb.append("fg_ativo = false");
        sb.append(" WHERE ");
        sb.append(idTable);
        sb.append("=");
        sb.append("?");

        // *********************** excluir servidor, ou no banco?
//        try {
//            connection.setAutoCommit(false);
//            pst = connection.prepareStatement(sb.toString());
//
//            pst.setInt(1, Integer.parseInt(entidade.getId()));
//
//            pst.executeUpdate();
//            connection.commit();
//
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        }finally{
//
//            try {
//                pst.close();
//                if(ctrlTransaction)
//                    connection.close();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    protected String openConnection(final String method, final String data)
    {
        try
        {
            if(conexao == null ||conexao == "") {
                conexao = ConexaoServidor.getConnection();
                Thread thread =  new Thread( new Runnable(){
                    public void run(){
                        answer = ConexaoServidor.getSetDataWeb(conexao + arqPHP, method, data);

                    }
                });
                thread.start();

                thread.join();
            }

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
