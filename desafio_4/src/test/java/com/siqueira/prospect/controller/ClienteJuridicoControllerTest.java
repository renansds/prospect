package com.siqueira.prospect.controller;

import com.siqueira.prospect.dto.input.ClienteJuridicoDTO;
import com.siqueira.prospect.dto.input.ContatoDTO;
import com.siqueira.prospect.dto.output.ClienteJuridicoResponseDTO;
import com.siqueira.prospect.exceptions.CompanyAlreadyExistsException;
import com.siqueira.prospect.exceptions.CompanyNotFoundException;
import com.siqueira.prospect.model.ClienteJuridico;
import com.siqueira.prospect.service.ClienteJuridicoService;
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
public class ClienteJuridicoControllerTest {

    @InjectMocks
    private ClienteJuridicoController clienteController;

    @Mock
    private ClienteJuridicoService clienteJuridicoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuscarClienteExistente() {
        UUID uuid = UUID.randomUUID();
        String cnpj = "03.755.586/0001-92";
        ClienteJuridico cliente = new ClienteJuridico();
        cliente.setUuid(uuid);
        cliente.setCnpj(cnpj);
        when(clienteJuridicoService.findByCnpj(cnpj)).thenReturn(Optional.of(cliente));
        ResponseEntity<ClienteJuridicoResponseDTO> response = clienteController.find(cnpj);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(uuid, response.getBody().getUuid());
        verify(clienteJuridicoService, times(1)).findByCnpj(cnpj);
    }

    @Test
    public void testBuscarClienteNaoExistente() {
        UUID uuid = UUID.randomUUID();
        String cnpj = "68.276.968/0001-89";
        when(clienteJuridicoService.findByCnpj(cnpj)).thenReturn(Optional.empty());
        CompanyNotFoundException exception = assertThrows(CompanyNotFoundException.class, () -> {
            clienteController.find(cnpj);
        });
        assertEquals("Não foi possível concluir a operação, pois a empresa não está registrado em nosso " +
                "sistema.", exception.getMessage());
        verify(clienteJuridicoService, times(1)).findByCnpj(cnpj);
    }

    @Test
    public void testSalvarClienteNaoExistente() {
        ClienteJuridicoDTO dto = new ClienteJuridicoDTO();
        dto.setCnpj("77.708.453/0001-00");
        dto.setRazaoSocial("Nome da minha empresa");
        dto.setMcc("1234");
        dto.setContato(new ContatoDTO("João teste api", "213.716.630-50", "seumail@gmail.com.br"));
        ClienteJuridico clienteJuridico = new ClienteJuridico();
        when(clienteJuridicoService.existsByCnpj(dto.getCnpj())).thenReturn(false);
        when(clienteJuridicoService.save(any(ClienteJuridico.class))).thenReturn(clienteJuridico);
        ResponseEntity<ClienteJuridicoResponseDTO> response = clienteController.save(dto,
                mock(HttpServletResponse.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(clienteJuridicoService, times(1)).existsByCnpj(dto.getCnpj());
        verify(clienteJuridicoService, times(1)).save(any(ClienteJuridico.class));
    }

    @Test
    public void testSalvarClienteJaExistente() {
        ClienteJuridicoDTO ClienteJuridicoDTO = new ClienteJuridicoDTO();
        ClienteJuridicoDTO.setCnpj("77.708.453/0001-00");
        when(clienteJuridicoService.existsByCnpj(ClienteJuridicoDTO.getCnpj())).thenReturn(true);
        CompanyAlreadyExistsException exception = assertThrows(CompanyAlreadyExistsException.class, () -> {
            clienteController.save(ClienteJuridicoDTO, mock(HttpServletResponse.class));
        });
        assertEquals("Empresa já cadastrada.", exception.getMessage());
        verify(clienteJuridicoService, times(1)).existsByCnpj(ClienteJuridicoDTO.getCnpj());
        verify(clienteJuridicoService, never()).save(any(ClienteJuridico.class));
    }
    @Test
    public void testAtualizarClienteExistente() {
        UUID uuid = UUID.randomUUID();
        ClienteJuridicoDTO dto = new ClienteJuridicoDTO();
        ClienteJuridico clienteJuridico = new ClienteJuridico();
        clienteJuridico.setUuid(uuid);
        when(clienteJuridicoService.existsById(uuid)).thenReturn(true);
        when(clienteJuridicoService.fromDTO(dto)).thenReturn(clienteJuridico);
        ResponseEntity<ClienteJuridicoResponseDTO> response = clienteController.update(dto, uuid);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteJuridicoService, times(1)).existsById(uuid);
        verify(clienteJuridicoService, times(1)).fromDTO(dto);
        verify(clienteJuridicoService, times(1)).update(clienteJuridico);
    }
    @Test
    public void testAtualizarClienteNaoExistente() {
        UUID uuid = UUID.randomUUID();
        ClienteJuridicoDTO dto = new ClienteJuridicoDTO();
        when(clienteJuridicoService.existsById(uuid)).thenReturn(false);
        CompanyNotFoundException exception = assertThrows(CompanyNotFoundException.class, () -> {
            clienteController.update(dto, uuid);
        });
        assertEquals("Não foi possível concluir a operação, pois a empresa não está registrado em nosso sistema.", exception.getMessage());
        verify(clienteJuridicoService, times(1)).existsById(uuid);
        verify(clienteJuridicoService, never()).fromDTO(dto);
        verify(clienteJuridicoService, never()).update(any(ClienteJuridico.class));
    }
    @Test
    public void testDeleteClienteExistente() {
        UUID uuid = UUID.randomUUID();
        when(clienteJuridicoService.delete(uuid)).thenReturn(true);
        ResponseEntity<Void> response = clienteController.delete(uuid);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteClienteNaoEncontrado() {
        UUID uuid = UUID.randomUUID();
        when(clienteJuridicoService.delete(uuid)).thenReturn(false);
        ResponseEntity<Void> response = clienteController.delete(uuid);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}