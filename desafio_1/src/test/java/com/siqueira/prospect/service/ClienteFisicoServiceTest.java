package com.siqueira.prospect.service;

import com.siqueira.prospect.dto.input.ClienteFisicoDTO;
import com.siqueira.prospect.model.ClienteFisico;
import com.siqueira.prospect.repository.ClienteFisicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteFisicoServiceTest {

    @Mock
    private ClienteFisicoRepository repository;

    @InjectMocks
    private ClienteFisicoService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSalvar() {
        ClienteFisico cliente = new ClienteFisico();
        when(repository.save(cliente)).thenReturn(cliente);
        ClienteFisico clienteSalvo = service.save(cliente);
        assertNotNull(clienteSalvo);
        assertEquals(cliente, clienteSalvo);
    }

    @Test
    public void testBuscarPorId() {
        UUID uuid = UUID.randomUUID();
        ClienteFisico cliente = new ClienteFisico();
        cliente.setUuid(uuid);
        when(repository.findById(uuid)).thenReturn(Optional.of(cliente));
        Optional<ClienteFisico> clienteEncontrado = service.findById(uuid);
        assertTrue(clienteEncontrado.isPresent());
        assertEquals(cliente, clienteEncontrado.get());
    }

    @Test
    public void testListarTodos() {
        int pagina = 0;
        int tamanho = 10;
        List<ClienteFisico> clientes = new ArrayList<>();
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<ClienteFisico> page = new PageImpl<>(clientes);
        when(repository.findAll(pageable)).thenReturn(page);
        Page<ClienteFisico> resultado = service.findAll(pagina, tamanho);
        assertNotNull(resultado);
        assertEquals(clientes, resultado.getContent());
    }

    @Test
    public void testExistePorId() {
        UUID uuid = UUID.randomUUID();
        when(repository.existsById(uuid)).thenReturn(true);
        assertTrue(service.existsById(uuid));
    }

    @Test
    public void testExistePorCpf() {
        String cpf = "123.456.789-09";
        when(repository.existsByCpf(cpf)).thenReturn(true);
        assertTrue(service.existsByCpf(cpf));
    }

    @Test
    public void testDeleteSucesso() {
        UUID uuid = UUID.randomUUID();
        when(repository.existsById(uuid)).thenReturn(true);
        assertTrue(service.delete(uuid));
    }
    @Test
    public void testDeleteNaoExiste() {
        UUID uuid = UUID.randomUUID();
        when(repository.existsById(uuid)).thenReturn(false);
        assertFalse(service.delete(uuid));
    }

    @Test
    public void testConverterDTO() {
        ClienteFisicoDTO dto = new ClienteFisicoDTO();
        dto.setNome("Nome do cliente");
        dto.setCpf("707.897.940-04");
        dto.setEmail("seu@gmail.com.br");
        dto.setMcc("1234");
        ClienteFisico cliente = service.fromDTO(dto);
        assertNotNull(cliente);
    }

    @Test
    public void testAtualizar() {
        UUID uuid = UUID.randomUUID();
        ClienteFisico clienteExistente = new ClienteFisico();
        clienteExistente.setUuid(uuid);
        clienteExistente.setNome("Nome do cliente");
        clienteExistente.setCpf("707.897.940-04");
        clienteExistente.setEmail("seu@gmail.com.br");
        clienteExistente.setMcc("1234");
        ClienteFisico clienteAtualizado = new ClienteFisico();
        clienteAtualizado.setUuid(uuid);
        clienteAtualizado.setNome("Novo Nome");
        clienteAtualizado.setNome(clienteExistente.getNome());
        clienteAtualizado.setCpf(clienteExistente.getCpf());
        clienteAtualizado.setEmail(clienteExistente.getEmail());
        clienteAtualizado.setMcc(clienteExistente.getMcc());
        when(repository.findById(uuid)).thenReturn(Optional.of(clienteExistente));
        when(repository.save(clienteExistente)).thenReturn(clienteAtualizado);
        ClienteFisico resultado = service.update(clienteAtualizado);
        assertNotNull(resultado);
        assertEquals(clienteAtualizado, resultado);
    }
}
