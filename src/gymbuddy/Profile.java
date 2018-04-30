package gymbuddy;

import java.util.HashSet;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * Stores profile information
 *
 */
public class Profile {

    Set<String> usernameSet = new HashSet<>();
    private String username;

    private int age;

    private String ageCategory;

    private double height;
    private double heightLB;
    private double heightKG;

    enum HeightUnit {
        CM, IN;
    }
    HeightUnit heightUnit;

    private double weight;

    enum WeightUnit {
        LB, KG;
    }
    WeightUnit weightUnit;

    private String weightCategory;

    enum Gender {
        MALE, FEMALE;
    }
    Gender gender;

    private double competitionSquat, competitionBench, competitionDeadlift;
    private double gymSquat, gymBench, gymDeadlift;

    // UNTRAINED, NOVICE, INTERMEDIATE, ADVANCED, ELITE;
    String strength;

    public Profile() {
    }

    public Profile(String username, int age, double weight, int weightUnit, double height, int heightUnit, int gender, double competitionSquat, double competitionBench, double competitionDeadlift, double gymSquat, double gymBench, double gymDeadlift, String strength) {
        this.username = username;
        this.age = age;
        this.weight = weight;

        if (weightUnit == 0) {
            this.weightUnit = WeightUnit.KG;
        } else {
            this.weightUnit = WeightUnit.LB;
        }
        
        if (heightUnit == 0) {
            this.heightUnit = HeightUnit.CM;
        } else {
            this.heightUnit = HeightUnit.IN;
        }

        this.height = height;
        
        if (gender == 0) {
            this.gender = Gender.MALE;
        } else {
            this.gender = Gender.FEMALE;
        }
        
        this.competitionSquat = competitionSquat;
        this.competitionBench = competitionBench;
        this.competitionDeadlift = competitionDeadlift;
        this.gymSquat = gymSquat;
        this.gymBench = gymBench;
        this.gymDeadlift = gymDeadlift;
        this.strength = strength;
    }

