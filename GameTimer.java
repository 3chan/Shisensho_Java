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
        return (int)(nTime - sTime -pTime);  // (���ݎ��� - �Q�[���J�n���� - �ꎞ��~��������)
    }

    public void setIsPause(boolean isStart) {
        pauseTime = new Date();
        if (isStart) {
            psTime = pauseTime.getTime();  // �ꎞ��~�J�n������ۑ�
        }
        else {
            pTime += (pauseTime.getTime() - psTime);  // (���ݎ��� - �ꎞ��~�J�n����) = �ꎞ��~�������� ���ꎞ��~��~���閈��pTime�ɉ��Z����
        }
    }
}