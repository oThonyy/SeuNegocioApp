package br.thony.fateczl.seunegocioapp.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.thony.fateczl.seunegocioapp.model.Produto;
import br.thony.fateczl.seunegocioapp.model.persistance.ProdutoDao;

public class ProdutoController implements IController<Produto> {

    private final ProdutoDao pDao;

    public ProdutoController(ProdutoDao pDao) {
        this.pDao = pDao;
    }

    @Override
    public void inserir(Produto produto) throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        pDao.insert(produto);
        pDao.close();
    }

    @Override
    public void modificar(Produto produto) throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        pDao.update(produto);
        pDao.close();
    }

    @Override
    public void deletar(Produto produto) throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        pDao.delete(produto);
        pDao.close();
    }

    @Override
    public Produto buscar(Produto produto) throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        return pDao.findOne(produto);
    }

    @Override
    public List<Produto> listar() throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        return pDao.findAll();
    }
}
