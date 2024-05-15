class ContaServico extends Conta {
    private String servico;
    private String site;

    public ContaServico(String usuario, String senha, String servico, String site) {
        super(usuario, senha);
        this.servico = servico;
        this.site = site;
    }

    public void mostrarConta(){
        System.out.println(usuario + senha + servico + site);
    }
}
