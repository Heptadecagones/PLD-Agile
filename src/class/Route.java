

public class Route {

    private String nom;
    private Intersection origine;
    private Intersection destination;
    private double longueur;

    Route()
    {
    }

    Route(String n, Intersection ori, Intersection dest, double l)
    {
        this.name = n;
        this.origin = ori;
        this.destination = dest;
        this.length = l;
    }

    public String obtenirNom()
    {
        return nom;
    }

        public String obtenirOrigine()
    {
        return origine;
    }

        public String obtenirDestination()
    {
        return destination;
    }

    public double obtenirLongueur()
    {
        return longueur;
    }

    public void modifierLongueur(double l)
    {
        this.longueur = l;
    }

        public void modifierOrigine(Intersection ori)
    {
        this.origine = ori;
    }

        public void modifierDestination(Intersection dest)
    {
        this.destination = dest;
    }

        public void modifierNom(String n)
    {
        this.nom = n;
    }
}