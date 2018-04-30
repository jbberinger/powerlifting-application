/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymbuddy;

/**
 *
 * @author jbber
 */
public class Dimensions {

    private final double barDiameter = 29; // millimeters
    private final double sleeveDiameter = 52;
    private final double relativeSleeveDiameter;
    private final double sleeveLength = 433;
    private final double relativeSleeveLength;
    private final double flangeDiameter = 84; // couldn't find dimensions online, made one up
    private final double relativeFlangeDiameter;
    private final double flangeWidth = 12;
    private final double relativeFlangeWidth;
    private final double relativeSleevePlusFlange; // used to calculate bar length on canvas
    private final double[] plateDiameter = {450, 400, 400, 325, 228, 190, 160};
    private final double[] plateWidth = {26, 22, 22, 22, 21, 16, 12};
    private final double[] relativePlateDiameter = new double[7];
    private final double[] relativePlateWidth = new double[7];

    private final double collarTotalLength = 110;
    private final double collarBigDiameter = 93;
    private final double relativeCollarBigDiameter;
    private final double collarBigLength = collarTotalLength * 0.55;
    private final double relativeCollarBigLength;
    private final double collarSmallDiameter = 73;
    private final double relativeCollarSmallDiameter;
    private final double collarSmallLength = collarTotalLength - collarBigLength;
    private final double relativeCollarSmallLength;

    private final double collarKnobHeight = collarBigDiameter / 4;
    private final double collarKnobLength = collarBigLength * 3/10;
    private final double collarPinHeight = collarKnobHeight * 3/6;
    private final double collarPinLength = collarBigDiameter;
    private final double relativeCollarKnobHeight;
    private final double relativeCollarKnobLength;
    private final double relativeCollarPinHeight;
    private final double relativeCollarPinLength;

    /**
     * Relevant IPF powerlifting bar and kg plate information.
     *
     * Bar Diameter: 29mm, Sleeve Diameter: 52mm, Sleeve Length: 433mm, Flange
     * diameter: ??, Flange Width: 12mm
     *
     * 25kg and 20kg Diameters: 60mm, 15 and under Diameters: 30mm 25kg - red,
     * 20kg - blue, 15kg - yellow, 10kg and under - anything
     *
     * @param relativeBarDiameter
     */
    // This constructor applies relevant dimension ratios based on bar diameter input
    public Dimensions(double relativeBarDiameter) {

        relativeSleeveDiameter = relativeBarDiameter * (sleeveDiameter / barDiameter);
        relativeSleeveLength = relativeBarDiameter * (sleeveLength / barDiameter);
        relativeFlangeDiameter = relativeBarDiameter * (flangeDiameter / barDiameter);
        relativeFlangeWidth = relativeBarDiameter * (flangeWidth / barDiameter);

        for (int i = 0; i < plateDiameter.length; i++) {
            relativePlateDiameter[i] = relativeBarDiameter * (plateDiameter[i] / barDiameter);
        }

        for (int i = 0; i < plateWidth.length; i++) {
            relativePlateWidth[i] = relativeBarDiameter * (plateWidth[i] / barDiameter);
        }

        relativeSleevePlusFlange = relativeSleeveLength + relativeFlangeWidth;

        relativeCollarBigDiameter = relativeBarDiameter * (collarBigDiameter / barDiameter);
        relativeCollarSmallDiameter = relativeBarDiameter * (collarSmallDiameter / barDiameter);
        relativeCollarBigLength = relativeBarDiameter * (collarBigLength / barDiameter);
        relativeCollarSmallLength = relativeBarDiameter * (collarSmallLength / barDiameter);

        relativeCollarKnobHeight = relativeBarDiameter * (collarKnobHeight / barDiameter);
        relativeCollarKnobLength = relativeBarDiameter * (collarKnobLength / barDiameter);
        relativeCollarPinHeight = relativeBarDiameter * (collarPinHeight / barDiameter);
        relativeCollarPinLength = relativeBarDiameter * (collarPinLength / barDiameter);

    }

    public double getSleeveDiameter() {
        return relativeSleeveDiameter;
    }

    public double getSleeveLength() {
        return relativeSleeveLength;
    }

    public double getFlangeDiameter() {
        return relativeFlangeDiameter;
    }

    public double getFlangeWidth() {
        return relativeFlangeWidth;
    }

    public double[] getPlateWidth() {
        return relativePlateWidth;
    }

    public double[] getPlateDiameter() {
        return relativePlateDiameter;
    }

    public double getSleevePlusFlange() {
        return relativeSleevePlusFlange;
    }

    public double getCollarBigDiameter() {
        return relativeCollarBigDiameter;
    }

    public double getCollarSmallDiameter() {
        return relativeCollarSmallDiameter;
    }

    public double getCollarBigLength() {
        return relativeCollarBigLength;
    }

    public double getCollarSmallLength() {
        return relativeCollarSmallLength;
    }

    public double getCollarKnobHeight() {
        return relativeCollarKnobHeight;
    }

    public double getCollarKnobLength() {
        return relativeCollarKnobLength;
    }

    public double getCollarPinHeight() {
        return relativeCollarPinHeight;
    }

    public double getCollarPinLength() {
        return relativeCollarPinLength;
    }

}
