package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhadoRetornoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico, UriComponentsBuilder uriBulder) {
        var medico = new Medico(dadosCadastroMedico);
        repository.save(medico);

        var uri = uriBulder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhadoRetornoMedico(medico));

    }

    //sem paginação
//    @GetMapping
//    public List<DadosListagemMedico> listarMedicos(Pageable paginacao) {
//        return repository.findAll(paginacao).stream().map(DadosListagemMedico::new).toList();
//    }

    //versão com pageable gerenciado pelo spring, podendo ser alterado ao passar na url
    //medicos?size=10&sort=nome
//    @GetMapping
//    public Page<DadosListagemMedico> listarMedicos(Pageable paginacao) {
//        return repository.findAll(paginacao).map(DadosListagemMedico::new);
//    }

    //alterando o default para 10 e ordenação por nome
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
//        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {

        // repository.findById(id)
        // repository.getReferenceById(id)

        // return ResponseEntity.ok(repository.findById(id).map(DadosDetalhadoRetornoMedico::new));
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhadoRetornoMedico(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity editarMedico(@RequestBody @Valid DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        //pegar uma referencia do tipo médico
        var medico = repository.getReferenceById(dadosAtualizacaoMedico.id());
        medico.atualizarDadosMedicos(dadosAtualizacaoMedico);
        //criar um DTO para retornar
        return ResponseEntity.ok(new DadosDetalhadoRetornoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }
}
