import java.sql.SQLException;

abstract class Conta {
    public abstract void cadastrarConta(String usuario, String senhaHah) throws SQLException;
    public abstract void cadastrarConta(String email, String senha, String site) throws SQLException;
}
