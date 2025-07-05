package med.voll.api.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public Endereco(DadosEndereco dadosEndereco) {
        this.logradouro = dadosEndereco.logradouro();
        this.numero = dadosEndereco.numero();
        this.complemento = dadosEndereco.complemento();
        this.bairro = dadosEndereco.bairro();
        this.cidade = dadosEndereco.cidade();
        this.uf = dadosEndereco.uf();
        this.cep = dadosEndereco.cep();
    }

    public void atualizarEndereco(Endereco dados) {
        Optional.ofNullable(dados.logradouro).ifPresent(l -> this.logradouro = l);
        Optional.ofNullable(dados.numero).ifPresent(n -> this.numero = n);
        Optional.ofNullable(dados.complemento).ifPresent(c -> this.complemento = c);
        Optional.ofNullable(dados.bairro).ifPresent(b -> this.bairro = b);
        Optional.ofNullable(dados.cidade).ifPresent(c -> this.cidade = c);
        Optional.ofNullable(dados.uf).ifPresent(u -> this.uf = u);
        Optional.ofNullable(dados.cep).ifPresent(c -> this.cep = c);
    }
}
