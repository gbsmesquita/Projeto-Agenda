public class Telefone {
    private Long id;
    private String ddd; 
    private Long numero;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDDD(String ddd) {
        this.ddd = ddd;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Long getId() {
        return this.id;
    }

    public String getDDD() {
        return this.ddd;
    }

    public Long getNumero() {
        return this.numero;
    }
}
