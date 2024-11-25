package br.thony.fateczl.seunegocioapp.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.thony.fateczl.seunegocioapp.model.Venda;
import br.thony.fateczl.seunegocioapp.model.persistance.VendaDao;

public class VendaController implements IController<Venda>{

    private final VendaDao vDao;

    public VendaController(VendaDao vDao){
        this.vDao = vDao;
    }

    @Override
    public void inserir(Venda venda) throws SQLException {
        if (vDao.open() == null){
            vDao.open();
        }
        vDao.insert(venda);
        vDao.close();
    }

    @Override
    public void modificar(Venda venda) throws SQLException {
        if (vDao.open() == null){
            vDao.open();
        }
        vDao.update(venda);
        vDao.close();
    }

    @Override
    public void deletar(Venda venda) throws SQLException {
        if (vDao.open() == null){
            vDao.open();
        }
        vDao.delete(venda);
        vDao.close();
    }

    @Override
    public Venda buscar(Venda venda) throws SQLException {
        if (vDao.open() == null){
            vDao.open();
        }
        return vDao.findOne(venda);
    }

    @Override
    public List<Venda> listar() throws SQLException {
        if (vDao.open() == null){
            vDao.open();
        }
        return vDao.findAll();
    }
}
