package root;

import java.util.ArrayList;

public class TimePeriod {
    private final int timePeriodId;
    private final String timePeriod;

    public TimePeriod(int timePeriodId, String timePeriod) {
        this.timePeriodId = timePeriodId;
        this.timePeriod = timePeriod;
    }

    public int getTimePeriodId() {
        return timePeriodId;
    }

    public String getTimePeriodAsString() {
        return timePeriod;
    }

    public static boolean isTimeClashing(int timePeriodId_1, int timePeriodId_2){
        boolean isClashing = false;
        ArrayList<Integer> periods1=new ArrayList<>();
        ArrayList<Integer> periods2=new ArrayList<>();
        while(timePeriodId_1!=0){
            int temp = timePeriodId_1%100;
            periods1.add(temp);
            timePeriodId_1/=100;
        }
        while(timePeriodId_2!=0){
            int temp = timePeriodId_2%100;
            periods2.add(temp);
            timePeriodId_2/=100;
        }
        for (int i = 0; i < periods1.size(); i++) {
            for (int j = 0; j < periods2.size(); j++) {
                if(periods1.get(i)==periods2.get(j)){
                    isClashing=true;
                    break;
                }
            }
            if(isClashing)
                break;
        }
        return isClashing;
    }

    public static String timePeriodIdToString(int timePeriodId){
        String timeString="";
        if(timePeriodId<100){
            int day = timePeriodId/10;
            int timePeriod = timePeriodId%10;
            if(day == 1)
                timeString+="Mon ";
            else if(day == 2)
                timeString+="Tue ";
            else if(day == 3)
                timeString+="Wed ";
            else if(day == 4)
                timeString+="Thu ";
            else if(day == 5)
                timeString+="Fri ";

            if(timePeriod == 1)
                timeString+="9:00 - 10:00";
            else if(timePeriod == 2)
                timeString+="10:00 - 11:00";
            else if(timePeriod == 3)
                timeString+="11:00 - 12:00";
            else if(timePeriod == 4)
                timeString+="12:00 - 13:00";
            else if(timePeriod == 5)
                timeString+="13:00 - 14:00";
            else if(timePeriod == 6)
                timeString+="14:00 - 15:00";
            else if(timePeriod == 7)
                timeString+="15:00 - 16:00";
            else if(timePeriod == 8)
                timeString+="16:00 - 17:00";
            else if(timePeriod == 9)
                timeString+="17:00 - 18:00";
        }
        else if(timePeriodId<10000){
            int day = timePeriodId/1000;
            int timePeriod = timePeriodId/100%10;
            if(day == 1)
                timeString+="Mon ";
            else if(day == 2)
                timeString+="Tue ";
            else if(day == 3)
                timeString+="Wed ";
            else if(day == 4)
                timeString+="Thu ";
            else if(day == 5)
                timeString+="Fri ";

            if(timePeriod == 1)
                timeString+="9:00 - 11:00";
            else if(timePeriod == 2)
                timeString+="10:00 - 12:00";
            else if(timePeriod == 3)
                timeString+="11:00 - 13:00";
            else if(timePeriod == 4)
                timeString+="12:00 - 14:00";
            else if(timePeriod == 5)
                timeString+="13:00 - 15:00";
            else if(timePeriod == 6)
                timeString+="14:00 - 16:00";
            else if(timePeriod == 7)
                timeString+="15:00 - 17:00";
            else if(timePeriod == 8)
                timeString+="16:00 - 18:00";
        }
        else if(timePeriodId<1000000){
            int day = timePeriodId/100000;
            int timePeriod = timePeriodId/10000%10;
            if(day == 1)
                timeString+="Mon ";
            else if(day == 2)
                timeString+="Tue ";
            else if(day == 3)
                timeString+="Wed ";
            else if(day == 4)
                timeString+="Thu ";
            else if(day == 5)
                timeString+="Fri ";

            if(timePeriod == 1)
                timeString+="9:00 - 12:00";
            else if(timePeriod == 2)
                timeString+="10:00 - 13:00";
            else if(timePeriod == 3)
                timeString+="11:00 - 14:00";
            else if(timePeriod == 4)
                timeString+="12:00 - 15:00";
            else if(timePeriod == 5)
                timeString+="13:00 - 16:00";
            else if(timePeriod == 6)
                timeString+="14:00 - 17:00";
            else if(timePeriod == 7)
                timeString+="15:00 - 18:00";
        }
        return timeString;
    }

    public int[][] getTimePeriodAsIndexes(){
        int timePeriodId = this.timePeriodId;
        int n;
        if(timePeriodId<100){
            n=1;
        }
        else if(timePeriodId<10000){
            n=2;
        }
        else{
            n=3;
        }

        int[][] indexes = new int[n][2];
        while (n!=0){
            int i = timePeriodId/10%10-1;
            int j = timePeriodId%10-1;
            timePeriodId/=100;
            indexes[n-1][0]=i;
            indexes[n-1][1]=j;
            n--;
        }
        return indexes;
    }
}
