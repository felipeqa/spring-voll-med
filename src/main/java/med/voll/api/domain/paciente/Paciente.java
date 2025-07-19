package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

import java.util.Optional;

@Table(name= "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Embedded
    private Endereco endereco;

    public Paciente(DadosCadastroPaciente dadosCadastroPaciente) {
        this.nome = dadosCadastroPaciente.nome();
        this.email = dadosCadastroPaciente.email();
        this.cpf = dadosCadastroPaciente.cpf();
        this.telefone = dadosCadastroPaciente.telefone();
        this.endereco = new Endereco(dadosCadastroPaciente.endereco());
    }

    public void atualizarPaciente(RequestAtualizarPaciente dadosPaciente) {
        Optional.ofNullable(dadosPaciente.nome())
                .ifPresent(n -> this.nome = n);
        Optional.ofNullable(dadosPaciente.telefone())
                .ifPresent(t -> this.telefone = t);
        Optional.ofNullable(dadosPaciente.endereco())
                .ifPresent(e -> this.endereco.atualizarEndereco(e));
    }
}
