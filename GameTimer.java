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
        return (int)(nTime - sTime -pTime);  // (Œ»İ - ƒQ[ƒ€ŠJn - ˆê’â~‚µ‚½ŠÔ)
    }

    public void setIsPause(boolean isStart) {
        pauseTime = new Date();
        if (isStart) {
            psTime = pauseTime.getTime();  // ˆê’â~ŠJn‚ğ•Û‘¶
        }
        else {
            pTime += (pauseTime.getTime() - psTime);  // (Œ»İ - ˆê’â~ŠJn) = ˆê’â~‚µ‚½ŠÔ ‚ğˆê’â~’â~‚·‚é–ˆ‚ÉpTime‚É‰ÁZ‚·‚é
        }
    }
}