package br.com.CRUDMVC.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.CRUDMVC.dao.UsuarioDao;
import br.com.CRUDMVC.domain.TipoSexo;
import br.com.CRUDMVC.domain.Usuario;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDao dao;

    @GetMapping("/todos")
    public ModelAndView home(ModelMap model) {
        model.addAttribute("usuarios", dao.getTodos());
        model.addAttribute("conteudo", "/user/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/nome")
    public ModelAndView listarPorNome(@RequestParam(value = "nome") String nome, ModelMap model) {

        if (nome == null) {
            return new ModelAndView("redirect:/usuario/todos");
        }

        model.addAttribute("usuarios", dao.getByNome(nome));
        model.addAttribute("conteudo", "/user/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/sexo")
    public ModelAndView listarPorSexo(@RequestParam(value = "tipoSexo") TipoSexo sexo, ModelMap model) {

        if (sexo == null) {
            return new ModelAndView("redirect:/usuario/todos");
        }

        model.addAttribute("usuarios", dao.getBySexo(sexo));
        model.addAttribute("conteudo", "/user/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Usuario usuario) {

        return new ModelAndView("layout", "conteudo", "/user/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Usuario usuario, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/user/add");
        }

        dao.salvar(usuario);
        attrib.addFlashAttribute("message", "Registro inserido com sucesso.");
        return new ModelAndView("redirect:/usuario/todos");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("usuario", dao.getId(id));
        model.addAttribute("conteudo", "/user/add");
        return new ModelAndView("layout", model);
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Usuario usuario, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/user/add");
        }

        dao.editar(usuario);
        attrib.addFlashAttribute("message", "Registro editado com sucesso.");
        return new ModelAndView("redirect:/usuario/todos");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attrib) {

        dao.excluir(id);
        attrib.addFlashAttribute("message", "Usuário removido com sucesso.");
        return "redirect:/usuario/todos";
    }

    @ModelAttribute("sexos")
    public TipoSexo[] tipoSexo() {
        return TipoSexo.values();
    }

}
