package com.siqueira.prospect.service;

import com.siqueira.prospect.dto.input.ClienteJuridicoDTO;
import com.siqueira.prospect.dto.input.ContatoDTO;
import com.siqueira.prospect.model.ClienteJuridico;
import com.siqueira.prospect.model.Contato;
import com.siqueira.prospect.repository.ClienteJuridicoRepository;
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

public class ClienteJuridicoServiceTest {

    @Mock
    private ClienteJuridicoRepository repository;

    @InjectMocks
    private ClienteJuridicoService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSalvar() {
        ClienteJuridico cliente = new ClienteJuridico();
        when(repository.save(cliente)).thenReturn(cliente);
        ClienteJuridico clienteSalvo = service.save(cliente);
        assertNotNull(clienteSalvo);
        assertEquals(cliente, clienteSalvo);
    }

    @Test
    public void testBuscarPorId() {
        UUID uuid = UUID.randomUUID();
        ClienteJuridico cliente = new ClienteJuridico();
        cliente.setUuid(uuid);
        when(repository.findById(uuid)).thenReturn(Optional.of(cliente));
        Optional<ClienteJuridico> clienteEncontrado = service.findById(uuid);
        assertTrue(clienteEncontrado.isPresent());
        assertEquals(cliente, clienteEncontrado.get());
    }

    @Test
    public void testListarTodos() {
        int pagina = 0;
        int tamanho = 10;
        List<ClienteJuridico> clientes = new ArrayList<>();
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<ClienteJuridico> page = new PageImpl<>(clientes);
        when(repository.findAll(pageable)).thenReturn(page);
        Page<ClienteJuridico> resultado = service.findAll(pagina, tamanho);
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
        String cnpj = "00865547000150";
        when(repository.existsByCnpj(cnpj)).thenReturn(true);
        assertTrue(service.existsByCnpj(cnpj));
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
        ClienteJuridicoDTO dto = new ClienteJuridicoDTO();
        dto.setContato(new ContatoDTO("Nome do cliente","707.897.940-04", "seu@gmail.com.br"));
        dto.setCnpj("00865547000150");
        dto.setRazaoSocial("Empresa 1 teste");
        dto.setMcc("4522");
        dto.setMcc("1234");
        ClienteJuridico cliente = service.fromDTO(dto);
        assertNotNull(cliente);
    }
    @Test
    public void testAtualizar() {
        UUID uuid = UUID.randomUUID();
        ClienteJuridico clienteExistente = new ClienteJuridico();
        clienteExistente.setUuid(uuid);
        clienteExistente.setRazaoSocial("Razão Social Antiga");
        clienteExistente.setCnpj("22250596000110");
        clienteExistente.setMcc("3344");
        Contato contatoAntigo = new Contato("Nome do cliente","707.897.940-04", "seu@gmail.com.br");
        clienteExistente.addContato(contatoAntigo);

        ClienteJuridico clienteAtualizado = new ClienteJuridico();
        clienteAtualizado.setUuid(uuid);
        clienteAtualizado.setRazaoSocial("Nova Razão Social");
        clienteAtualizado.setCnpj("45.032.051/0001-33");
        clienteAtualizado.setMcc("1122");
        Contato novoContato = new Contato("Novo","707.897.940-04", "seu@gmail.com.br");
        clienteAtualizado.addContato(novoContato);



        when(repository.findById(uuid)).thenReturn(Optional.of(clienteExistente));
        when(repository.save(clienteExistente)).thenReturn(clienteAtualizado);
        ClienteJuridico resultado = service.update(clienteAtualizado);

        assertEquals(uuid, clienteAtualizado.getUuid());
        assertEquals("Nova Razão Social", resultado.getRazaoSocial());
        assertEquals("45032051000133", resultado.getCnpj());
        assertEquals("1122", resultado.getMcc());
        assertNotNull(resultado.getContato());
        assertEquals("Novo", resultado.getContato().getNome());
    }
}
