package com.delivery.delivery.controllers;

import com.delivery.delivery.model.Pedido;
import com.delivery.delivery.model.Produto;
import com.delivery.delivery.model.Usuario;
import com.delivery.delivery.repository.PedidoRepository;
import com.delivery.delivery.repository.ProdutoRepository;
import com.delivery.delivery.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
public ResponseEntity<?> criarPedido(@RequestBody Pedido pedido) {
    try {
        if (pedido.getEnderecoEntrega() == null || pedido.getEnderecoEntrega().isEmpty()) {
            return ResponseEntity.badRequest().body("Endereço de entrega é obrigatório.");
        }

        Usuario usuario = usuarioRepository.findById(pedido.getUsuario().getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Produto> produtos = produtoRepository.findAllById(pedido.getProdutos().stream().map(Produto::getId).toList());

        pedido.setUsuario(usuario);
        pedido.setProdutos(produtos);
        Pedido novoPedido = pedidoRepository.save(pedido);

        return ResponseEntity.ok(novoPedido);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao criar o pedido: " + e.getMessage());
    }
}

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obterPedido(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return ResponseEntity.ok(pedido);
    }
}
