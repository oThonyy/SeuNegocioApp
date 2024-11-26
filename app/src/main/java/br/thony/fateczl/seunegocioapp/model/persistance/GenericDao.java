package br.thony.fateczl.seunegocioapp.model.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {

    private static final String DATABASE = "SeuNegocio";

    private static final int DATABASE_VER = 1;

    private static final String CREATE_TABLE_PROD =
            "CREATE TABLE produto ( " +
                    "codProd INT NOT NULL PRIMARY KEY, " +
                    "nomeProd VARCHAR(25) NOT NULL, " +
                    "valorProd FLOAT NOT NULL, " +
                    "descProd VARCHAR(100), " +
                    "fornProd VARCHAR (25) NOT NULL);";

    private static final String CREATE_TABLE_VEND =
            "CREATE TABLE venda ( " +
                    "codVend INT NOT NULL PRIMARY KEY, " +
                    "dataVend VARCHAR(10) NOT NULL, " +
                    "qntProd INT NOT NULL, " +
                    "totalProd FLOAT NOT NULL, " +
                    "codProd INT NOT NULL," +
                    "FOREIGN KEY (codProd) REFERENCES produto(codProd));";

    public GenericDao(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PROD);
        sqLiteDatabase.execSQL(CREATE_TABLE_VEND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antigaVersao, int novaVersao) {
        if (novaVersao > antigaVersao) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS produto");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS venda");
            onCreate(sqLiteDatabase);
        }
    }
}
