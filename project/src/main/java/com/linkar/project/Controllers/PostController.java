package com.linkar.project.Controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.linkar.project.Repository.CurtidaRepository;
import com.linkar.project.Repository.PostRepository;
import com.linkar.project.Repository.UsuarioRepository;
import com.linkar.project.inum.Categoria;
import com.linkar.project.model.Curtida;
import com.linkar.project.model.Post;
import com.linkar.project.model.Usuario;
import com.linkar.project.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurtidaRepository curtidaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static String UPLOAD_DIR = "src/main/resources/static/uploads/posts/";

    // -------------------------------------------------------------------------
    // NOVO POST
    // -------------------------------------------------------------------------
    @GetMapping("/novo-post")
    public String mostrarFormularioPost(Model model) {

        model.addAttribute("categorias", Categoria.values());

        return "novo-post";
    }

    @PostMapping("/salvar-post")
    public String salvarPost(
            @RequestParam("conteudo") String conteudo,
            @RequestParam("categoria") String categoria,
            @RequestParam("foto") MultipartFile foto,
            HttpSession session,
            Model model) {

        try {
            // pegar usuário logado
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

            // foto igual seu código atual
            ...
            
            Post post = new Post();
            post.setConteudo(conteudo);
            post.setCategoria(Categoria.valueOf(categoria));
            post.setFotoUrl(fotoUrl);
            post.setUsuario(usuario);

            postRepository.save(post);

            return "redirect:/feed?categoria=TODOS";

        } catch (Exception e) {
            e.printStackTrace();
            return "novo-post";
        }
    }


    // -------------------------------------------------------------------------
    // FEED COM FILTRO
    // -------------------------------------------------------------------------
    @GetMapping("/feed")
    public String feed(
            @RequestParam(defaultValue = "TODOS") String categoria,
            Model model,
            HttpServletRequest request) throws UnsupportedEncodingException {

        String usuarioIdStr = CookieService.getCookie(request, "usuarioId");

        if (usuarioIdStr == null) {
            return "redirect:/login";
        }

        Long usuarioId = Long.parseLong(usuarioIdStr);

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        model.addAttribute("usuario", usuario);

        Categoria catEnum = Categoria.valueOf(categoria);

        List<Post> posts;

        if (catEnum == Categoria.TODOS) {
            posts = postRepository.findAllByOrderByCriadoEmDesc();
        } else {
            posts = postRepository.findByCategoriaOrderByCriadoEmDesc(catEnum);
        }

        model.addAttribute("categoriaAtual", categoria);
        model.addAttribute("posts", posts);

        return "feed";
    }



    // -------------------------------------------------------------------------
    // CURTIR POST
    // -------------------------------------------------------------------------
    @PostMapping("/posts/{id}/curtir")
    @Transactional
    public String curtirPost(@PathVariable Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Usuario usuario = usuarioRepository.findById(1L).orElseThrow();

            boolean jaCurtiu = curtidaRepository.existsByPostAndUsuario(post, usuario);

            if (jaCurtiu) {
                curtidaRepository.deleteByPostAndUsuario(post, usuario);
            } else {
                Curtida curtida = new Curtida();
                curtida.setPost(post);
                curtida.setUsuario(usuario);
                curtidaRepository.save(curtida);
            }
        }

        return "redirect:/feed";
    }

    // -------------------------------------------------------------------------
    // COMENTAR (ainda vai ser implementado)
    // -------------------------------------------------------------------------
    @PostMapping("/posts/comentar")
    public String comentar() {
        return "redirect:/feed";
    }
}
