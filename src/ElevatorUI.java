import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class ElevatorUI extends JFrame {
    private final ControlSystem controlSystem;
    private final RequestGenerator generator;

    public ElevatorUI(ControlSystem controlSystem, RequestGenerator generator) {
        this.controlSystem = controlSystem;
        this.generator = generator;
        setTitle("Elevator Control System");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(controlSystem.getElevators().size(), 1));


        initUI();
    }

    private void initUI() {
        for (int i = 0; i < 10; i++) { // Assuming 10 floors
            int floor = i;
            JButton button = new JButton("Call Floor " + floor);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controlSystem.requestElevator(floor);
                }
            });
            add(button);
        }

        JLabel[] elevatorLabels = new JLabel[controlSystem.getElevators().size()];
        for (int i = 0; i < elevatorLabels.length; i++) {
            elevatorLabels[i] = new JLabel("Elevator " + i + " at floor: 0");
            add(elevatorLabels[i]);
        }

        new Timer(1000, e -> {
            List<Elevator> elevators = controlSystem.getElevators();
            for (int i = 0; i < elevators.size(); i++) {
                elevatorLabels[i].setText("Elevator " + i + " at floor: " + elevators.get(i).getCurrentFloor());
            }
        }).start();
    }

    public static void main(String[] args) {
        ControlSystem controlSystem = new ControlSystem(3);
        RequestGenerator generator = new RequestGenerator(controlSystem, 10);
        new Thread(generator).start();

        SwingUtilities.invokeLater(() -> {
            ElevatorUI ui = new ElevatorUI(controlSystem, generator);
            ui.setVisible(true);
        });
    }
}
