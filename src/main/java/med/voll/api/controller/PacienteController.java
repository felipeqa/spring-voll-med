package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.DadosRetornoPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosRetornoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosCadastroPaciente, UriComponentsBuilder uriBulder) {
        var paciente = new Paciente(dadosCadastroPaciente);
        repository.save(paciente);

        var uri = uriBulder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosRetornoPaciente(paciente));
    }
}
