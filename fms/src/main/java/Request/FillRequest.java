package Request;

/**
 * Stores information from a Fill Request
 */
public class FillRequest {
    private String username;
    private int generations;


    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public FillRequest(String username) {
        this.username = username;
        generations = 4;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
