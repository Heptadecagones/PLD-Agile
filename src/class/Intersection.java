package src;

public class Intersection {

    private String id;
    private double longitude;
    private double latitude;

    Intersection()
    {
    }

    Intersection(String i, double longi, double lat)
    {
        this.id = i;
        this.longitude = longi;
        this.latitude = lat;
    }

    public double getLongitude()
    {
        return longitude;
    }

        public double getLatitude()
    {
        return latitude;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String i)
    {
        this.id = i;
    }

        public void setLongitude(double longi)
    {
        this.longitude = longi;
    }

        public void setLatitude(double lat)
    {
        this.latitude = lat;
    }

}