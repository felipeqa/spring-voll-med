package med.voll.api.endereco;

public record DadosEndereco(String logradouro,
                            int numero,
                            String complemento,
                            String bairro,
                            String cidade,
                            String uf,
                            String cep) {
}
