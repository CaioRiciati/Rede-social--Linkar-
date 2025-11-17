import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarefas")
public class Tarefas{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String titulo_tarefa;
    private String descricao;
    private Date data;

    //REFAZER NO BANCO O IDUSUARIO E ENUM DE PRIORIDADE, APÓS ISSO ADICIONAR NESSE MODEL


}