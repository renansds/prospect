package com.siqueira.prospect.controller;

import com.siqueira.prospect.dto.input.ClienteFisicoDTO;
import com.siqueira.prospect.dto.output.ClienteFisicoResponseDTO;
import com.siqueira.prospect.exceptions.CustomerAlreadyExistsException;
import com.siqueira.prospect.exceptions.CustomerNotFoundException;
import com.siqueira.prospect.model.ClienteFisico;
import com.siqueira.prospect.service.ClienteFisicoService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableWebMvc
@ExtendWith(SpringExtension.class)
public class ClienteFisicoControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteFisicoService clienteFisicoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuscarClienteExistente() {
        UUID uuid = UUID.randomUUID();
        ClienteFisico clienteFisico = new ClienteFisico();
        clienteFisico.setUuid(uuid);
        clienteFisico.setCpf("014.212.450-86");
        when(clienteFisicoService.findByCpf(clienteFisico.getCpf())).thenReturn(Optional.of(clienteFisico));
        ResponseEntity<ClienteFisicoResponseDTO> response = clienteController.find(clienteFisico.getCpf());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(uuid, response.getBody().getUuid());
        verify(clienteFisicoService, times(1)).findByCpf(clienteFisico.getCpf());
    }
    @Test
    public void testBuscarClienteNaoExistente() {
        String cpf = "617.420.960-18";
        when(clienteFisicoService.findByCpf(cpf)).thenReturn(Optional.empty());
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            clienteController.find(cpf);
        });
        assertEquals("Não foi possível concluir a operação, pois o cliente não está registrado em nosso sistema.", exception.getMessage());
        verify(clienteFisicoService, times(1)).findByCpf(cpf);
    }
    @Test
    public void testSalvarClienteNaoExistente() {
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO();
        clienteFisicoDTO.setCpf("705.664.790-10");
        clienteFisicoDTO.setNome("João teste api");
        clienteFisicoDTO.setEmail("seumail@gmail.com.br");
        clienteFisicoDTO.setMcc("5912");
        ClienteFisico clienteFisico = new ClienteFisico();
        when(clienteFisicoService.existsByCpf(clienteFisicoDTO.getCpf())).thenReturn(false);
        when(clienteFisicoService.save(any(ClienteFisico.class))).thenReturn(clienteFisico);
        ResponseEntity<ClienteFisicoResponseDTO> response = clienteController.save(clienteFisicoDTO, mock(HttpServletResponse.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(clienteFisicoService, times(1)).existsByCpf(clienteFisicoDTO.getCpf());
        verify(clienteFisicoService, times(1)).save(any(ClienteFisico.class));
    }
    @Test
    public void testSalvarClienteJaExistente() {
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO();
        clienteFisicoDTO.setCpf("705.664.790-10");
        when(clienteFisicoService.existsByCpf(clienteFisicoDTO.getCpf())).thenReturn(true);
        CustomerAlreadyExistsException exception = assertThrows(CustomerAlreadyExistsException.class, () -> {
            clienteController.save(clienteFisicoDTO, mock(HttpServletResponse.class));
        });
        assertEquals("Cliente já cadastrado", exception.getMessage());
        verify(clienteFisicoService, times(1)).existsByCpf(clienteFisicoDTO.getCpf());
        verify(clienteFisicoService, never()).save(any(ClienteFisico.class));
    }
    @Test
    public void testAtualizarClienteExistente() {
        UUID uuid = UUID.randomUUID();
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO();
        ClienteFisico clienteFisico = new ClienteFisico();
        clienteFisico.setUuid(uuid);
        when(clienteFisicoService.existsById(uuid)).thenReturn(true);
        when(clienteFisicoService.fromDTO(clienteFisicoDTO)).thenReturn(clienteFisico);
        ResponseEntity<ClienteFisicoResponseDTO> response = clienteController.update(clienteFisicoDTO, uuid);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteFisicoService, times(1)).existsById(uuid);
        verify(clienteFisicoService, times(1)).fromDTO(clienteFisicoDTO);
        verify(clienteFisicoService, times(1)).update(clienteFisico);
    }

    @Test
    public void testAtualizarClienteNaoExistente() {
        UUID uuid = UUID.randomUUID();
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO();
        when(clienteFisicoService.existsById(uuid)).thenReturn(false);
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            clienteController.update(clienteFisicoDTO, uuid);
        });
        assertEquals("Não foi possível concluir a operação, pois o cliente não está registrado em nosso sistema.", exception.getMessage());
        verify(clienteFisicoService, times(1)).existsById(uuid);
        verify(clienteFisicoService, never()).fromDTO(clienteFisicoDTO);
        verify(clienteFisicoService, never()).update(any(ClienteFisico.class));
    }
    @Test
    public void testDeleteClienteExistente() {
        UUID uuid = UUID.randomUUID();
        when(clienteFisicoService.delete(uuid)).thenReturn(true);
        ResponseEntity<Void> response = clienteController.delete(uuid);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteClienteNaoEncontrado() {
        UUID uuid = UUID.randomUUID();
        when(clienteFisicoService.delete(uuid)).thenReturn(false);
        ResponseEntity<Void> response = clienteController.delete(uuid);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}