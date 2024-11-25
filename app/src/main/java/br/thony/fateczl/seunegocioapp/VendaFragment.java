package br.thony.fateczl.seunegocioapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.thony.fateczl.seunegocioapp.controller.ProdutoController;
import br.thony.fateczl.seunegocioapp.controller.VendaController;
import br.thony.fateczl.seunegocioapp.model.Produto;
import br.thony.fateczl.seunegocioapp.model.Venda;
import br.thony.fateczl.seunegocioapp.model.persistance.ProdutoDao;
import br.thony.fateczl.seunegocioapp.model.persistance.VendaDao;

public class VendaFragment extends Fragment {

    private View view;

    private EditText etCodVend, etDataVend, etQntVend, etTotalVend;

    private Button btnSalvarVend, btnEditarVend, btnApagarVend, btnListarVend, btnBuscarVend, btnCalcTotal;

    private Spinner spProdVenda;

    private TextView tvListarVend;

    private VendaController vCont;

    private ProdutoController pCont;

    private List<Produto> produtos;

    public VendaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_venda, container, false);

        etCodVend = view.findViewById(R.id.etCodVend);
        etDataVend = view.findViewById(R.id.etDataVend);
        etQntVend = view.findViewById(R.id.etQntVend);
        etTotalVend = view.findViewById(R.id.etTotalVend);

        btnSalvarVend = view.findViewById(R.id.btnSalvarVend);
        btnEditarVend = view.findViewById(R.id.btnEditarVend);
        btnApagarVend = view.findViewById(R.id.btnApagarVend);
        btnListarVend = view.findViewById(R.id.btnListarVend);
        btnBuscarVend = view.findViewById(R.id.btnBuscarVend);
        btnCalcTotal = view.findViewById(R.id.btnCalcTotal);

        spProdVenda = view.findViewById(R.id.spProdVenda);

        tvListarVend = view.findViewById(R.id.tvListarVend);
        tvListarVend.setMovementMethod(new ScrollingMovementMethod());

        vCont = new VendaController(new VendaDao(view.getContext()));
        pCont = new ProdutoController(new ProdutoDao(view.getContext()));
        preencheSpinner();
        
        btnSalvarVend.setOnClickListener(op -> acaoInserir());
        btnEditarVend.setOnClickListener(op -> acaoEditar());
        btnApagarVend.setOnClickListener(op -> acaoApagar());
        btnBuscarVend.setOnClickListener(op -> acaoBuscar());
        btnListarVend.setOnClickListener(op -> acaoListar());
        btnCalcTotal.setOnClickListener(op -> acaoCalcTotal());

        return view;
    }

    private void acaoCalcTotal() {
    }

    private void acaoInserir() {
        int spPos = spProdVenda.getSelectedItemPosition();
        if (spPos > 0) {
            Venda venda = montaVenda();
            try {
                vCont.inserir(venda);
                Toast.makeText(view.getContext(), "Venda inserida com sucesso",
                        Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            limpaCampos();
        } else {
            Toast.makeText(view.getContext(), "Selecione um produto", Toast.LENGTH_SHORT).show();
        }
    }

    private void acaoEditar() {
        int spPos = spProdVenda.getSelectedItemPosition();
        if (spPos > 0) {
            Venda venda = montaVenda();
            try {
                vCont.modificar(venda);
                Toast.makeText(view.getContext(), "Venda atualizada com sucesso",
                        Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            limpaCampos();
        } else {
            Toast.makeText(view.getContext(), "Selecione um produto", Toast.LENGTH_SHORT).show();
        }
    }

    private void acaoApagar() {
        Venda venda = montaVenda();
        try {
            vCont.modificar(venda);
            Toast.makeText(view.getContext(), "Venda excluída com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        Venda venda = montaVenda();
        try {
            produtos = pCont.listar();
            venda = vCont.buscar(venda);
            if (venda.getProduto() != null) {
                preencheCampos(venda);
            } else {
                Toast.makeText(view.getContext(), "Venda não encontrada",
                        Toast.LENGTH_SHORT).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void acaoListar() {
        try {
            List<Venda> venda = vCont.listar();
            StringBuffer buffer = new StringBuffer();
            for (Venda v : venda) {
                buffer.append(v.toString() + "\n");
            }
            tvListarVend.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void preencheSpinner() {
        Produto p0 = new Produto();
        p0.setCodProd(0);
        p0.setNomeProd("Selecione um produto");
        p0.setValorProd(0);
        p0.setDescProd("");
        p0.setFornProd("");

        try {
            produtos = pCont.listar();
            produtos.add(0, p0);

            ArrayAdapter ad = new ArrayAdapter(view.getContext(),
                    android.R.layout.simple_spinner_item,
                    produtos);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spProdVenda.setAdapter(ad);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private Venda montaVenda() {
        Venda v = new Venda();
        v.setCodVend(Integer.parseInt(etCodVend.getText().toString()));
        v.setDataVend(etDataVend.getText().toString());
        v.setQntVend(Integer.parseInt(etQntVend.getText().toString()));
        v.setTotalVend(Float.parseFloat(etTotalVend.getText().toString()));
        v.setProduto((Produto) spProdVenda.getSelectedItem());

        return v;
    }

    private void limpaCampos() {
        etCodVend.setText("");
        etDataVend.setText("");
        etQntVend.setText("");
        etTotalVend.setText("");
        spProdVenda.setSelection(0);
    }

    public void preencheCampos(Venda v) {
        etCodVend.setText(String.valueOf(v.getCodVend()));
        etDataVend.setText(v.getDataVend());
        etQntVend.setText(String.valueOf(v.getQntVend()));
        etTotalVend.setText(String.valueOf(v.getTotalVend()));

        int cont = 1;
        for (Produto p : produtos) {
            if (p.getCodProd() == v.getProduto().getCodProd()) {
                spProdVenda.setSelection(cont);
            } else {
                cont++;
            }
        }
        if (cont > produtos.size()) {
            spProdVenda.setSelection(0);
        }
    }
}