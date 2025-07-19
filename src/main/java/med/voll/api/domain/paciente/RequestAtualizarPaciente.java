package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.Endereco;

public record RequestAtualizarPaciente(
        Long id,
        String nome,
        String telefone,
        Endereco endereco) {
}
