package med.voll.api.medico;

import med.voll.api.endereco.Endereco;

public record DadosDetalhadoRetornoMedico(String nome, String email, String crm, String telefone, Especialidade especialidade, Endereco endereco) {

    public DadosDetalhadoRetornoMedico(Medico medico) {
        this(medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }
}
