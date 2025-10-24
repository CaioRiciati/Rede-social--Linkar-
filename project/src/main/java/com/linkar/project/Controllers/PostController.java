package com.linkar.project.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.linkar.project.inum.Visibilidade;
import com.linkar.project.model.Curtida;
import com.linkar.project.model.Post;
import com.linkar.project.model.Usuario;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurtidaRepository curtidaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    private static String UPLOAD_DIR = "src/main/resources/static/uploads/posts/";

    @GetMapping("/novo-post")
    public String mostrarFormularioPost() {
        return "novo-post";
    }

    
    
    
    
    
    
    @PostMapping("/salvar-post")
    public String salvarPost(
            @RequestParam("conteudo") String conteudo,
            @RequestParam("visibilidade") String visibilidade,
            @RequestParam("foto") MultipartFile foto,
            Model model) {

        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fotoUrl = null;
            if (!foto.isEmpty()) {
                String nomeArquivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
                Path caminho = Paths.get(UPLOAD_DIR + nomeArquivo);
                Files.write(caminho, foto.getBytes());
                fotoUrl = "/uploads/posts/" + nomeArquivo;
            }

            Post post = new Post();
            post.setConteudo(conteudo);
            post.setVisibilidade(Visibilidade.valueOf(visibilidade));
            post.setFotoUrl(fotoUrl);

            // Usuário de teste por enquanto
            Usuario usuarioFake = new Usuario();
            usuarioFake.setId(1L);
            post.setUsuario(usuarioFake);

            postRepository.save(post);
            model.addAttribute("mensagem", "Post criado com sucesso!");
            return "redirect:/novo-post";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("mensagem", "Erro ao salvar a imagem!");
            return "novo-post";
        }
    }

    
    
    
    
    
    
    
    @GetMapping("/feed")
    public String feed(Model model) {
    	model.addAttribute("posts", postRepository.findAll());
    	return "feed";
    }
    
    
    
    
    
    
    
    
    @PostMapping("/posts/{id}/curtir")
    @Transactional
    public String curtirPost(@PathVariable Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // Buscar usuário real do banco
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


    @PostMapping("/posts/comentar")
    public String comentar(Model model) {
    	return null;
    }
    
    
}
