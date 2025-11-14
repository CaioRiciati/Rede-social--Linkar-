package com.linkar.project.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.linkar.project.Repository.CurtidaRepository;
import com.linkar.project.Repository.PostRepository;
import com.linkar.project.Repository.UsuarioRepository;
import com.linkar.project.inum.Categoria;
import com.linkar.project.model.Curtida;
import com.linkar.project.model.Post;
import com.linkar.project.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurtidaRepository curtidaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/posts/";

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
            // Garantir pasta
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Foto
            String fotoUrl = null;
            if (!foto.isEmpty()) {
                String nomeArquivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
                Path caminho = Paths.get(UPLOAD_DIR + nomeArquivo);
                Files.write(caminho, foto.getBytes());
                fotoUrl = "/uploads/posts/" + nomeArquivo;
            }

            // Usuário logado
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario == null) {
                return "redirect:/login";
            }

            // Criar post
            Post post = new Post();
            post.setConteudo(conteudo);
            post.setCategoria(Categoria.valueOf(categoria));
            post.setFotoUrl(fotoUrl);
            post.setUsuario(usuario);

            postRepository.save(post);

            return "redirect:/feed?categoria=TODOS";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("mensagem", "Erro ao salvar a imagem!");
            return "novo-post";
        }
    }

    // -------------------------------------------------------------------------
    // FEED
    // -------------------------------------------------------------------------
    @GetMapping("/feed")
    public String feed(
            @RequestParam(defaultValue = "TODOS") String categoria,
            Model model,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        model.addAttribute("usuario", usuario);

        Categoria catEnum = Categoria.valueOf(categoria);
        List<Post> posts = (catEnum == Categoria.TODOS)
                ? postRepository.findAllByOrderByCriadoEmDesc()
                : postRepository.findByCategoriaOrderByCriadoEmDesc(catEnum);

        // adicionar contagem de curtidas em cada post (evita erro no thymeleaf)
       // posts.forEach(p ->
        //        p.setCurtidas(curtidaRepository.countByPost(p))
       // );

        model.addAttribute("categoriaAtual", categoria);
        model.addAttribute("posts", posts);

        return "feed";
    }

    // -------------------------------------------------------------------------
    // CURTIR POST
    // -------------------------------------------------------------------------
    @PostMapping("/posts/{id}/curtir")
    @ResponseBody
    @Transactional
    public Map<String, Object> curtirPost(@PathVariable Long id, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        Post post = postRepository.findById(id).orElseThrow();

        boolean jaCurtiu = curtidaRepository.existsByPostAndUsuario(post, usuario);

        if (jaCurtiu) {
            curtidaRepository.deleteByPostAndUsuario(post, usuario);
        } else {
            Curtida c = new Curtida();
            c.setPost(post);
            c.setUsuario(usuario);
            curtidaRepository.save(c);
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("qtd", curtidaRepository.countByPost(post));

        return resp;
    }
}
