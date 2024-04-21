package landforlife.tn.models;

public class demande {

    //att
    private int id;
    private int animal_id;
    private int user_id;
    private String sujet;
    private String details;

    //constructor
    public demande() {

    }

    public demande(int id, int animal_id, int user_id, String sujet, String details) {
        this.id = id;
        this.animal_id = animal_id;
        this.user_id = user_id;
        this.sujet = sujet;
        this.details = details;
    }

    //getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(int animal_id) {
        this.animal_id = animal_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    //toString

    @Override
    public String toString() {
        return "demande{" +
                "id=" + id +
                ", animal_id=" + animal_id +
                ", user_id=" + user_id +
                ", sujet='" + sujet + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
