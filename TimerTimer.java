import java.util.Timer;
import java.util.TimerTask;

public class TimerTimer {

    int secondsPassed = 60;     // set the start of the Timer.

    Timer myTimer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            if (secondsPassed > 0)
                secondsPassed--;
        }
    };

    public void start() {       // set the time for 1 countdown --> and do it
        myTimer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public int getSeconds() {
        return secondsPassed;
    }

    public void setSeconds() {
        secondsPassed = 0;
    }

    public static void main(String[] args) {
        TimerTimer timerProjekt = new TimerTimer();
        timerProjekt.start();
    }

}
