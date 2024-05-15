class ContaMestra extends Conta {
    public ContaMestra(String usuario, String senha){
        super(usuario, senha);
    }

    public void mostrarConta(){
        System.out.println(usuario + senha);
    }
}
