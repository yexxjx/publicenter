package securityincident;

import securityincident.view.IncidentView;
import securityincident.view.MainView;

public class AppStart {
    public static void main(String[] args) {
        //MainView.getInstance().index();
        IncidentView.getInstance().incidentMenu();
    }
}
