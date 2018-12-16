package edu.lewisu.cs.zakaryakrumlinde.fantasyfootballprojections;

public class Projection {
    private String name;
    private String standard;
    private String ppr;

    public Projection(String name, String standard, String ppr){
        this.name = name;
        this.standard = standard;
        this.ppr = ppr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getPpr() {
        return ppr;
    }

    public void setPpr(String ppr) {
        this.ppr = ppr;
    }
}
