package org.wladska;

class HalsteadMetrics {
    private final int programLength;
    private final int vocabularySize;
    private final double programVolume;
    private final double difficulty;
    private final double effort;
    private final double timeRequiredToProgram;
    private final double numberOfDeliveredBugs;

    public HalsteadMetrics(int programLength, int vocabularySize, double programVolume, double difficulty,
                           double effort, double timeRequiredToProgram, double numberOfDeliveredBugs) {
        this.programLength = programLength;
        this.vocabularySize = vocabularySize;
        this.programVolume = programVolume;
        this.difficulty = difficulty;
        this.effort = effort;
        this.timeRequiredToProgram = timeRequiredToProgram;
        this.numberOfDeliveredBugs = numberOfDeliveredBugs;
    }

    public int getProgramLength() {
        return programLength;
    }

    public int getVocabularySize() {
        return vocabularySize;
    }

    public double getProgramVolume() {
        return programVolume;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public double getEffort() {
        return effort;
    }

    public double getTimeRequiredToProgram() {
        return timeRequiredToProgram;
    }

    public double getNumberOfDeliveredBugs() {
        return numberOfDeliveredBugs;
    }
}
