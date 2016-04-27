package omscs.edtech.roster;

import omscs.edtech.db.model.Student;
import java.io.File;

public interface RosterReader {
    Student[] readRoster(File file);
}
