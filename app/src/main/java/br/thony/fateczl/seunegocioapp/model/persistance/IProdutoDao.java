package br.thony.fateczl.seunegocioapp.model.persistance;

import java.sql.SQLException;

public interface IProdutoDao {

    public ProdutoDao open() throws SQLException;

    public void close();

}
