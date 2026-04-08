/*
Name: Emily Ochmann
Course: CEN 3024C
Date: March 30, 2026

Class Name: GameManagerGUI

This class provides the GUI for the Video Game Collection Manager and allows users to
interact with the system using buttons, text fields, and a table display. The GUI allows users to
add games, view all games, update, delete, search games by title, and load data from a text file.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GameManagerGUI extends JFrame {

    private GameDatabaseService dbService;

    private JTextField titleField;
    private JTextField genreField;
    private JComboBox<String> platformComboBox;
    private JTextField yearField;
    private JTextField ratingField;
    private JTextField searchField;

    private JTable gameTable;
    private DefaultTableModel tableModel;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton loadFileButton;
    private JButton clearButton;
    private JButton showAllButton;

    public GameManagerGUI(GameDatabaseService dbService) {
        this.dbService = dbService;
        initializeGUI();
    }
/*
Method Name: initializeGUI
Purpose: Initializes the main window and layout of the GUI

Parameters: none
Returns: none
 */
    private void initializeGUI() {
        setTitle("Video Game Collection Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(20, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("V I D E O  G A M E  C O L L E C T I O N  M A N A G E R", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 26));
        titleLabel.setForeground(new Color(57, 255, 20));
        titleLabel.setBorder(BorderFactory.createLineBorder(new Color(57, 255, 20), 2));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(57, 255, 20), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = createFormPanel();
        JPanel tablePanel = createTablePanel();

        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);
        addButtonListeners();

        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();

            if (!path.endsWith(".db")) {
                path += ".db";
            }

            if (dbService.connect(path)) {
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to connect to database.");
            }
        }
    }
/*
Method Name: createFormPanel
Purpose: Creates the left-side panel input fields and action buttons

Parameters: none
Returns: JPanel
 */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setForeground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 500));
        javax.swing.border.TitledBorder border =
                BorderFactory.createTitledBorder("Game Details");

        border.setTitleColor(new Color(255, 0, 128)); // neon pink
        border.setTitleFont(new Font("SansSerif", Font.BOLD, 14));

        panel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setForeground(new Color(57, 255, 20));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(titleLabel, gbc);
        gbc.gridy++;
        titleField = new JTextField();
        panel.add(titleField, gbc);

        gbc.gridy++;
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setForeground(new Color(57, 255, 20));
        genreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(genreLabel, gbc);
        gbc.gridy++;
        genreField = new JTextField();
        panel.add(genreField, gbc);

        gbc.gridy++;
        JLabel platformLabel = new JLabel("Platform:");
        platformLabel.setForeground(new Color(57, 255, 20));
        platformLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(platformLabel, gbc);
        gbc.gridy++;

        String[] platforms = {"Xbox", "PlayStation", "PC", "Nintendo"};
        platformComboBox = new JComboBox<>(platforms);
        panel.add(platformComboBox, gbc);

        gbc.gridy++;
        JLabel yearLabel = new JLabel("Release Year:");
        yearLabel.setForeground(new Color(57, 255, 20));
        yearLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(yearLabel, gbc);
        gbc.gridy++;
        yearField = new JTextField();
        panel.add(yearField, gbc);

        gbc.gridy++;
        JLabel ratingLabel = new JLabel("Rating:");
        ratingLabel.setForeground(new Color(57, 255, 20));
        ratingLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(ratingLabel, gbc);
        gbc.gridy++;
        ratingField = new JTextField();
        panel.add(ratingField, gbc);

        gbc.gridy++;
        addButton = new JButton("Add Game");
        panel.add(addButton, gbc);
        addHoverEffect(addButton);

        gbc.gridy++;
        updateButton = new JButton("Update Game");
        panel.add(updateButton, gbc);
        addHoverEffect(updateButton);

        gbc.gridy++;
        deleteButton = new JButton("Delete Game");
        panel.add(deleteButton, gbc);
        addHoverEffect(deleteButton);

        gbc.gridy++;
        clearButton = new JButton("Clear Fields");
        panel.add(clearButton, gbc);
        addHoverEffect(clearButton);


        return panel;
    }
