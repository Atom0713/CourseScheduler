package root;

public class Preference {
    final int preferenceId;
    final int professorId;
    final int[][] preferences;

    public Preference(int preferenceId, int professorId, int[][] preferences) {
        this.preferenceId = preferenceId;
        this.professorId = professorId;
        this.preferences = preferences;
    }

    public int getPreferenceId() {
        return preferenceId;
    }

    public int[][] getPreferences() {
        return preferences;
    }

    public int getProfessorId() {
        return professorId;
    }
}
