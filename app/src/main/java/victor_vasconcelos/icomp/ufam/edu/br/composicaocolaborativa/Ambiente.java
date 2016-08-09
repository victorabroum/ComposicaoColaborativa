package victor_vasconcelos.icomp.ufam.edu.br.composicaocolaborativa;

/**
 * Created by Victor Freitas Vasconcelos on 01/06/2016.
 */
public class Ambiente {

    private int id;
    private String descricao;
    private double latitude, longitude,
                    latitudeB,longitudeB,
                    latitudeC,longitudeC;
    private float raio;
    private int totalPessoas;

    public Ambiente(int id, String descricao,
                    double longitude, double latitude,
                    double latitudeB, double longitudeB,
                    double latitudeC, double longitudeC,
                    float raio, int totalPessoas) {
        this.id = id;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitudeB = latitudeB;
        this.longitudeB = longitudeB;
        this.latitudeC = latitudeC;
        this.longitudeC = longitudeC;
        this.raio = raio;
        this.totalPessoas = totalPessoas;
    }

    public int getTotalPessoas() {
        return totalPessoas;
    }

    public void setTotalPessoas(int totalPessoas) {
        this.totalPessoas = totalPessoas;
    }

    public float getRaio() {
        return raio;
    }

    public void setRaio(float raio) {
        this.raio = raio;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getid() {
        return id;
    }

    public double getLatitudeB() {
        return latitudeB;
    }

    public void setLatitudeB(double latitudeB) {
        this.latitudeB = latitudeB;
    }

    public double getLongitudeB() {
        return longitudeB;
    }

    public void setLongitudeB(double longitudeB) {
        this.longitudeB = longitudeB;
    }

    public double getLatitudeC() {
        return latitudeC;
    }

    public void setLatitudeC(double latitudeC) {
        this.latitudeC = latitudeC;
    }

    public double getLongitudeC() {
        return longitudeC;
    }

    public void setLongitudeC(double longitudeC) {
        this.longitudeC = longitudeC;
    }
}
