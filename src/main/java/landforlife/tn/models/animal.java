package landforlife.tn.models;

public class animal {
    // Attributes
    private int id;
    private String name;
    private int age;
    private String species;
    private String adoption_status;
    private String description;
    private byte[] image;

    // Constructor
    public animal(String name, int id, int age, String species, String adoption_status, String description, byte[] image) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.species = species;
        this.adoption_status = adoption_status;
        this.description = description;
        this.image = image;
    }

    public animal() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getAdoption_status() {
        return adoption_status;
    }

    public void setAdoption_status(String adoption_status) {
        this.adoption_status = adoption_status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    // ToString
    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", species='" + species + '\'' +
                ", adoption_status='" + adoption_status + '\'' +
                ", description='" + description + '\'' +
                ", image='" + (image != null ? image.length + " bytes" : "null") + '\'' +
                '}';
    }
}