    public boolean isUsernameAvailable(String newUsername) {
        return !usernameSet.contains(newUsername);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /*
    *   open (14, 150);
    *   sub_junior (14, 18);
    *   junior (19, 23);
    *   masterI (40, 49);
    *   masterII (50, 59);
    *   masterIII (60, 69);
    *   masterIV (70, 150);
     */
    public String getAgeCategory() {
        if (age >= 14 && age <= 18) {
            ageCategory = "Sub-Junior";
        } else if (age <= 23) {
            ageCategory = "Junior";
        } else if (age <= 39){
            ageCategory = "Open";
        } else if (age <= 49) {
            ageCategory = "Master I";
        } else if (age <= 59) {
            ageCategory = "Master II";
        } else if (age <= 69) {
            ageCategory = "Master III";
        } else {
            ageCategory = "Master IV";
        }
        return ageCategory;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setWeightUnit(int weightUnit) {
        if (weightUnit == 0) {
            this.weightUnit = WeightUnit.KG;
        } else {
            this.weightUnit = WeightUnit.LB;
        }
    }
    
    public void setHeight(double height){
        this.height = height;
    }

    /**
     *
     * Men: up to 53 kg (Sub-Junior/Junior), 59 kg, 66 kg, 74 kg, 83 kg, 93 kg,
     * 105 kg, 120 kg, 120 kg+
     *
     * Women: up to 43 kg (Sub-Junior/Junior), 47 kg, 52 kg, 57 kg, 63 kg, 72
     * kg, 84 kg, 84 kg +
     *
     * @return
     */
    public String getWeightCategory() {
        switch (gender) {
            case MALE:
                if (weight <= 53) {
                    return "53kg";
                } else if (weight <= 59) {
                    return "59kg";
                } else if (weight <= 66) {
                    return "66kg";
                } else if (weight <= 74) {
                    return "74kg";
                } else if (weight <= 83) {
                    return "83kg";
                } else if (weight <= 93) {
                    return "93kg";
                } else if (weight <= 105) {
                    return "105kg";
                } else if (weight <= 120) {
                    return "120kg";
                } else {
                    return "120kg+";
                }
            case FEMALE:
                if (weight <= 43) {
                    return "43kg";
                } else if (weight <= 47) {
                    return "47kg";
                } else if (weight <= 52) {
                    return "52kg";
                } else if (weight <= 57) {
                    return "57kg";
                } else if (weight <= 63) {
                    return "63kg";
                } else if (weight <= 72) {
                    return "72kg";
                } else if (weight <= 84) {
                    return "84kg";
                } else {
                    return "84kg+";
                }
        }
        return weightCategory;
    }

    public String getGender() {
        switch (gender) {
            case MALE:
                return "Male";
            default:
                return "Female";
        }
    }

    public void setGender(int gender) {
        if (gender == 0) {
            this.gender = Gender.MALE;
        } else {
            this.gender = Gender.FEMALE;
        }
    }

    public double getHeight() {
        return height;
    }
    
    public void setHeight() {
        
    }

    public void setHeightUnit(int heightUnit) {
        if (heightUnit == 0) {
            this.heightUnit = HeightUnit.CM;
        } else {
            this.heightUnit = HeightUnit.IN;
        }
    }

    public double getCompSquat() {
        return competitionSquat;
    }

    public void setCompSquat(double competitionSquat) {
        this.competitionSquat = competitionSquat;
    }

    public double getCompBench() {
        return competitionBench;
    }

    public void setCompBench(double competitionBench) {
        this.competitionBench = competitionBench;
    }

    public double getCompDeadlift() {
        return competitionDeadlift;
    }

    public void setCompDeadlift(double competitionDeadlift) {
        this.competitionDeadlift = competitionDeadlift;
    }

    public double getGymSquat() {
        return gymSquat;
    }

    public void setGymSquat(double gymSquat) {
        this.gymSquat = gymSquat;
    }

    public double getGymBench() {
        return gymBench;
    }

    public void setGymBench(double gymBench) {
        this.gymBench = gymBench;
    }

    public double getGymDeadlift() {
        return gymDeadlift;
    }

    public void setGymDeadlift(double gymDeadlift) {
        this.gymDeadlift = gymDeadlift;
    }

    public double getBenchWilks() {
        return wilks(competitionBench);
    }

    public double getSquatWilks() {
        return wilks(competitionSquat);
    }

    public double getDeadliftWilks() {
        return wilks(competitionDeadlift);
    }
    
    public double getWilksTotal() {
        return wilks(competitionBench + competitionSquat + competitionDeadlift);
    }

    // based on this http://i.imgur.com/aRg0C.png
    public String getStrength() {

        double wilksTotal = getWilksTotal();

        if (wilksTotal < 200) {
            strength = "Un-trained";
        } else if (wilksTotal < 238) {
            strength = "Novice";
        } else if (wilksTotal < 326) {
            strength = "Intermediate";
        } else if (wilksTotal < 414) {
            strength = "Advanced";
        } else {
            strength = "Elite";
        }
        return strength;
    }

    /**
     *
     * @param weightLifted
     * @return
     */
    private double wilks(double weightLifted) {

        double wilks;
        double a, b, c, d, e, f;
        a = b = c = d = e = f = 0;

        switch (gender) {
            case MALE:
                a = -216.0475144;
                b = 16.2606339;
                c = -0.002388645;
                d = -0.00113732;
                e = 7.01863E-06;
                f = -1.291E-08;
                break;
            case FEMALE:
                a = 594.31747775582;
                b = -27.23842536447;
                c = 0.82112226871;
                d = -0.00930733913;
                e = 4.731582E-05;
                f = -9.054E-08;
                break;
        }

        wilks = weightLifted * (500 / (a + (b * weight) + (c * Math.pow(weight, 2)) + (d * Math.pow(weight, 3)) + (e * Math.pow(weight, 4)) + (f * Math.pow(weight, 5))));

        return wilks;
    }

}
