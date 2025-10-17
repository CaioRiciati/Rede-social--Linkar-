package com.linkar.project.Controllers;


import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.linkar.project.model.Usuario;
import com.linkar.project.service.CookieService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository ur;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logar")
    public String loginUsuario(Usuario usuario, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        Usuario usuarioLogado = ur.login(usuario.getEmail(), usuario.getSenha());
        if (usuarioLogado != null) {
            CookieService.setCookie(response, "usuarioId", String.valueOf(usuarioLogado.getId()), 10000);
            CookieService.setCookie(response, "nomeUsuario", usuarioLogado.getNome(), 10000);
            return "redirect:/home";
        }

        model.addAttribute("erro", "Usuário ou senha inválidos!");
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
