package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listarPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
//        return ResponseEntity.ok(repository.findAll(paginacao).map(DadosListagemPaciente::new));
        return ResponseEntity.ok(repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhadoPaciente> buscarPaciente(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhadoPaciente(paciente));

    }

    @PutMapping
    public ResponseEntity<DadosDetalhadoPaciente> editarPaciente(@RequestBody @Valid RequestAtualizarPaciente requestAtualizarPaciente) {
        var paciente = repository.getReferenceById(requestAtualizarPaciente.id());
        paciente.atualizarPaciente(requestAtualizarPaciente);

        return ResponseEntity.ok(new DadosDetalhadoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }
}
