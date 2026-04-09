package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.application.Application;

/**
 * Panel containing the list of applications.
 */
public class ApplicationListPanel extends UiPart<Region> {
    private static final String FXML = "ApplicationListPanel.fxml";
    private static final int NUM_COLUMNS = 2;

    @FXML
    private GridPane applicationGridPane;

    /**
     * Creates a {@code ApplicationListPanel} with the given {@code ObservableList}.
     */
    public ApplicationListPanel(ObservableList<Application> applicationList) {
        super(FXML);

        for (int i = 0; i < NUM_COLUMNS; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / NUM_COLUMNS);
            applicationGridPane.getColumnConstraints().add(col);
        }

        fillGrid(applicationList);

        applicationList.addListener((ListChangeListener<Application>) change -> {
            fillGrid(applicationList);
        });
    }

    private void fillGrid(ObservableList<Application> applicationList) {
        applicationGridPane.getChildren().clear();

        int col = 0;
        int row = 0;
        for (int i = 0; i < applicationList.size(); i++) {
            ApplicationCard card = new ApplicationCard(applicationList.get(i), i + 1);
            Region cardRoot = card.getRoot();
            cardRoot.getStyleClass().add("application-grid-card");
            applicationGridPane.add(cardRoot, col, row);
            col++;
            if (col == NUM_COLUMNS) {
                col = 0;
                row++;
            }
        }
    }
}
