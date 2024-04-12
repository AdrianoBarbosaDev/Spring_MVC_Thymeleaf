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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.CRUDMVC.dao.UsuarioDao;
import br.com.CRUDMVC.domain.TipoSexo;
import br.com.CRUDMVC.domain.Usuario;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private UsuarioDao usuarioDao;

	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public ModelAndView listarTodos(ModelMap model) {
		model.addAttribute("usuarios", usuarioDao.getTodos());
		return new ModelAndView("/user/list", model);

	}

	@GetMapping("/cadastro")
	public String cadastro(@ModelAttribute("usuario") Usuario usuario, ModelMap model) {
		model.addAttribute("sexos", TipoSexo.values());
		return "/user/add";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result,
			RedirectAttributes rtt) {
		if (result.hasErrors()) {
			return "redirect:/user/add";
		}
		usuarioDao.salvar(usuario);
		rtt.addFlashAttribute("message", "Usuario Salvo com sucesso!");
		return "redirect:/usuario/todos";
	}

	@GetMapping("/update/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Long id, ModelMap model) {
		Usuario usuario = usuarioDao.getId(id);
		model.addAttribute("usuario", usuario);
		return new ModelAndView("/user/add", model);
	}

	@PostMapping("/update")
	public ModelAndView update(@ModelAttribute("usuario") Usuario usuario, BindingResult result,
			RedirectAttributes rtt) {
		if (result.hasErrors()) {
			return new ModelAndView("redirect:/user/add");
		}
		usuarioDao.editar(usuario);
		rtt.addFlashAttribute("message", "Usuário Alterado com sucesso!");
		return new ModelAndView("redirect:/usuario/todos");
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes rtt) {
		usuarioDao.excluir(id);
		rtt.addFlashAttribute("message", "Usuário Excluido com sucesso!");
		return "redirect:/usuario/todos";
	}

}
