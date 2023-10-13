import java.time.Duration;
import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        String sTime = start;
        int a = Integer.parseInt(sTime.split(":")[0]);
        int fTime = a+ duration;
        String time11 = String.valueOf(fTime);
        if(fTime>24){
            fTime = fTime%24;
        }
        if (fTime<10){
            time11 = "0"+fTime;
        }
        String time1= time11+":"+sTime.split(":")[1];
        return time1;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        double weight = (double) importance * (urgent ? 2000 : 1) / duration;
        return weight;

    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        Task otherTask = (Task) o;
        if(this.getFinishTime().split(":")[0].equals(otherTask.getFinishTime().split(":")[0])){
            return Integer.compare(Integer.parseInt(this.getFinishTime().split(":")[1]),Integer.parseInt(otherTask.getFinishTime().split(":")[1]));
        }else {
            return Integer.compare(Integer.parseInt(this.getFinishTime().split(":")[0]),Integer.parseInt(otherTask.getFinishTime().split(":")[0]));
        }

    }
}
