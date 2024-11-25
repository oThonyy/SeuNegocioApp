package br.thony.fateczl.seunegocioapp.model.persistance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.thony.fateczl.seunegocioapp.model.Produto;
import br.thony.fateczl.seunegocioapp.model.Venda;

public class VendaDao implements IVendaDao, ICRUDDao<Venda> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public VendaDao(Context context) {
        this.context = context;
    }

    @Override
    public VendaDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Venda venda) throws SQLException {
        ContentValues contentValues = getContentValues(venda);
        database.insert("venda", null, contentValues);
    }

    @Override
    public int update(Venda venda) throws SQLException {
        ContentValues contentValues = getContentValues(venda);
        int ret = database.update("venda", contentValues,
                "codVend = " + venda.getCodVend(), null);
        return ret;
    }

    @Override
    public void delete(Venda venda) throws SQLException {
        database.delete("venda", "codVend = " + venda.getCodVend(),
                null);
    }

    @SuppressLint("Range")
    @Override
    public Venda findOne(Venda venda) throws SQLException {
        String sql = "SELECT p.nome AS nome_prod, v.*" +
                "FROM produto p, venda v" +
                "WHERE p.codProd = v.codProd_produto" +
                "AND p.codigo = " + venda.getCodVend();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            Produto produto = new Produto();
            produto.setNomeProd(cursor.getString(cursor.getColumnIndex("nome_prod")));

            venda.setCodVend(cursor.getInt(cursor.getColumnIndex("codVend")));
            venda.setDataVend(cursor.getString(cursor.getColumnIndex("dataVend")));
            venda.setQntVend(cursor.getInt(cursor.getColumnIndex("qntProd")));
            venda.setTotalVend(cursor.getFloat(cursor.getColumnIndex("totalProd")));
        }
        cursor.close();
        return venda;
    }

    @SuppressLint("Range")
    @Override
    public List<Venda> findAll() throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT p.nome AS nome_prod, v.* " +
                "FROM produto p, venda v " +
                "WHERE p.codProd = v.codProd_produto";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            Venda venda = new Venda();
            Produto produto = new Produto();
            produto.setNomeProd(cursor.getString(cursor.getColumnIndex("nome_prod")));

            venda.setCodVend(cursor.getInt(cursor.getColumnIndex("codVend")));
            venda.setDataVend(cursor.getString(cursor.getColumnIndex("dataVend")));
            venda.setQntVend(cursor.getInt(cursor.getColumnIndex("qntProd")));
            venda.setTotalVend(cursor.getFloat(cursor.getColumnIndex("totalProd")));

            vendas.add(venda);
            cursor.moveToNext();
        }
        cursor.close();
        return vendas;
    }

    private static ContentValues getContentValues(Venda venda) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codVend", venda.getCodVend());
        contentValues.put("dataVend", venda.getDataVend());
        contentValues.put("qntProd", venda.getQntVend());
        contentValues.put("totalProd", venda.getTotalVend());
        contentValues.put("codProd_produto", venda.getProduto().getCodProd());

        return contentValues;
    }

}
