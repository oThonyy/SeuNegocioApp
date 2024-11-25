package br.thony.fateczl.seunegocioapp.model;

public class Venda {

    private int codVend;
    private String dataVend;
    private int qntVend;
    private float totalVend;
    private Produto produto;

    public int getCodVend() {
        return codVend;
    }

    public void setCodVend(int codVend) {
        this.codVend = codVend;
    }

    public String getDataVend() {
        return dataVend;
    }

    public void setDataVend(String dataVend) {
        this.dataVend = dataVend;
    }

    public int getQntVend() {
        return qntVend;
    }

    public void setQntVend(int qntVend) {
        this.qntVend = qntVend;
    }

    public float getTotalVend() {
        return totalVend;
    }

    public void setTotalVend(float totalVend) {
        this.totalVend = totalVend;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return codVend + " - " + produto.getNomeProd() + " - " + dataVend + " - " + qntVend + " - " + totalVend;
    }
}
