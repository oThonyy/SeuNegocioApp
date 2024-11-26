package br.thony.fateczl.seunegocioapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.thony.fateczl.seunegocioapp.controller.ProdutoController;
import br.thony.fateczl.seunegocioapp.model.Produto;
import br.thony.fateczl.seunegocioapp.model.persistance.ProdutoDao;

public class ProdutoFragment extends Fragment {

    private View view;

    private EditText etCodProduto, etNomeProduto, etValProd, etDescProd, etFornProd;

    private Button btnSalvarProd, btnEditarProd, btnApagarProd, btnListarProd, btnBuscarProd;

    private TextView tvListarProd;

    private ProdutoController pCont;

    public ProdutoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_produto, container, false);

        etCodProduto = view.findViewById(R.id.etCodProduto);
        etNomeProduto = view.findViewById(R.id.etNomeProd);
        etValProd = view.findViewById(R.id.etValProd);
        etDescProd = view.findViewById(R.id.etDescProd);
        etFornProd = view.findViewById(R.id.etFornProd);

        btnSalvarProd = view.findViewById(R.id.btnSalvaProd);
        btnEditarProd = view.findViewById(R.id.btnEditarProd);
        btnApagarProd = view.findViewById(R.id.btnApagarProd);
        btnListarProd = view.findViewById(R.id.btnListarProd);
        btnBuscarProd = view.findViewById(R.id.btnBuscarProd);

        tvListarProd = view.findViewById(R.id.tvListarProd);
        tvListarProd.setMovementMethod(new ScrollingMovementMethod());

        pCont = new ProdutoController(new ProdutoDao(view.getContext()));

        btnSalvarProd.setOnClickListener(op -> acaoInserir());
        btnEditarProd.setOnClickListener(op -> acaoEditar());
        btnApagarProd.setOnClickListener(op -> acaoApagar());
        btnListarProd.setOnClickListener(op -> acaoListar());
        btnBuscarProd.setOnClickListener(op -> acaoBuscar());

        return view;
    }

    private void acaoInserir() {
        Produto produto = montaProduto();
        try {
            pCont.inserir(produto);
            Toast.makeText(view.getContext(), "Produto inserido com sucesso", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        limpaCampos();
    }

    private void acaoEditar() {
        Produto produto = montaProduto();
        try {
            pCont.modificar(produto);
            Toast.makeText(view.getContext(), "Produto atualizado com sucesso", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        limpaCampos();
    }

    private void acaoApagar() {
        Produto produto = montaProduto();
        try {
            pCont.deletar(produto);
            Toast.makeText(view.getContext(), "Produto excluído com sucesso", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        limpaCampos();
    }

    private void acaoListar() {
        try {
            List<Produto> produtos = pCont.listar();
            StringBuffer buffer = new StringBuffer();
            for (Produto p : produtos) {
                buffer.append(p.toString() + "\n");
            }
            tvListarProd.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void acaoBuscar() {
        Produto produto = montaProduto();
        try {
            produto = pCont.buscar(produto);
            if (produto.getNomeProd() != null) {
                preencheCampos(produto);
            } else {
                Toast.makeText(view.getContext(), "Produto não encontrado",
                        Toast.LENGTH_SHORT).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Produto montaProduto() {
        Produto p = new Produto();

        try {
            // Validate codProd (integer field)
            String codProdStr = etCodProduto.getText().toString().trim();
            if (codProdStr.isEmpty()) {
                etCodProduto.setError("Código do produto não pode estar vazio");
                return null;
            }
            p.setCodProd(Integer.parseInt(codProdStr));
        } catch (NumberFormatException e) {
            etCodProduto.setError("Código do produto inválido");
            return null; // Abort creation if the value is invalid
        }

        // Validate nomeProd (string field)
        String nomeProd = etNomeProduto.getText().toString().trim();
        if (nomeProd.isEmpty()) {
            etNomeProduto.setError("Nome do produto não pode estar vazio");
            return null;
        }
        p.setNomeProd(nomeProd);

        try {
            // Validate valorProd (float field)
            String valorProdStr = etValProd.getText().toString().trim();
            if (valorProdStr.isEmpty()) {
                etValProd.setError("Valor do produto não pode estar vazio");
                return null;
            }
            p.setValorProd(Float.parseFloat(valorProdStr));
        } catch (NumberFormatException e) {
            etValProd.setError("Valor do produto inválido");
            return null;
        }

        // Validate descProd (optional string field)
        p.setDescProd(etDescProd.getText().toString().trim());

        // Validate fornProd (string field)
        String fornProd = etFornProd.getText().toString().trim();
        if (fornProd.isEmpty()) {
            etFornProd.setError("Fornecedor não pode estar vazio");
            return null;
        }
        p.setFornProd(fornProd);

        return p;
    }

    private void preencheCampos(Produto p) {
        etCodProduto.setText(String.valueOf(p.getCodProd()));
        etNomeProduto.setText(p.getNomeProd());
        etValProd.setText(String.valueOf(p.getValorProd()));
        etDescProd.setText(p.getDescProd());
        etFornProd.setText(p.getFornProd());
    }

    private void limpaCampos() {
        etCodProduto.setText("");
        etNomeProduto.setText("");
        etValProd.setText("");
        etDescProd.setText("");
        etFornProd.setText("");
    }

}