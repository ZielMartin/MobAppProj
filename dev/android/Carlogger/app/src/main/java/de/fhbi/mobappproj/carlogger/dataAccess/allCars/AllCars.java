package de.fhbi.mobappproj.carlogger.dataAccess.allCars;

/**
 * Created by martin on 19.01.18.
 */

public class AllCars {
    private String hsn, tsn, cm3, baujahre, ps, name, kraftstoff;


    public AllCars() {

    }

    public String getHsn() {
        return hsn;
    }

    public String getTsn() {
        return tsn;
    }

    public String getCm3() {
        return cm3;
    }

    public String getBaujahre() {
        return baujahre;
    }

    public String getPs() {
        return ps;
    }

    public String getName() {
        return name;
    }

    public String getKraftstoff() {
        return kraftstoff;
    }

    @Override
    public String toString() {
        return "AllCars{" +
                "hsn='" + hsn + '\'' +
                ", tsn='" + tsn + '\'' +
                ", cm3='" + cm3 + '\'' +
                ", baujahre='" + baujahre + '\'' +
                ", ps='" + ps + '\'' +
                ", name='" + name + '\'' +
                ", kraftstoff='" + kraftstoff + '\'' +
                '}';
    }
}
