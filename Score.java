class Score {
    int time4compare;
    int[] time;
    String name;
    String[] nameArray;

    Score (int time, String name) {
        this.time = new int[5];
        this.time4compare = time;
        int num  = time;

        for (int i = 0; i < 4; i++) {
            if (i < 2) num = time / 60;
            else num = time % 60;
            if (i % 2 == 0) this.time[i] = num / 10;  // 10‚ÌˆÊ‚¾‚Á‚½‚ç
            else this.time[i] = num % 10;  // 1‚ÌˆÊ‚¾‚Á‚½‚ç
        }

        // •ª•ª0•b•b ‚É‚È‚é‚æ‚¤‚É’²®
        this.time[4] = this.time[3];
        this.time[3] = this.time[2]; 
        this.time[2] = 0;

        this.name = name;
        nameArray = name.split(",");
    }

    int getTime4Compare() {
        return time4compare;
    }

    int[] getTime() {
        return time;
    }

    int getName(int i) {
        return Integer.parseInt(nameArray[i]);
    }
}