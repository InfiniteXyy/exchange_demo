package demo;

public class PersonSubs {
    public String name;
    public String shop;

    public PersonSubs(String name, String shop) {
        this.name = name;
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "PersonSubs{" +
                "name='" + name + '\'' +
                ", shop='" + shop + '\'' +
                '}';
    }
}
