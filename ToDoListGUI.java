import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoListGUI extends JFrame {
    public static final int MAX_TASKS = 15;
    public int taskCount = 0;
    public String[] titles = new String[MAX_TASKS];
    public String[] dueDateTimes = new String[MAX_TASKS];
    public JTextArea taskDisplay;

    public ToDoListGUI() {
        setTitle("To-Do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(true);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setBounds(20, 20, 150, 30);
        add(addTaskButton);

        JButton viewTaskButton = new JButton("View Tasks");
        viewTaskButton.setBounds(20, 60, 150, 30);
        add(viewTaskButton);

        JButton deleteTaskButton = new JButton("Delete Task");
        deleteTaskButton.setBounds(20, 100, 150, 30);
        add(deleteTaskButton);

        JButton editTaskButton = new JButton("Edit Task");
        editTaskButton.setBounds(20, 140, 150, 30);
        add(editTaskButton);

        JButton searchTaskButton = new JButton("Search Task");
        searchTaskButton.setBounds(20, 180, 150, 30);
        add(searchTaskButton);

        taskDisplay = new JTextArea();
        taskDisplay.setEditable(false);
        taskDisplay.setBounds(200, 20, 260, 420);
        add(taskDisplay);


        addTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskCount < MAX_TASKS) {
                    String task = JOptionPane.showInputDialog(ToDoListGUI.this, "Enter task title:");
                    if (task != null && !task.isEmpty()) {
                        String dueDate = JOptionPane.showInputDialog(ToDoListGUI.this, "Enter due date and time (YYYY-MM-DD HH:MM AM/PM):");
                        titles[taskCount] = task;
                        dueDateTimes[taskCount] = dueDate;
                        taskCount++;
                        refreshDisplay();
                    }
                } else {
                    JOptionPane.showMessageDialog(ToDoListGUI.this, "Task limit reached!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskCount == 0) {
                    JOptionPane.showMessageDialog(ToDoListGUI.this, "No tasks added yet!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    refreshDisplay();
                }
            }
        });

        deleteTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskCount == 0) {
                    JOptionPane.showMessageDialog(ToDoListGUI.this, "No tasks to delete!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String input = JOptionPane.showInputDialog(ToDoListGUI.this, "Enter task number to delete (1 - " + taskCount + "):");
                    try {
                        int taskToDelete = Integer.parseInt(input);
                        if (taskToDelete >= 1 && taskToDelete <= taskCount) {
                            for (int i = taskToDelete - 1; i < taskCount - 1; i++) {
                                titles[i] = titles[i + 1];
                                dueDateTimes[i] = dueDateTimes[i + 1];
                            }
                            titles[taskCount - 1] = null;
                            dueDateTimes[taskCount - 1] = null;
                            taskCount--;
                            refreshDisplay();
                        } else {
                            JOptionPane.showMessageDialog(ToDoListGUI.this, "Invalid task number!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ToDoListGUI.this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        editTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskCount == 0) {
                    JOptionPane.showMessageDialog(ToDoListGUI.this, "No tasks to edit!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String input = JOptionPane.showInputDialog(ToDoListGUI.this, "Enter task number to edit (1 - " + taskCount + "):");
                    try {
                        int taskToEdit = Integer.parseInt(input);
                        if (taskToEdit >= 1 && taskToEdit <= taskCount) {
                            String newTitle = JOptionPane.showInputDialog(ToDoListGUI.this, "Enter new task title:");
                            String newDueDate = JOptionPane.showInputDialog(ToDoListGUI.this, "Enter new due date and time (YYYY-MM-DD HH:MM AM/PM):");
                            titles[taskToEdit - 1] = newTitle;
                            dueDateTimes[taskToEdit - 1] = newDueDate;
                            refreshDisplay();
                        } else {
                            JOptionPane.showMessageDialog(ToDoListGUI.this, "Invalid task number!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ToDoListGUI.this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        searchTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskCount == 0) {
                    JOptionPane.showMessageDialog(ToDoListGUI.this, "No tasks to search!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String searchTitle = JOptionPane.showInputDialog(ToDoListGUI.this, "Enter the title to search:");
                    boolean found = false;
                    StringBuilder searchResults = new StringBuilder("------ Search Results ------\n");
                    for (int i = 0; i < taskCount; i++) {
                        if (titles[i].toLowerCase().contains(searchTitle.toLowerCase())) {
                            searchResults.append("Task ").append(i + 1).append(": Title: ").append(titles[i])
                                         .append(", Due: ").append(dueDateTimes[i]).append("\n");
                            found = true;
                        }
                    }
                    if (found) {
                        JOptionPane.showMessageDialog(ToDoListGUI.this, searchResults.toString(), "Search Results", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ToDoListGUI.this, "No tasks found with the given title.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    public void refreshDisplay() {
        taskDisplay.setText("");
        for (int i = 0; i < taskCount; i++) {
            if (titles[i] != null) {
                taskDisplay.append("Task " + (i + 1) + ": Title: " + titles[i] + ", Due: " + dueDateTimes[i] + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ToDoListGUI();
                
            }
        });
    }
}

