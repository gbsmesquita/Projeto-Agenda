public class Contato {
    private Long id;
    private String nome;
    private String sobrenome;

    public void setId(Long novoId) {
        this.id = novoId;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public void setSobrenome(String novoSobrenome) {
        this.sobrenome = novoSobrenome;
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }
}
