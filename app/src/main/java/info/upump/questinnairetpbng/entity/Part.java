package info.upump.questinnairetpbng.entity;

public class Part {
    private int id;
    private String shortName;
    private String name;

    public Part(String shortName, String name) {
        this.shortName = shortName;
        this.name = name;
    }

    public Part(int id) {
        this.id = id;
    }

    public Part() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
