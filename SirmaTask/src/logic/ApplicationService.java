package logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

public class ApplicationService {

    public static ArrayList<ProjectResult> getEmployeesWhoWorkedMost(String fileName) {

        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            LinkedList<Record> records = new LinkedList<>();

            while ((line = bufferedReader.readLine()) != null) {
                String recordRow = line;
                recordRow = recordRow.replaceAll("\\s+", "");
                String[] recordInfo = recordRow.split(",");
                Record record = new Record(recordInfo[0], recordInfo[1], recordInfo[2], recordInfo[3]);
                records.push(record);
            }

            HashMap<Integer, ProjectResult> results = new HashMap<>();
            ArrayList<Integer> projectsID = new ArrayList<>();
            Record recordI;
            Record recordJ;
            for (int i = 0; i < records.size(); i++) {
                recordI = records.get(i);
                for (int j = i + 1; j < records.size(); j++) {
                    recordJ = records.get(j);
                    if (recordI.getProjectID() == recordJ.getProjectID() && recordI.getId() != recordJ.getId()) {
                        Calendar laterDateFrom;
                        if (recordI.getDateFrom().compareTo(recordJ.getDateFrom()) > 0) {
                            laterDateFrom = recordI.getDateFrom();
                        } else {
                            laterDateFrom = recordJ.getDateFrom();
                        }
                        Calendar soonerDateTo;
                        if (recordI.getDateTo().compareTo(recordJ.getDateTo()) > 0) {
                            soonerDateTo = recordJ.getDateTo();
                        } else {
                            soonerDateTo = recordI.getDateTo();
                        }
                        long daysDifference = Duration.between(laterDateFrom.toInstant(), soonerDateTo.toInstant()).toDays();
                        if (daysDifference > 0) {
                            if (results.get(recordI.getProjectID()) == null) {
                                projectsID.add(recordI.getProjectID());
                                results.put(recordI.getProjectID(),
                                        new ProjectResult(recordI.getId(), recordJ.getId(), recordI.getProjectID(), daysDifference));
                            } else if (results.get(recordI.getProjectID()).getDaysWorked() < daysDifference) {
                                results.put(recordI.getProjectID(),
                                        new ProjectResult(recordI.getId(), recordJ.getId(), recordI.getProjectID(), daysDifference));
                            }
                        }
                    }
                }
            }

            ArrayList<ProjectResult> projectList = new ArrayList<>();
            for (int i = 0; i < projectsID.size(); i++) {
                ProjectResult projectResult = results.get(projectsID.get(i));
                projectList.add(projectResult);
            }
            bufferedReader.close();

            return projectList;
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            return new ArrayList<>();
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            return new ArrayList<>();
        }
    }
}
