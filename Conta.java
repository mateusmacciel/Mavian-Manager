abstract class Conta {
    protected String usuario;
    protected String senha;

    public Conta(String usuario, String senha){
        this.usuario = usuario;
        this.senha = senha;
    }

    public abstract void mostrarConta();
}