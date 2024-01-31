package org.example.entity;


import org.example.exception.NotReLaunchException;

public class Frame {

    private FallenKeel fallenKeel;
    private Roll roll = new Roll();
    private boolean relance = false;
    private int lauchNumber = 1;
    private boolean seriesIsStandard = true;
    public Frame(FallenKeel fallenKeel){
        this.fallenKeel = fallenKeel;
    }

    public boolean isRelance() {
        return relance;
    }

    public void setRelance(boolean relance) {
        this.relance = relance;
    }

    public boolean isSeriesIsStandard() {
        return seriesIsStandard;
    }

    public void setSeriesIsStandard(boolean seriesIsStandard) {
        this.seriesIsStandard = seriesIsStandard;
    }

    public FallenKeel getFallenKeel() {
        return fallenKeel;
    }

    public void setFallenKeel(FallenKeel fallenKeel) {
        this.fallenKeel = fallenKeel;
    }

    public Roll getRoll() {
        return roll;
    }

    public int getLauchNumber() {
        return lauchNumber;
    }

    public void setLauchNumber(int lauchNumber) {
        this.lauchNumber = lauchNumber;
    }

    public void setRoll(Roll roll) {
        this.roll = roll;
    }

    public void updateScore() {

        if (seriesIsStandard) {
            if ( lauchNumber == 1  ){
                roll.setScore(fallenKeel.fallenKeelRandomNumber());

                if (roll.getScore() == 10 ){
                    throw new NotReLaunchException();
                } else {
                    this.relance = true;
                    lauchNumber++;
                }
            } else if (lauchNumber == 2){
                roll.setScore(roll.getScore() + fallenKeel.fallenKeelRandomNumber());
                lauchNumber ++;
                // this.relance = false;
            } else if (lauchNumber == 3 ) {
                System.out.println(fallenKeel.fallenKeelRandomNumber());
                throw new NotReLaunchException();
            }

        } else {
            if ( lauchNumber == 1  ){
                roll.setScore(fallenKeel.fallenKeelRandomNumber());
                this.relance = true;
                lauchNumber++;

            } else if (lauchNumber == 2){

//                if (roll.getScore() == 10 ){
//                    roll.setScore(roll.getScore() + fallenKeel.fallenKeelRandomNumber());
//                    lauchNumber ++;
//                }

                roll.setScore(roll.getScore() + fallenKeel.fallenKeelRandomNumber());
                lauchNumber ++;
                // this.relance = false;
            } else if (lauchNumber == 3 ) {
                roll.setScore(roll.getScore() + fallenKeel.fallenKeelRandomNumber());

            }
        }

    }
}
