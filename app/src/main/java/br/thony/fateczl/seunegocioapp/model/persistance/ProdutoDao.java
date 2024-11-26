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

public class ProdutoDao implements IProdutoDao, ICRUDDao<Produto> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public ProdutoDao(Context context) {
        this.context = context;
    }

    @Override
    public ProdutoDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Produto produto) throws SQLException {
        ContentValues contentValues = getContentValues(produto);
        database.insert("produto", null, contentValues);
    }

    @Override
    public int update(Produto produto) throws SQLException {
        ContentValues contentValues = getContentValues(produto);
        int ret = database.update("produto", contentValues, "codProd = " +
                produto.getCodProd(), null);
        return ret;
    }

    @Override
    public void delete(Produto produto) throws SQLException {
        database.delete("produto", "codProd = " +
                produto.getCodProd(), null);
    }

    @SuppressLint("Range")
    @Override
    public Produto findOne(Produto produto) throws SQLException {
        String sql = "SELECT * FROM produto WHERE codProd = " + produto.getCodProd();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            produto = new Produto();
            produto.setCodProd(cursor.getInt(cursor.getColumnIndex("codProd")));
            produto.setNomeProd(cursor.getString(cursor.getColumnIndex("nomeProd")));
            produto.setValorProd(cursor.getFloat(cursor.getColumnIndex("valorProd")));
            produto.setDescProd(cursor.getString(cursor.getColumnIndex("descProd")));
            produto.setFornProd(cursor.getString(cursor.getColumnIndex("fornProd")));
        }

        if (cursor != null) {
            cursor.close();
        }

        return produto;
    }

    //    @SuppressLint("Range")
//    @Override
//    public Produto findOne(Produto produto) throws SQLException {
//        String sql = "SELECT * FROM produto WHERE codProd = " + produto.getCodProd();
//        Cursor cursor = database.rawQuery(sql, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        if (!cursor.isAfterLast()) {
//            produto.setCodProd(cursor.getInt(cursor.getColumnIndex("codProd")));
//            produto.setNomeProd(cursor.getString(cursor.getColumnIndex("nomeProd")));
//            produto.setValorProd((float) cursor.getDouble(cursor.getColumnIndex("valorProd")));
//            produto.setDescProd(cursor.getString(cursor.getColumnIndex("descProd")));
//            produto.setFornProd(cursor.getString(cursor.getColumnIndex("fornProd")));
//        }
//        cursor.close();
//        return produto;
//    }

    @SuppressLint("Range")
    @Override
    public List<Produto> findAll() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Produto produto = new Produto();
            produto.setCodProd(cursor.getInt(cursor.getColumnIndex("codProd")));
            produto.setNomeProd(cursor.getString(cursor.getColumnIndex("nomeProd")));
            produto.setValorProd((float) cursor.getDouble(cursor.getColumnIndex("valorProd")));
            produto.setDescProd(cursor.getString(cursor.getColumnIndex("descProd")));
            produto.setFornProd(cursor.getString(cursor.getColumnIndex("fornProd")));

            produtos.add(produto);
            cursor.moveToNext();
        }
        cursor.close();
        return produtos;
    }

    private static ContentValues getContentValues(Produto produto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codProd", produto.getCodProd());
        contentValues.put("nomeProd", produto.getNomeProd());
        contentValues.put("valorProd", produto.getValorProd());
        contentValues.put("descProd", produto.getDescProd());
        contentValues.put("fornProd", produto.getFornProd());

        return contentValues;
    }
}
