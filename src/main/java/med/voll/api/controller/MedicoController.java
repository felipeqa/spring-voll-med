package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico) {
        repository.save(new Medico(dadosCadastroMedico));
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
    public Page<DadosListagemMedico> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void editarMedico(@RequestBody @Valid DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        //pegar uma referencia do tipo médico
        var medico = repository.getReferenceById(dadosAtualizacaoMedico.id());
        medico.atualizarDadosMedicos(dadosAtualizacaoMedico);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
