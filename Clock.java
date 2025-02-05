import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

public class Clock extends JFrame {
    SimpleDateFormat timeFormat;
    SimpleDateFormat dayFormat;
    SimpleDateFormat dateFormat;    
    JLabel timeLabel;
    JLabel dayLabel;
    JLabel dateLabel;    
    JLabel stopwatchLabel;
    JButton startButton, stopButton, resetButton;    
    JTextField alarmField;
    JButton setAlarmButton;
    Timer alarmTimer;
    
    String time;
    String day;
    String date;
    
    private Timer stopwatchTimer;
    private int elapsedSeconds = 0;
    private String alarmTime = null; // Store the alarm time

    Clock() {
        this.setTitle("Digital Clock with Stopwatch and Alarm");
        this.setSize(440, 255);
        super.setLocation(600, 350);           
        this.setLayout(new FlowLayout());
        this.setResizable(false);         

        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        timeLabel = new JLabel();    
        timeLabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 50));
        timeLabel.setForeground(new Color(255, 255, 255));
        timeLabel.setBackground(new Color(128, 128, 128));
        timeLabel.setOpaque(true);

        dayFormat = new SimpleDateFormat("EEEE");
        dayLabel = new JLabel();    
        dayLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 25));

        dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        dateLabel = new JLabel();    
        dateLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 35));
        
        stopwatchLabel = new JLabel("Stopwatch: 00:00");
        stopwatchLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        stopwatchLabel.setForeground(new Color(61,58,66));

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");

        // Alarm components
		// hh:mm:ss AM/PM
        alarmField = new JTextField(10);
        setAlarmButton = new JButton("Set Alarm");
        
        this.add(timeLabel);  
        this.add(dayLabel);
        this.add(dateLabel);
        this.add(stopwatchLabel);
        this.add(startButton);
        this.add(stopButton);
        this.add(resetButton);
        this.add(alarmField);
        this.add(setAlarmButton);
        
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setTime();  

        // Button actions for the stopwatch
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startStopwatch();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopStopwatch();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetStopwatch();
            }
        });

        // Action for the Set Alarm button
        setAlarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAlarm();
            }
        });
        
        // Timer to check the alarm
        alarmTimer = new Timer(1000, e -> checkAlarm());
        alarmTimer.start();
    }
    
    public void setTime() {
        Timer clockTimer = new Timer(1000, e -> {
            time = timeFormat.format(Calendar.getInstance().getTime());    
            timeLabel.setText(time);
            
            day = dayFormat.format(Calendar.getInstance().getTime());
            dayLabel.setText(day);
            
            date = dateFormat.format(Calendar.getInstance().getTime());
            dateLabel.setText(date);
        });
        clockTimer.start();
    }
    
    private void startStopwatch() {
        if (stopwatchTimer == null) {
            elapsedSeconds = 0; // Reset seconds on start
            stopwatchTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    elapsedSeconds++;
                    int minutes = elapsedSeconds / 60;
                    int seconds = elapsedSeconds % 60;
                    stopwatchLabel.setText(String.format("Stopwatch: %02d:%02d", minutes, seconds));
                }
            });
            stopwatchTimer.start();
        }
    }

    private void stopStopwatch() {
        if (stopwatchTimer != null) {
            stopwatchTimer.stop();
            stopwatchTimer = null;
        }
    }

    private void resetStopwatch() {
        stopStopwatch();
        elapsedSeconds = 0;
        stopwatchLabel.setText("Stopwatch: 00:00");
    }

    private void setAlarm() {
        alarmTime = alarmField.getText();
        if (!alarmTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Alarm set for: " + alarmTime);
        }
    }

    private void checkAlarm() {
        if (alarmTime != null) {
            String currentTime = timeFormat.format(Calendar.getInstance().getTime());
            if (currentTime.equalsIgnoreCase(alarmTime)) {
                JOptionPane.showMessageDialog(this, "Alarm ringing!");
                alarmTime = null; // Reset alarm after it rings
            }
        }
    }

    public static void main(String[] args) {
        Clock w = new Clock();
    }
}
