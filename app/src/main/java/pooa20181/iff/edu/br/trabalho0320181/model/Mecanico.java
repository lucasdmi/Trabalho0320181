package pooa20181.iff.edu.br.trabalho0320181.model;

public class Mecanico {

    private int id;

    private String nome;
    private String funcao;
    private String dataNascimento;
    private String rua;
    private String bairro;
    private String municipio;
    private String latitude;
    private String longitude;



    public Mecanico(){

    }

    public Mecanico(int id, String nome, String funcao, String dataNascimento, String rua, String bairro, String municipio)
    {
        this.id = id;
        this.nome = nome;
        this.funcao = funcao;
        this.dataNascimento = dataNascimento;
        this.rua = rua;
        this.bairro = bairro;
        this.municipio = municipio;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
