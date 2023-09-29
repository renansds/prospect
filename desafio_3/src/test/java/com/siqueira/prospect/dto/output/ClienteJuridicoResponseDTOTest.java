package com.siqueira.prospect.dto.output;

import com.siqueira.prospect.model.ClienteJuridico;
import com.siqueira.prospect.model.Contato;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClienteJuridicoResponseDTOTest {

    @Test
    public void testDeveCriarInstanciaComLocalizacao() {
        Contato contato = new Contato("Joao", "026.671.269-04", "anabarbaracarvalho@leonardocordeiro.com");
        ClienteJuridico clienteJuridico = new ClienteJuridico("00865547000150", "EMPRESA TEST1", "5912", contato);
        URI location = URI.create("http://example.com/cliente/juridico/123");
        ClienteJuridicoResponseDTO responseDTO = new ClienteJuridicoResponseDTO(clienteJuridico, location);
        assertEquals(clienteJuridico.getRazaoSocial(), responseDTO.getRazaoSocial());
        assertEquals(clienteJuridico.getCnpj(), responseDTO.getCnpj());
        assertEquals(clienteJuridico.getMcc(), responseDTO.getMcc());
        assertEquals(clienteJuridico.getContato().getNome(), responseDTO.getContato().getNome());
        assertEquals(clienteJuridico.getContato().getEmail(), responseDTO.getContato().getEmail());
        assertEquals(clienteJuridico.getUuid(), responseDTO.getUuid());
        assertTrue(responseDTO.getLinks().containsKey("self"));
        assertEquals(location.toString(), responseDTO.getLinks().get("self"));
    }
}
