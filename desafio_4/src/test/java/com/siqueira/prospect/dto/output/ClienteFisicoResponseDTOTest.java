package com.siqueira.prospect.dto.output;

import com.siqueira.prospect.model.ClienteFisico;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteFisicoResponseDTOTest {

    @Test
    public void testDeveCriarInstanciaComLocalizacao() {
        ClienteFisico clienteFisico = new ClienteFisico();
        clienteFisico.setCpf("123.456.789-09");
        clienteFisico.setMcc("1234");
        clienteFisico.setNome("João Silva");
        clienteFisico.setEmail("joao@example.com");
        clienteFisico.setUuid(UUID.randomUUID());
        URI location = URI.create("http://example.com/cliente/123");
        ClienteFisicoResponseDTO responseDTO = new ClienteFisicoResponseDTO(clienteFisico, location);
        assertEquals(clienteFisico.getCpf(), responseDTO.getCpf());
        assertEquals(clienteFisico.getMcc(), responseDTO.getMcc());
        assertEquals(clienteFisico.getNome(), responseDTO.getNome());
        assertEquals(clienteFisico.getEmail(), responseDTO.getEmail());
        assertEquals(clienteFisico.getUuid(), responseDTO.getUuid());
        assertTrue(responseDTO.getLinks().containsKey("self"));
        assertEquals(location.toString(), responseDTO.getLinks().get("self"));
    }

    @Test
    public void testDeveCriarInstanciaSemLocalizacao() {
        ClienteFisico clienteFisico = new ClienteFisico();
        clienteFisico.setCpf("123.456.789-09");
        clienteFisico.setMcc("1234");
        clienteFisico.setNome("João Silva");
        clienteFisico.setEmail("joao@example.com");
        clienteFisico.setUuid(UUID.randomUUID());
        ClienteFisicoResponseDTO responseDTO = new ClienteFisicoResponseDTO(clienteFisico);
        assertEquals(clienteFisico.getCpf(), responseDTO.getCpf());
        assertEquals(clienteFisico.getMcc(), responseDTO.getMcc());
        assertEquals(clienteFisico.getNome(), responseDTO.getNome());
        assertEquals(clienteFisico.getEmail(), responseDTO.getEmail());
        assertEquals(clienteFisico.getUuid(), responseDTO.getUuid());
        assertFalse(responseDTO.getLinks().containsKey("self"));
    }
}
