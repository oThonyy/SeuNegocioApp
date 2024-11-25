package br.thony.fateczl.seunegocioapp.model;

public class Produto {

    private int codProd;
    private String nomeProd;
    private float valorProd;
    private String descProd;
    private String fornProd;

    public int getCodProd() {
        return codProd;
    }

    public void setCodProd(int codProd) {
        this.codProd = codProd;
    }

    public String getNomeProd() {
        return nomeProd;
    }

    public void setNomeProd(String nomeProd) {
        this.nomeProd = nomeProd;
    }

    public float getValorProd() {
        return valorProd;
    }

    public void setValorProd(float valorProd) {
        this.valorProd = valorProd;
    }

    public String getDescProd() {
        return descProd;
    }

    public void setDescProd(String descProd) {
        this.descProd = descProd;
    }

    public String getFornProd() {
        return fornProd;
    }

    public void setFornProd(String fornProd) {
        this.fornProd = fornProd;
    }

    @Override
    public String toString() {
        return codProd + " - " + nomeProd + " - " + valorProd + " - " + descProd + " - " + fornProd;
    }
}
