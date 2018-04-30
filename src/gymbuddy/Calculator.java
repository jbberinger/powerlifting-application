package gymbuddy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Calculator {

    private final double WEIGHTRATIO = 0.45359237;
    private double weightInKilograms;
    private double weightInPounds;
    private static final double[] KILOGRAM_PLATES = {25,20,15,10,5,2.5,1.25};
    private int[] kilogramPlatesToLoad = {0,0,0,0,0,0,0};
    private static final double[] POUND_PLATES = {45,35,25,15,10,5,2.5};
    private int[] poundPlatesToLoad = {0,0,0,0,0,0,0};
    boolean isWeightFromPlates = false; // true if user inputs plates manually

    public double poundsToKilograms(double weight) {
        double kilograms = weight * WEIGHTRATIO;
        weightInKilograms = round(kilograms, 2);
        return weightInKilograms;
    }

    public double kilogramsToPounds(double weight) {
        double pounds = weight / WEIGHTRATIO;
        weightInPounds = round(pounds, 2);
        return weightInPounds;
    }

    private void kilogramPlates() {
        System.out.println("-----calculating kilogram plates from " + weightInKilograms + "kg-----");
        // subtract 20kg in barbell weight here until more customization is added
        double weightRemaining = 0;
        if(weightInKilograms >= 20){
            weightRemaining = Math.rint(weightInKilograms) - 20;
        }
        System.out.println("weight Remaining: " + weightRemaining);

        for (int i = 0; i < KILOGRAM_PLATES.length; i++) {
            int platePairs = 0;
            platePairs = (int)Math.floor((weightRemaining / KILOGRAM_PLATES[i]) / 2);
            System.out.println(KILOGRAM_PLATES[i] + "kg plates:" + (int) (platePairs * 2));
            if (platePairs >= 1) {
                System.out.println("rounded plate pairs: " + platePairs);
                kilogramPlatesToLoad[i] = platePairs * 2;
                weightRemaining = weightRemaining - platePairs * KILOGRAM_PLATES[i] * 2;
                System.out.println("weight remaining: " + weightRemaining + "\n");
            } else {
                kilogramPlatesToLoad[i] = 0;
            }
        }
        System.out.println(Arrays.toString(kilogramPlatesToLoad));
    }

    private void poundPlates() {
        System.out.println("-----calculating pound plates from " + weightInPounds + "lb-----");
        // subtract 45lb in barbell weight here until more customization is added
        double weightRemaining = 0;
        if (weightInPounds >= 45){
            weightRemaining = Math.rint(weightInPounds) - 45;
        }
        for (int i = 0; i < POUND_PLATES.length; i++) {
            int platePairs = 0;
            platePairs = (int) Math.floor((weightRemaining / POUND_PLATES[i]) / 2);
            System.out.println(POUND_PLATES[i] + "lb plates:" + (int) (platePairs * 2));
            if (platePairs >= 1) {
                System.out.println("rounded plate pairs: " + platePairs);
                poundPlatesToLoad[i] = platePairs * 2;
                weightRemaining = weightRemaining - platePairs * POUND_PLATES[i] * 2;
                System.out.println("weight remaining: " + weightRemaining + "\n");
            } else {
                poundPlatesToLoad[i] = 0;
            }
        }
        System.out.println(Arrays.toString(poundPlatesToLoad));
    }

    public int[] getKilogramPlatesToLoad() {
        kilogramPlates();
        return kilogramPlatesToLoad;
    }

    public void setKilogramPlatesToLoad(int[] newKilogramPlatesToLoad) {
        //isWeightFromPlates = true;
        kilogramPlatesToLoad = newKilogramPlatesToLoad;
        weightInKilograms = weightInKilogramsFromPlates();
        //isWeightFromPlates = false;
    }

    public int[] getPoundPlatesToLoad() {
        poundPlates();
        return poundPlatesToLoad;
    }

    public void setPoundPlatesToLoad(int[] newPoundPlatesToLoad) {
        //isWeightFromPlates = true;
        poundPlatesToLoad = newPoundPlatesToLoad;
        weightInPounds = weightInPoundsFromPlates();
        //isWeightFromPlates = false;
    }

    public double getWeightInKilograms() {
        weightInKilograms = weightInKilogramsFromPlates();
        return weightInKilograms;
    }
    
    public void setWeightInKilograms(double newWeight) {
        weightInKilograms = newWeight;
    }

    public double getWeightInPounds() {
        weightInPounds = weightInPoundsFromPlates();
        return weightInPounds;
    }
    
    public void setWeightInPounds(double newWeight) {
        weightInPounds = newWeight;
    }

    // adds the 45lb bar for now
    private double weightInPoundsFromPlates() {
        double weight = 0;
        for (int i = 0; i < POUND_PLATES.length; i++) {
            weight += POUND_PLATES[i] * poundPlatesToLoad[i];
        }
        System.out.println("weightInPoundsFromPlates: " + (weight + 45));
        return weight + 45;
    }

    // adds the 20kg bar for now
    private double weightInKilogramsFromPlates() {
        double weight = 0;
        for (int i = 0; i < KILOGRAM_PLATES.length; i++) {
            weight += KILOGRAM_PLATES[i] * kilogramPlatesToLoad[i];
        }
        System.out.println("weightInKilogramsFromPlates: " + (weight + 20));
        return weight + 20;
    }

    private static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
