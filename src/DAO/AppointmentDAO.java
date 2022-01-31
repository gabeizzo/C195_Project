package DAO;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.SQLException;

public interface AppointmentDAO {

    public ObservableList<Appointment> getAllAppts() throws SQLException;

}
