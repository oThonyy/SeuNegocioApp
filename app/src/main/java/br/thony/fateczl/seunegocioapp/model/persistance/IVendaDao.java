package br.thony.fateczl.seunegocioapp.model.persistance;

import java.sql.SQLException;

public interface IVendaDao {

    public VendaDao open() throws SQLException;

    public void close();

}
