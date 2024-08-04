package Application.Model;

public class WhiskyMængde {
    private double literMængde;
    private Fad fad;

    public WhiskyMængde(double literMængde, Fad fad) {
        this.literMængde = literMængde;
        this.fad = fad;
    }

    public double getLiterMængde() {
        return literMængde;
    }

    public Fad getFad() {
        return fad;
    }

    @Override
    public String toString() {
        return String.format(literMængde + " liter fra fad: '" + fad.getFadNr() + "' af type: '" + fad.getFadtype() + "'");
    }
}
