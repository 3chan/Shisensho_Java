import java.util.*;

class GameTimer {
    Date startTime;
    Date nowTime;
    Date pauseTime;
    long sTime;
    long nTime;
    long pTime;
    long psTime;

    public void setStartTime() {
        startTime = new Date();
        sTime = startTime.getTime();
    }

    public int getTime() {
        nowTime = new Date();
        nTime = nowTime.getTime();
        return (int) (nTime - sTime - pTime); // (»έ - Q[Jn - κβ~΅½Τ)
    }

    public void setIsPause(boolean isStart) {
        pauseTime = new Date();
        if (isStart) {
            psTime = pauseTime.getTime(); // κβ~JnπΫΆ
        } else {
            pTime += (pauseTime.getTime() - psTime); // (»έ - κβ~Jn) = κβ~΅½Τ πκβ~β~·ιΙpTimeΙΑZ·ι
        }
    }
}