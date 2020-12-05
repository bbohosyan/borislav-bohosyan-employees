package logic;

public class ProjectResult {
    private int employee1ID;
    private int employee2ID;
    private int projectID;
    private long daysWorked;

    public ProjectResult(int employee1ID, int employee2ID, int projectID, long daysWorked) {
        this.employee1ID = employee1ID;
        this.employee2ID = employee2ID;
        this.projectID = projectID;
        this.daysWorked = daysWorked;
    }

    public long getDaysWorked() {
        return daysWorked;
    }

    public int getEmployee1ID() {
        return employee1ID;
    }

    public int getEmployee2ID() {
        return employee2ID;
    }

    public int getProjectID() {
        return projectID;
    }
}
