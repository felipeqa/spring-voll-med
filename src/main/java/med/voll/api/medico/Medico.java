package med.voll.api.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;

import java.util.Optional;

@Table(name= "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedico dadosCadastroMedico) {
        this.nome = dadosCadastroMedico.nome();
        this.email = dadosCadastroMedico.email();
        this.crm = dadosCadastroMedico.crm();
        this.especialidade = dadosCadastroMedico.especialidade();
        this.telefone = dadosCadastroMedico.telefone();
        this.endereco = new Endereco(dadosCadastroMedico.endereco());
    }

    public void atualizarDadosMedicos(DadosAtualizacaoMedico dadosAtualizadosMedico){

        Optional.ofNullable(dadosAtualizadosMedico.nome())
                .ifPresent(n -> this.nome = n);
        Optional.ofNullable(dadosAtualizadosMedico.telefone())
                .ifPresent(t -> this.telefone = t);
        Optional.ofNullable(dadosAtualizadosMedico.endereco())
                .ifPresent(e -> this.endereco.atualizarEndereco(e));

    }
}
