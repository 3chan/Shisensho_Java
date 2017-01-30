import java.util.*;

class GameTimer {
    Date startTime;
    Date nowTime;
    long sTime;
    long nTime;

    public void setStartTime() {
        startTime = new Date();
        sTime = startTime.getTime();
    }

    public int getTime() {
        nowTime = new Date();
        nTime = nowTime.getTime();
        return (int)(nTime - sTime);
    }
}