/*
Method Name: createTablePanel
Purpose: Creates the panel that displays the table of video games and includes the search and file loading controls

Parameters: none
Returns: JPanel
 */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        topPanel.add(new JLabel("Search Title:"));
        searchField = new JTextField(15);
        topPanel.add(searchField);

        searchButton = new JButton("Search");
        topPanel.add(searchButton);
        addHoverEffect(searchButton);

        showAllButton = new JButton("Show All");
        topPanel.add(showAllButton);
        addHoverEffect(showAllButton);

        loadFileButton = new JButton("Open Database");
        topPanel.add(loadFileButton);
        addHoverEffect(loadFileButton);

        panel.add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Title", "Genre", "Platform", "Year", "Rating"};
        tableModel = new DefaultTableModel(columns, 0);
        gameTable = new JTable(tableModel);
        gameTable.setRowHeight(25);
        gameTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gameTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        gameTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && gameTable.getSelectedRow() != -1) {

                int row = gameTable.getSelectedRow();

                titleField.setText(tableModel.getValueAt(row, 1).toString());
                genreField.setText(tableModel.getValueAt(row, 2).toString());
                platformComboBox.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                yearField.setText(tableModel.getValueAt(row, 4).toString());
                ratingField.setText(tableModel.getValueAt(row, 5).toString());
            }
        });

        JScrollPane scrollPane = new JScrollPane(gameTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
/*
Method Name: addButtonListeners
Purpose: Attaches event listeners to all buttons to handle user interactions

Parameters: none
Returns: none
 */
private void addButtonListeners() {

    addButton.addActionListener(e -> {
        int id = dbService.getNextGameId();
        VideoGame game = getGameFromFields(id);

        if (game != null) {
            if (dbService.addGame(game)) {
                JOptionPane.showMessageDialog(this, "Game added successfully.");
                refreshTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid game data.");
            }
        }
    });

    clearButton.addActionListener(e -> clearFields());

    deleteButton.addActionListener(e -> {
        int selectedRow = gameTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a game to delete.");
            return;
        }

        int gameId = (int) tableModel.getValueAt(selectedRow, 0);

        if (dbService.deleteGame(gameId)) {
            JOptionPane.showMessageDialog(this, "Game deleted successfully.");
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Game could not be deleted.");
        }
    });

    updateButton.addActionListener(e -> {
        int selectedRow = gameTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a game to update.");
            return;
        }

        int gameId = (int) tableModel.getValueAt(selectedRow, 0);

        VideoGame updatedGame = getGameFromFields(gameId);

        if (updatedGame != null) {

            boolean success = true;

            if (!dbService.updateGame(gameId, 1, updatedGame.getTitle())) success = false;
            if (!dbService.updateGame(gameId, 2, updatedGame.getGenre())) success = false;
            if (!dbService.updateGame(gameId, 3, updatedGame.getPlatform())) success = false;
            if (!dbService.updateGame(gameId, 4, String.valueOf(updatedGame.getReleaseYear()))) success = false;
            if (!dbService.updateGame(gameId, 5, String.valueOf(updatedGame.getRating()))) success = false;

            if (success) {
                JOptionPane.showMessageDialog(this, "Game updated successfully.");
                refreshTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        }
    });

    searchButton.addActionListener(e -> {
        String searchText = searchField.getText().trim();

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a title to search.");
            return;
        }

        tableModel.setRowCount(0);

        for (VideoGame game : dbService.searchByTitle(searchText)) {
            Object[] row = {
                    game.getGameId(),
                    game.getTitle(),
                    game.getGenre(),
                    game.getPlatform(),
                    game.getReleaseYear(),
                    game.getRating()
            };
            tableModel.addRow(row);
        }
    });

    showAllButton.addActionListener(e -> refreshTable());

    loadFileButton.addActionListener(e -> {
        JOptionPane.showMessageDialog(this, "Database already loaded. File loading is no longer used.");
    });
}
/*
Method Name: refreshTable
Purpose: Updates the table display to reflect the current list of video games

Parameters: none
Returns: none
 */
    private void refreshTable() {
        tableModel.setRowCount(0);

        for (VideoGame game : dbService.getAllGames()) {
            Object[] row = {
                    game.getGameId(),
                    game.getTitle(),
                    game.getGenre(),
                    game.getPlatform(),
                    game.getReleaseYear(),
                    game.getRating()
            };
            tableModel.addRow(row);
        }
    }
/*
Method Name: getGameFromFields
Purpose: Retrieves user input from the form fields, validates the data, and creates a VideoGame object

Parameters: gameId
Returns: VideoGame
 */
    private VideoGame getGameFromFields(int gameId) {
        String title = titleField.getText().trim();
        String genre = genreField.getText().trim();
        String platform = (String) platformComboBox.getSelectedItem();

        if (title.isEmpty() || genre.isEmpty() || platform.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title, genre, and platform cannot be empty.");
            return null;
        }

        int year;
        try {
            year = Integer.parseInt(yearField.getText().trim());
            if (year < 1958 || year > 2026) {
                JOptionPane.showMessageDialog(this, "Release year must be between 1958 and 2026.");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Release year must be a valid number.");
            return null;
        }

        double rating;
        try {
            rating = Double.parseDouble(ratingField.getText().trim());
            if (rating < 0 || rating > 10) {
                JOptionPane.showMessageDialog(this, "Rating must be between 0 and 10.");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Rating must be a valid number.");
            return null;
        }

        return new VideoGame(gameId, title, genre, platform, year, rating);
    }
/*
Method Name: clearFields
Purpose: Clears all input fields and resets the table selection

Parameters: none
Returns: none
 */
    private void clearFields() {
        titleField.setText("");
        genreField.setText("");
        platformComboBox.setSelectedIndex(0);
        yearField.setText("");
        ratingField.setText("");
        gameTable.clearSelection();
    }
/*
Method Name: addHoverEffect
Purpose: Adds a hover effect to buttons by changing their appearance when the mouse hovers over them

Parameters: button
Returns: none
 */
    private void addHoverEffect(JButton button) {

        Color normalColor = new Color(57, 255, 20);
        Color hoverColor = new Color(140, 255, 140);

        button.setBackground(normalColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }
}