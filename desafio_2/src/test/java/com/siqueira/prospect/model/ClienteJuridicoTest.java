package com.siqueira.prospect.model;

import com.siqueira.prospect.exceptions.CNPJInvalidException;
import com.siqueira.prospect.exceptions.NameValidationException;
import com.siqueira.prospect.exceptions.RazaoSocialValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteJuridicoTest {

    private String cnpj = "00865547000150";
    private String razaoSocial = "EMPRESA - 1";
    private String mcc = "4902";
    private Contato contato = new Contato("Carlos Silva", "987.654.321-00", "contato@gmail.com");
    @Test
    public void testCriacaoClienteJuridicoValido() {
        ClienteJuridico output = new ClienteJuridico(cnpj, razaoSocial,mcc,contato);
        assertNotNull(output);
        assertTrue(output instanceof ClienteJuridico);
        assertEquals(cnpj, output.getCnpj());
        assertEquals(razaoSocial, output.getRazaoSocial());
        assertEquals(mcc, output.getMcc());
        assertEquals(contato.getCpf(), output.getContato().getCpf());
        assertEquals(cnpj, output.getIdentificacao());
        assertEquals(contato.getNome(), output.getContato().getNome());
        assertEquals(contato.getEmail(), output.getContato().getEmail());
        assertEquals(contato.getEmail(), output.getEmail());
    }

    @Test
    public void testNaoDevePermitirNomeDoContatoComMaisDeCinquentaCaracteres() {
        assertThrows(NameValidationException.class, () -> {
            String nome = "José da Silva Santos Oliveira Pereira Rodrigues Souza Lima Almeida Castro Gomes Fernandes " +
                    "Dias Barbosa";
            this.contato.setNome(nome);
        });
    }

    @Test
    public void testDeveLancarExceptionCNPJInvalido(){
        assertCNPJInvalido("");
        assertCNPJInvalido(" ");
        assertCNPJInvalido(null);
        assertCNPJInvalido("00865547000149");
        assertCNPJInvalido("12.345.678/0001-99");
        assertCNPJInvalido("12.345.67/0001-90");
    }
    public void assertCNPJInvalido(String cnpj){
        assertThrows(CNPJInvalidException.class, () -> {
            new ClienteJuridico(cnpj, razaoSocial,mcc,contato);
        });
    }

    @Test
    public void testDeveLancarExceptionRazaoSocialInvalida(){
        assertRazaoSocialInvalid("");
        assertRazaoSocialInvalid(" ");
        assertRazaoSocialInvalid("Empresa de Tecnologia XYZ Ltda. de Desenvolvimento de Software");
    }
    public void assertRazaoSocialInvalid(String razaoSocial){
        assertThrows(RazaoSocialValidationException.class, () -> {
            new ClienteJuridico(cnpj, razaoSocial,mcc,contato);
        });

    }

}
